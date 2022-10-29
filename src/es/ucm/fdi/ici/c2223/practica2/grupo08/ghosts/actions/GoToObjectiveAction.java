package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoToObjectiveAction implements Action {

    GHOST ghost;
    GhostData gData;
	public GoToObjectiveAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {
        	int thisGhost = game.getGhostCurrentNodeIndex(ghost);
        	int objective = gData.currentGhostDest[ghost.ordinal()];
        	
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
