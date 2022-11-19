package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts;

import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.rules.RulesInput;
import jess.Fact;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsInput extends RulesInput {

	private boolean BLINKYedible;
	private boolean INKYedible;
	private boolean PINKYedible;
	private boolean SUEedible;
	private int minPacmanDistancePPill;
	// private int pacmanNearestPP;
	private int pacmanPosition;
	private MOVE pacmanLastMove;

	private boolean[] thereIsAnotherGhostNotEdible;
	private boolean[] thereIsAnotherGhostEdible;
	private boolean[] thereIsAnotherGhostInLair;
	private boolean[] ghostMustInterceptJunction;

	private int[] ghostPositions;
	private int[] minGhostsDistancePPill;
	private int[] ghostNearestPP;
	private int[] distanceToPacman;
	private int[] distanceToLair;
	private int[] distanceToPacmanWithSpeed;

	private MOVE[] ghostLastMove;

	private boolean pacmanInCorner;
	private boolean BLINKYhasObjective;
	private boolean PINKYhasObjective;

	private int BLINKYdistanceToObjective;
	private int PINKYdistanceToObjective;

	private final int GHOST_RANGE = 50;
	private final int PACMAN_MAX_DIST_TO_PP = 40;
	private final int SAFETY_DISTANCE_WHEN_EDIBLE = 20;
	private final int SURE_DEATH_DISTANCE = 100;
	// private final int ORBITING_DISTANCE = 30;
	// private final int CHASING_TIME_LIMIT = 30;
	private int BLINKYremainingTime;
	private int INKYremainingTime;
	private int PINKYremainingTime;
	private int SUEremainingTime;

	private GhostData ghostData;

	public GhostsInput(Game game, GhostData gData) {
		super(game);

		ghostData = gData;
	}

	@Override
	public Collection<String> getFacts() {
		Vector<String> facts = new Vector<String>();

		facts.add(String.format(
				"(BLINKY (edible %s) (minDistancePPill %d) (anotherGhostNotEdible %s) (anotherGhostEdible %s) "
						+ "(anotherGhostInLair %s)) (intercept %s) (distanceToPacman %d) (distanceToLair %d) "
						+ "(remainingTime %d) (position %d) (RANGE %d) (PACMAN_MAX_DIST_TO_PP %d) (SAFETY_DISTANCE_WHEN_EDIBLE %d) "
						+ "(SURE_DEATH_DISTANCE %d) (pacmanInCorner %s) (distanceToPacmanWithSpeed %d) (hasObjective %s) "
						+ "(distanceToObjective %d) (pacmanDistanceToPPill %d)",
				this.BLINKYedible, (int) this.minGhostsDistancePPill[GHOST.BLINKY.ordinal()],
				this.thereIsAnotherGhostNotEdible[GHOST.BLINKY.ordinal()],
				this.thereIsAnotherGhostEdible[GHOST.BLINKY.ordinal()],
				this.thereIsAnotherGhostInLair[GHOST.BLINKY.ordinal()],
				this.ghostMustInterceptJunction[GHOST.BLINKY.ordinal()],
				(int) this.distanceToPacman[GHOST.BLINKY.ordinal()], (int) this.distanceToLair[GHOST.BLINKY.ordinal()],
				(int) this.BLINKYremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.ghostPositions[GHOST.BLINKY.ordinal()], (int) this.GHOST_RANGE,
				(int) this.PACMAN_MAX_DIST_TO_PP, (int) this.SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.SURE_DEATH_DISTANCE, this.pacmanInCorner,
				(int) this.distanceToPacmanWithSpeed[GHOST.BLINKY.ordinal()], this.BLINKYhasObjective,
				(int) this.BLINKYdistanceToObjective, (int) this.minPacmanDistancePPill));
		facts.add(String.format(
				"(PINKY (edible %s) (minDistancePPill %d) (anotherGhostNotEdible %s) (anotherGhostEdible %s) "
						+ "(anotherGhostInLair %s)) (intercept %s) (distanceToPacman %d) (distanceToLair %d) "
						+ "(remainingTime %d) (position %d) (RANGE %d) (PACMAN_MAX_DIST_TO_PP %d) (SAFETY_DISTANCE_WHEN_EDIBLE %d) "
						+ "(SURE_DEATH_DISTANCE %d) (pacmanInCorner %s) (distanceToPacmanWithSpeed %d) (hasObjective %s) "
						+ "(distanceToObjective %d) (pacmanDistanceToPPill %d)",
				this.PINKYedible, (int) this.minGhostsDistancePPill[GHOST.PINKY.ordinal()],
				this.thereIsAnotherGhostNotEdible[GHOST.PINKY.ordinal()],
				this.thereIsAnotherGhostEdible[GHOST.PINKY.ordinal()],
				this.thereIsAnotherGhostInLair[GHOST.PINKY.ordinal()],
				this.ghostMustInterceptJunction[GHOST.PINKY.ordinal()],
				(int) this.distanceToPacman[GHOST.PINKY.ordinal()], (int) this.distanceToLair[GHOST.PINKY.ordinal()],
				(int) this.PINKYremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.ghostPositions[GHOST.PINKY.ordinal()], (int) this.GHOST_RANGE,
				(int) this.PACMAN_MAX_DIST_TO_PP, (int) this.SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.SURE_DEATH_DISTANCE, this.pacmanInCorner,
				(int) this.distanceToPacmanWithSpeed[GHOST.PINKY.ordinal()], this.PINKYhasObjective,
				this.PINKYdistanceToObjective, (int) this.minPacmanDistancePPill));
		facts.add(String.format(
				"(INKY (edible %s) (minDistancePPill %d) (anotherGhostNotEdible %s) (anotherGhostEdible %s) "
						+ "(anotherGhostInLair %s)) (intercept %s) (distanceToPacman %d) (distanceToLair %d) "
						+ "(remainingTime %d) (position %d) (RANGE %d) (PACMAN_MAX_DIST_TO_PP %d) (SAFETY_DISTANCE_WHEN_EDIBLE %d) "
						+ "(SURE_DEATH_DISTANCE %d) (pacmanInCorner %s) (distanceToPacmanWithSpeed %d) (pacmanDistanceToPPill %d)",
				this.INKYedible, (int) this.minGhostsDistancePPill[GHOST.INKY.ordinal()],
				this.thereIsAnotherGhostNotEdible[GHOST.INKY.ordinal()],
				this.thereIsAnotherGhostEdible[GHOST.INKY.ordinal()],
				this.thereIsAnotherGhostInLair[GHOST.INKY.ordinal()],
				this.ghostMustInterceptJunction[GHOST.INKY.ordinal()],
				(int) this.distanceToPacman[GHOST.INKY.ordinal()], (int) this.distanceToLair[GHOST.INKY.ordinal()],
				(int) this.INKYremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.ghostPositions[GHOST.INKY.ordinal()], (int) this.GHOST_RANGE,
				(int) this.PACMAN_MAX_DIST_TO_PP, (int) this.SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.SURE_DEATH_DISTANCE, this.pacmanInCorner,
				(int) this.distanceToPacmanWithSpeed[GHOST.INKY.ordinal()], (int) this.minPacmanDistancePPill));
		facts.add(String.format(
				"(SUE (edible %s) (minDistancePPill %d) (anotherGhostNotEdible %s) (anotherGhostEdible %s) "
						+ "(anotherGhostInLair %s)) (intercept %s) (distanceToPacman %d) (distanceToLair %d) "
						+ "(remainingTime %d) (position %d) (RANGE %d) (PACMAN_MAX_DIST_TO_PP %d) (SAFETY_DISTANCE_WHEN_EDIBLE %d) "
						+ "(SURE_DEATH_DISTANCE %d) (pacmanInCorner %s) (distanceToPacmanWithSpeed %d) (pacmanDistanceToPPill %d)",
				this.SUEedible, (int) this.minGhostsDistancePPill[GHOST.SUE.ordinal()],
				this.thereIsAnotherGhostNotEdible[GHOST.SUE.ordinal()],
				this.thereIsAnotherGhostEdible[GHOST.SUE.ordinal()],
				this.thereIsAnotherGhostInLair[GHOST.SUE.ordinal()],
				this.ghostMustInterceptJunction[GHOST.SUE.ordinal()], (int) this.distanceToPacman[GHOST.SUE.ordinal()],
				(int) this.distanceToLair[GHOST.SUE.ordinal()],
				(int) this.SUEremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.ghostPositions[GHOST.SUE.ordinal()], (int) this.GHOST_RANGE,
				(int) this.PACMAN_MAX_DIST_TO_PP, (int) this.SAFETY_DISTANCE_WHEN_EDIBLE,
				(int) this.SURE_DEATH_DISTANCE, this.pacmanInCorner,
				(int) this.distanceToPacmanWithSpeed[GHOST.SUE.ordinal()], (int) this.minPacmanDistancePPill));

		return facts;
	}

	public void parseFact(Fact actionFact) {

	}

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub

		// edible
		BLINKYedible = game.isGhostEdible(GHOST.BLINKY);
		PINKYedible = game.isGhostEdible(GHOST.PINKY);
		INKYedible = game.isGhostEdible(GHOST.INKY);
		SUEedible = game.isGhostEdible(GHOST.SUE);

		// positions, distances and last moves
		pacmanPosition = game.getPacmanCurrentNodeIndex();
		pacmanLastMove = game.getPacmanLastMoveMade();

		distanceToPacmanWithSpeed = new int[4];
		ghostPositions = new int[4];
		ghostNearestPP = new int[4];
		distanceToPacman = new int[4];
		distanceToLair = new int[] { 0, 0, 0, 0 };
		minGhostsDistancePPill = new int[4];
		ghostLastMove = new MOVE[4];
		for (GHOST g : GHOST.values()) {

			try {
				ghostLastMove[g.ordinal()] = game.getGhostLastMoveMade(g);

			} catch (Exception e) {
				ghostLastMove[g.ordinal()] = MOVE.NEUTRAL;
			}
			ghostPositions[g.ordinal()] = game.getGhostCurrentNodeIndex(g);

			distanceToPacman[g.ordinal()] = game.getShortestPathDistance(ghostPositions[g.ordinal()], pacmanPosition,
					ghostLastMove[g.ordinal()]);

			distanceToPacmanWithSpeed[g.ordinal()] = game.getShortestPathDistance(ghostPositions[g.ordinal()],
					pacmanPosition, ghostLastMove[g.ordinal()]) * 2;

			thereIsAnotherGhostEdible = new boolean[] { false, false, false, false };
			thereIsAnotherGhostNotEdible = new boolean[] { false, false, false, false };
			thereIsAnotherGhostInLair = new boolean[] { false, false, false, false };
			ghostMustInterceptJunction = new boolean[] { false, false, false, false };

			// ghost edible or not
			for (GHOST g2 : GHOST.values()) {
				if (g2 != g) {
					thereIsAnotherGhostEdible[g.ordinal()] = thereIsAnotherGhostEdible[g.ordinal()]
							|| game.isGhostEdible(g2);

					thereIsAnotherGhostNotEdible[g.ordinal()] = thereIsAnotherGhostNotEdible[g.ordinal()]
							|| !game.isGhostEdible(g2);

					thereIsAnotherGhostInLair[g.ordinal()] = thereIsAnotherGhostInLair[g.ordinal()]
							|| game.getGhostLairTime(g2) == 0;
				}
			}

			if (ghostData != null) {
				// objectives
				if (g == GHOST.BLINKY) {
					if (ghostData.getGhostObjective(g) == -1) {
						BLINKYhasObjective = false;
						BLINKYdistanceToObjective = 0;
					}

					else {
						BLINKYhasObjective = true;
						BLINKYdistanceToObjective = game.getShortestPathDistance(ghostPositions[g.ordinal()],
								ghostData.getGhostObjective(g), ghostLastMove[g.ordinal()]);
					}
				} else if (g == GHOST.PINKY) {
					if (ghostData.getGhostObjective(g) == -1) {
						PINKYhasObjective = false;
						PINKYdistanceToObjective = 0;
					}

					else {
						PINKYhasObjective = true;
						PINKYdistanceToObjective = game.getShortestPathDistance(ghostPositions[g.ordinal()],
								ghostData.getGhostObjective(g), ghostLastMove[g.ordinal()]);
					}
				}
			}
		}
		// power pill distances

		int closestPP = -1;
		minPacmanDistancePPill = Integer.MAX_VALUE;
		for (int pp : game.getActivePowerPillsIndices()) {
			int dist = game.getShortestPathDistance(pacmanPosition, pp, pacmanLastMove);

			if (dist < minPacmanDistancePPill) {
				minPacmanDistancePPill = dist;
				closestPP = pp;
			}
			for (GHOST g : GHOST.values()) {
				dist = game.getShortestPathDistance(ghostPositions[g.ordinal()], pp, ghostLastMove[g.ordinal()]);

				if (dist < minGhostsDistancePPill[g.ordinal()]) {
					minGhostsDistancePPill[g.ordinal()] = dist;
					ghostNearestPP[g.ordinal()] = pp;
				}
			}
		}

		if (game.getNumberOfActivePowerPills() > 0) {
			int[] shortestPath = game.getShortestPath(closestPP, pacmanPosition);
			int closestJunctionFromPP = -1;
			for (int node : shortestPath) {
				if (game.isJunction(node) && closestJunctionFromPP == -1) {
					closestJunctionFromPP = node;
				}
			}

			for (GHOST g : GHOST.values()) {
				if (game.getShortestPathDistance(ghostPositions[g.ordinal()], closestJunctionFromPP) < game
						.getShortestPathDistance(pacmanPosition, closestJunctionFromPP)) {
					ghostMustInterceptJunction[g.ordinal()] = true;
				}
			}
		}
	}

}
