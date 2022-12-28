package es.ucm.fdi.ici.c2223.practica5.grupo08;

import java.util.EnumMap;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts.GhostsCBRengine;
import es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts.GhostsStorageManager;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	GhostsCBRengine cbrEngine;
	GhostsStorageManager edibleStorageManager;
	GhostsStorageManager notEdibleStorageManager;

	public Ghosts() {
		this.edibleStorageManager = new GhostsStorageManager();
		this.notEdibleStorageManager = new GhostsStorageManager();
		cbrEngine = new GhostsCBRengine(edibleStorageManager, notEdibleStorageManager);
	}

	@Override
	public void preCompute(String opponent) {
		cbrEngine.setOpponent(opponent);
		try {
			cbrEngine.configure();
			cbrEngine.preCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postCompute() {
		try {
			cbrEngine.postCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {

		EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

		for (GHOST g : GHOST.values()) {
			// This implementation only computes a new action when MsPacMan is in a
			// junction.
			// This is relevant for the case storage policy
			if (!game.doesGhostRequireAction(g))
				moves.put(g, MOVE.NEUTRAL);

			try {
				GhostsInput input = new GhostsInput(game, g);
				input.parseInput();
				edibleStorageManager.setGame(game);
				notEdibleStorageManager.setGame(game);
				cbrEngine.cycle(input.getQuery());
				MOVE move = cbrEngine.getSolution();
				moves.put(g, move);
			} catch (Exception e) {
				e.printStackTrace();
			}
			moves.put(g, MOVE.NEUTRAL);
		}
	
		return moves;
	}

}
