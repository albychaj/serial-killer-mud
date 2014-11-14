package Controller;

import java.util.ArrayList;
import java.util.List;

import MOBs.MOB;
import Players.Player;
import Rooms.RoomCollection;
import Rooms.SceneRoom;
import Rooms.TrappingRoom;

/**
 * 
 * @author Team Alpha-Super-Awesome-Cool-Dynamite-Wolf-Squadron
 *
 */
public class Client 
{
	List<Player> playersInRoom = new ArrayList<Player>();
	List<MOB> mobsInRoom = new ArrayList<MOB>(); 
	SceneRoom exit = new SceneRoom(need args here);
	RoomCollection roomCollection = new RoomCollection(need all 30 rooms here);
	TrappingRoom desert = new TrappingRoom("Desert", "The Desert room is a trapping room, so your chance of escaping is 50/50.", playersInRoom, mobsInRoom, exit, roomCollection);
}
