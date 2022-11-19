package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChasePowerPillPA implements RulesAction{
	final int TIME_EDIBLE_GHOST_LIMIT = 6;
	
		public ChasePowerPillPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
			int PPindex = getNearestPPValid(game);
			
			GameView.addLines(game, Color.CYAN, game.getPacmanCurrentNodeIndex(), PPindex);
	        return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestPPValid(game), game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanChasePP";
			
		}

		 int getNearestPPValid(Game game) {
			 int to = -1; int distance = 9999;
			 
			 for (int pill : game.getActivePillsIndices())
				{
				 int d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());
				 boolean valid = true;
				 for (GHOST g : GHOST.values()) {
						if(game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) >= TIME_EDIBLE_GHOST_LIMIT)
							if (game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill, game.getGhostLastMoveMade(g)) <= d){
								valid = false;
								break;
							}
					}
					if (valid && d < distance) {
						to = pill;
						distance = d;
					}
				}
			 
			 return to;
		 }
}


