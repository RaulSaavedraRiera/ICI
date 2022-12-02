package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.util.ArrayList;
import java.util.Arrays;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayCornerPA implements Action{
	
	
	ArrayList<Integer> corners = new ArrayList<Integer>();
	
	
	
		public RunAwayCornerPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
			
			//almacenamos las esquinas
			if (corners.isEmpty()) {
				for(int i = 0; i < game.getActivePowerPillsIndices().length; i++) {
					corners.add(game.getActivePowerPillsIndices()[i]);
				}
			}
			
			
	       
	        return game.getApproximateNextMoveTowardsTarget(
	        		game.getPacmanCurrentNodeIndex(), betterWay(game), game.getPacmanLastMoveMade(), DM.PATH);
		}


		@Override
		public String getActionId() {
			return "PacmanRunAwayCorner";
			
		}
		
		
		int betterWay(Game game) {
			
			
				// obtenemos todos los nodos colindantes
				int[] neighbours = game.getNeighbouringNodes(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());

				// interseciion
				if (neighbours.length > 1) {

					// obtenemos la powerPill mas cercana
					int corner = getNearestCorner(game);
					int pillsNumber = 0;
					int nextNode = -1;

					// exploramos cualquier ruta de la posicion donde esta
					for (int i = 0; i < neighbours.length; i++) {
						int t = wayValues(game, neighbours[i], corner);

						if (t != -1 && (nextNode == -1 || t > pillsNumber)) {
							nextNode = neighbours[i];
							pillsNumber = t;
						}
					}

					if (nextNode != -1)
					{
						return nextNode;
					}
						
				}
				
				return neighbours[0];
		}
		
		int wayValues(Game game, int ini, int corner) {
			int pills = 0;
			//presuponemos que la ruta es valida
			boolean valid = true;
			//var para lamacenar nodo siguiente al actual y nodo actual
			int nextNode = ini; int currentNode = game.getPacmanCurrentNodeIndex(); 
			
			//donde tomaremos los siguientes vecinos
			int[] nextNeighbours = null;
			
			
			do {						
				 //si el siguiente nodo es la powerPill no valido
				 if(nextNode == corner)
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
		
		int getNearestCorner(Game game)
		{
			int distance = 99999;
			int corner = -1;
			
			int currentDistance = 0;
			
			for(int c : corners){
				
				if(!Arrays.stream(game.getActivePowerPillsIndices()).anyMatch(i -> i == c)) {
					
					currentDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), corner);
					
					if(currentDistance < distance) {
						corner = c;
						distance = currentDistance;		
					}
				}
				
			}
			return corner;
		}

		
}
