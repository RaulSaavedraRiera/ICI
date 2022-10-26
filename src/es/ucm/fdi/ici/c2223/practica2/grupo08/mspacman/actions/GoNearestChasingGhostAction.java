package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoNearestChasingGhostAction implements Action {
	public GoNearestChasingGhostAction() {
		// TODO Auto-generated constructor stub
	}

    
	@Override
	public MOVE execute(Game game) {
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestchasingGhost(game), game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Go Nearest Chasing Ghost Action";
	}
	
	int getNearestchasingGhost(Game game) {
		
		
		GHOST nearest = null;
		int minDistance = 10000;
		int currentDistance;
		
		// por cada uno de los fantasmas
		for (Constants.GHOST g : Constants.GHOST.values()) {
			// si no ha sido seleccionado, no e comible y no esta encerrado
			if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) == 0) {
				// comprobamos si esta mas cerca que el limite actual
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g),
						game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
				if (currentDistance < minDistance) {
					// en caso correcto lo guardamos
					nearest = g;
					minDistance = currentDistance;
				}
			}

		}

		return game.getGhostCurrentNodeIndex(nearest);
	}
}
