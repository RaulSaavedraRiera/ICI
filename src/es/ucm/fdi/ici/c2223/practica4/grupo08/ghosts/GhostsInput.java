package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

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

		vars.put("PacmanVisible", parseBoolToDouble(pacmanVisible));

		return vars;
	}

	public HashMap<String, Double> getFuzzyValues(GHOST g) {
		// TODO Auto-generated method stub

		HashMap<String, Double> vars = new HashMap<String, Double>();

		vars.put(g.name() + "DistanceToPacman", (double) distanceToPacmanLastPosition[g.ordinal()]);						
		vars.put(g.name() + "Edible", parseBoolToDouble(edible[g.ordinal()]));
		vars.put(g.name() + "AnotherGhostEdible", parseBoolToDouble(anotherGhostEdible[g.ordinal()]));
		vars.put(g.name() + "AnotherGhostNotEdible", parseBoolToDouble(anotherGhostNotEdible[g.ordinal()]));
		vars.put(g.name() + "AnotherGhostInLair", parseBoolToDouble(anotherGhostInLair[g.ordinal()]));
			
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

				mem.setPacmanLastPosition(pos);
			}
			
			//distancia a la ultima posicion conocida de pacman
			distanceToPacmanLastPosition[g.ordinal()] = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), mem.getPacmanLastPosition(),
					game.getGhostLastMoveMade(g));
			
			edible[g.ordinal()] = game.isGhostEdible(g);
		
			for (GHOST g2 : GHOST.values()) {
				if (g2 != g) {
					anotherGhostEdible[g.ordinal()] = anotherGhostEdible[g.ordinal()]
							|| game.isGhostEdible(g2);

					anotherGhostNotEdible[g.ordinal()] = anotherGhostNotEdible[g.ordinal()]
							|| !game.isGhostEdible(g2);

					anotherGhostInLair[g.ordinal()] = anotherGhostInLair[g.ordinal()]
							|| game.getGhostLairTime(g2) > 0;
				}
			}
			
			int minDistToPP = Integer.MAX_VALUE;
			int dist;
			int nearestPP = -1;
			for (int pp : game.getCurrentMaze().powerPillIndices) 
			{
				//inicializar a true la existencia de las PP
				if (!mem.PPEntryExists(pp))
					mem.setPPActive(pp, true);
				
				//pp mas cercana a pacman
				else if (mem.isPPActive(pp)) 
				{
					dist = game.getShortestPathDistance(mem.getPacmanLastPosition(), pp, mem.getPacmanLastDirection());
					if (dist < minDistToPP) 
					{
						nearestPP = pp;
						minDistToPP = dist;
					}
				}
				
				//si ve que no hay una PP lo marca
				if (game.isNodeObservable(pp) && !game.isPowerPillStillAvailable(pp)) 
					mem.setPPActive(pp, false);
			}
			
			pacmanDistToPP = minDistToPP;
			pacmanNearestPP = nearestPP;
		}
	}
	
	double parseBoolToDouble(boolean bool) 
	{
		if (bool) return 1.0;
		
		return 0.0;
	}

}
