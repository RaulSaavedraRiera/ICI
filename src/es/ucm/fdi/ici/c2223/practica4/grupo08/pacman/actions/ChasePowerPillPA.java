package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChasePowerPillPA implements Action{
	final int TIME_EDIBLE_GHOST_LIMIT = 6;
	
	MsPacManFuzzyMemory memory;
	
		public ChasePowerPillPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
	       
			int PPindex = getNearestPP(game);
			
			GameView.addLines(game, Color.CYAN, game.getPacmanCurrentNodeIndex(), PPindex);
	        return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestPP(game), game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public String getActionId() {
			return "GoToPPill";
			
		}

		 int getNearestPP(Game game) {
			 int to = -1; int distance = 9999;
			 
			 for (int key:memory.powerPills.keySet()) {
				if(memory.powerPills.get(key))
				{
					 int d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), key, game.getPacmanLastMoveMade());
						if(d < distance)
						{
							to = key;
							distance = d;
						}
				}
				}
			 
			 return to;
		 }
}


