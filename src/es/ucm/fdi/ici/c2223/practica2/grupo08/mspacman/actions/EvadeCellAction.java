package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import java.util.Arrays;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EvadeCellAction implements Action {
	
	
	int jailIndex = -1;
	
	public EvadeCellAction() {
		// TODO Auto-generated constructor stub
	}

    
	@Override
	public MOVE execute(Game game) {
		
		if (jailIndex == -1 && game.getGhostLairTime(GHOST.BLINKY) <= 0) {
			jailIndex = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		}
		
		int route = toNearestPill(game);
		
		//no interseccio, no relevante no puede girar
		if(route == -1) 
			return MOVE.NEUTRAL;
		else 
			return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), route, game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Evade Cell Action";
	}
	
	
	public int toNearestPill(Game game) {

		// si tiene que evitar powerPills

		// obtenemos todos los nodos colindantes
		int[] neighbours = game.getNeighbouringNodes(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());

		
		int nextNode = -1;
		// interseciion
		if (neighbours.length > 1) {
			
			int cell = 1;//obtener direccion de la cell
			int pillsNumber = -1;
			int distanceToPowerPill = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), cell);
			

			// exploramos cualquier ruta de la posicion donde esta
			for (int i = 0; i < neighbours.length; i++) {
				int t = noCellRoute(game, neighbours[i], cell);

				//esto no lo he testeado pero deberia funcionar
				if (t != -1 && distanceToPowerPill <   game.getShortestPathDistance(t, cell)
						&& (nextNode == -1 || t > pillsNumber)) {
					nextNode = neighbours[i];
					pillsNumber = t;
				}
			}
		}
		return nextNode;
	}

	
	int noCellRoute(Game game, int ini, int cell)
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
			 if(nextNode == cell)
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
	
	
}
