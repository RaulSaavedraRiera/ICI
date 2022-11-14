package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChasePowerPillPA implements RulesAction{
	
	
		public ChasePowerPillPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
	        return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestPP(game), game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanChasePP";
			
		}

		 int getNearestPP(Game game) {
			 int to = -1; int distance = 9999;
			 
			 for (int pill : game.getActivePillsIndices())
				{
					int currentDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());

					if (currentDistance < distance) {
						to = pill;
						distance = currentDistance;
					}
				}
			 
			 
			 return to;
		 }
}
