package Rooms;

import java.util.*;

import Items.Item;
import Items.ItemCollection;
import MOBs.MOB;
import MOBs.MOBCollection;
import Players.*;

/**
 * 
 * @author Team Alpha-Super-Awesome-Cool-Dynamite-Wolf-Squadron     
 *
 */
public class RoomCollection 
{
	private List<Room> rooms;

	private MOBCollection mobCollection;
	private ItemCollection itemCollection;
		
	private Room entrance;
	private Room lawn, murderCastle, farmhouse, basement, secret, woods, factory, hospital, pool, kitchen;
	private Room attic, jail, policeStation, gasStation, bedroom, cemetery, bank, casino, adventureLand, darkAlley; 
	private Room spain, paris, dubai, airport, theStreets, subway, dinner, doomsville, motel, desert;
	
	public RoomCollection()
	{
		rooms = new ArrayList<Room>();
		itemCollection = new ItemCollection();
		mobCollection = new MOBCollection();
		
		addDefaultRooms();
		setItemsInRooms();
		setMOBsInRooms();
	} 
	
	private void addDefaultRooms()
	{
		//dont have to make all these lists, add items to rooms by saying room.addItem(anItem);
		// Initializing lists for items in all 30 rooms
//		List<Item> itemsInLawn = new ArrayList<Item>();
//		List<Item> itemsInMurderCastle = new ArrayList<Item>();
//		List<Item> itemsInFarmhouse = new ArrayList<Item>();
//		List<Item> itemsInBasement = new ArrayList<Item>();
//		List<Item> itemsInSecret = new ArrayList<Item>();
//		List<Item> itemsInWoods = new ArrayList<Item>();
//		List<Item> itemsInFactory = new ArrayList<Item>();
//		List<Item> itemsInHospital = new ArrayList<Item>();
//		List<Item> itemsInPool = new ArrayList<Item>();
//		List<Item> itemsInKitchen = new ArrayList<Item>();
//		List<Item> itemsInAttic = new ArrayList<Item>();
//		List<Item> itemsInJail = new ArrayList<Item>();
//		List<Item> itemsInPoliceStation= new ArrayList<Item>();
//		List<Item> itemsInGasStation = new ArrayList<Item>();
//		List<Item> itemsInBedroom= new ArrayList<Item>();
//		List<Item> itemsInCemetery = new ArrayList<Item>();
//		List<Item> itemsInBank = new ArrayList<Item>();
//		List<Item> itemsInCasino = new ArrayList<Item>();
//		List<Item> itemsInAdventureLand= new ArrayList<Item>();
//		List<Item> itemsInDarkAlley = new ArrayList<Item>();
//		List<Item> itemsInSpain= new ArrayList<Item>();
//		List<Item> itemsInParis = new ArrayList<Item>();
//		List<Item> itemsInDubai = new ArrayList<Item>();
//		List<Item> itemsInAirport = new ArrayList<Item>();
//		List<Item> itemsInTheStreets = new ArrayList<Item>();
//		List<Item> itemsInSubway = new ArrayList<Item>();
//		List<Item> itemsInDinner = new ArrayList<Item>();
//		List<Item> itemsInDoomsville = new ArrayList<Item>();
//		List<Item> itemsInMotel = new ArrayList<Item>();
//		List<Item> itemsInDesert = new ArrayList<Item>();
		

		
		lawn = new SceneRoom("The Lawn", "small area of dead grass");
		secret = new SceneRoom("Secret Room", "Under Construction");
		woods = new SceneRoom("Dark Woods", "Under Construction");
		basement = new SceneRoom("Basement", "Under Construction");
		murderCastle = new SceneRoom("Murder Castle", "601-603 W. 63rd St. Chicago. Home of Dr. Henry Howard Holmes. Three stories and a block long.");
		farmhouse = new SceneRoom("Wisconsin Farmhouse of Horrors", "Average farmhouse, nothing in particular");
		factory = new SceneRoom("Abandoned Factory", "Under Construction");
		motel = new SceneRoom("Roach Motel", "Under Construction");
		hospital = new SceneRoom("Save Yo Self Hospital", "Under Construction");
		pool = new SceneRoom("Deep Water Pool Area", "Under Construction");
		kitchen = new SceneRoom("Hannibal's Kitchen", "Under Construction");
		attic= new SceneRoom("Creepy Attic", "Under Construction");
		jail = new SceneRoom("Dead End Jail", "Under Construction");
		policeStation = new SceneRoom("Police Station", "Under Construction");
		gasStation = new SceneRoom("Ten Second Stop Gas Station", "Under Construction");
		bedroom = new SceneRoom("Dark Woods", "Under Construction");
		cemetery = new SceneRoom("Memorial Cemetery", "Under Construction");
		bank= new SceneRoom("Kingfield Bank", "Under Construction");
		casino = new SceneRoom("Casino Especial", "Under Construction");
		adventureLand = new SceneRoom("Adventure Land Theme Park", "Under Construction");
		darkAlley= new SceneRoom("Dark Alley", "Under Construction");
		spain = new SceneRoom("Spain", "Under Construction");
		dubai = new SceneRoom("Dubai", "Under Construction");
		paris = new SceneRoom("Paris", "Under Construction");
		airport = new SceneRoom("International Airport", "Under Construction");
		theStreets = new SceneRoom("the Streets of Detroit", "Under Construction");
		subway = new SceneRoom("Chamber Station", "Subway station");
		dinner = new SceneRoom("Late Night Dinner", "Under Construction");
		doomsville = new SceneRoom("Dooms Ville", "Under Construction");
		desert = new SceneRoom("Deadly Desert", "Under Construction");

		
		entrance = lawn;
		
		//placing adjacent rooms/constructing room layout for game and placing items in rooms
		//1
		lawn.setNorthRoom(murderCastle);
//		itemsInLawn.add(itemCollection.getItem(4));
		//2
		secret.setNorthRoom(factory);
		//3
		woods.setNorthRoom(hospital);
		woods.setEastRoom(basement);
		//4
		basement.setNorthRoom(pool);
		basement.setEastRoom(murderCastle);
		basement.setWestRoom(woods);
		//5
		murderCastle.setNorthRoom(kitchen);
		murderCastle.setEastRoom(farmhouse);
		murderCastle.setSouthRoom(lawn);
		murderCastle.setWestRoom(basement);
//		itemsInMurderCastle.add(itemCollection.getItem(0));
//		itemsInMurderCastle.add(itemCollection.getItem(1));
		//6
		farmhouse.setSouthRoom(murderCastle);
//		itemsInFarmhouse.add(itemCollection.getItem(2));
//		itemsInFarmhouse.add(itemCollection.getItem(3));
		//7
		factory.setSouthRoom(secret);
		factory.setEastRoom(motel);
		//8
		motel.setNorthRoom(jail);
		motel.setEastRoom(hospital);
		motel.setSouthRoom(factory);
		//9
		hospital.setNorthRoom(policeStation);
		hospital.setEastRoom(pool);
		hospital.setSouthRoom(woods);
		hospital.setWestRoom(motel);
		//10
	    pool.setNorthRoom(gasStation);
	    pool.setEastRoom(kitchen);
	    pool.setSouthRoom(basement);
	    pool.setWestRoom(hospital);
	    //11
	    kitchen.setNorthRoom(bedroom);
	    kitchen.setEastRoom(attic);
	    kitchen.setSouthRoom(murderCastle);
	    kitchen.setWestRoom(pool);
	    //12
	    attic.setNorthRoom(cemetery);
	    attic.setSouthRoom(farmhouse);
	    kitchen.setWestRoom(kitchen);
	    //13
	    jail.setEastRoom(policeStation);
	    jail.setSouthRoom(motel);
	    //14
	    policeStation.setEastRoom(gasStation);
	    policeStation.setSouthRoom(hospital);
	    policeStation.setWestRoom(jail);
	    //15
	    gasStation.setNorthRoom(bank);
	    gasStation.setEastRoom(bedroom);
	    gasStation.setSouthRoom(pool);
	    gasStation.setWestRoom(policeStation);
	    //16
	    bedroom.setNorthRoom(casino);
	    bedroom.setEastRoom(cemetery);
	    bedroom.setSouthRoom(attic);
	    bedroom.setWestRoom(gasStation);
	    //17
	    cemetery.setNorthRoom(adventureLand);
	    cemetery.setSouthRoom(attic);
	    cemetery.setWestRoom(bedroom);
	    //18
	    bank.setEastRoom(casino);
	    bank.setSouthRoom(gasStation);
	    //19
	    casino.setNorthRoom(darkAlley);
	    casino.setEastRoom(adventureLand);
	    casino.setSouthRoom(bedroom);
	    casino.setWestRoom(bank);
	    //20
	    adventureLand.setSouthRoom(cemetery);
	    adventureLand.setWestRoom(casino);
	    //21
	    darkAlley.setNorthRoom(theStreets);
	    darkAlley.setSouthRoom(casino);
	    //22
	    spain.setNorthRoom(airport);
	    //23
	    paris.setEastRoom(airport);
	    //24
	    dubai.setSouthRoom(airport);
	    //25
	    airport.setNorthRoom(dubai);
	    airport.setEastRoom(theStreets);
	    airport.setSouthRoom(spain);
	    airport.setWestRoom(paris);
	    //26
	    theStreets.setEastRoom(subway);
	    theStreets.setSouthRoom(darkAlley);
	    theStreets.setWestRoom(airport);
	    //27
	    subway.setNorthRoom(doomsville);
	    subway.setEastRoom(dinner);
	    subway.setWestRoom(theStreets);
	    //28
	    dinner.setNorthRoom(desert);
	    dinner.setWestRoom(subway);
	    //29
	    doomsville.setEastRoom(desert);
	    doomsville.setSouthRoom(subway);
	    //30
	    desert.setSouthRoom(dinner);
	    desert.setWestRoom(doomsville);
	    
	    
		rooms.add(lawn);
		rooms.add(secret);
		rooms.add(woods);
		rooms.add(basement);
		rooms.add(murderCastle);
		rooms.add(farmhouse);
		rooms.add(adventureLand);
		rooms.add(motel);
		rooms.add(hospital);
		rooms.add(pool);
		rooms.add(kitchen);
		rooms.add(attic);
		rooms.add(jail);
		rooms.add(policeStation);
		rooms.add(gasStation);
		rooms.add(bedroom);
		rooms.add(cemetery);
		rooms.add(bank);
		rooms.add(casino);
		rooms.add(adventureLand);
		rooms.add(darkAlley);
		rooms.add(spain);
		rooms.add(paris);
		rooms.add(dubai);
		rooms.add(airport);
		rooms.add(theStreets);
		rooms.add(subway);
		rooms.add(dinner);
		rooms.add(doomsville);
		rooms.add(desert);
		
	}
	
	public void setItemsInRooms(){
		
	}
	
	public void setMOBsInRooms(){
		
	}
	
	public ItemCollection getItemCollection(){
		return itemCollection;
	}
	
	
	public Room getRoomAt(int index)
	{
		return rooms.get(index);
	}
	
	public Room getRoomByName(String name){
		for(Room room : rooms){
			if(room.getRoomName().equals(name))
				return room;
		}
		return null;
	}
	
	public void addNewPlayerToRooms(Player player)
	{
		entrance.addPlayer(player);
		
		// Make sure that the player is holding a reference
		// to the room it is located in. In this case, that
		// would be the entrance to the game. 
		player.setLocation(entrance);
	}
	
//	public void setRoomsPlayerList(List<Player> players, int index)
//	{
//		switch(index)
//		{
//		case 0:
//			playersInMurderCastle = players;
//			break;
//		case 1:
//			playersInFarmhouse = players;
//			break;
//		case 2:
//			playersInLawn = players;
//		}
//	}
//	
//	public boolean isItemInRoom(Room room, Item item)
//	{
//		
//		
//		String name = room.getRoomName().toLowerCase();
//		
//			for(Item i : lawn.getItems())
//			{
//				if(i.getName() == item.getName())
//				{
//					return lawn;
//				}
//			}
//		
//			for(Item i : murderCastle.getItems())
//			{
//				if(i.getName() == item.getName())
//				{
//					return murderCastle;
//				}
//			}
//		
//			for(Item i : farmhouse.getItems())
//			{
//				if(i.getName() == item.getName())
//				{
//					return farmhouse;
//				}
//			}
//	
//		
//		return null;
//	}
	
}
