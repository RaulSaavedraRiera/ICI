package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import java.util.Random;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayLairPA implements RulesAction{
	
	int jailIndex = -1;
	Random rnd = new Random();
	
		public RunAwayLairPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
			if (jailIndex == -1 && game.getGhostLairTime(GHOST.BLINKY) <= 0) 
				jailIndex = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
			
			
			if(jailIndex != -1)
				return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), jailIndex, game.getPacmanLastMoveMade(), DM.PATH);
						
			else 
				return game.getApproximateNextMoveTowardsTarget(
						game.getPacmanCurrentNodeIndex(), game.getPowerPillIndices()[rnd.nextInt(game.getPowerPillIndices().length)], game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanRunAwayLair";
			
		}

		 
}
