package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoToNearestPPAction implements RulesAction {

	GHOST ghost;
	GhostData gData;

	public GoToNearestPPAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}

	@Override
	public MOVE execute(Game game) {
//		gData.setGhostObjective(ghost, -1);

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
		return ghost + "goesToNearestPP";
	}

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}

}