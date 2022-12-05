package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman;

import java.util.HashMap;

import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsFuzzyMemory;
import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManInput extends FuzzyInput {
	
	//tal y como esta configurado ahora si funciona cuando un ghost esta en lair time sabemos en todo momento 
	//que esta en la celda por loq ue la confianza no debe bajar en este caso
	
	private MsPacManFuzzyMemory mem;
	
	final int NEAR_PILL = 30; 
	final int DISTANCE_GHOST_NEAR = 40;
	final int DISTANCE_GHOST_MEDIUM = 60;
	
	private double[] distanceGhosts;
	
	private int[] corners;

	private int lairPos; 
	
	private int pillsNear;
	private int distanceToLair;
	private int distanceToNearestCorner;
	private double[] distancePP;
	
	
	
	public MsPacManInput(Game game,  MsPacManFuzzyMemory pacMem) {
		super(game);
		
		mem = pacMem;
		//llamar a esto por cada cambio de mapa
		if(mem.currentMaze != game.getMazeIndex()) 
			mem.setMapValues(game.getMazeIndex(), game.getCurrentMaze().lairNodeIndex, game.getCurrentMaze().pillIndices, game.getCurrentMaze().powerPillIndices);;
		
		//para acceder informacion de memoria
		
		//para obtener las posiciones fijas del mapa
		lairPos = game.getCurrentMaze().lairNodeIndex;		
		corners = game.getPowerPillIndices();
		
	}
	
	@Override
	public void parseInput() {
		
		if (mem == null) return;
		
		mapValues(game);
		
		ghostsValues(game);
		
		if(game.wasPillEaten())
			pillEaten(game);
		if(game.wasPowerPillEaten())
			PPEaten(game);
		
		for(GHOST g: GHOST.values()) 
			if(game.wasGhostEaten(g))
				ghostEaten(game, g);
		
	}
	
	public boolean isVisible(GHOST ghost)
	{
		return distanceGhosts[ghost.ordinal()]!=-1;
	}
	
	public boolean wasEaten(GHOST ghost)
	{
		return game.wasGhostEaten(ghost);
	}
	
	//metodos para cuando se debe actualziar informacion en la memoria
	void pillEaten(Game game) {
		mem.pills.put(game.getPacmanCurrentNodeIndex(), false);
	}
	
	void PPEaten(Game game) 
	{
		mem.powerPills.put(game.getPacmanCurrentNodeIndex(), false);
		
		for(GHOST g: GHOST.values()) {
			if(mem.lairTimeGhosts[g.ordinal()] == 0) 
				mem.edibleTimeGhosts[g.ordinal()] = mem.EDIBLE_GHOSTS_TIME; 
		}
		
	}
	
	void ghostEaten(Game g, GHOST ghost) 
	{
		mem.lairTimeGhosts[ghost.ordinal()] = mem.LAIR_TIME;
		mem.edibleTimeGhosts[ghost.ordinal()] = 0;
		mem.lastDirectionGhosts[ghost.ordinal()] = MOVE.NEUTRAL;
		
	}
	
	//presuponemos que si conoces un nodo puedes saber cual es el siguiente
	void mapValues(Game game) {
		
		int pacmanP = game.getPacmanCurrentNodeIndex();
		
		
		pillsNear = 0;
		mem.pills.forEach((pos,active) -> {if(active && game.getShortestPathDistance(pacmanP, pos) <= NEAR_PILL)
			pillsNear++; });
		
		distanceToLair = game.getShortestPathDistance(pacmanP, lairPos);
		
		distanceToNearestCorner = 99999;

		for(int c : corners) {
			int distance = game.getShortestPathDistance(pacmanP, c);
			if(distance < distanceToNearestCorner)  distanceToNearestCorner = distance;
		}
		
		
		PPDistances(game, pacmanP);
		
	}
	
	void PPDistances(Game game, int pacmanP) 
	{

		if(game.getPacmanLastMoveMade() != null)		
			distancePP = new double[] {
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[0], game.getPacmanLastMoveMade()),
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[1], game.getPacmanLastMoveMade()),
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[2], game.getPacmanLastMoveMade()),
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[3], game.getPacmanLastMoveMade())
					};
		
		
		else 
			distancePP = new double[] {
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[0]),
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[1]),
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[2]),
					(double)game.getShortestPathDistance(pacmanP, game.getPowerPillIndices()[3])
					};
	}
			
	
	
	void ghostsValues(Game game) {
		
		distanceGhosts = new double[] {-1,-1,-1,-1};
		
		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();
			int pos = game.getGhostCurrentNodeIndex(g);
			if(pos != -1) {
				//se peude cambiar por un get distance mas preciso
				distanceGhosts[index] = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pos);	
				mem.lastPosGhost[index] = game.getGhostCurrentNodeIndex(g);
				mem.lastDirectionGhosts[index] = game.getGhostLastMoveMade(g);
				
			}
			else
			{
				distanceGhosts[index] = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), (int)mem.lastPosGhost[index]);;
			}
				
		}
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		
		
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"distance",   distanceGhosts[g.ordinal()]);
			vars.put(g.name()+"direction",   (double)mem.lastDirectionGhosts[g.ordinal()].ordinal());
		}
		
		
		
		for(int i = 0; i < 4; ++i)
			vars.put("PP" + Integer.toString(i) + "distance", distancePP[i]);
			
		
		vars.put("PILLSnear", (double)pillsNear);
		vars.put("LAIRdistance", (double)distanceToLair);
		vars.put("NEARESTCORNERDistance", (double)distanceToNearestCorner);
		
		saveNumGhostDistance(vars);

		
		return vars;
	}
	
	void saveNumGhostDistance(HashMap<String,Double> vars) {
		int farGhosts = 0, mediumGhosts = 0, nearGhosts = 0;
		
		
		//los fantasmas salen media distancia si estan en jaula vistos por ultiam vez
		for(double dG : distanceGhosts)
		{
			if(dG >= this.DISTANCE_GHOST_MEDIUM)
				farGhosts++;
			else if(dG >= this.DISTANCE_GHOST_NEAR || dG == -1)
				mediumGhosts++;
			else
				nearGhosts++;
		}
			if(farGhosts == 0)
				vars.put("NOGHOSTS"  + "far", 1.);
			else
				vars.put("NOGHOSTS"  + "far", 0.);
			
			if(mediumGhosts == 0)
				vars.put("NOGHOSTS"  + "medium", 1.);
			else
				vars.put("NOGHOSTS"  + "medium", 0.);
			
			if(nearGhosts == 0)
				vars.put("NOGHOSTS"  + "near", 1.);
			else
				vars.put("NOGHOSTS"  + "near", 0.);
		
		for(int i = 1; i <=4; ++i) {
			if(i == farGhosts)
				vars.put("G" + Integer.toString(i) + "DISTANCE_far", 1.);
			else
				vars.put("G" + Integer.toString(i) + "DISTANCE_far", 0.);
			
			if(i == mediumGhosts)
				vars.put("G" + Integer.toString(i) + "DISTANCE_medium", 1.);
			else
				vars.put("G" + Integer.toString(i) + "DISTANCE_medium", 0.);
			
			if(i == nearGhosts)
				vars.put("G" + Integer.toString(i) + "DISTANCE_near", 1.);
			else
				vars.put("G" + Integer.toString(i) + "DISTANCE_near", 0.);
		}
	}

}

