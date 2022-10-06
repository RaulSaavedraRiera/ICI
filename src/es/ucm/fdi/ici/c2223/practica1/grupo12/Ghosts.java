package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;

public class Ghosts extends GhostController {
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private MOVE[] allMoves = MOVE.values();
	private Random rnd = new Random();
	int limit = 30;
	
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		//limpiamos movimientos
		moves.clear();
		
		//para cada uno de los fantasmas
		for (Constants.GHOST ghostType : Constants.GHOST.values()) {
			//si requieren accion
			if (game.doesGhostRequireAction(ghostType)) {
				
				//obtenemos la posiciond e pacman actual
				int pacManPos = game.getPacmanCurrentNodeIndex();
				
				//si puede ser comido o pacman esta cerca de una powerPill huimos
				if(game.isGhostEdible(ghostType) || pacManNearToPill(game, pacManPos))
					moves.put(ghostType, game.getNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), 
						game.getGhostLastMoveMade(ghostType), DM.PATH));
				
				//caso contrario avanzamos a por ella o aleatoriamente segun rnd
				else 
				{					
					if(rnd.nextFloat(0, 1) <= 0.9)
						moves.put(ghostType, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), 
								game.getGhostLastMoveMade(ghostType), DM.PATH));
					else
						moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);					
				}
			}
		}
		return moves;
	}
	
	Boolean pacManNearToPill(Game game, int pacManPos)
	{
		
		int distance = 9999, currentDistance;
		
		for(int pill : game.getActivePowerPillsIndices()) // y lo mismo para los powerPills
		{
			currentDistance = 
					game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());
			
			if(currentDistance < distance)					
				distance = currentDistance;
			
		}
		
		
		
		return distance <= limit;
	}

}
