package es.ucm.fdi.ici.c2223.practica2.grupo08;

import java.util.HashMap;
import java.util.Map.Entry;

import pacman.game.Constants.MOVE;

public class GhostData {

	public static HashMap<Integer, HashMap<MOVE, Integer>> junctionMarks;
	
	public static int[] currentGhostDest = new int[] { -1, -1, -1, -1 };
	
	public static void update() 
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
}
