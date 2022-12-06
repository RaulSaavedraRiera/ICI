package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import java.awt.Color;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsFuzzyMemory;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.JunctionManager;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.MsPacmanPredictor;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class ChaseAction implements Action {

	GHOST ghost;
	GhostsFuzzyMemory fuzzyMem;
	JunctionManager junctMan;

	public ChaseAction(GHOST ghost, GhostsFuzzyMemory mem, JunctionManager junctMan) {
		this.ghost = ghost;
		this.fuzzyMem = mem;
		this.junctMan = junctMan;
	}

	@Override
	public MOVE execute(Game game) {

		int pos = game.getGhostCurrentNodeIndex(ghost);
		int pacmanPos = fuzzyMem.getPacmanLastPosition();
		MOVE lastMove = game.getGhostLastMoveMade(ghost);

		GameView.addPoints(game, Color.RED, game.getShortestPath(pos, pacmanPos, lastMove));

		if (game.doesGhostRequireAction(ghost)) // if it requires an action
		{
			MOVE move;
			
			if (fuzzyMem.getPredictorPosPoints() > -100)
				move = game.getApproximateNextMoveTowardsTarget(pos, fuzzyMem.getMostProbableActualPacmanPos(), lastMove, DM.PATH);
			
			else
				move = game.getApproximateNextMoveTowardsTarget(pos, pacmanPos, lastMove, DM.PATH);
			
			move = junctMan.getNextAvailableMove(pos, move, lastMove);
			
			return move;
		}
		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "Chase";
	}

}