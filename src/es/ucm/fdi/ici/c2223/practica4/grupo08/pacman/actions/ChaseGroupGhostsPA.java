package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChaseGroupGhostsPA implements Action{
	
	final int MAXTIME_EDIBLEGHOST = 5;
	final int MAXDISTANCE_GHOSTSGROUP = 30;

	MsPacManFuzzyMemory memory;
	
		public ChaseGroupGhostsPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
	       
			GHOST nearestGhost = getGroupEdibleGhosts(game);

			GameView.addLines(game, Color.BLUE, game.getPacmanCurrentNodeIndex(), (int)memory.lastPosGhost[nearestGhost.ordinal()]);
			return game.getApproximateNextMoveTowardsTarget(
					game.getPacmanCurrentNodeIndex(), (int)memory.lastPosGhost[nearestGhost.ordinal()], game.getPacmanLastMoveMade(), DM.PATH);
			
		}

		@Override
		public String getActionId() {
			return "PacmanChaseGroupGhosts";
			
		}
		
		
	private GHOST getGroupEdibleGhosts(Game game) {
		GHOST target = null;
		
		boolean groupFound = false;
		int sizeGroup = -1;

		for (Constants.GHOST g : Constants.GHOST.values()) {
			
			
			if(memory.lairTimeGhosts[g.ordinal()] <= 0 && memory.edibleTimeGhosts[g.ordinal()] > MAXTIME_EDIBLEGHOST)
			{
				int group = 0;
				
				for (Constants.GHOST gO : Constants.GHOST.values()) {
					// puede ser edibletime 0 si da igual que vaya a dejar de ser comible en pocos
					// segundos o sacar la distancia a pacman
					
					//se van a repetir grupo pero da igual pq si es el mismo tamaño no se va a sobreescribir
					if (memory.lairTimeGhosts[gO.ordinal()] <= 0 && memory.edibleTimeGhosts[gO.ordinal()] > MAXTIME_EDIBLEGHOST) {
						try {
							if (game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g),
									game.getGhostCurrentNodeIndex(gO),
									game.getGhostLastMoveMade(g)) <= MAXDISTANCE_GHOSTSGROUP)
								group++;
						} catch (Exception e) {	}
					}
				}
				
				if(!groupFound || group > sizeGroup)
				{
					groupFound = true;
					sizeGroup = group;
					target = g;
				}
			}
			

		}
		
		return target;
		}

		
}
