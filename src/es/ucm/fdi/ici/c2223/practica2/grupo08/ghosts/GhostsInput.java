package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts;

import java.util.ArrayList;

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
	private int minPacmanDistancePPill;

	private int[] ghostPositions;
	private int[] distanceToObjective;
	private int[] minGhostsDistancePPill;
	private int[] distanceToPacman;

	private int[] junctionsIndices;
	private MOVE[][] possibleDirections;

	private final int GHOST_RANGE = 50;
	private final int PACMAN_MAX_DIST_TO_PP = 40;
	private final int SAFETY_DISTANCE_WHEN_EDIBLE = 20;
	private int BLINKYremainingTime;
	private int INKYremainingTime;
	private int PINKYremainingTime;
	private int SUEremainingTime;
	GhostData gData;

	public GhostsInput(Game game, ArrayList<GhostData> ghostData) {
		super(game);

		gData = ghostData.get(0);
		parseInput();
	}

	@Override
	public void parseInput() {

		if (minGhostsDistancePPill == null)
			minGhostsDistancePPill = new int[4];

		if (distanceToObjective == null)
			distanceToObjective = new int[4];

		if (distanceToPacman == null)
			distanceToPacman = new int[4];

		if (ghostPositions == null)
			ghostPositions = new int[4];

		if (possibleDirections == null)
			possibleDirections = new MOVE[4][];

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
			this.minGhostsDistancePPill[g.ordinal()] = Integer.MAX_VALUE;
			this.distanceToPacman[g.ordinal()] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pacman);

			ghostPositions[g.ordinal()] = game.getGhostCurrentNodeIndex(g);

			this.possibleDirections[g.ordinal()] = game.getPossibleMoves(ghostPositions[g.ordinal()],
					game.getGhostLastMoveMade(g));

			if (gData != null) {
				gData.setGhostMove(g, game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(g), pacman,
						game.getGhostLastMoveMade(g), DM.PATH));

				if (gData.getGhostObjective(g) != -1) {
					distanceToObjective[g.ordinal()] = game.getShortestPathDistance(ghostPositions[g.ordinal()],
							gData.getGhostObjective(g), game.getGhostLastMoveMade(g));

				} else
					distanceToObjective[g.ordinal()] = 0;

			}
		}
		this.junctionsIndices = game.getJunctionIndices();

		this.minPacmanDistancePPill = Integer.MAX_VALUE;
		for (int ppill : game.getActivePowerPillsIndices()) {
			
			int distance = game.getShortestPathDistance(pacman, ppill, game.getPacmanLastMoveMade());
			this.minPacmanDistancePPill = Math.min(distance, this.minPacmanDistancePPill);
			for (Constants.GHOST ghostType : Constants.GHOST.values()) {
				int distance1 = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), ppill,
						game.getGhostLastMoveMade(ghostType));

				minGhostsDistancePPill[ghostType.ordinal()] = Math.min(distance1,
						this.minGhostsDistancePPill[ghostType.ordinal()]);
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

	public int getMinPacmanDistancePPill() {
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

		return minGhostsDistancePPill[g.ordinal()];
	}

	public int getDistanceToPacman(GHOST g) {

		return distanceToPacman[g.ordinal()];
	}

}
