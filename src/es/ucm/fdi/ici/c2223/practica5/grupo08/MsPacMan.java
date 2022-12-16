package es.ucm.fdi.ici.c2223.practica5.grupo08;

import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.ici.practica5.grupoXX.mspacman.MsPacManCBRengine;
import es.ucm.fdi.ici.practica5.grupoXX.mspacman.MsPacManInput;
import es.ucm.fdi.ici.practica5.grupoXX.mspacman.MsPacManStorageManager;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	GhostsCBRengine cbrEngine;
	GhostsStorageManager storageManager;
		
	public MsPacMan()
	{		
		this.storageManager = new GhostsStorageManager();
		cbrEngine = new GhostsCBRengine(storageManager);
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
	public MOVE getMove(Game game, long timeDue) {
		
		
		//This implementation only computes a new action when MsPacMan is in a junction. 
		//This is relevant for the case storage policy
		if(!game.isJunction(game.getPacmanCurrentNodeIndex()))
			return MOVE.NEUTRAL;
		
		
		try {
			GhostsInput input = new GhostsInput(game);
			input.parseInput();
			storageManager.setGame(game);
			cbrEngine.cycle(input.getQuery());
			MOVE move = cbrEngine.getSolution();
			return move;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MOVE.NEUTRAL;
	}

}
