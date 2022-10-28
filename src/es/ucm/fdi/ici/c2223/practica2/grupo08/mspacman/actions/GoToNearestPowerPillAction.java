package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class GoToNearestPowerPillAction implements Action {
	
	final int TIMELIMIT_EDIBLEGHOSTS = 12;
	
	public GoToNearestPowerPillAction() {
		// TODO Auto-generated constructor stub
	}

    
	@Override
	public MOVE execute(Game game) {
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getSecurePill(game), game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Go Tonearest PowerPill Action";
	}
	
	int getSecurePill(Game game) {
		 int pacmanPos = game.getPacmanCurrentNodeIndex(); MOVE lastMove = game.getPacmanLastMoveMade();
		int bestValid = -1;
		int distance = -1;
		int[] pacmanToPill;
		int[] ghostToPacman;
		int[] ghostToPill;
		
		// para cada pildora
		for (int p : game.getActivePowerPillsIndices()) {
			boolean valid = true;

			// comprobamos que no se toque con fantasmas
			for (Constants.GHOST g : Constants.GHOST.values()) {

				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < TIMELIMIT_EDIBLEGHOSTS) {

					pacmanToPill = game.getShortestPath(pacmanPos, p, lastMove);
					
					ghostToPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos,
							game.getGhostLastMoveMade(g));
					ghostToPill = game.getShortestPath(game.getGhostCurrentNodeIndex(g), p,
							game.getGhostLastMoveMade(g));

					if (collisionRoute(pacmanToPill, ghostToPacman) || collisionRoute(pacmanToPill, ghostToPill)) {
						valid = false;
						break;
					}
				}
			}

			if (valid && (bestValid == -1 || distance > game.getShortestPathDistance(pacmanPos, p, lastMove))) {
				bestValid = p;
				distance = game.getShortestPathDistance(pacmanPos, p, lastMove);
			}
		}

		return bestValid;
	}
	
	boolean collisionRoute(int[] route, int[] otherRoute) {

		// para cada punto en la ruta
		for (int i = 0; i < route.length; i++)
			// comprobamos con los puntos de la otra ruta
			for (int j = 0; j < otherRoute.length; j++)

				if (route[i] == otherRoute[j] && j <= i)
					return true;

		return false;
	}
}
