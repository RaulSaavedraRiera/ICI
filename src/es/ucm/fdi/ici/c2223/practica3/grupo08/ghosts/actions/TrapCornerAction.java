package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica3.grupo08.JunctionManager;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class TrapCornerAction implements RulesAction {
	
	JunctionManager juncManager;
	GHOST ghost;
    GhostData gData;
	public TrapCornerAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}
	@Override
	public String getActionId() {
		
		return ghost +"trapCorner";
	}
	@Override
	public MOVE execute(Game game) {
		gData.setGhostObjective(ghost, -1);
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

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}

}
