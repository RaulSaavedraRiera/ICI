package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseGhostPA implements RulesAction{
	
	
		public ChaseGhostPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
	        return MOVE.NEUTRAL;
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanChaseGhost";
			
		}

		
}
