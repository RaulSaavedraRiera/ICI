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
	public GoToDirectionAction( GHOST ghost, MOVE move) {
		this.ghost = ghost;
		this.dir = move;
	}

	@Override
	public MOVE execute(Game game) {
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
