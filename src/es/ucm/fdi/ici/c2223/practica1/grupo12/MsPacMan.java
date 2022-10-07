package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.awt.Color;
import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

public class MsPacMan extends PacmanController {

	// distancia para huir / tiempo-margen de comer seguro / tiemoo-margen de
	// fantasma no peligroso seguro / distancia pill segura
	int runLimit = 100;
	int eatLimit = 100;
	int eatTimeSecure = 20;
	int limitEdibleTime = 6;
	int pillLimit = 75;
	int maxPillChecked = 500;

	int pacmanPos;

	GHOST[] ghosts = new GHOST[3];
	private Random rnd = new Random();

	private MOVE[] allMoves = MOVE.values();

	int powerPillT, pillT;
	GHOST target = null;

	public MsPacMan() {
		super();

		setName("MsPacMan 12");

		setTeam("Team 12");

	}

	@Override
	public MOVE getMove(Game game, long timeDue) {

		pacmanPos = game.getPacmanCurrentNodeIndex();
		lastMove = game.getPacmanLastMoveMade();

		// obtenemos los fantasmas que pueden cazarte
		GetChasingGhosts(game);
		int to;

		// si no hay fantasmas peligrosos por la zona
		if (ghosts[0] == null) {

			getNearestTargetGhost(game);

			// si tampoco hay fantasma que podamos comer a pildora sin fantasmas cerca
			if (target == null)
				return toNearestPill(game, true);
			// si si vamos a por el
			else {

				DrawPath(game, Color.RED, pacmanPos, game.getGhostCurrentNodeIndex(target), lastMove);
				return toNearestGhost(game);
			}
		} 
		else {
			// si solo hay 1 fantasma peligroso huimos, guardamos pildora para momentos mas
			// peligrosos
			if (ghosts[1] == null) {
				DrawPath(game, Color.BLUE, pacmanPos, game.getGhostCurrentNodeIndex(ghosts[0]), lastMove);
				to = awayFromMultipleGhosts(game);
				if (to != -1) {
					pillT = to;

					DrawPath(game, Color.PINK, pacmanPos, to, lastMove);
					return game.getNextMoveTowardsTarget(pacmanPos, to, lastMove, DM.PATH);
				} else {
					System.out.println("random");
					return toNearestPill(game, false);
				}
			}

			// si hay mas de un fantasma en la zona
			else {

				to = powerPillInRange(game);

				if (to != -1) {
					powerPillT = to;

					DrawPath(game, Color.GREEN, pacmanPos, to, lastMove);
					return game.getNextMoveTowardsTarget(pacmanPos, to, lastMove, DM.PATH);
				} else {

					to = awayFromMultipleGhosts(game);
					if (to != -1) {
						pillT = to;
						DrawPath(game, Color.PINK, pacmanPos, to, lastMove);
						return game.getNextMoveTowardsTarget(pacmanPos, to, lastMove, DM.PATH);
					} else {
						System.out.println("random");
						return toNearestPill(game, false);
					}
				}
			}
		}

	}

	public void DrawPath(Game game, Color color, int p, int to, MOVE last) {
		GameView.addPoints(game, color, game.getShortestPath(p, to, last));
	}

	// metodos para legibilidad del codigo
	public MOVE toNearestGhost(Game game) {
		try {
			GameView.addPoints(game, Color.RED, game.getShortestPath(game.getPacmanCurrentNodeIndex(),
					game.getGhostCurrentNodeIndex(target), game.getPacmanLastMoveMade()));
		} catch (Exception e) {
		}
		;

		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(target),
				game.getPacmanLastMoveMade(), DM.MANHATTAN);
	}

	public MOVE toNearestPill(Game game, boolean secure) {
		try {
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
					getNearestPill(game, game.getPacmanCurrentNodeIndex(), game.getActivePillsIndices(), secure),
					DM.PATH);
		} catch (Exception e) {
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
					getNearestPill(game, game.getPacmanCurrentNodeIndex(), game.getActivePillsIndices(), secure),
					game.getPacmanLastMoveMade(), DM.PATH);
		}
	}

	int powerPillInRange(Game game) {
		int bestValid = -1;
		int distance = -1;
		int[] pacmanToPill;
		int[] ghostToPacman;
		int[] ghostToPill;
		// para cada pildora
		for (int p : game.getActivePowerPillsIndices()) {
			boolean valid = true;

			// comprobamos que no se toque con fantasmas
			for (Constants.GHOST g : Constants.GHOST.values()) {

				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < limitEdibleTime) {

					pacmanToPill = game.getShortestPath(pacmanPos, p, lastMove);
					ghostToPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos,
							game.getGhostLastMoveMade(g));
					ghostToPill = game.getShortestPath(game.getGhostCurrentNodeIndex(g), p,
							game.getGhostLastMoveMade(g));

					if (collisionRoute(pacmanToPill, ghostToPacman) || collisionRoute(pacmanToPill, ghostToPill)) {
						valid = false;
						break;
					}
				}
			}

			if (valid && (bestValid == -1 || distance > game.getShortestPathDistance(pacmanPos, p, lastMove))) {
				bestValid = p;
				distance = game.getShortestPathDistance(pacmanPos, p, lastMove);
			}
		}

		return bestValid;
	}

	public int awayFromMultipleGhosts(Game game) {

		int pacman[] = null;

		int ghostPacman[] = null;

		boolean valid;
		int i = 0;
		int p = -1;
		
		// para cada pildora
		do {
			p = game.getPillIndices()[rnd.nextInt(game.getPillIndices().length)];
			i++;
			// obtenemos la ruta
			pacman = game.getShortestPath(pacmanPos, p, lastMove);
			// para cada una suponemos que es valida
			valid = true;
			for (Constants.GHOST g : Constants.GHOST.values()) {
				// si el fantasma no esta encerrado y supone una amenaza comprobamos ruta con el
				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < limitEdibleTime) {
					try {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos,
								game.getGhostLastMoveMade(g));

					} catch (Exception e) {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos);
					}

					// en caso de que haya colision la marcamos como no valida y salimos
					if (!pillSecure(game, p) || collisionRoute(pacman, ghostPacman)) // la pildora esta lejos de
																						// fantasmas es segura
					{
						valid = false;
						break;
					}
				}

			}
		} while (!valid && i <= maxPillChecked);

		return p;

	}

	// metodos para la obtención de información del mapa

	public void GetChasingGhosts(Game game) {
		ghosts[0] = ghosts[1] = ghosts[2] = null;

		for (int i = 0; i < 3; i++)
			getNearestChasingGhost(game, i);
	}

	public void getNearestChasingGhost(Game game, int index) {
		GHOST nearest = null;
		int currentDistance;

		// por cada uno de los fantasmas
		for (Constants.GHOST g : Constants.GHOST.values()) {
			// si no ha sido seleccionado, no e comible y no esta encerrado
			if (g != ghosts[0] && g != ghosts[1] && game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) <= limitEdibleTime) {
				// comprobamos si esta mas cerca que el limite actual
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pacmanPos, game.getGhostLastMoveMade(g));
				if (currentDistance < runLimit) {
					// en caso correcto lo guardamos
					nearest = g; runLimit = currentDistance;
				}
			}

		}

		ghosts[index] = nearest;

	}

	public void getNearestTargetGhost(Game game) // sin limite de seguridad , int eatLimit
	{
		GHOST nearestGhost = null;
		
		int nearestDistance = eatLimit;
		
		for (Constants.GHOST g : Constants.GHOST.values()) {
			if(game.getGhostEdibleTime(g) > 0 && game.getShortestPathDistance(pacmanPos, game.getGhostCurrentNodeIndex(g), lastMove) < nearestDistance)
			{
			nearestGhost = g;
			nearestDistance = game.getShortestPathDistance(pacmanPos, game.getGhostCurrentNodeIndex(g), lastMove);
			}
			
		}
		
		target = nearestGhost;

	}

	public int getNearestPill(Game game, int pacManPos, int[] pills, boolean secure) {
		if (pills.length == 0)
			return -1;

		// variables para controlar las posiciones
		int distance = 9999, to = -1, currentDistance;

		for (int pill : pills) // comprobamos para las pill cual es la mas cercana
		{
			currentDistance = game.getShortestPathDistance(pacManPos, pill, game.getPacmanLastMoveMade());

			if (currentDistance < distance && (!secure || pillSecure(game, pill))) {
				to = pill;
				distance = currentDistance;
			}
		}

		// devolvemos la pill mas cercana

		if (secure && to == -1)
			getNearestPill(game, pacManPos, pills, false);

		return to;
	}

	// metodos para comprobacion de rutas validas

	boolean pillSecure(Game game, int pill) {
		boolean nonGhosts;

		for (Constants.GHOST g : Constants.GHOST.values()) {

			if (game.getGhostLairTime(g) > 0) {
				try {
					nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill,
							game.getGhostLastMoveMade(g)) > pillLimit;
				} catch (Exception e) {
					nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill) > pillLimit;
				}
				if (!nonGhosts)
					return false;
			}

		}

		return true;
	}

	boolean collisionRoute(int[] route, int[] otherRoute) {

		// para cada punto en la ruta
		for (int i = 0; i < route.length; i++)
			// comprobamos con los puntos de la otra ruta
			for (int j = 0; j < otherRoute.length; j++)

				if (route[i] == otherRoute[j] && j <= i)
					return true;

		return false;
	}

}
