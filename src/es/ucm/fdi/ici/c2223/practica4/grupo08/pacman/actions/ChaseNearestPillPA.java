package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.util.Arrays;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseNearestPillPA implements Action{
	
	
		public ChaseNearestPillPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
	        return game.getApproximateNextMoveTowardsTarget(
	        		game.getPacmanCurrentNodeIndex(), getNextPill(game), game.getPacmanLastMoveMade(), DM.PATH);
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
