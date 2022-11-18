package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import java.awt.Color;

import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class GoToObjectiveAction implements RulesAction {

    GHOST ghost;
    GhostData gData;
	public GoToObjectiveAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
	}

	@Override
	public MOVE execute(Game game) {

		int thisGhost = game.getGhostCurrentNodeIndex(ghost);
		int objective = gData.getGhostObjective(ghost);

		if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {	
			return game.getApproximateNextMoveTowardsTarget(thisGhost,
					objective, game.getGhostLastMoveMade(ghost), DM.PATH);
        }
        return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "goesToObjective";
	}

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}

	

}