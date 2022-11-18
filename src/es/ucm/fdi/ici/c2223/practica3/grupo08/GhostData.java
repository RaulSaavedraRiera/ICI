package es.ucm.fdi.ici.c2223.practica3.grupo08;

import pacman.game.Constants.GHOST;

public class GhostData {

	private int[] currentGhostDest;

	public GhostData() {
		currentGhostDest = new int[] { -1, -1, -1, -1 };
	}

	public void reset() {
		currentGhostDest = new int[] { -1, -1, -1, -1 };
	}

	public void setGhostObjective(GHOST ghost, int dest) {
		currentGhostDest[ghost.ordinal()] = dest;
	}

	public int getGhostObjective(GHOST ghost) {
		return currentGhostDest[ghost.ordinal()];
	}
}