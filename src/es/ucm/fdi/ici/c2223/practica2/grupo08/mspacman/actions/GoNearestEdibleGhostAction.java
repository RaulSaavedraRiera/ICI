package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoNearestEdibleGhostAction implements Action {
	public GoNearestEdibleGhostAction() {
		// TODO Auto-generated constructor stub
	}

    
	@Override
	public MOVE execute(Game game) {
		System.out.println("GoNearestEdibleGhostAction");
		
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestEdibleGhost(game), game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Go Nearest Edible Ghost Action";
	}
	
	int getNearestEdibleGhost(Game game) {
		
		
		GHOST nearest = null;
		int minDistance = 10000;
		int currentDistance;
		
		
		for (Constants.GHOST g : Constants.GHOST.values()) {
			// no he puesto un margen de tiempo para ser comido, pero intuyo que esto se ve en la condicion
			if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) > 0) {
				
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g),
						game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
				if (currentDistance < minDistance) {
				
					nearest = g;
					minDistance = currentDistance;
				}
			}

		}
		
		if (nearest == null) return -1;
		return game.getGhostCurrentNodeIndex(nearest);
	}
}
