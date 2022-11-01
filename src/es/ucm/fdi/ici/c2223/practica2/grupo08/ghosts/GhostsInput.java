package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts;

import java.util.EnumMap;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsInput extends Input {

	private boolean BLINKYedible;
	private boolean INKYedible;
	private boolean PINKYedible;
	private boolean SUEedible;
	private double minPacmanDistancePPill;

	private int[] ghostPositions;
	private int[] distanceToObjective;

	private int[] junctionsIndices;
	private MOVE[][] possibleDirections;

	private final int GHOST_RANGE = 50;
	private final int PACMAN_MAX_DIST_TO_PP = 40;
	private final int SAFETY_DISTANCE_WHEN_EDIBLE = 20;
	private EnumMap<GHOST, Double> minGhostsDistancePPill;
	private int BLINKYremainingTime;
	private int INKYremainingTime;
	private int PINKYremainingTime;
	private int SUEremainingTime;
	private EnumMap<GHOST, Integer> distanceToPacman;
	GhostData gData;

	public GhostsInput(Game game, GhostData ghostData) {
		super(game);

		minGhostsDistancePPill = new EnumMap<GHOST, Double>(GHOST.class);
		distanceToPacman = new EnumMap<GHOST, Integer>(GHOST.class);

		gData = ghostData;
	}

	@Override
	public void parseInput() {

		if (gData != null) {
			
			if (minGhostsDistancePPill == null)
				minGhostsDistancePPill = new EnumMap<GHOST, Double>(GHOST.class);
			
			if (distanceToPacman == null)
				distanceToPacman = new EnumMap<GHOST, Integer>(GHOST.class);

			this.BLINKYedible = game.isGhostEdible(GHOST.BLINKY);
			this.INKYedible = game.isGhostEdible(GHOST.INKY);
			this.PINKYedible = game.isGhostEdible(GHOST.PINKY);
			this.SUEedible = game.isGhostEdible(GHOST.SUE);
			this.BLINKYremainingTime = game.getGhostEdibleTime(GHOST.BLINKY);
			this.INKYremainingTime = game.getGhostEdibleTime(GHOST.INKY);
			this.PINKYremainingTime = game.getGhostEdibleTime(GHOST.PINKY);
			this.SUEremainingTime = game.getGhostEdibleTime(GHOST.SUE);

			int pacman = game.getPacmanCurrentNodeIndex();

			for (GHOST g : GHOST.values()) {
				this.minGhostsDistancePPill.put(g, Double.MAX_VALUE);
				this.distanceToPacman.put(g, game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pacman));

				ghostPositions[g.ordinal()] = game.getGhostCurrentNodeIndex(g);

				this.possibleDirections[g.ordinal()] = game.getPossibleMoves(ghostPositions[g.ordinal()],
						game.getGhostLastMoveMade(g));

				if (gData.currentGhostDest[g.ordinal()] != -1)
					distanceToObjective[g.ordinal()] = game.getShortestPathDistance(ghostPositions[g.ordinal()],
							gData.currentGhostDest[g.ordinal()], gData.ghostNextMoves[g.ordinal()]);
				else
					distanceToObjective[g.ordinal()] = -1;
			}

			this.junctionsIndices = game.getJunctionIndices();

			this.minPacmanDistancePPill = Double.MAX_VALUE;
			for (int ppill : game.getPowerPillIndices()) {
				double distance = game.getDistance(pacman, ppill, DM.PATH);
				this.minPacmanDistancePPill = Math.min(distance, this.minPacmanDistancePPill);
				for (Constants.GHOST ghostType : Constants.GHOST.values()) {
					double distance1 = game.getDistance(game.getGhostCurrentNodeIndex(ghostType), ppill, DM.PATH);
					this.minGhostsDistancePPill.put(ghostType,
							Math.min(distance1, this.minGhostsDistancePPill.get(ghostType)));
				}
			}
		}
	}

	public int[] getDistanceToObjective() {
		return distanceToObjective;
	}

	public MOVE[] getPossibleDirections(GHOST ghost) {
		return possibleDirections[ghost.ordinal()];
	}

	public int[] getGhostPositions() {
		return ghostPositions;
	}

	public int[] getJunctionsIndices() {
		return junctionsIndices;
	}

	public boolean isBLINKYedible() {
		return BLINKYedible;
	}

	public boolean isINKYedible() {
		return INKYedible;
	}

	public boolean isPINKYedible() {
		return PINKYedible;
	}

	public boolean isSUEedible() {
		return SUEedible;
	}

	public double getMinPacmanDistancePPill() {
		return minPacmanDistancePPill;
	}

	public int getSAFETY_DISTANCE_WHEN_EDIBLE() {
		return SAFETY_DISTANCE_WHEN_EDIBLE;
	}

	public int getPACMAN_MAX_DIST_TO_PP() {
		return PACMAN_MAX_DIST_TO_PP;
	}

	public int getGHOST_RANGE() {
		return GHOST_RANGE;
	}

	public int getRemainingTime(GHOST ghost) {
		switch (ghost) {
		case BLINKY:
			return BLINKYremainingTime;
		case INKY:
			return INKYremainingTime;
		case PINKY:
			return PINKYremainingTime;
		case SUE:
			return SUEremainingTime;
		default:
			return -1;
		}
	}

	public double getMinGhostDistancePPill(GHOST g) {
		return minGhostsDistancePPill.get(g);
	}

	public int getDistanceToPacman(GHOST g) {
		return distanceToPacman.get(g);
	}

}
