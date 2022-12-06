package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoToNearestPPAction implements Action {

	GHOST ghost;

	public GoToNearestPPAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {

		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{	
			int thisGhost = game.getGhostCurrentNodeIndex(ghost);
			int minDistanceToPPill = Integer.MAX_VALUE;
			int nearestPP = -1;

			for (int ppill : game.getPowerPillIndices()) {
				int distance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), ppill,
						game.getPacmanLastMoveMade());

				if (distance < minDistanceToPPill) {
					minDistanceToPPill = Math.min(distance, minDistanceToPPill);
					nearestPP = ppill;
				}
			}

			return game.getApproximateNextMoveTowardsTarget(thisGhost, nearestPP, game.getGhostLastMoveMade(ghost),
					DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "GoesToPP";
	}
}