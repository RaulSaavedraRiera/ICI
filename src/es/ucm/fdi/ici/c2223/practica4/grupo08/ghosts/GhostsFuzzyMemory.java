package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostsFuzzyMemory {

	private int pacmanLastPosition;
	private MOVE pacmanLastDirection;

	private int pacmanPosConfidence;
	
	private HashMap<Integer, Boolean> activePP;

	private HashMap<String, Double> mem;
	
	public GhostsFuzzyMemory() 
	{
		activePP = new HashMap<Integer, Boolean>();
		mem = new HashMap<String, Double>();
		
		pacmanLastPosition = 0;
		pacmanLastDirection = MOVE.UP;
		pacmanPosConfidence = 0;
	}

	public void getInput(GhostsInput input) {

		double conf = pacmanPosConfidence;
		if (input.isPacmanVisible())
			conf = 100;
		else
			conf = Double.max(0, conf - 5);
		
		mem.put("pacmanPosConfidence", conf);
		//mem.put("pacmanPos", (double) pacmanLastPosition);

		for (GHOST g : GHOST.values()) {

			
		}
		
		for (int ppNode : activePP.keySet()) {
			double active = 0;
			
			if (activePP.get(ppNode))
				active = 1;
			
			//mem.put("PP" + ppNode, active);
		}

	}

	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}

	public int getPacmanLastPosition() {
		return pacmanLastPosition;
	}
	
	public void setPacmanLastPosition(int pacmanLastPosition) {
		this.pacmanLastPosition = pacmanLastPosition;
	}
	
	public boolean isPPActive(int node) 
	{
		return activePP.get(node);
	}
	
	public void setPPActive(int node, boolean active) 
	{
		activePP.put(node, active);
	}
	
	public boolean PPEntryExists(int node) 
	{
		return activePP.containsKey(node);
	}

	public MOVE getPacmanLastDirection() {
		return pacmanLastDirection;
	}
	
	public void setPacmanLastDirection(MOVE pacmanLastDirection) {
		this.pacmanLastDirection = pacmanLastDirection;
	}
}
