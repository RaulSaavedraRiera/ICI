package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsRandom extends GhostController {
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private MOVE[] allMoves = MOVE.values();
	private Random rnd = new Random();

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		moves.clear();
		for (GHOST ghostType : GHOST.values()) {
//			if(game.wasGhostEaten(ghostType))
//				System.out.println("lair" + game.getGhostLairTime(ghostType));
//			
//			 if(game.getGhostEdibleTime(ghostType) > 0)
//				 System.out.println("edible"+game.getGhostEdibleTime(ghostType));
			 
			if (game.doesGhostRequireAction(ghostType)) {
				moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
			}
		}
		return moves;
	}
}