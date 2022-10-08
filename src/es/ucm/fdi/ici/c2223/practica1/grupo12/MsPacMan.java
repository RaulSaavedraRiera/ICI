package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.awt.Color;
import java.util.Arrays;
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
	final int RUN_LIMIT = 80;
	final int EAT_LIMIT = 80;
	final int LIMIT_EDIBLE_TIME = 12;
	final int PILL_LIMIT = 80;
	final int MAX_PILL_CHECKED = 8000;

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
		
		try {
			lastMove = game.getPacmanLastMoveMade();
		}
		catch(Exception e)
		{
			lastMove = MOVE.NEUTRAL;
		}
		

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
				return game.getApproximateNextMoveAwayFromTarget(pacmanPos, game.getGhostCurrentNodeIndex(ghosts[0]), lastMove, DM.PATH);
			}

			// si hay mas de un fantasma en la zona
			else {

				to = powerPillInRange(game);

				if (to != -1) {
					powerPillT = to;

					DrawPath(game, Color.GREEN, pacmanPos, to, lastMove);
					return game.getApproximateNextMoveTowardsTarget(pacmanPos, to, lastMove, DM.PATH);
				} else {

					to = awayFromMultipleGhosts(game);
					if (to != -1) {
						pillT = to;
						DrawPath(game, Color.PINK, pacmanPos, to, lastMove);
						return game.getApproximateNextMoveTowardsTarget(pacmanPos, to, lastMove, DM.PATH);
					} else {
						return toNearestPill(game, true);
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
		
			GameView.addPoints(game, Color.RED, game.getShortestPath(pacmanPos,
					game.getGhostCurrentNodeIndex(target), game.getPacmanLastMoveMade()));
		
		return game.getApproximateNextMoveTowardsTarget(pacmanPos, game.getGhostCurrentNodeIndex(target),
				game.getPacmanLastMoveMade(), DM.MANHATTAN);
	}
	

	public MOVE toNearestPill(Game game,  boolean nonPowerPill) {

		// si tiene que evitar powerPills
		if (nonPowerPill && game.getActivePowerPillsIndices().length > 0) {
			// obtenemos todos los nodos colindantes
			int[] neighbours = game.getNeighbouringNodes(pacmanPos, lastMove);

			// interseciion
			if (neighbours.length > 1) {

				// obtenemos la powerPill mas cercana
				int pP = getNearestPill(game, pacmanPos, game.getActivePowerPillsIndices());
				int pillsNumber = 0;
				int nextNode = -1;

				// exploramos cualquier ruta de la posicion donde esta
				for (int i = 0; i < neighbours.length; i++) {
					int t = noPowerPillRoute(game, neighbours[i], pP);

					if (t != -1 && (nextNode == -1 || t > pillsNumber)) {
						nextNode = neighbours[i];
						pillsNumber = t;
					}
				}

				if (nextNode != -1)
				{
					return game.getApproximateNextMoveTowardsTarget(pacmanPos, nextNode, lastMove, DM.PATH);
				}
					
			}
		}

		// si no que busque una pildora habitual

		return game.getApproximateNextMoveTowardsTarget(pacmanPos,
				getNearestPill(game, pacmanPos, game.getActivePillsIndices()), lastMove, DM.PATH);

	}

	
	int noPowerPillRoute(Game game, int ini, int pP)
	{
		int pills = 0;
		//presuponemos que la ruta es valida
		boolean valid = true;
		//var para lamacenar nodo siguiente al actual y nodo actual
		int nextNode = ini; int currentNode = pacmanPos; 
		
		//donde tomaremos los siguientes vecinos
		int[] nextNeighbours = null;
		
		
		do {						
			 //si el siguiente nodo es la powerPill no valido
			 if(nextNode == pP)
				 valid = false;
			 else
			 {
				//obten los vecinos del siguiente nodo
				 nextNeighbours = game.getNeighbouringNodes(nextNode,
							game.getApproximateNextMoveTowardsTarget(currentNode, nextNode, MOVE.NEUTRAL, DM.PATH));
				 
				 
				 //avanzamos una posicion
				 final int node = currentNode = nextNode;
				  
				  //comprobamos si hay pildora en esta casilla
				  if(Arrays.stream(game.getActivePillsIndices()).anyMatch(i -> i == node))
					  pills++;
					  
				
				  //solo debe haber 1
				  nextNode = nextNeighbours[0];
				  
				
			 }
		}
		//mientras no sea una interseccion
		while(nextNeighbours.length < 2 && valid);
		
		//si encuentra una ruta sin powerPills la devuelve
		if (valid) 
			return pills;
		else 
			return -1;
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

				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < LIMIT_EDIBLE_TIME) {

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

		int[] pacman = null;

		int[] ghostPacman = null;

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
				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < LIMIT_EDIBLE_TIME) {
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
		} while (!valid && i <= MAX_PILL_CHECKED);

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
		int minDistance = RUN_LIMIT;

		// por cada uno de los fantasmas
		for (Constants.GHOST g : Constants.GHOST.values()) {
			// si no ha sido seleccionado, no e comible y no esta encerrado
			if (g != ghosts[0] && g != ghosts[1] && game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) <= LIMIT_EDIBLE_TIME) {
				// comprobamos si esta mas cerca que el limite actual
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pacmanPos, game.getGhostLastMoveMade(g));
				if (currentDistance < minDistance) {
					// en caso correcto lo guardamos
					nearest = g; 
					minDistance = currentDistance;
				}
			}

		}

		ghosts[index] = nearest;

	}

	public void getNearestTargetGhost(Game game) // sin limite de seguridad , int eatLimit
	{
		GHOST nearestGhost = null;
		
		int nearestDistance = EAT_LIMIT;
		
		for (Constants.GHOST g : Constants.GHOST.values()) {
			if(game.getGhostEdibleTime(g) > 0 && game.getShortestPathDistance(pacmanPos, game.getGhostCurrentNodeIndex(g), lastMove) < nearestDistance)
			{
			nearestGhost = g;
			nearestDistance = game.getShortestPathDistance(pacmanPos, game.getGhostCurrentNodeIndex(g), lastMove);
			}
			
		}
		
		target = nearestGhost;

	}

	public int getNearestPill(Game game, int pacManPos, int[] pills) {
		if (pills.length == 0)
			return -1;

		// variables para controlar las posiciones
		int distance = 9999, to = -1, currentDistance;

		for (int pill : pills) // comprobamos para las pill cual es la mas cercana
		{
			currentDistance = game.getShortestPathDistance(pacManPos, pill, game.getPacmanLastMoveMade());

			if (currentDistance < distance) {
				to = pill;
				distance = currentDistance;
			}
		}

		// devolvemos la pill mas cercana
		return to;
	}

	// metodos para comprobacion de rutas validas

	boolean pillSecure(Game game, int pill) {
		
		boolean nonGhosts = true;

		for (Constants.GHOST g : Constants.GHOST.values()) {

			if (game.getGhostLairTime(g) > 0 && game.getGhostEdibleTime(g) < LIMIT_EDIBLE_TIME) {
				try {
					nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill,
							game.getGhostLastMoveMade(g)) > PILL_LIMIT;
				} catch (Exception e) {
					nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill) > PILL_LIMIT;
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
