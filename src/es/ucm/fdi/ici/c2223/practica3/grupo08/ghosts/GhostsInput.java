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
	//private int pacmanNearestPP;
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
	private double[] distanceToPacmanWithSpeed;

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
	//private final int ORBITING_DISTANCE = 30;
	//private final int CHASING_TIME_LIMIT = 30;
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

		facts.add(String.format("(BLINKY (edible %s))", this.BLINKYedible));
		facts.add(String.format("(INKY (edible %s))", this.INKYedible));
		facts.add(String.format("(PINKY (edible %s))", this.PINKYedible));
		facts.add(String.format("(SUE (edible %s))", this.SUEedible));

		facts.add(String.format("(BLINKY (minBLINKYDistancePPill %d))",
				(int) this.minGhostsDistancePPill[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (minINKYDistancePPill %d))",
				(int) this.minGhostsDistancePPill[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (minPINKYDistancePPill %d))",
				(int) this.minGhostsDistancePPill[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (minSUEDistancePPill %d))",
				(int) this.minGhostsDistancePPill[GHOST.SUE.ordinal()]));

		facts.add(String.format("(MSPACMAN (mindistancePPill %d))", (int) this.minPacmanDistancePPill));

		facts.add(String.format("(BLINKY (anotherGhostNotEdible %s))",
				(boolean) this.thereIsAnotherGhostNotEdible[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (anotherGhostNotEdible %s))",
				(boolean) this.thereIsAnotherGhostNotEdible[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (anotherGhostNotEdible %s))",
				(boolean) this.thereIsAnotherGhostNotEdible[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (anotherGhostNotEdible %s))",
				(boolean) this.thereIsAnotherGhostNotEdible[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (anotherGhostEdible %s))",
				(boolean) this.thereIsAnotherGhostEdible[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (anotherGhostEdible %s))",
				(boolean) this.thereIsAnotherGhostEdible[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (anotherGhostEdible %s))",
				(boolean) this.thereIsAnotherGhostEdible[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (anotherGhostEdible %s))",
				(boolean) this.thereIsAnotherGhostEdible[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (anotherGhostInLair %s))",
				(boolean) this.thereIsAnotherGhostInLair[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (anotherGhostInLair %s))",
				(boolean) this.thereIsAnotherGhostInLair[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (anotherGhostInLair %s))",
				(boolean) this.thereIsAnotherGhostInLair[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (anotherGhostInLair %s))",
				(boolean) this.thereIsAnotherGhostInLair[GHOST.SUE.ordinal()]));
		
		facts.add(String.format("(BLINKY (intercept %s))",
				(boolean) this.ghostMustInterceptJunction[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (intercept %s))",
				(boolean) this.ghostMustInterceptJunction[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (intercept %s))",
				(boolean) this.ghostMustInterceptJunction[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (intercept %s))",
				(boolean) this.ghostMustInterceptJunction[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (distanceToPacman %d))", (int) this.distanceToPacman[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (distanceToPacman %d))", (int) this.distanceToPacman[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (distanceToPacman %d))", (int) this.distanceToPacman[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (distanceToPacman %d))", (int) this.distanceToPacman[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (distanceToLair %d))", (int) this.distanceToLair[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (distanceToLair %d))", (int) this.distanceToLair[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (distanceToLair %d))", (int) this.distanceToLair[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (distanceToLair %d))", (int) this.distanceToLair[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (remainingTime %d))", (int) this.BLINKYremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE ));
		facts.add(String.format("(INKY (remainingTime %d))", (int) this.INKYremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(PINKY (remainingTime %d))", (int) this.PINKYremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(SUE (remainingTime %d))", (int) this.SUEremainingTime + SAFETY_DISTANCE_WHEN_EDIBLE));

		facts.add(String.format("(BLINKY (position %d))", (int) this.ghostPositions[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(PINKY (position %d))", (int) this.ghostPositions[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(INKY (position %d))", (int) this.ghostPositions[GHOST.INKY.ordinal()]));
		facts.add(String.format("(SUE (position %d))", (int) this.ghostPositions[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (hasObjective %s))", (boolean) this.BLINKYhasObjective));
		facts.add(String.format("(PINKY (hasObjective %s))", (boolean) this.PINKYhasObjective));


		facts.add(String.format("(BLINKY (RANGE %d))", (int) this.GHOST_RANGE));
		facts.add(String.format("(INKY (RANGE %d))", (int) this.GHOST_RANGE));
		facts.add(String.format("(PINKY (RANGE %d))", (int) this.GHOST_RANGE));
		facts.add(String.format("(SUE (RANGE %d))", (int) this.GHOST_RANGE));

		facts.add(String.format("(BLINKY(PACMAN_MAX_DIST_TO_PP %d)", (int) this.PACMAN_MAX_DIST_TO_PP));
		facts.add(String.format("(INKY(PACMAN_MAX_DIST_TO_PP %d)", (int) this.PACMAN_MAX_DIST_TO_PP));
		facts.add(String.format("(PINKY(PACMAN_MAX_DIST_TO_PP %d)", (int) this.PACMAN_MAX_DIST_TO_PP));
		facts.add(String.format("(SUE(PACMAN_MAX_DIST_TO_PP %d)", (int) this.PACMAN_MAX_DIST_TO_PP));

		facts.add(String.format("(BLINKY(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int) this.SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(INKY(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int) this.SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(PINKY(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int) this.SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(SUE(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int) this.SAFETY_DISTANCE_WHEN_EDIBLE));

		facts.add(String.format("(BLINKY(SURE_DEATH_DISTANCE %d))", (int) this.SURE_DEATH_DISTANCE));
		facts.add(String.format("(INKY(SURE_DEATH_DISTANCE %d))", (int) this.SURE_DEATH_DISTANCE));
		facts.add(String.format("(PINKY(SURE_DEATH_DISTANCE %d))", (int) this.SURE_DEATH_DISTANCE));
		facts.add(String.format("(SUE(SURE_DEATH_DISTANCE %d))", (int) this.SURE_DEATH_DISTANCE));

		facts.add(String.format("(BLINKY (hasObjective %s))", (boolean) this.pacmanInCorner));
		facts.add(String.format("(INKY (hasObjective %s))", (boolean) this.pacmanInCorner));
		facts.add(String.format("(PINKY (hasObjective %s))", (boolean) this.pacmanInCorner));
		facts.add(String.format("(SUE (hasObjective %s))", (boolean) this.pacmanInCorner));

		facts.add(String.format("(BLINKY (distanceToPacmanWithSpeed  %d))", (int) this.distanceToPacmanWithSpeed [GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(PINKY (distanceToPacmanWithSpeed  %d))", (int) this.distanceToPacmanWithSpeed [GHOST.PINKY.ordinal()]));
		facts.add(String.format("(INKY (distanceToPacmanWithSpeed  %d))", (int) this.distanceToPacmanWithSpeed [GHOST.INKY.ordinal()]));
		facts.add(String.format("(SUE (distanceToPacmanWithSpeed  %d))", (int) this.distanceToPacmanWithSpeed [GHOST.SUE.ordinal()]));

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
		
		distanceToPacmanWithSpeed = new double[4];
		ghostPositions = new int[4];
		ghostNearestPP = new int[4];
		distanceToPacman = new int[4];
		distanceToLair = new int[4];
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
			
			distanceToPacmanWithSpeed[g.ordinal()]=game.getShortestPathDistance(ghostPositions[g.ordinal()], pacmanPosition,
					ghostLastMove[g.ordinal()]) * 0.5 * 0.5;

			thereIsAnotherGhostEdible = new boolean[] { false, false, false, false };
			thereIsAnotherGhostNotEdible = new boolean[] { false, false, false, false };
			thereIsAnotherGhostInLair = new boolean[] { false, false, false, false };
			ghostMustInterceptJunction = new boolean[] {false, false,false,false};

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
			}
			else if (g == GHOST.PINKY) {
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

		// power pill distances

		int closestPP = -1;
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
		int[] shortestPath = game.getShortestPath(closestPP, pacmanPosition);
		int closestJunctionFromPP = -1;
		for(int node : shortestPath){
			if(game.isJunction(node) && closestJunctionFromPP == -1) {
				closestJunctionFromPP = node;
			}
		}
		
		for(GHOST g: GHOST.values()) {
			if(game.getShortestPathDistance(ghostPositions[g.ordinal()],closestJunctionFromPP) < game.getShortestPathDistance(pacmanPosition,closestJunctionFromPP) ) {
				ghostMustInterceptJunction[g.ordinal()] = true;
			}
		}

	}

}
