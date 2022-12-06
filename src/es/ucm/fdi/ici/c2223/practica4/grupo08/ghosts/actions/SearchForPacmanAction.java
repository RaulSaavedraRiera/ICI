package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions;

import java.awt.Color;
import java.util.Random;

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

	boolean searchCorner = true;

	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();

	public SearchForPacmanAction(GHOST ghost, GhostsFuzzyMemory mem) {
		this.ghost = ghost;
		this.fuzzyMem = mem;
	}

	@Override
	public MOVE execute(Game game) {

		// objectivo de la busqueda (de momento la PP asignada)
		int objective = fuzzyMem.getGhostAsignedPP(ghost);
		int lairNode = game.getCurrentMaze().initialGhostNodeIndex;
		int pos = game.getGhostCurrentNodeIndex(ghost);
		MOVE lastMove = game.getGhostLastMoveMade(ghost);

		if (searchCorner)
			GameView.addLines(game, Color.YELLOW, game.getGhostCurrentNodeIndex(ghost), objective);

		else
			GameView.addLines(game, Color.YELLOW, game.getGhostCurrentNodeIndex(ghost), lairNode);

		// busca yendo a la esquina que le corresponde
		if (searchCorner && game.getShortestPathDistance(pos, objective, lastMove) > 10)
			return game.getApproximateNextMoveTowardsTarget(pos, objective, lastMove, DM.PATH);

		// busca yendo hacia el centro o a uno aleatorio
		else {
			if (searchCorner)
				searchCorner = false;

			int random = rnd.nextInt(0, 2);

			if (random == 0) {

				if (game.getShortestPathDistance(pos, lairNode, lastMove) > 10) {
					return game.getApproximateNextMoveTowardsTarget(pos, lairNode, lastMove, DM.PATH);
				}

				else {
					searchCorner = true;
				}
			}

			else {
				return allMoves[rnd.nextInt(allMoves.length)];
			}
		}

		return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "SearchPacman";
	}

}