package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class MsPacmanPredictor {

	private ArrayList<Integer> msPacmanPosiblePos;
	private ArrayList<MOVE> msPacmanPosibleDirs;

	Game game;
	GhostsFuzzyMemory ghostMem;

	public MsPacmanPredictor(Game game, GhostsFuzzyMemory mem) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.ghostMem = mem;

		msPacmanPosiblePos = new ArrayList<Integer>();
		msPacmanPosibleDirs = new ArrayList<MOVE>();
	}

	// calcula las posibles posiciones de pacman
	public void calculate() {
		if (msPacmanPosiblePos.size() == 0) {
			int pacmanLastPos = ghostMem.getPacmanLastPosition();
			for (int pos : game.getNeighbouringNodes(pacmanLastPos, ghostMem.getPacmanLastDirection())) {
				MOVE moveDone = game.getMoveToMakeToReachDirectNeighbour(pacmanLastPos, pos);
				msPacmanPosiblePos.add(pos);
				msPacmanPosibleDirs.add(moveDone);

				GameView.addPoints(game, Color.GREEN, pos);
			}
		}

		else {
			ArrayList<Integer> posiblePos = (ArrayList<Integer>) msPacmanPosiblePos.clone();
			ArrayList<MOVE> posibleDirs = (ArrayList<MOVE>) msPacmanPosibleDirs.clone();

			msPacmanPosiblePos.clear();
			msPacmanPosibleDirs.clear();

			int cont = 0;
			for (int p : posiblePos) {
				for (int pos : game.getNeighbouringNodes(p, posibleDirs.get(cont))) {
					MOVE moveDone = game.getMoveToMakeToReachDirectNeighbour(p, pos);
					msPacmanPosiblePos.add(pos);
					msPacmanPosibleDirs.add(moveDone);

					GameView.addPoints(game, Color.GREEN, pos);
				}

				cont++;
			}
		}
	}

	// resetea las posiciones de pacman
	public void reset() {
		msPacmanPosiblePos = new ArrayList<Integer>();
		msPacmanPosibleDirs = new ArrayList<MOVE>();
	}

	public List<Integer> getMsPacmanPossiblePos() {
		return msPacmanPosiblePos;
	}

	public int getMostProbablePos() {
		int mostProbablePos = -1;

		int maxPoints = -Integer.MAX_VALUE;

		int cont = 0;
		for (int nextPos : msPacmanPosiblePos) {
			int points = 0;

			// cada nodo de distancia a la PP mas cercana quita 2 puntos
			MOVE lastMove = msPacmanPosibleDirs.get(cont);
			int nearestPP = getNearestPP(nextPos, lastMove);
			int distanceToPP = game.getShortestPathDistance(nextPos, nearestPP, lastMove);

			points -= distanceToPP * 2;

			// cada nodo de distancia al fantasma mas cercano no comestible da 1 punto
			GHOST nearestNotEdibleGhost = getNearestNotEdibleGhost(nextPos);
			if (nearestNotEdibleGhost != null) {
				int distanceToNotEdibleGhost = game.getShortestPathDistance(
						ghostMem.getGhostPosition(nearestNotEdibleGhost), nextPos,
						ghostMem.getGhostLastMove(nearestNotEdibleGhost));

				points += distanceToNotEdibleGhost;
			}

			// cada nodo de distancia al fantasma comestible mas cercano quita 2 puntos
			GHOST nearestEdibleGhost = getNearestEdibleGhost(nextPos, lastMove);
			if (nearestEdibleGhost != null) {
				int distanceToEdibleGhost = game.getShortestPathDistance(nextPos,
						ghostMem.getGhostPosition(nearestEdibleGhost), lastMove);

				points -= distanceToEdibleGhost;
			}

			if (points > maxPoints) {
				mostProbablePos = nextPos;
				maxPoints = points;
			}

			cont++;
		}

		if (mostProbablePos != -1) {
			GameView.addPoints(game, Color.RED, mostProbablePos);
		}

		return mostProbablePos;
	}

	private int getNearestPP(int node, MOVE lastMove) {
		int minDistance = Integer.MAX_VALUE, nearestPP = -1, currentDistance;

		for (int pp : game.getCurrentMaze().powerPillIndices) {
			if (ghostMem.isPPActive(pp)) {
				currentDistance = game.getShortestPathDistance(node, pp, lastMove);

				if (currentDistance < minDistance) {
					nearestPP = pp;
					minDistance = currentDistance;
				}
			}
		}

		return nearestPP;
	}

	private GHOST getNearestNotEdibleGhost(int node) {
		GHOST nearestGhost = null;

		int minDistance = Integer.MAX_VALUE, currentDistance;

		for (GHOST g : GHOST.values()) {
			if (!game.isGhostEdible(g)) {
				currentDistance = game.getShortestPathDistance(ghostMem.getGhostPosition(g), node,
						ghostMem.getGhostLastMove(g));

				if (currentDistance < minDistance) {
					nearestGhost = g;
					minDistance = currentDistance;
				}
			}
		}

		return nearestGhost;
	}

	private GHOST getNearestEdibleGhost(int node, MOVE lastMove) {
		GHOST nearestGhost = null;

		int minDistance = Integer.MAX_VALUE, currentDistance;

		for (GHOST g : GHOST.values()) {
			if (game.isGhostEdible(g)) {
				currentDistance = game.getShortestPathDistance(node, ghostMem.getGhostPosition(g), lastMove);

				if (currentDistance < minDistance) {
					nearestGhost = g;
					minDistance = currentDistance;
				}
			}
		}

		return nearestGhost;
	}
}
