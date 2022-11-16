package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import java.util.Arrays;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseNearestPill implements RulesAction{
	
	
		public ChaseNearestPill() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
	        return game.getApproximateNextMoveTowardsTarget(
	        		game.getPacmanCurrentNodeIndex(), getNextPill(game), game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanGoNearestPill";
			
		}
		
		int getNextPill(Game game) {
		
			
			int nextPill = -1;
			int distance = 99999;
			
			for(int p : game.getActivePillsIndices()) {
				
				int cD;
				try {
					cD = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), p, game.getPacmanLastMoveMade());
				}
				catch(Exception e) {
					cD = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), p);
				}
				
				
				if(cD < distance)
				{
					distance = cD;
					nextPill = p;
				}
			}
			
			
			return nextPill;
		
		}
		

}
