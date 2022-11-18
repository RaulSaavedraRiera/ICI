package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ProtectEdibleAction implements RulesAction {

	GHOST ghost;
	GhostData gData;

	public ProtectEdibleAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}

	@Override
	public String getActionId() {
		return ghost + "protectsEdible";
	}

	@Override
	public MOVE execute(Game game) {
		gData.setGhostObjective(ghost, -1);

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

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub

	}

}
