package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsInput extends FuzzyInput {

	private boolean pacmanVisible;
	private boolean pacmanInCorner;
	private boolean[] edible;
	private boolean[] anotherGhostEdible;
	private boolean[] anotherGhostNotEdible;
	private boolean[] anotherGhostInLair;

	private int pacmanDistToPP;
	private int pacmanNearestPP;
	private int[] distanceToPacmanLastPosition;
	private int[] distanceToAsignedPP;
	private int[] distanceToLair;
	private int[] remainingTime;
	private int[] distanceToNearestPP;
	private double[] distanceToPacmanWithSpeed;

	private int mostProbablePacmanPos;
	private int mostProbablePacmanPosPoints;

	private GhostsFuzzyMemory mem;
	private MsPacmanPredictor pacmanPredictor;

	public GhostsInput(Game game, GhostsFuzzyMemory ghostsMem, MsPacmanPredictor pacmanPredictor) {
		super(game);
		// TODO Auto-generated constructor stub
		mem = ghostsMem;
		this.pacmanPredictor = pacmanPredictor;
	}

	public boolean isPacmanVisible() {
		return game.getPacmanCurrentNodeIndex() != -1;
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		// TODO Auto-generated method stub

		HashMap<String, Double> vars = new HashMap<String, Double>();

		vars.put("pacmanDistanceToPPill", (double) pacmanDistToPP);
		vars.put("pacmanInCorner", parseBoolToDouble(pacmanInCorner));
		
		return vars;
	}

	public HashMap<String, Double> getFuzzyValues(GHOST g) {
		// TODO Auto-generated method stub

		HashMap<String, Double> vars = new HashMap<String, Double>();

		vars.put("distanceToPacman", (double) distanceToPacmanLastPosition[g.ordinal()]);
		vars.put("distanceToPacmanWithSpeed", distanceToPacmanWithSpeed[g.ordinal()]);
		vars.put("edible", parseBoolToDouble(edible[g.ordinal()]));
		vars.put("anotherGhostEdible", parseBoolToDouble(anotherGhostEdible[g.ordinal()]));
		vars.put("anotherGhostNotEdible", parseBoolToDouble(anotherGhostNotEdible[g.ordinal()]));
		vars.put("anotherGhostInLair", parseBoolToDouble(anotherGhostInLair[g.ordinal()]));
		vars.put("distanceToLair", (double) distanceToLair[g.ordinal()]);
		vars.put("remainingTime", (double) remainingTime[g.ordinal()]);
		vars.put("arrivesFirstToPP",
				parseBoolToDouble(pacmanNearestPP != -1 && (mem.getGhostNearestPP(g) == pacmanNearestPP)
						&& (distanceToNearestPP[g.ordinal()] < pacmanDistToPP)));

		return vars;
	}

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub
		pacmanVisible = false;
		pacmanInCorner = false;

		distanceToPacmanLastPosition = new int[4];
		distanceToPacmanWithSpeed = new double[4];
		distanceToAsignedPP = new int[4];
		remainingTime = new int[4];
		distanceToLair = new int[4];
		distanceToNearestPP = new int[] { 2000, 2000, 2000, 2000 };

		edible = new boolean[4];
		anotherGhostEdible = new boolean[4];
		anotherGhostNotEdible = new boolean[4];
		anotherGhostInLair = new boolean[4];

		if (mem == null)
			return;

		// coger posicion inicial de pacman
		if (mem.getPacmanLastPosition() == -1) {
			mem.setPacmanLastPosition(game.getCurrentMaze().initialPacManNodeIndex);

			// inicializamos las PP asignadas a cada fantasma
			int cont = 0;
			for (int pp : game.getCurrentMaze().powerPillIndices) {
				mem.setGhostAsignedPP(GHOST.values()[cont], pp);
				cont++;
			}
		}

		for (GHOST g : GHOST.values()) {
			int pacmanPos = game.getPacmanCurrentNodeIndex();
			if (pacmanPos != -1) {
				pacmanVisible = true;
				pacmanPredictor.reset();

				mem.setPacmanLastPosition(pacmanPos);
				mem.setPacmanLastDirection(game.getPacmanLastMoveMade());
			}

			int pos = game.getGhostCurrentNodeIndex(g);
			mem.setGhostPostion(g, pos);
			MOVE lastMove = game.getGhostLastMoveMade(g);
			mem.setGhostLastMove(g, lastMove);

			// distancia a la ultima posicion conocida de pacman
			distanceToPacmanLastPosition[g.ordinal()] = game.getShortestPathDistance(pos, mem.getPacmanLastPosition(),
					lastMove);

			distanceToPacmanWithSpeed[g.ordinal()] = distanceToPacmanLastPosition[g.ordinal()] * 0.5;

			edible[g.ordinal()] = game.isGhostEdible(g);

			distanceToAsignedPP[g.ordinal()] = game.getShortestPathDistance(pos, mem.getGhostAsignedPP(g), lastMove);

			distanceToLair[g.ordinal()] = game.getShortestPathDistance(pos, game.getCurrentMaze().initialGhostNodeIndex,
					lastMove);

			remainingTime[g.ordinal()] = game.getGhostEdibleTime(g);

			for (GHOST g2 : GHOST.values()) {
				if (g2 != g) {
					anotherGhostEdible[g.ordinal()] = anotherGhostEdible[g.ordinal()] || game.isGhostEdible(g2);

					anotherGhostNotEdible[g.ordinal()] = anotherGhostNotEdible[g.ordinal()] || !game.isGhostEdible(g2);

					anotherGhostInLair[g.ordinal()] = anotherGhostInLair[g.ordinal()] || game.getGhostLairTime(g2) > 0;
				}
			}
		}

		int minDistToPP = 2000;
		int dist;
		int nearestPP = -1;
		for (int pp : game.getCurrentMaze().powerPillIndices) {
			// inicializar a true la existencia de las PP
			if (!mem.PPEntryExists(pp))
				mem.setPPActive(pp, true);
			
			if (mem.getPacmanPosConfidence() > 0 && game.getShortestPathDistance(mem.getPacmanLastPosition(), pp) < 10) 
				pacmanInCorner = true;

			// pp mas cercana a pacman
			if (mem.isPPActive(pp)) {
				dist = game.getShortestPathDistance(mem.getPacmanLastPosition(), pp, mem.getPacmanLastDirection());
				if (dist < minDistToPP) {
					nearestPP = pp;
					minDistToPP = dist;
				}

				for (GHOST g : GHOST.values()) {
					if (dist < distanceToNearestPP[g.ordinal()]) {
						distanceToNearestPP[g.ordinal()] = dist;
						mem.setGhostNearestPP(g, nearestPP);
					}
				}
			}

			// si ve que no hay una PP lo marca
			if (game.isNodeObservable(pp) && game.getActivePowerPillsIndices().length == 0)
				mem.setPPActive(pp, false);
		}

		pacmanDistToPP = minDistToPP;
		pacmanNearestPP = nearestPP;

		if (mem.getPacmanPosConfidence() > 0) {
			
			//predictor
			pacmanPredictor.calculate();
			mostProbablePacmanPos = pacmanPredictor.getMostProbablePos();
			mostProbablePacmanPosPoints = pacmanPredictor.getMostProbablePosPoints();
			
			//marcar PP mas cercana a pacman como comida si se comio una
			if (game.wasPowerPillEaten() && pacmanNearestPP != -1) 
			{
				mem.setPPActive(pacmanNearestPP, false);
			}
		}

	}

	double parseBoolToDouble(boolean bool) {
		if (bool)
			return 1.0;

		return 0.0;
	}

}
