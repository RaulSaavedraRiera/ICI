package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;
import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChaseEdiblePA implements Action {
	
	MsPacManFuzzyMemory memory;
	private Random rnd = new Random();
	
		public ChaseEdiblePA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
			
			GHOST nearestGhost = getNearestEdibleGhost(game);
			
			if (nearestGhost == null)
				return MOVE.values()[rnd.nextInt(MOVE.values().length)];
			
			GameView.addLines(game, Color.BLUE, game.getPacmanCurrentNodeIndex(), (int)memory.lastPosGhost[nearestGhost.ordinal()]);
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
					(int)memory.lastPosGhost[nearestGhost.ordinal()],
					game.getPacmanLastMoveMade(),
					Constants.DM.PATH);
		}

		@Override
		public String getActionId() {
			return "GoToEdible";
			
		}
		
		private GHOST getNearestEdibleGhost(Game game) {
			
			GHOST ret = null;
			int closestGhostDist = 9999;

			for (GHOST ghostType : GHOST.values()) {
				
				if (memory.lairTimeGhosts[ghostType.ordinal()] <= 0 && memory.edibleTimeGhosts[ghostType.ordinal()] >= 0 && memory.lastPosGhost[ghostType.ordinal()] != -1 && memory.lastPosGhost[ghostType.ordinal()] != memory.LAIR_INDEX) {
				
					int dist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), (int)memory.lastPosGhost[ghostType.ordinal()], game.getPacmanLastMoveMade());
					
					if (dist < closestGhostDist || memory.confidence[ghostType.ordinal()] > memory.confidence[ret.ordinal()] + 15) {
						closestGhostDist = dist;
						ret = ghostType;
					}			
				}
			}
			
			return ret;
		}

		
}
