package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

public class RunAwayNearestChasingGhostNonFollowPA implements RulesAction{

	final int LIMIT_EDIBLE_TIME = 6;
	
		public RunAwayNearestChasingGhostNonFollowPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
			int nearestGhost = getNearestChasingGhost(game);
			
			GameView.addLines(game, Color.RED, game.getPacmanCurrentNodeIndex(), nearestGhost);
	        return game.getApproximateNextMoveAwayFromTarget(
	        		game.getPacmanCurrentNodeIndex(), nearestGhost, game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanRunAwayLNearesGhost->NonFollow";
			
		}
		
		
		int getNearestChasingGhost(Game game) {
			GHOST nearest = null;
			int minDistance = 9999;
			int currentDistance;
			
			for (GHOST g : Constants.GHOST.values()) {
				
				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) <= LIMIT_EDIBLE_TIME && game.getGhostLastMoveMade(g) != game.getPacmanLastMoveMade()) {
					// comprobamos si esta mas cerca que el limite actual
					currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
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
