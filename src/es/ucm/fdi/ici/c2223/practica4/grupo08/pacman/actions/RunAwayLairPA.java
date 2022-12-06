package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;
import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class RunAwayLairPA implements Action{
	
	int jailIndex = -1;
	Random rnd = new Random();
	MsPacManFuzzyMemory memory;
	
		public RunAwayLairPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
			
				GameView.addLines(game, Color.RED, game.getPacmanCurrentNodeIndex(), game.getCurrentMaze().lairNodeIndex);
				return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getCurrentMaze().lairNodeIndex, game.getPacmanLastMoveMade(), DM.PATH);
						
		}
		
		@Override
		public String getActionId() {
			return "RunawayLAIR";
			
		}

		 
}
