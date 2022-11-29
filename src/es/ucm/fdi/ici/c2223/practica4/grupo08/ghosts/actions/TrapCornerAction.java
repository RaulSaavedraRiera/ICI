package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica3.grupo08.JunctionManager;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class TrapCornerAction implements Action {
	
	JunctionManager juncManager;
	GHOST ghost;
	public TrapCornerAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
	}
	@Override
	public String getActionId() {
		
		return ghost +"trapCorner";
	}
	@Override
	public MOVE execute(Game game) {
		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{	
			MOVE move = game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
					game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghost), DM.PATH);
			juncManager.markDirection(game.getGhostCurrentNodeIndex(ghost), game.getPacmanLastMoveMade());
			
			move = juncManager.getNextAvailableMove(game.getGhostCurrentNodeIndex(ghost), move, game.getGhostLastMoveMade(ghost));
			return move;
		}
		return MOVE.NEUTRAL;
	}
}
