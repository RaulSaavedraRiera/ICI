package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoToObjectiveAction implements Action {

    GHOST ghost;
	public GoToObjectiveAction( GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
        if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {
        	int thisGhost = game.getGhostCurrentNodeIndex(ghost);
        	int objective = GhostData.currentGhostDest[ghost.ordinal()];
        	
			return game.getApproximateNextMoveTowardsTarget(thisGhost,
					objective, game.getGhostLastMoveMade(ghost), DM.PATH);
        }
        return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "goesToObjective";
	}

	

}
