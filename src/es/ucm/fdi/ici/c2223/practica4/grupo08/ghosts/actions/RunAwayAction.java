package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsFuzzyMemory;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayAction implements Action {

    GHOST ghost;
    GhostsFuzzyMemory fuzzyMem;
    
	public RunAwayAction(GHOST ghost, GhostsFuzzyMemory mem) {
		this.ghost = ghost;
		this.fuzzyMem = mem;
	}

	@Override
	public MOVE execute(Game game) {
		
        if (game.doesGhostRequireAction(ghost))        //if it requires an action
        {
                return game.getApproximateNextMoveAwayFromTarget(game.getGhostCurrentNodeIndex(ghost),
                        game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghost), DM.PATH);
        }
            
        return MOVE.NEUTRAL;	
	}

	@Override
	public String getActionId() {
		return ghost+ "Runaway";
	}
}
