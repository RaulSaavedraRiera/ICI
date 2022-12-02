package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.awt.Color;
import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class PacManRandom extends PacmanController {

	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();

	@Override
	public MOVE getMove(Game game, long timeDue) {

		//show lines to powerPills
//		int[] activePowerPills = game.getActivePowerPillsIndices();
//
//		for (int i = 0; i < activePowerPills.length; i++)
//			GameView.addLines(game, Color.CYAN, game.getPacmanCurrentNodeIndex(), activePowerPills[i]);

		// Show way to ghosts
//		for (Constants.GHOST g : Constants.GHOST.values()) {
//			int ghost = game.getGhostCurrentNodeIndex(g);
//			int mspacman = game.getPacmanCurrentNodeIndex();
//			Color[] colours = { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW };
//
//			if (game.getGhostLairTime(g) <= 0)
//
//				GameView.addPoints(game, colours[g.ordinal()], game.getShortestPath(ghost, mspacman));
//		}
		 
		return allMoves[rnd.nextInt(allMoves.length)];
	}

}
