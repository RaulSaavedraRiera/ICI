package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

public class ChaseGroupGhostsPA implements Action{
	
	final int MAXTIME_EDIBLEGHOST = 5;
	final int MAXDISTANCE_GHOSTSGROUP = 30;

	
	
		public ChaseGroupGhostsPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
			GHOST nearestGhost = getGroupEdibleGhosts(game);

			GameView.addLines(game, Color.BLUE, game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(nearestGhost));
			return game.getApproximateNextMoveTowardsTarget(
					game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex( getGroupEdibleGhosts(game)), game.getPacmanLastMoveMade(), DM.PATH);
			
		}

		@Override
		public String getActionId() {
			return "PacmanChaseGroupGhosts";
			
		}
		
		private GHOST getNearestGhost(Game game) {
			int d;
			int distance = 99999;
			GHOST n = null;

			for (Constants.GHOST g : Constants.GHOST.values()) {

				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) > 0) {
					d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g),
							game.getPacmanLastMoveMade());

					if (d < distance) {
						distance = d;
						n = g;
					}
				}

			}

			return n;
		}
	private GHOST getGroupEdibleGhosts(Game game) {
		GHOST target = getNearestGhost(game);
		
		boolean groupFound = false;
		int sizeGroup = -1;

		for (Constants.GHOST g : Constants.GHOST.values()) {
			
			
			if(game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) > MAXTIME_EDIBLEGHOST)
			{
				int group = 0;
				
				for (Constants.GHOST gO : Constants.GHOST.values()) {
					// puede ser edibletime 0 si da igual que vaya a dejar de ser comible en pocos
					// segundos o sacar la distancia a pacman
					
					//se van a repetir grupo pero da igual pq si es el mismo tamaño no se va a sobreescribir
					if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) > MAXTIME_EDIBLEGHOST) {
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
