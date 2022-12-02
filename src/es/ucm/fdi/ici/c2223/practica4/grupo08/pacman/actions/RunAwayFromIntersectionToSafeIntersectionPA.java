package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class RunAwayFromIntersectionToSafeIntersectionPA implements Action {
	
	final int LIMIT_EDIBLE_TIME = 6;
	MsPacManFuzzyMemory memory;
	
		public RunAwayFromIntersectionToSafeIntersectionPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {

			// Estoy en intersección, veo al menos dos fantasmas
			// Si veo una intersección con camino sin fantasmas, coger ese camino
			
			// Hacer solo con direcciones? Puede haber intersección y después fantasma en la misma dir, me come el fantasma antes de llegar yo a la intersección
			// Ir hacia dirección en la que no veo fantasma, ¿o la que estoy más seguro de no tener fantasmas cerca?
			
			int nearestGhost = getNearestChasingGhost(game);
			
			GameView.addLines(game, Color.RED, game.getPacmanCurrentNodeIndex(), nearestGhost);
	        return game.getApproximateNextMoveAwayFromTarget(
	        		game.getPacmanCurrentNodeIndex(), nearestGhost, game.getPacmanLastMoveMade(), DM.PATH);
		}


		@Override
		public String getActionId() {
			return "PacmanRunAwayFromIntersectionToSafeIntersection";
			
		}
		
		
		int getNearestChasingGhost(Game game) {
			GHOST nearest = null;
			int minDistance = 9999;
			int currentDistance;
			
			for (GHOST g : Constants.GHOST.values()) {
				
				if (memory.edibleTimeGhosts[g.ordinal()] <= LIMIT_EDIBLE_TIME) {
					currentDistance = game.getShortestPathDistance((int)memory.lastPosGhost[g.ordinal()], game.getPacmanCurrentNodeIndex(), memory.lastDirectionGhosts[g.ordinal()]);
				
					if (currentDistance < minDistance) {
						// en caso correcto lo guardamos
						nearest = g; 
						minDistance = currentDistance;
					}
				}
				
			}
			return (int)memory.lastPosGhost[nearest.ordinal()];
		}

		
}
