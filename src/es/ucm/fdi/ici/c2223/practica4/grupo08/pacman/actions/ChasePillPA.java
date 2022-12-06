package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.util.Arrays;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class ChasePillPA implements Action{
	
	MsPacManFuzzyMemory memory;
	
		public ChasePillPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
	       
	        return game.getApproximateNextMoveTowardsTarget(
	        		game.getPacmanCurrentNodeIndex(), getBetterPillWay(game), game.getPacmanLastMoveMade(), DM.PATH);
		}


		@Override
		public String getActionId() {
			return "ChasePILL";
			
		}
		
		int getBetterPillWay(Game game) {
		
				// obtenemos todos los nodos colindantes
				int[] neighbours = game.getNeighbouringNodes(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());

				// interseciion
				if (neighbours.length > 1) {

				
					int pillsNumber = 0;
					int nextNode = -1;

					// exploramos cualquier ruta de la posicion donde esta
					for (int i = 0; i < neighbours.length; i++) {
						int t = wayValues(game, neighbours[i]);

						if (t != -1 && (nextNode == -1 || t > pillsNumber)) {
							nextNode = neighbours[i];
							pillsNumber = t;
						}
					}
					
					
					return nextNode;
						
				}
				
				return neighbours[0];
		
		}
		
		int wayValues(Game game, int ini) {

			int pills = 0;
			// presuponemos que la ruta es valida
			boolean valid = true;
			// var para lamacenar nodo siguiente al actual y nodo actual
			int nextNode = ini;
			int currentNode = game.getPacmanCurrentNodeIndex();

			// donde tomaremos los siguientes vecinos
			int[] nextNeighbours = null;

			do {
				// obten los vecinos del siguiente nodo
				nextNeighbours = game.getNeighbouringNodes(nextNode,
						game.getApproximateNextMoveTowardsTarget(currentNode, nextNode, MOVE.NEUTRAL, DM.PATH));

				// avanzamos una posicion
				final int node = currentNode = nextNode;

				// comprobamos si hay pildora en esta casilla
				if (Arrays.stream(game.getActivePillsIndices()).anyMatch(i -> i == node))
					pills++;

				// solo debe haber 1
				nextNode = nextNeighbours[0];

			}

			while (nextNeighbours.length < 2);

			return pills;

		}

}
