package Model;

import java.util.ArrayList;
import java.util.List;

import Rooms.*;
import Items.Item;
import MOBs.*;
import Players.*;

/**
 * 
 * @author Team Alpha-Super-Awesome-Cool-Dynamite-Wolf-Squadron
 *
 */
public class SerialKillerMud
{
	private RoomCollection roomCollection;
	private transient List<Player> playersOnline;
	private transient List<Player> administrators;
	
	public SerialKillerMud()
	{
		roomCollection = new RoomCollection();
		playersOnline = new ArrayList<Player>();
		administrators = new ArrayList<Player>();
		addAdmins();
	}

	private void addAdmins()
	{
		Player adminZero = new Player("damaris", "0000");
		Player adminOne = new Player("alby", "1111");
		Player adminThree = new Player("alexa", "3333" );
		Player adminNine = new Player("lisa", "9999");
		
		administrators.add(adminZero);
		administrators.add(adminOne);
		administrators.add(adminThree);
		administrators.add(adminNine);
	}

	public void addPlayerToGame(Player player)
	{		
		// Add the new player to the collection of existing players
		playersOnline.add(player);
		
		// Add the player to the rooms. Initially, every new player
		// will start out in the same location. 
		roomCollection.addPlayerToRooms(player);
	}
	
	public List<Player> getPlayersOnline()
	{
		return playersOnline;
	}
	
	public List<Player> getAdministrators()
	{
		return administrators;
	}
	
	public RoomCollection getRoomCollection(){
		return roomCollection;
	}
	
} // end of class SerialKillerMud
