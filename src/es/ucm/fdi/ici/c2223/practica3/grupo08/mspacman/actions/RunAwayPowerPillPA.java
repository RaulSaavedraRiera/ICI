package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions;

import java.util.Arrays;

import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RunAwayPowerPillPA implements RulesAction{
	
	
		public RunAwayPowerPillPA() {
			
		}

		@Override
		public MOVE execute(Game game) {
	       
			 return game.getApproximateNextMoveTowardsTarget(
		        		game.getPacmanCurrentNodeIndex(), betterWay(game), game.getPacmanLastMoveMade(), DM.PATH);
		}

		@Override
		public void parseFact(Fact actionFact) {
			// Nothing to parse
			
		}

		@Override
		public String getActionId() {
			return "PacmanRunAwayPill";
			
		}
		
		int betterWay(Game game) {
			
			
			// obtenemos todos los nodos colindantes
			int[] neighbours = game.getNeighbouringNodes(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());

			// interseciion
			if (neighbours.length > 1) {

				// obtenemos la powerPill mas cercana
				int pp = getNearestPP(game);
				int pillsNumber = 0;
				int nextNode = -1;

				// exploramos cualquier ruta de la posicion donde esta
				for (int i = 0; i < neighbours.length; i++) {
					int t = wayValues(game, neighbours[i], pp);

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
	
	int getNearestPP(Game game)
	{
		int distance = 99999;
		int corner = -1;
		
		int currentDistance = 0;
		
		for(int c : game.getActivePowerPillsIndices()){
			
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
