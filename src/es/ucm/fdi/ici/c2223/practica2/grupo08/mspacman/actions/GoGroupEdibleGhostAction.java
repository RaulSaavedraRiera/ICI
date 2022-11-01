package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoGroupEdibleGhostAction implements Action {
	
	final int MAXTIME_EDIBLEGHOST = 6;
	final int MAXDISTANCE_GHOSTSGROUP = 30;
	
	public GoGroupEdibleGhostAction() {
		// TODO Auto-generated constructor stub
	}

	//hay que hacerlo
    
	@Override
	public MOVE execute(Game game) {
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(getGroupTarget(game)), game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Go Group Ghost Action";
	}
	
	
	GHOST getGroupTarget(Game game)
	{	
		GHOST target = null;
		boolean groupFound = false;
		int sizeGroup = 0;

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
