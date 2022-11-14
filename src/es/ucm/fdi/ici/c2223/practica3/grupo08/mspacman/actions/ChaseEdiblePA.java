package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseEdiblePA implements RulesAction{
	
	
		public ChaseEdiblePA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
			return game.getApproximateNextMoveTowardsTarget(
					game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex( getNearestEdibleGhost(game)), game.getPacmanLastMoveMade(), DM.PATH);
			
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanChaseEdible";
			
		}
		
		private GHOST getNearestEdibleGhost(Game game) {
			
			GHOST ret = null;
			int closestGhostDist = 9999;

			for (GHOST ghostType : GHOST.values()) {
				
				if (game.getGhostLairTime(ghostType) <= 0 && !game.isGhostEdible(ghostType)) {
				
					int dist = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghostType));
					
					if (game.getGhostLairTime(ghostType) <= 0 && !game.isGhostEdible(ghostType) && dist < closestGhostDist
							&& game.getGhostLastMoveMade(ghostType) != game.getPacmanLastMoveMade()) {
						closestGhostDist = dist;
						ret = ghostType;
					}			
				}
			}
			
			return ret;
		}

		
}
