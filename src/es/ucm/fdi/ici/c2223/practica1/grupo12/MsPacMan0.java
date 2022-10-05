package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.awt.Color;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

public class MsPacMan0 extends PacmanController {

	int limit = 100;	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		int pacManPos = game.getPacmanCurrentNodeIndex();
		
		GHOST nearest = getNearestChasingGhost(game, pacManPos, limit);
		
		if(nearest != null) // si hay fantasma peligroso		
			return game.getNextMoveAwayFromTarget(pacManPos, game.getGhostCurrentNodeIndex(nearest), game.getPacmanLastMoveMade(), DM.PATH);
		
		
		nearest = getNearestEdibleGhost(game, pacManPos, limit);
		
		if(nearest != null) //si hay fantasma comible
			return game.getNextMoveTowardsTarget(pacManPos, game.getGhostCurrentNodeIndex(nearest), game.getPacmanLastMoveMade(), DM.PATH);
		
		
		return game.getNextMoveTowardsTarget(pacManPos, getNearestPill(game, pacManPos), DM.PATH); //la pildora mas cercana
	}
	
	
	public GHOST getNearestChasingGhost(Game game, int currentPos, int limit)
	{
		GHOST nearest = null;
		
		
		int currentDistance;
		
		//por cada uno de los fantasmas
		for (Constants.GHOST ghostType : Constants.GHOST.values()) {
			//si es del tipo indicado
			if(!game.isGhostEdible(ghostType))
			{
				//comprobamos si esta mas cerca que el limite actual
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), currentPos, game.getGhostLastMoveMade(ghostType));
				if (currentDistance < limit && currentDistance != 0) {
					//en caso correcto lo guardamos
					nearest = ghostType;
					limit = currentDistance;
				}
			}
			
		}
		
		if(nearest != null && game.getGhostLairTime(nearest) <= 0)		
				GameView.addPoints(game, Color.YELLOW, game.getShortestPath(game.getGhostCurrentNodeIndex(nearest), game.getPacmanCurrentNodeIndex()));
		
		return nearest;
	}
	
	public GHOST getNearestEdibleGhost(Game game, int currentPos, int limit) {

		GHOST nearest = null;

		int currentDistance;

		// por cada uno de los fantasmas
		for (Constants.GHOST ghostType : Constants.GHOST.values()) {
			// si es del tipo indicado
			if (game.isGhostEdible(ghostType)) {
				// comprobamos si esta mas cerca que el limite actual
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), currentPos,
						game.getGhostLastMoveMade(ghostType));
				if (currentDistance < limit && currentDistance != 0) {
					// en caso correcto lo guardamos
					nearest = ghostType;
					limit = currentDistance;
				}
			}

		}
		
		if(nearest != null && game.getGhostLairTime(nearest) <= 0)		
			GameView.addPoints(game, Color.YELLOW, game.getShortestPath(game.getGhostCurrentNodeIndex(nearest), game.getPacmanCurrentNodeIndex()));

		return nearest;
	}
	
	public int getNearestPill(Game game, int pacManPos)
	{
		
		//variables para controlar las posiciones
		int distance = 9999, to = -1, currentDistance;
		
		for(int pill : game.getActivePillsIndices()) //comprobamos para las pill cual es la mas cercana
		{
			currentDistance = 
					game.getShortestPathDistance(pacManPos, pill, game.getPacmanLastMoveMade());
			
			if(currentDistance < distance)
			{
				to = pill;
				distance = currentDistance;
			}
		}
		
		for(int pill : game.getActivePowerPillsIndices()) // y lo mismo para los powerPills
		{
			currentDistance = 
					game.getShortestPathDistance(pacManPos, pill, game.getPacmanLastMoveMade());
			
			if(currentDistance < distance)
			{
				to = pill;
				distance = currentDistance;
			}
		}
		
		//devolvemos la pill mas cercana		
			return to;
	}

}
