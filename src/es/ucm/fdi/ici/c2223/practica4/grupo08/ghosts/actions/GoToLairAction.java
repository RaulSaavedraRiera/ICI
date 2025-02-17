package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoToLairAction implements Action {

	GHOST ghost;

	public GoToLairAction(GHOST ghost) {
		this.ghost = ghost;
	}

	@Override
	public MOVE execute(Game game) {
		
		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{	
			int lairNode = game.getCurrentMaze().lairNodeIndex;
			
			return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
					lairNode, game.getGhostLastMoveMade(ghost), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "RunsToLair";
	}
}