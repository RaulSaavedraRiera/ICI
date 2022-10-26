package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman;

import java.util.Arrays;

import es.ucm.fdi.ici.Input;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.Constants.GHOST;

public class MsPacManInput extends Input {

	
	final int ENOUGH_PILLS_NEAR = 15;
	
	final int MAXDISTANCE_GHOSTSEDIBLE = 20;
	final int MAXDISTANCE_GHOSTSCHASING = 20;
	final int MAXDISTANCE_PILLSNEAR = 100;
	
	private int pacmanDistanceNearPowerPill;
	private int pacmanDistanceCell;
	private int pacmanDistanceNearCorner;
	
	private int edibleGhostsInRange;
	private int chasingGhostsInRange;
	
	private int nearPills;
	private boolean secureRoute;
	private boolean ghostFollowPacman;
	
	
	private int cell;
	
	
	public MsPacManInput(Game game) {
		super(game);
		
	}

	@Override
	public void parseInput() {
		
		// does nothing.
		pacmanDistanceNearPowerPill = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),  getNearestPoint(game.getActivePillsIndices()), game.getPacmanLastMoveMade());
		//habra que meter lo de obtener casilla de celda aqui?
		pacmanDistanceCell = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), cell, game.getPacmanLastMoveMade());
		
		pacmanDistanceNearCorner = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),  getNearestCorner(), game.getPacmanLastMoveMade());
		
		edibleGhostsInRange = getGhostsNear(true);
		chasingGhostsInRange = getGhostsNear(false);
		
		nearPills = getPillsNear();
		
		
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
	
	
	int n;
	
	int[] pills = game.getActivePillsIndices();
	
	if (pills.length == 0)
		return 0;

	// variables para controlar las posiciones


	for (int pill : pills) // comprobamos para las pill cual es la mas cercana
	{
		if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade()) <= MAXDISTANCE_PILLSNEAR) {
			n++;
	}

	// devolvemos la pill mas cercana
	return n;
}
	
	
	
	
	
	

}
