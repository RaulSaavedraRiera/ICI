package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoToLairAction implements RulesAction {

	GHOST ghost;
	GhostData gData;

	public GoToLairAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}

	@Override
	public MOVE execute(Game game) {
		gData.setGhostObjective(ghost, -1);

		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{	
			int lairNode = game.getNumberOfNodes()/2;
			
			return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
					lairNode, game.getGhostLastMoveMade(ghost), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "chases";
	}

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}

}