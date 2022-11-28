package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class GhostsInput extends FuzzyInput {

	private int[] distance;

	public GhostsInput(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	public boolean isPacmanVisible() {
		return game.getPacmanCurrentNodeIndex() != -1;
	}

	@Override
	public Map<String, Double> getFuzzyValues() {
		// TODO Auto-generated method stub

		HashMap<String, Double> vars = new HashMap<String, Double>();
		for (GHOST g : GHOST.values()) {
			vars.put(g.name() + "distance", (double) distance[g.ordinal()]);
		}
		return vars;
	}

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub
		distance = new int[] { -1, -1, -1, -1 };

		for (GHOST g : GHOST.values()) {
			int pos = game.getPacmanCurrentNodeIndex();
			if (pos != -1) {
				distance[g.ordinal()] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pos,
						game.getGhostLastMoveMade(g));
			}
		}
	}

}
