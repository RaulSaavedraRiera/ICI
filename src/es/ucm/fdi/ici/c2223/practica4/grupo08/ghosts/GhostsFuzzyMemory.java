package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;

import pacman.game.Constants.GHOST;

public class GhostsFuzzyMemory {

	private int pacmanLastPosition;
	private int pacmanPosConfidence;
	private HashMap<Integer, Boolean> activePP;
	
	private HashMap<String, Double> mem;
	
	public void getInput(GhostsInput input)
	{
		for(GHOST g: GHOST.values()) {
			double conf = pacmanPosConfidence;
			if(input.isVisible(g))
				conf = 100;
			else
				conf = Double.max(0, conf-5);
			
			mem.put("PacmanPosConfidence", conf);	
			mem.put("PacmanPos", (double)pacmanLastPosition);
		}

	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
}
