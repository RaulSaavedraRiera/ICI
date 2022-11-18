package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.util.HashMap;
import java.util.Map.Entry;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

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