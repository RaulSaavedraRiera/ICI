package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostsFuzzyMemory {

	private int pacmanLastPosition;
	private MOVE pacmanLastDirection;

	private int pacmanPosConfidence;

	private int pacmanTimeSinceSeen;
	
	private HashMap<Integer, Boolean> activePP;

	private HashMap<String, Double> mem;
	
	private int[] ghostAsignedPP; //PP asignada a cada fantasma, utilizada para dividirse y buscar a mspacman
	
	public GhostsFuzzyMemory() 
	{
		activePP = new HashMap<Integer, Boolean>();
		mem = new HashMap<String, Double>();
		
		pacmanLastPosition = -1;
		pacmanLastDirection = MOVE.NEUTRAL;
		pacmanPosConfidence = 0;
		pacmanTimeSinceSeen = 0;
		
		ghostAsignedPP = new int[4];
	}

	public void getInput(GhostsInput input) {

		if (input.isPacmanVisible())
		{
			pacmanPosConfidence = 100;
			pacmanTimeSinceSeen = 0;
		}
		else 
		{
			pacmanPosConfidence = Integer.max(0, pacmanPosConfidence - 2);
			pacmanTimeSinceSeen++;
		}
			
		mem.put("pacmanPosConfidence", (double) pacmanPosConfidence);

		for (GHOST g : GHOST.values()) {

			
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
	
	public int getGhostAsignedPP(GHOST ghost) 
	{
		return ghostAsignedPP[ghost.ordinal()];
	}
	
	public void setGhostAsignedPP(GHOST ghost, int PPNode) 
	{
		ghostAsignedPP[ghost.ordinal()] = PPNode;
	}
	
	public int getPacmanTimeSinceSeen() {
		return pacmanTimeSinceSeen;
	}
	
	public int getPacmanPosConfidence() {
		return pacmanPosConfidence;
	}
}
