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

public class RunAwayNearestChasingGhostSightPA implements Action {

	final int LIMIT_EDIBLE_TIME = 5;
	MsPacManFuzzyMemory memory;
	
		public RunAwayNearestChasingGhostSightPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
			
			// Marcar luego toda la fila / columna en rojo o algo
			// GameView.addLines(game, Color.RED, game.getPacmanCurrentNodeIndex(), mostRecentGhostIndex);
	        return game.getApproximateNextMoveTowardsTarget(
	        		game.getPacmanCurrentNodeIndex(), game.getCurrentMaze().graph.length / 2, game.getPacmanLastMoveMade(), DM.PATH);
		}
		
		@Override
		public String getActionId() {
			return "PacmanRunAwayNearestChasingGhostSight";
			
		}
		
}
