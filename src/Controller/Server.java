package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

import Commands.Command;
import Commands.DisconnectCommand;
import Commands.ForServerCommand;
import Commands.QuitCommand;
import Commands.SimpleCommandFactory;
import Commands.TellMessageCommand;
import Commands.UpdateClientCommand;
import Commands.UpdateClientsCommand;
import Commands.UpdateCommandsOfClient;
import Enums.Administrators;
import Enums.Commands;
import MOBs.MOB;
import Model.*;
import Players.*;
import Rooms.Room;
import Rooms.SceneRoom;

import javax.swing.Timer;

/**
 * sends and receives commands, etc.
 * 
 * @author Team Alpha-Super-Awesome-Cool-Dynamite-Wolf-Squadron
 *
 */
public class Server 
{
	private ServerSocket socket; // the server socket
	private HashMap<String, ObjectOutputStream> outputs; // map of all connected user's output streams
	
	private List<String> chatMessages; // the chat log
	private SerialKillerMud mud;
	private SimpleCommandFactory factory;
	private Timer t;
	private Timer t2;
	
	public static void main(String [] args)
	{
		new Server();
	}
	
	public Server()
	{
		chatMessages = new ArrayList<String>(); // create the chat log
		outputs = new HashMap<String, ObjectOutputStream>(); // setup the map
		mud = new SerialKillerMud(); // setup the model
		factory = new SimpleCommandFactory();


		t = new Timer(5000, new SayListener());

		t.start();
		t2 = new Timer(20000, new MoveListener());
		t2.start();
		/*t = new Timer();
		mud.updateMOBsOnTimer();*/
		//t.scheduleAtFixedRate(mud.updateMOBsOnTimer(), 25000, 5000);
		try
		{
			// start a new server on port 9001
			socket = new ServerSocket(9001);
			/*String mobs = "";
			for (MOB mob : mud.getListOfMOBs()){
				mobs += mob.getIdentity() + "\n";
			}*/
			System.out.println("MUD Server started on port 9001");
			
			
			// spawn a client accepter thread
			new Thread(new ClientAccepter()).start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	} // end of constructor Server
	
	private class MoveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Random random = new Random();
			
			for(MOB mob : mud.getListOfMOBs()){
				int r = random.nextInt(4);
				switch(r){
				case 0:
					if(mob.getCurrentLocation().hasEast()){
						mob.changeRoom(mob.getCurrentLocation().getEastRoom());
						System.out.println(mob.getIdentity() + "new location: " + mob.getCurrentLocation().getRoomName());
					}
					break;
				case 1:
					if(mob.getCurrentLocation().hasWest()){
						mob.changeRoom(mob.getCurrentLocation().getWestRoom());
						System.out.println(mob.getIdentity() + "new location: " + mob.getCurrentLocation().getRoomName());

					}
					break;
				case 2:
					if(mob.getCurrentLocation().hasNorth()){
						mob.changeRoom(mob.getCurrentLocation().getNorthRoom());
						System.out.println(mob.getIdentity() + "new location: " + mob.getCurrentLocation().getRoomName());

					}
					break;
				case 3:
					if(mob.getCurrentLocation().hasSouth()){
						mob.changeRoom(mob.getCurrentLocation().getSouthRoom());
						System.out.println(mob.getIdentity() + "new location: " + mob.getCurrentLocation().getRoomName());

					}
					break;
				default:
					System.out.println("Didn't move");
					break;
				}
			}
			System.out.println();
		}
		
	}


	private class SayListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			/*Set<String> names = outputs.keySet();
			Object[] usernames = names.toArray();*/
			for(int i = 0; i < 9; i++){				
				for(Player p : mud.getPlayersOnline()){
					System.out.println(mud.getListOfMOBs().get(i).getIdentity());
					addSayMessage(p.getUsername(), mud.getMobCollection().getMessage(), mud.getListOfMOBs().get(i).getCurrentLocation(), mud.getListOfMOBs().get(i).getIdentity());
					
				}
			}
		}
		
	}
	
	/**
	 * This thread listens for and sets up connections to new clients
	 */
	public class ClientAccepter implements Runnable
	{

		@Override
		public void run() 
		{
			try
			{
				while (true)
				{
					// Accept a new client then get its output/input streams.
					Socket s = socket.accept();
					ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(s.getInputStream());
					
					// The first thing the server needs to do is send the client a list of the 
					// current players. This is so when someone is logging in, the system is able 
					// to determine whether the user is already logged in, thereby disallowing 
					// the same user to be logged in multiple times. 
					output.writeObject(mud.getPlayersOnline());
					output.writeObject(mud.getAdministrators());
					// Read in the information of the player associated with this client.
					Player player = (Player)input.readObject();
					
					// Add the player to the MUD. 
					mud.addPlayerToGame(player);
					/*Room toAdd = mud.getRoomCollection().getRoomByName("The Lawn");
					mud.getPlayersOnline().get(mud.getPlayersOnline().size()-1).changeRoom(toAdd);
					System.out.println("Player's location" + player.getLocation().getRoomName());*/
					
					// Map the player's username to the output stream.
					outputs.put(player.getUsername(), output);
					
					// Spawn a thread to handle communication with this client. 
					new Thread(new ClientHandler(input)).start();
					
					// Add a notification message to the chat log
					addMessage(player.getUsername() + " connected");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}	
	} // end of private class ClientAccepter
	
	private class ClientHandler implements Runnable
	{
		private ObjectInputStream input; // the input stream from the client
		
		public ClientHandler(ObjectInputStream input)
		{
			this.input = input;
		}

		@SuppressWarnings("unchecked")
		public void run() 
		{
			try
			{
				while(true)
				{
					// Read a command from the client, execute on the server.
					Command<Server> command = (Command<Server>)input.readObject();
					command.execute(Server.this);
					
					// Terminate if client is disconnecting
					if (command instanceof DisconnectCommand)
					{
						input.close();
						return;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	} // end of private class ClientHandler

	public void addMessage(String message) 
	{
		chatMessages.add(message);
		updateAllClients();
	} // end of method addMessage

	public void updateAllClients()
	{
		// make an UpdatedClientsCommand, write to all connected users
		UpdateClientsCommand update = new UpdateClientsCommand(chatMessages);
		
		try
		{
			for(ObjectOutputStream out : outputs.values())
				out.writeObject(update);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	} // end of method updateClients
	
	public void disconnect(String clientName)
	{
		try
		{
			outputs.get(clientName).close(); // close outputs stream
			outputs.remove(clientName); // remove from map
			Player playerToRemove = null;
			for (Player p : mud.getPlayersOnline())
			{
				if(p.getUsername().equals(clientName))
				{
					playerToRemove = p;
					break;
				}
			}
			mud.getPlayersOnline().remove(playerToRemove);
			
			// add notification message
			addMessage(clientName + " disconnected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	} // end of method disconnect

	public void PrintToClient(String clientName, Commands command, String argument) 
	{
		//print commands in client's right side text area
		if (command == Commands.SHUTDOWN)
			closeAllClientsAndServer(clientName);
		else if (command == Commands.OOC){
			String pl = "";
			for (Player p : mud.getPlayersOnline()){
				pl += p.getLocation().getRoomName() + "\n";
			}
			addMessage(clientName + ": " + argument + pl);
		}
		else if(command == Commands.TELL){
	/*		TellMessageCommand t = new TellMessageCommand(clientName, argument, command);
			ObjectOutputStream out = outputs.get(clientName);
			System.out.println("Clientname: " + clientName + "\nArgument: " + argument);*/
			String receiver = argument.substring(0, argument.indexOf(" "));
			String message = argument.substring(argument.indexOf(" ") + 1);
			
			addTellMessage(clientName, receiver, message);
			/*try {
				out.writeObject(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
		else if(command == Commands.SAY){
			System.out.println("Here");
			List<Player> ps = mud.getPlayersOnline();
			Room currentLocation = new SceneRoom(null, null);
			for (Player p : ps){
				if(p.getUsername() == clientName){
					currentLocation = p.getLocation();
					System.out.println("Current Location: " + p.getLocation().getRoomName());
				}
			}
			addSayMessage(clientName, argument, currentLocation, "");
		}
		
		else
		{
			try
			{
				Command<Client> update = factory.createCommand(mud, command, argument, clientName);
				ObjectOutputStream out = outputs.get(clientName);
				out.writeObject(update);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	

	private void closeAllClientsAndServer(String username)
	{
		username = username.toUpperCase();
		Administrators admin = Administrators.valueOf(username);
		
		switch (admin)
		{
			case ADMIN: 
			
				try
				{
					// send a message to all client on shutdown tell them to  disconnect and close their GUI 
					// When that is done close down server
					// make an UpdatedClientCommand, write to all connected users
					QuitCommand update = new QuitCommand();
					
					
						for(ObjectOutputStream out : outputs.values())
							out.writeObject(update);
					
					
					System.exit(0);
				}
				catch (Exception e)
				{
					//e.printStackTrace();
				}
				
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	private void addSayMessage(String clientName, String argument, Room currentLocation, String mob) {
		if(mob.equals("")){
		String mess = clientName + "to everyone in the room: " + argument;
		List<String> newMessages = new ArrayList<String>();
		for( String m : chatMessages){
			newMessages.add(m);
		}
		newMessages.add(mess);
		UpdateClientSay(clientName, newMessages, currentLocation);
		}
		else{
			String mess = mob + " to those in " + currentLocation.getRoomName() + ": " + argument;
			System.out.println(mess);
			List<String> newMessages = new ArrayList<String>();
			for( String m : chatMessages){
				newMessages.add(m);
			}
			newMessages.add(mess);
			UpdateClientSay(clientName, newMessages, currentLocation);
		}
	}

	
	public void addTellMessage(String messageSender, String messageReceiver, String message) 
	{
		
		String completeMessage = messageSender + " to " + messageReceiver + ": " + message;
		List<String> newMessages = new ArrayList<String>();
		for( String m : chatMessages){
			newMessages.add(m);
		}
		newMessages.add(completeMessage);
			
		//chatMessages.add(completeMessage);
		UpdateClient(messageReceiver, messageSender, newMessages); 
	}

	private void UpdateClient(String messageReceiver, String messageSender, List<String> messages) 
	{
		UpdateClientCommand update = new UpdateClientCommand(messages, messageReceiver, messageSender);
		
		try
		{
			for(String clientName: outputs.keySet()){
				System.out.println(clientName);
				if (clientName.equalsIgnoreCase(messageReceiver) || clientName.equalsIgnoreCase(messageSender)){
					outputs.get(clientName).writeObject(update);
				}
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void UpdateClientSay(String clientName, List<String> newMessages, Room currentLocation) {
		
		
		try
		{
			List<Player> ps = mud.getPlayersOnline();
			List<Player> inRoom = new ArrayList<Player>();
			for(Player p : ps){
				if(p.getLocation() == currentLocation){
					UpdateClientCommand update = new UpdateClientCommand(newMessages, p.getUsername(), clientName);
					outputs.get(p.getUsername()).writeObject(update);
					inRoom.add(p);
				}
			}
			
			for(Player p : inRoom){
				System.out.println(p.getUsername());
			}
			/*for(String clients: outputs.keySet()){
				
				if (clients.getLocation()){
					outputs.get(clientName).writeObject(update);
				}
			}*/
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}



	public void getConfirmation(String item, String player, String clientName) {
		UpdateCommandsOfClient update = new UpdateCommandsOfClient(item, player, clientName);
		try {
			outputs.get(player).writeObject(update);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addPlayer(Player player) {
		mud.getRoomCollection().getRoomByName("The Lawn").addPlayer(player);
	}
	

	/*public void setPlayerStart(Player player) {
		mud.addPlayerToGame(player);
		player.setLocation(mud.getRoomCollection().getRoomByName("The Lawn"));
	}*/
} // end of class Server
