package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayToGhostAction implements Action {

	GHOST ghost;

	public RunAwayToGhostAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{
			int thisGhost = game.getGhostCurrentNodeIndex(this.ghost);
			int otherGhost;

			int nearestGhostDist = Integer.MAX_VALUE;
			int nearestNotEdibleGhost = null;
			for (GHOST g : GHOST.values()) {

				if (ghost != g && !game.isGhostEdible(g)) {
					otherGhost = game.getGhostCurrentNodeIndex(g);
					int distance = game.getShortestPathDistance(thisGhost, otherGhost);
					
					nearestGhostDist = Math.min(distance, nearestGhostDist);
					nearestNotEdibleGhost = otherGhost;
				}
			}

			return game.getApproximateNextMoveTowardsTarget(thisGhost,
					nearestNotEdibleGhost, game.getGhostLastMoveMade(ghost), DM.PATH);
		}

		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "runsAwayToGhost";
	}
}
