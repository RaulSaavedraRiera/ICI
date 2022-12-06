package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChaseGhostPA implements Action{

		MsPacManFuzzyMemory memory;
	
		public ChaseGhostPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
	       
			GHOST nearestGhost = getNearestAgressiveGhost(game);
			
			GameView.addLines(game, Color.GREEN, game.getPacmanCurrentNodeIndex(), (int)memory.lastPosGhost[nearestGhost.ordinal()]);
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
					(int)memory.lastPosGhost[nearestGhost.ordinal()],
					game.getPacmanLastMoveMade(),
					Constants.DM.PATH);
			
		}
		
		@Override
		public String getActionId() {
			return "ChaseGHOST";
			
		}
		
		
		private GHOST getNearestAgressiveGhost(Game game) {
			
			GHOST ret = null;
			int closestGhostDist = 9999;

			for (GHOST ghostType : GHOST.values()) {
				
				if (memory.lairTimeGhosts[ghostType.ordinal()] <= 0 && memory.edibleTimeGhosts[ghostType.ordinal()] <= 0) {
				
					int dist = game.getShortestPathDistance((int)memory.lastPosGhost[ghostType.ordinal()], game.getPacmanCurrentNodeIndex(), memory.lastDirectionGhosts[ghostType.ordinal()]);
					
					if (dist < closestGhostDist && memory.lastDirectionGhosts[ghostType.ordinal()] != game.getPacmanLastMoveMade()) {
						closestGhostDist = dist;
						ret = ghostType;
					}			
				}
			}
			
			return ret;
		}

		
}
