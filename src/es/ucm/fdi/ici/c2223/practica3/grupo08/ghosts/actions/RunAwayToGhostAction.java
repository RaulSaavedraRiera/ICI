package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayToGhostAction implements Action {

	GHOST ghost;
    GhostData gData;
	public RunAwayToGhostAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}

	@Override
	public MOVE execute(Game game) {
		gData.setGhostObjective(ghost, -1);
		
		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{
			int thisGhost = game.getGhostCurrentNodeIndex(this.ghost);
			int otherGhost;

			int nearestGhostDist = Integer.MAX_VALUE;
			int nearestNotEdibleGhost = -1;
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
