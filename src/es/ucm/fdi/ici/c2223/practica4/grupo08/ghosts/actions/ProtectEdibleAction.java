package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ProtectEdibleAction implements Action {

	GHOST ghost;

	public ProtectEdibleAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public String getActionId() {
		return ghost + "protectsEdible";
	}

	@Override
	public MOVE execute(Game game) {

		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{
			int thisGhost = game.getGhostCurrentNodeIndex(this.ghost);
			int otherGhost;

			int nearestGhostDist = Integer.MAX_VALUE;
			int nearestEdibleGhost = -1;
			for (GHOST g : GHOST.values()) {

				if (ghost != g && game.isGhostEdible(g)) {
					otherGhost = game.getGhostCurrentNodeIndex(g);
					int distance = game.getShortestPathDistance(thisGhost, otherGhost);

					nearestGhostDist = Math.min(distance, nearestGhostDist);
					nearestEdibleGhost = otherGhost;
				}
			}

			return game.getApproximateNextMoveTowardsTarget(thisGhost, nearestEdibleGhost,
					game.getGhostLastMoveMade(ghost), DM.PATH);
		}

		return MOVE.NEUTRAL;
	}
}
