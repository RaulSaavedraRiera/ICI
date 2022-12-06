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

public class RunAwayNearestChasingGhostAnyDirPA implements Action {
	
	final int LIMIT_EDIBLE_TIME = 6;
	MsPacManFuzzyMemory memory;
	
		public RunAwayNearestChasingGhostAnyDirPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {

			int nearestGhost = getNearestChasingGhost(game);
			
			GameView.addLines(game, Color.RED, game.getPacmanCurrentNodeIndex(), nearestGhost);
	        return game.getApproximateNextMoveAwayFromTarget(
	        		game.getPacmanCurrentNodeIndex(), nearestGhost, game.getPacmanLastMoveMade(), DM.PATH);
		}


		@Override
		public String getActionId() {
			return "Runaway";
			
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
