package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;

import pacman.game.Constants.GHOST;

public class GhostsFuzzyMemory {

	private int pacmanLastPosition;
	private int[] distanceToPacmanLastPosition;

	private int pacmanPosConfidence;
	private HashMap<Integer, Boolean> activePP;

	private HashMap<String, Double> mem;

	public void getInput(GhostsInput input) {

		double conf = pacmanPosConfidence;
		if (input.isPacmanVisible())
			conf = 100;
		else
			conf = Double.max(0, conf - 5);
		
		mem.put("PacmanPosConfidence", conf);
		mem.put("PacmanPos", (double) pacmanLastPosition);

		for (GHOST g : GHOST.values()) {

			mem.put("DistanceToPacman", (double) distanceToPacmanLastPosition[g.ordinal()]);
		}
		
		for (int ppNode : activePP.keySet()) {
			double active = 0;
			
			if (activePP.get(ppNode))
				active = 1;
			
			mem.put("PP" + ppNode, active);
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
	
	public void setPacmanLastDistance(GHOST g, int distance) 
	{
		distanceToPacmanLastPosition[g.ordinal()] = distance;
	}
	
	public int getPacmanLastDistance(GHOST g) 
	{
		return distanceToPacmanLastPosition[g.ordinal()];
	}
}
