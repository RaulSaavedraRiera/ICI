package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoSecondNearestChasingGhostAction implements Action {
	public GoSecondNearestChasingGhostAction() {
		// TODO Auto-generated constructor stub
	}

    //se tiene que comprobar que haya segundo
	@Override
	public MOVE execute(Game game) {
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), get2NearestchasingGhost(game), game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Go 2º Nearest Chasing Ghost Action";
	}
	
	
	
	int get2NearestchasingGhost(Game game) {
		
		
		GHOST nearest = null, nearest2 = null;
		int currentDistance;
		
		int minDistance = 10000;
		
		
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
		
		minDistance = 10000;
		
		for (Constants.GHOST g : Constants.GHOST.values()) {
			// si no ha sido seleccionado, no e comible y no esta encerrado
			if (g != nearest && game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) == 0) {
				// comprobamos si esta mas cerca que el limite actual
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g),
						game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
				if (currentDistance < minDistance) {
					// en caso correcto lo guardamos
					nearest2 = g;
					minDistance = currentDistance;
				}
			}

		}
		
		

		return game.getGhostCurrentNodeIndex(nearest2);
	}
}
