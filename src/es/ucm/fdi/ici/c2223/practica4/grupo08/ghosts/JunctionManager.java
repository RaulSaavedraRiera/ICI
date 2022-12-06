package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;
import java.util.Map.Entry;

import pacman.game.Constants;
import pacman.game.Constants.MOVE;

/**
 * @author Rodrigo
 *
 */
public final class JunctionManager {
	
	private final int TICKS_TO_CLEAR = 16;
	 
	private HashMap<Integer, HashMap<MOVE, Integer>> junctionMarks;
	
	/**
	 * Iterates through the map reducing the remaining ticks of each move.
	 * If the remaining ticks reaches 0 removes the entry from the map.
	 */
	public void update() 
	{
		for (HashMap<MOVE, Integer> junctions : junctionMarks.values()) 
		{
			MOVE[] movesToDelete = new MOVE[junctions.size()];
			
			int i = 0;
			for (Entry<MOVE, Integer> entry : junctions.entrySet()) 
			{
				int ticks = entry.getValue();
				
				entry.setValue(ticks - 1);
				
				if (ticks == 0) 
				{
					movesToDelete[i] = entry.getKey();
					
					i++;
				}
	        }
			
			for (int j = 0; j < i; j++) 
			{
				junctions.remove(movesToDelete[j]);
			}
        }
	}
	
	public boolean isDirectionAvailable(int junction, MOVE move) 
	{
		if (junctionMarks.containsKey(junction)) 
		{
			if (junctionMarks.get(junction).containsKey(move)) return false;
		}
		
		return true;
	}
	
	public void markDirection(int junction, MOVE move) 
	{
		if (junctionMarks.containsKey(junction)) 
		{
			junctionMarks.get(junction).put(move, TICKS_TO_CLEAR);
			
			junctionMarks.put(junction, junctionMarks.get(junction));
		}
		
		else 
		{
			HashMap<MOVE, Integer> map = new HashMap<MOVE, Integer>();
			map.put(move, TICKS_TO_CLEAR);
			
			junctionMarks.put(junction, map);
		}
	}
	
	/**
	 * Method that searches for the first direction available in a junction and adds it to the mark hashmap
	 * @param junction Juntion where to check directions
	 * @param moveToCheck Move to check if is available
	 * @param lastMoveMade LastMove made by the entity to check
	 * @return the first available move
	 */
	public MOVE getNextAvailableMove(int junction, MOVE moveToCheck, MOVE lastMoveMade) 
	{
		if (isDirectionAvailable(junction, moveToCheck)) 
		{
			markDirection(junction, moveToCheck);
			
			return moveToCheck;
		}
		
		MOVE move = moveToCheck;
		
		for (MOVE m : Constants.MOVE.values()) 
		{
			if (m != move && m != MOVE.NEUTRAL && m.opposite() != lastMoveMade && isDirectionAvailable(junction, m)) 
			{
				move = m;
			}
		}
		
		markDirection(junction, move);
		
		return move;
	}

	public JunctionManager() {
		super();
		this.junctionMarks = new HashMap<Integer, HashMap<MOVE, Integer>>();
	}
	
	public void reset() 
	{
		this.junctionMarks = new HashMap<Integer, HashMap<MOVE, Integer>>();
	}
}