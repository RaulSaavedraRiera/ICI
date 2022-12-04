package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostsInput extends FuzzyInput {

	boolean pacmanVisible;
	private boolean[] edible;
	private boolean[] anotherGhostEdible;
	private boolean[] anotherGhostNotEdible;
	private boolean[] anotherGhostInLair;

	private int pacmanDistToPP;
	private int pacmanNearestPP;
	private int[] distanceToPacmanLastPosition;

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

		// vars.put("pacmanVisible", parseBoolToDouble(pacmanVisible));

		return vars;
	}

	public HashMap<String, Double> getFuzzyValues(GHOST g) {
		// TODO Auto-generated method stub

		HashMap<String, Double> vars = new HashMap<String, Double>();

		vars.put("distanceToPacman", (double) distanceToPacmanLastPosition[g.ordinal()]);
		vars.put("edible", parseBoolToDouble(edible[g.ordinal()]));
		vars.put("anotherGhostEdible", parseBoolToDouble(anotherGhostEdible[g.ordinal()]));
		vars.put("anotherGhostNotEdible", parseBoolToDouble(anotherGhostNotEdible[g.ordinal()]));
		vars.put("anotherGhostInLair", parseBoolToDouble(anotherGhostInLair[g.ordinal()]));

		return vars;
	}

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub
		pacmanVisible = false;

		distanceToPacmanLastPosition = new int[4];
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
			}

			int pos = game.getGhostCurrentNodeIndex(g);
			mem.setGhostPostion(g, pos);
			MOVE lastMove = game.getGhostLastMoveMade(g);
			mem.setGhostLastMove(g, lastMove);

			// distancia a la ultima posicion conocida de pacman
			distanceToPacmanLastPosition[g.ordinal()] = game.getShortestPathDistance(pos, mem.getPacmanLastPosition(),
					lastMove);

			edible[g.ordinal()] = game.isGhostEdible(g);

			for (GHOST g2 : GHOST.values()) {
				if (g2 != g) {
					anotherGhostEdible[g.ordinal()] = anotherGhostEdible[g.ordinal()] || game.isGhostEdible(g2);

					anotherGhostNotEdible[g.ordinal()] = anotherGhostNotEdible[g.ordinal()] || !game.isGhostEdible(g2);

					anotherGhostInLair[g.ordinal()] = anotherGhostInLair[g.ordinal()] || game.getGhostLairTime(g2) > 0;
				}
			}

			int minDistToPP = Integer.MAX_VALUE;
			int dist;
			int nearestPP = -1;
			for (int pp : game.getCurrentMaze().powerPillIndices) {
				// inicializar a true la existencia de las PP
				if (!mem.PPEntryExists(pp))
					mem.setPPActive(pp, true);

				// pp mas cercana a pacman
				if (mem.isPPActive(pp)) {
					dist = game.getShortestPathDistance(mem.getPacmanLastPosition(), pp, mem.getPacmanLastDirection());
					if (dist < minDistToPP) {
						nearestPP = pp;
						minDistToPP = dist;
					}
				}

				// si ve que no hay una PP lo marca
				if (game.isNodeObservable(pp) && game.getActivePowerPillsIndices().length == 0) // &&
																								// !game.isPowerPillStillAvailable(pp)
					mem.setPPActive(pp, false);
			}

			pacmanDistToPP = minDistToPP;
			pacmanNearestPP = nearestPP;
		}

		if (mem.getPacmanPosConfidence() != 0) 
		{
			pacmanPredictor.calculate();
			pacmanPredictor.getMostProbablePos();
		}
	}

	double parseBoolToDouble(boolean bool) {
		if (bool)
			return 1.0;

		return 0.0;
	}

}
