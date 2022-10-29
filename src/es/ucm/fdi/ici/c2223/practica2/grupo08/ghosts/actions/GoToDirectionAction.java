package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoToDirectionAction implements Action {

    GHOST ghost;
    MOVE dir;
    GhostData gData;
	public GoToDirectionAction( GHOST ghost, MOVE move, GhostData ghostData) {
		this.ghost = ghost;
		this.dir = move;
		gData = ghostData;
	}

	@Override
	public MOVE execute(Game game) {
		gData.currentGhostDest[ghost.ordinal()] = -1;
		
        if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {
        	return dir;
        }
        return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "takes " + dir + " path";
	}

	

}
