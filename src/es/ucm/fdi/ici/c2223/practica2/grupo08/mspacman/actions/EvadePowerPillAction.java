package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import java.util.Arrays;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EvadePowerPillAction implements Action {
	
	final int CRITICAL_DISTANCE = 8;
	
	public EvadePowerPillAction() {
		// TODO Auto-generated constructor stub
	}

    
	@Override
	public MOVE execute(Game game) {
		
		int route = toNearestPill(game);
		
		//no interseccio, no relevante no puede girar
		if(route == -1) 
			return MOVE.NEUTRAL;
		
		else		
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), route, game.getPacmanLastMoveMade(), DM.PATH);
			
		
			
		}

	@Override
	public String getActionId() {
		return "Evade PowerPill Action";
	}
	

	
	public int toNearestPill(Game game) {

		// si tiene que evitar powerPills

		// obtenemos todos los nodos colindantes
		int[] neighbours = game.getNeighbouringNodes(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());

		
		int nextNode = -1;
		// interseciion
		if (neighbours.length > 1) {

			// obtenemos la powerPill mas cercana
			int pP = getNearestPill(game);
			int pillsNumber = -1;
			int distanceToPowerPill = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pP);
			

			// exploramos cualquier ruta de la posicion donde esta
			for (int i = 0; i < neighbours.length; i++) {
				int t = noPowerPillRoute(game, neighbours[i], pP);

				//esto no lo he testeado pero deberia funcionar
				if (t != -1 && distanceToPowerPill <   game.getShortestPathDistance(t, pP)
						&& (nextNode == -1 || t > pillsNumber)) {
					nextNode = neighbours[i];
					pillsNumber = t;
				}
			}
		}
		return nextNode;
	}

	
	int noPowerPillRoute(Game game, int ini, int pP)
	{
		int pills = 0;
		//presuponemos que la ruta es valida
		boolean valid = true;
		//var para lamacenar nodo siguiente al actual y nodo actual
		int nextNode = ini; int currentNode = game.getPacmanCurrentNodeIndex(); 
		
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
	
	int getNearestPill(Game game) {
		
		int[] pills = game.getActivePowerPillsIndices();
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
}
