package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class CheckAvailableDirectionAction implements Action {

    GHOST ghost;
    GhostData gData;
	public CheckAvailableDirectionAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}

	@Override
	public MOVE execute(Game game) {
		gData.setGhostObjective(ghost, -1);
		
		gData.setGhostMove(ghost, game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
                game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghost), DM.PATH));
		
        return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "checksDirectionAvailable";
	}

	

}
