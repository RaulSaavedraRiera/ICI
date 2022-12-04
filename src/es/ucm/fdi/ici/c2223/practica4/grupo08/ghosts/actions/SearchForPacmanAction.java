package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsFuzzyMemory;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class SearchForPacmanAction implements Action {

	GHOST ghost;
	GhostsFuzzyMemory fuzzyMem;

	public SearchForPacmanAction(GHOST ghost, GhostsFuzzyMemory mem) {
		this.ghost = ghost;
		this.fuzzyMem = mem;
	}

	@Override
	public MOVE execute(Game game) {
		
		//objectivo de la busqueda (de momento la PP asignada)
		int objective = fuzzyMem.getGhostAsignedPP(ghost);
		
		GameView.addLines(game, Color.YELLOW, game.getGhostCurrentNodeIndex(ghost), objective);
		
		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{	
			return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost),
					objective, game.getGhostLastMoveMade(ghost), DM.PATH);
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "SearchPacman";
	}

}