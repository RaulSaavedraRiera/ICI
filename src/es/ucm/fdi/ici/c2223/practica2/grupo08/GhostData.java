package es.ucm.fdi.ici.c2223.practica2.grupo08;

import java.util.HashMap;

import pacman.game.Constants.MOVE;

public class GhostData {

	public static HashMap<Integer, HashMap<MOVE, Integer>> junctionMarks;
	
	public static int[] currentGhostDest = new int[] { -1, -1, -1, -1 };
}
