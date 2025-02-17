package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions;

import java.util.ArrayList;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChaseAction implements Action {

	GHOST ghost;
	GhostData gData;

	public ChaseAction(GHOST ghost, ArrayList<GhostData> ghostData) {
		this.ghost = ghost;
		gData = ghostData.get(0);
	}

	@Override
	public MOVE execute(Game game) {
		gData.setGhostObjective(ghost, -1);

		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{
			//System.out.println(ghost.name() + ", persigo a pacman");
			
			return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
					game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghost), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "chases";
	}

}