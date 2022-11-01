package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman;

import java.util.Arrays;
import java.util.Random;

import es.ucm.fdi.ici.Input;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManInput extends Input {

	
	final int ENOUGH_PILLS_NEAR = 15;
	
	final int MAXDISTANCE_GHOSTSEDIBLE = 20;
	final int MAXDISTANCE_GHOSTSCHASING = 20;
	final int MAXDISTANCE_GHOSTSGROUP = 30;
	final int MAXDISTANCE_PILLSNEAR = 100;
	final int MAXTIME_EDIBLEGHOST = 12;
	final int MAXCOUNT_PILLSCHECKED = 8000;
	
	private int pacmanDistanceNearPowerPill;
	private int pacmanDistanceCell;
	private int pacmanDistanceNearCorner;
	
	private int edibleGhostsInRange;
	private int chasingGhostsInRange;
	
	private int nearPills;
	
	private boolean secureRoute; 
	private boolean ghostFollowPacman;
	private boolean groupEdibleGhosts; 
	private boolean pacmanCanReachPowerPill;

	
	
	private int cell = -1;
	
	private Random rnd = new Random();
	
	
	public MsPacManInput(Game game) {
		super(game);
		
	}

	@Override
	public void parseInput() {
		
		// does nothing.
		pacmanDistanceNearPowerPill = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),  getNearestPoint(game.getActivePillsIndices()), game.getPacmanLastMoveMade());
		
		//habra que meter lo de obtener casilla de celda aqui?
		if(cell == -1)
			pacmanDistanceCell = getCellIndex();
		else pacmanDistanceCell = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), cell, game.getPacmanLastMoveMade());
		
		pacmanDistanceNearCorner = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),  getNearestCorner(), game.getPacmanLastMoveMade());
		
		edibleGhostsInRange = getGhostsNear(true);
		chasingGhostsInRange = getGhostsNear(false);
		
		nearPills = getPillsNear();		
		ghostFollowPacman = isGhostFollowsPacman();		
		secureRoute = secureRouteAvailable();
		groupEdibleGhosts = groupAvailable();
		
		pacmanCanReachPowerPill = powerPillSecure();
		
		
	}
	
	public int pacmanDistanceNearPowerPill() {
		return pacmanDistanceNearPowerPill;
	}
	
	public int pacmanDistanceCell() {
		return pacmanDistanceCell;
	}
	
	public int pacmanDistanceNearCorner() {
		return pacmanDistanceNearCorner;
	}
	
	public int edibleGhostsInRange() {
		return edibleGhostsInRange;
	}
	
	public int chasingGhostsInRange() {
		return chasingGhostsInRange;
	}
	
	public int nearPills() {
		return nearPills;
	}
	
	public boolean secureRoute() {
		return secureRoute;
	}
	
	
	public boolean ghostFollowPacman() {
		return ghostFollowPacman;
	}
	
	public boolean groupEdibleGhosts() {
		return groupEdibleGhosts;
	}
	
	public boolean pacmanCanReachPowerPill() {
		return pacmanCanReachPowerPill;
	}
	
int getNearestPoint(int[] pills) {		
		
		if (pills.length == 0)
			return -1;

		// variables para controlar las posiciones
		int distance = 9999, to = -1, currentDistance;

		for (int pill : pills) // comprobamos para las pill cual es la mas cercana
		{
			currentDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());

			if (currentDistance < distance) {
				to = pill;
				distance = currentDistance;
			}
		}

		// devolvemos la pill mas cercana
		return to;
	}

int getNearestCorner() {
	
	int[] pills = game.getActivePowerPillsIndices();
	if (pills.length == 0)
		return -1;

	// variables para controlar las posiciones
	int distance = 9999, to = -1, currentDistance;

	for (int pill : pills) // comprobamos para las pill cual es la mas cercana
	{
		if(!Arrays.stream(game.getActivePillsIndices()).anyMatch(i -> i == pill))
		{
			currentDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());

			if (currentDistance < distance) {
				to = pill;
				distance = currentDistance;
			}
		}		
	}

	// devolvemos la pill mas cercana
	return to;
}


int getGhostsNear(boolean edible)
{
	int n = 0;
	int currentDistance;
	int distance; if(edible) distance = MAXDISTANCE_GHOSTSEDIBLE; else distance = MAXDISTANCE_GHOSTSCHASING;
	
	// por cada uno de los fantasmas
	for (Constants.GHOST g : Constants.GHOST.values()) {
		// si no ha sido seleccionado, no e comible y no esta encerrado
		if (game.getGhostLairTime(g) == 0 && 
				((game.getGhostEdibleTime(g) == 0 && edible) || (game.getGhostEdibleTime(g) > 0 && !edible))) {
			// comprobamos si esta mas cerca que el limite actual
			currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g),
					game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
			if (currentDistance < distance) {
				// en caso correcto lo guardamos
			n++;
			}
		}

	}
	return n;
}


int getPillsNear() {
	
	
	int n = 0;
	
	int[] pills = game.getActivePillsIndices();
	
	if (pills.length == 0)
		return 0;

	// variables para controlar las posiciones


	for (int pill : pills) // comprobamos para las pill cual es la mas cercana
	{
		if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade()) <= MAXDISTANCE_PILLSNEAR) 
			n++;
	}

	// devolvemos la pill mas cercana
	return n;
}

//si antes de la siguiente interseccion donde esta el fantasma esta pacman
boolean isGhostFollowsPacman()
{
	GHOST nearest = null;
	int minDistance = 10000;
	int currentDistance;
	
	// por cada uno de los fantasmas
	for (Constants.GHOST g : Constants.GHOST.values()) {
		// si no ha sido seleccionado, no e comible y no esta encerrado
		if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) == 0) {
			// comprobamos si esta mas cerca que el limite actual
			currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g),
					game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
			if (currentDistance < minDistance) {
				// en caso correcto lo guardamos
				nearest = g;
				minDistance = currentDistance;
			}
		}

	}
	
	//si ahora mismo no esta en una interseccion devolvemos que no
	if(nearest == null || game.getNeighbouringNodes(game.getGhostCurrentNodeIndex(nearest), game.getGhostLastMoveMade(nearest)).length > 1)
		return false;
	
	//si no obtenemos el nodo siguiente
	int node = game.getNeighbouringNodes(game.getGhostCurrentNodeIndex(nearest))[0];
	//guardamos los nodos
	int[] nodes;
	
		do {		
			//si el nodo es el de pacman es que le esta siguiendo
			if(node == game.getPacmanCurrentNodeIndex())
				return true;
			//cogemos los nodos siguientes al actual
			nodes = game.getNeighbouringNodes(node);
			node = nodes[0];
			//si es interseccion salimos
		}while(nodes.length < 2);
	
	//si pacman no esta ahi no lo esta siguiendo
	return false;
	
}


//habria que usar la routa que nos da, el int finald e la pill pero no se si se puede pasar a accion
boolean secureRouteAvailable() {
	
	int pacmanPos = game.getPacmanCurrentNodeIndex(); 
	MOVE lastMove = game.getPacmanLastMoveMade();
	
	int[] pacman = null;

	int[] ghostPacman = null;

	boolean valid;
	int i = 0;
	int p = -1;
	
	// para cada pildora
	do {
		p = game.getPillIndices()[new Random().nextInt(game.getPillIndices().length)];
		i++;
		// obtenemos la ruta
		pacman = game.getShortestPath(pacmanPos, p, lastMove);
		// para cada una suponemos que es valida
		valid = true;
		for (Constants.GHOST g : Constants.GHOST.values()) {
			// si el fantasma no esta encerrado y supone una amenaza comprobamos ruta con el
			if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < MAXTIME_EDIBLEGHOST) {
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
	} while (!valid && i <= MAXCOUNT_PILLSCHECKED);

	//return p; deberiamos guardar la pildora
	
	if(p != -1) return true;
	else return false;
}
	

boolean pillSecure(Game game, int pill) {
	
	boolean nonGhosts = true;

	for (Constants.GHOST g : Constants.GHOST.values()) {

		if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < MAXTIME_EDIBLEGHOST) {
			try {
				nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill,
						game.getGhostLastMoveMade(g)) > MAXDISTANCE_GHOSTSCHASING;
			} catch (Exception e) {
				nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill) > MAXDISTANCE_GHOSTSCHASING;
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


//podriamos obtener el grupo aqui en vez de en la accion?
boolean groupAvailable() {
	
	for (Constants.GHOST g : Constants.GHOST.values()) {

		
		for (Constants.GHOST gO : Constants.GHOST.values()) {
			// puede ser edibletime 0 si da igual que vaya a dejar de ser comible en pocos
			// segundos o sacar la distancia a pacman
			
			//se van a repetir grupo pero da igual pq si es el mismo tamaÃ±o no se va a sobreescribir
			if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) > MAXTIME_EDIBLEGHOST && game.getGhostLairTime(gO) == 0 && game.getGhostEdibleTime(gO) > MAXTIME_EDIBLEGHOST) {

				if ( g != gO && game.getGhostCurrentNodeIndex(g) != -1 && game.getGhostCurrentNodeIndex(gO) != -1) {
				
					if (game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), game.getGhostCurrentNodeIndex(gO),
							game.getGhostLastMoveMade(g)) <= MAXDISTANCE_GHOSTSGROUP) {
							
						return true;
					}
				}
			}
		}
	}
	return false;

}
	

boolean powerPillSecure() {
	
	 int pacmanPos = game.getPacmanCurrentNodeIndex(); MOVE lastMove = game.getPacmanLastMoveMade();
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

			if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < MAXTIME_EDIBLEGHOST) {

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

		if (valid) {
		return true;
		}
	}

	return false;
}

int getCellIndex() {
	if (game.getGhostLairTime(GHOST.BLINKY) <= 0) {
		cell = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		return game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), cell, game.getPacmanLastMoveMade());
	}
	else return 99999;
}

}
