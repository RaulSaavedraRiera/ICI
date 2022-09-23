package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.awt.Color;
import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

public class MsPacManRunAway extends PacmanController {

	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();
	@Override
	public MOVE getMove(Game game, long timeDue) {

		int i = 9999;
		int distance;

		GHOST g = null;

		for (GHOST ghostType : GHOST.values()) {

			distance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghostType));
			if (distance < i && distance != 0) {
				g = ghostType;
				i = distance;
			}
		}
		
		
		// Show way to ghosts
				for (Constants.GHOST v : Constants.GHOST.values()) {
					int ghost = game.getGhostCurrentNodeIndex(v);
					int mspacman = game.getPacmanCurrentNodeIndex();
					Color[] colours = { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW };

					if (game.getGhostLairTime(v) <= 0)

						GameView.addPoints(game, colours[g.ordinal()], game.getShortestPath(ghost, mspacman));
				}
		
		if(g != null)
			return  game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g),
					game.getPacmanLastMoveMade(), DM.MANHATTAN);
		else 
			return allMoves[rnd.nextInt(allMoves.length)];
	}
}

