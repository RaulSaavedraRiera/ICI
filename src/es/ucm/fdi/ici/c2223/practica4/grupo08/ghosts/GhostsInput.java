package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class GhostsInput extends FuzzyInput {

	private boolean pacmanVisible;

	private GhostsFuzzyMemory mem;

	public GhostsInput(Game game, GhostsFuzzyMemory ghostsMem) {
		super(game);
		// TODO Auto-generated constructor stub
		mem = ghostsMem;
	}

	public boolean isPacmanVisible() {
		return game.getPacmanCurrentNodeIndex() != -1;
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		// TODO Auto-generated method stub

		HashMap<String, Double> vars = new HashMap<String, Double>();

		if (pacmanVisible)
			vars.put("PacmanVisible", (double) 1);

		else
			vars.put("PacmanVisible", (double) 0);

		for (GHOST g : GHOST.values()) {

		}
		return vars;
	}

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub	
		pacmanVisible = false;
		
		for (GHOST g : GHOST.values()) {
			int pos = game.getPacmanCurrentNodeIndex();
			if (pos != -1) {
				pacmanVisible = true;
				
				int dist = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pos,
						game.getGhostLastMoveMade(g));

				mem.setPacmanLastDistance(g, dist);

				mem.setPacmanLastPosition(pos);
			}
		}
	}

}
