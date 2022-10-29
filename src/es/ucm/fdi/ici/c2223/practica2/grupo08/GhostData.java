package es.ucm.fdi.ici.c2223.practica2.grupo08;

import java.util.HashMap;
import java.util.Map.Entry;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostData {
	
	private final int TICKS_TO_CLEAR = 16;

	public HashMap<Integer, HashMap<MOVE, Integer>> junctionMarks;
	
	public int[] currentGhostDest;
	
	public MOVE[] ghostNextMoves;
	
	public GhostData()
	{
		currentGhostDest = new int[] { -1, -1, -1, -1 };
		
		junctionMarks = new HashMap<Integer, HashMap<MOVE, Integer>>();
		
		ghostNextMoves = new MOVE[] { MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL };
	}
	
	public void reset() 
	{
		currentGhostDest = new int[] { -1, -1, -1, -1 };	
		
		junctionMarks.clear();
	}
	
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
	
	public void setGhostMove(GHOST ghost, MOVE move) 
	{
		ghostNextMoves[ghost.ordinal()] = move;
	}
	
	public MOVE getGhostMove(GHOST ghost) 
	{
		return ghostNextMoves[ghost.ordinal()];
	}
}
