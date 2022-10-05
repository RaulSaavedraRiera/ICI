package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class Ghosts extends GhostController {
	
	private final int GHOST_RANGE = 50;
	private final int PACMAN_MAX_DIST_TO_PP = 40;
	private final int SAFETY_DISTANCE_WHEN_EDIBLE = 20;
	
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private MOVE[] allMoves = MOVE.values();
	private Random rnd = new Random();
	
	private int[] currentGhostDest = new int[] { -1, -1, -1, -1 };

	private JunctionManager junctionMan = new JunctionManager();

	public Ghosts() 
	{
		super();

		 setName("Ghost 12");

		 setTeam("Team 12");
	}
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {

		// limpiamos movimientos
		moves.clear();

		junctionMan.update();

		// para cada uno de los fantasmas
		for (Constants.GHOST ghostType : Constants.GHOST.values()) {
			if (game.doesGhostRequireAction(ghostType)) {

				int ghostNode = game.getGhostCurrentNodeIndex(ghostType);
				MOVE ghostLastMoveMade = game.getGhostLastMoveMade(ghostType);
				int pacmanNode = game.getPacmanCurrentNodeIndex();
				MOVE pacmanLastMoveMade = game.getPacmanLastMoveMade();

				int dist = game.getShortestPathDistance(ghostNode, pacmanNode);

				if (!game.isGhostEdible(ghostType)) {
					// fuera de rango
					if (dist > GHOST_RANGE) {

						// si actualmente el fantasma tiene un objetivo
						if (currentGhostDest[ghostType.ordinal()] != -1) {

							GameView.addLines(game, Color.GREEN, ghostNode, currentGhostDest[ghostType.ordinal()]);

							MOVE move = game.getApproximateNextMoveTowardsTarget(ghostNode,
									currentGhostDest[ghostType.ordinal()], ghostLastMoveMade, DM.PATH);

							moves.put(ghostType, move);

							// si ha llegado al destino cambia el objetivo
							if (game.getShortestPathDistance(ghostNode, currentGhostDest[ghostType.ordinal()]) <= 20) {
								currentGhostDest[ghostType.ordinal()] = -1;
							}

							break;
						}

						// 2 van a por pacman evitándose, marcan en el array de marcas las
						// intersecciones
						if (ghostType == GHOST.BLINKY || ghostType == GHOST.PINKY) {
							MOVE move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
									ghostLastMoveMade, DM.PATH);

							move = junctionMan.getNextAvailableMove(ghostNode, move, ghostLastMoveMade);

							moves.put(ghostType, move);
						}

						// 2 van a una zona cercana a pacman sin entrar en
						// elige un nodo aleatorio del mapa (que sea interseccion) y mira si la
						// distancia hasta pacman es menos
						// que 2 veces el rango
						else {
							Boolean close = false;

							int randomNode = -1;

							int[] junctions = game.getJunctionIndices();

							while (!close) {

								int randomIndex = rnd.nextInt(junctions.length);
								randomNode = junctions[randomIndex];

								int distance = game.getShortestPathDistance(randomNode, pacmanNode);

								if (distance < GHOST_RANGE * 2)
									close = true;
							}

							currentGhostDest[ghostType.ordinal()] = randomNode;

							MOVE move = game.getApproximateNextMoveTowardsTarget(ghostNode, randomNode,
									ghostLastMoveMade, DM.PATH);

							move = junctionMan.getNextAvailableMove(ghostNode, move, ghostLastMoveMade);

							moves.put(ghostType, move);
						}
					}

					// pacman en rango
					else {

						GameView.addLines(game, Color.RED, pacmanNode, ghostNode);

						// si habia un objetivo, pero ha entrado en rango, quita dicho objetivo
						if (currentGhostDest[ghostType.ordinal()] != -1)
							currentGhostDest[ghostType.ordinal()] = -1;

						int closestPP = getClosestPowerPill(game, pacmanNode, pacmanLastMoveMade);

						int pacmanDistToPP = game.getShortestPathDistance(pacmanNode, closestPP, pacmanLastMoveMade);

						// si pacman esta cerca de la PP
						if (pacmanDistToPP < PACMAN_MAX_DIST_TO_PP) {
							int ghostDistanceToPP = game.getShortestPathDistance(ghostNode, closestPP,
									pacmanLastMoveMade);

							// si el fantasma llega antes a la PP va a por ella
							if (ghostDistanceToPP < pacmanDistToPP) {
								MOVE move = game.getApproximateNextMoveTowardsTarget(ghostNode, closestPP,
										ghostLastMoveMade, DM.PATH);

								moves.put(ghostType, move);
							}

							// si no huye
							else {
								MOVE move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
										ghostLastMoveMade, DM.PATH);

								moves.put(ghostType, move);
							}
						}

						// Si no esta cerca de PP va hacia donde se dirige Pacman
						else {
							MOVE move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
									ghostLastMoveMade, DM.PATH);

							move = junctionMan.getNextAvailableMove(ghostNode, move, ghostLastMoveMade);

							moves.put(ghostType, move);
						}
					}
				}
				
				//si el fantasma es comestible
				else 
				{
					int remainingTime = game.getGhostEdibleTime(ghostType);
					
					float speed = 0.5f;
					
					//si calcula que se acaba el tiempo restante antes de llegar a pacman
					//(calculando que pacman persiga al fantasma directamente)
					if (dist * speed * 0.5 > remainingTime + SAFETY_DISTANCE_WHEN_EDIBLE) 
					{
						MOVE move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode,
								ghostLastMoveMade, DM.PATH);

						move = junctionMan.getNextAvailableMove(ghostNode, move, ghostLastMoveMade);

						moves.put(ghostType, move);
					}
					
					//si no, huye de pacman
					else 
					{
						MOVE move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode,
								ghostLastMoveMade, DM.PATH);

						moves.put(ghostType, move);
					}
				}
			}
		}
		return moves;
	}

	// Devuelve la power pill mas cercana al nodo dado, teniendo en cuenta el ultimo
	// movimiento realizado
	private int getClosestPowerPill(Game game, int nodeFrom, MOVE lastMove) {

		int[] pPills = game.getActivePowerPillsIndices();

		int minDistPillNode = 0;
		int minDist = Integer.MAX_VALUE;
		for (int pillNode : pPills) {
			int dist = game.getShortestPathDistance(nodeFrom, pillNode, lastMove);

			if (dist < minDist) {
				minDist = dist;
				minDistPillNode = pillNode;
			}
		}

		return minDistPillNode;
	}

}
