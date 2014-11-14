package Rooms;

import java.util.List;
import java.util.Random;

import MOBs.MOB;
import Players.Player;

public abstract class TrappingRoom 
{

	protected String name;
	protected String description;
	protected List<Player> players;
	protected List<MOB> mobs;
	protected RoomCollection roomCollection;
	protected SceneRoom onlyWayOut;
	
	
	public TrappingRoom(String name, String description, List<Player> players, List<MOB> mobs, SceneRoom onlyWayOut, RoomCollection roomCollection)
	{
		this.name = name;
		this.description = description;
		this.players = players;
		this.mobs = mobs;
		this.roomCollection = roomCollection;
		this.onlyWayOut = onlyWayOut;
		
	}
	//the room is escapable if the randomly generated value is
	//even. If it's not escapable, they will die in the room.
	public boolean canEscape()
	{
		Random ran = new Random();
		int generatedVal = ran.nextInt(10);
		if(generatedVal % 2 == 0)
		{
			return true;
		}
		else
			return false;
	}
}