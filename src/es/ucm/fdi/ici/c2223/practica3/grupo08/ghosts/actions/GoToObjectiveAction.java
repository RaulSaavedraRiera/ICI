package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import java.awt.Color;
import java.util.ArrayList;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class GoToObjectiveAction implements Action {

    GHOST ghost;
    GhostData gData;
	public GoToObjectiveAction(GHOST ghost, ArrayList<GhostData> ghostData) {
		this.ghost = ghost;
		gData = ghostData.get(0);
	}

	@Override
	public MOVE execute(Game game) {

		int thisGhost = game.getGhostCurrentNodeIndex(ghost);
		int objective = gData.getGhostObjective(ghost);

		if (objective != -1) {
			gData.getDistanceToObjective()[ghost.ordinal()] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), objective,
					game.getGhostLastMoveMade(ghost));

		} else
			gData.getDistanceToObjective()[ghost.ordinal()] = 0;
		
		GameView.addLines(game, Color.GREEN, thisGhost, objective);

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

	

}