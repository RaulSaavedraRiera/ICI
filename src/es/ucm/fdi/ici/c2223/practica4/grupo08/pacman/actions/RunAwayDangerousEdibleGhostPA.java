package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class RunAwayDangerousEdibleGhostPA implements Action{
	
	final int LIMIT_EDIBLE_TIME = 6;
	
		public RunAwayDangerousEdibleGhostPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
			int ghostIndex = getDangerousGhost(game);
			
			GameView.addLines(game, Color.RED, game.getPacmanCurrentNodeIndex(), ghostIndex);
	        return game.getApproximateNextMoveAwayFromTarget(
	        		game.getPacmanCurrentNodeIndex(), getDangerousGhost(game), game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public String getActionId() {
			return "PacmanRunAwayLDangerousEdibleGhost";
			
		}
		
		
		int getDangerousGhost(Game game) {
			GHOST nearest = null;
			int minDistance = 9999;
			int currentDistance;
			
			for (GHOST g : Constants.GHOST.values()) {
				
				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) > 0 && game.getGhostEdibleTime(g)  <= LIMIT_EDIBLE_TIME) {
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
