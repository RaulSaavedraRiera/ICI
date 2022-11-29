package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman;

import java.util.HashMap;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManInput extends FuzzyInput {
	
	//tal y como esta configurado ahora si funciona cuando un ghost esta en lair time sabemos en todo momento 
	//que esta en la celda por loq ue la confianza no debe bajar en este caso
	
	final int NEAR_PILL = 30; 
	int LAIR_GHOSTS_TIME;
	
	//esta puesto a ojo pedirselo a rodri
	int EDIBLE_GHOSTS_TIME = 10;;

	
	protected double[] lairTimeGhosts;
	private double[] distanceGhosts;
	private double[] edibleTimeGhosts;	
	private double[] timeSawGhosts;
	private MOVE[] directionGhosts;
	
	private int[] corners;
	
	private HashMap<Integer,Boolean> pills = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> powerPills = new HashMap<Integer,Boolean>();
	
	private int lairPos; 
	
	private int pillsNear;
	private int distanceToLair;
	private int distanceToNearestCorner;
	private double[] distancePP;
	
	
	public MsPacManInput(Game game) {
		super(game);
		
		LAIR_GHOSTS_TIME = game.getGhostLairTime(GHOST.values()[0]);
		
		edibleTimeGhosts = new double[] {0,0,0,0};
		
		//suponiendo que los ve dentro, si no contar el tiempo a ojo para el primer mapa usando los ghosts
		lairTimeGhosts =  new double[] 
				{LAIR_GHOSTS_TIME,LAIR_GHOSTS_TIME,LAIR_GHOSTS_TIME,LAIR_GHOSTS_TIME};
		timeSawGhosts =  new double[] {0,0,0,0};
		directionGhosts = new MOVE[]{MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL};
		
		//llamar a esto por cada cambio de mapa
		initialValues(game);
	}
	
	@Override
	public void parseInput() {
		
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
	
	

	void initialValues(Game game) {
		
		
		lairPos = game.getCurrentMaze().lairNodeIndex;
		
		corners = game.getPowerPillIndices();
		
		
		for(int p : game.getPillIndices()) 
			pills.put(p, true);
		
		for(int p : game.getPowerPillIndices()) 
			powerPills.put(p, true);
			
	}
	
	void pillEaten(Game game) {
	pills.put(game.getPacmanCurrentNodeIndex(), false);
		
	}
	
	void PPEaten(Game game) 
	{
		powerPills.put(game.getPacmanCurrentNodeIndex(), false);
		
		for(GHOST g: GHOST.values()) {
			if(lairTimeGhosts[g.ordinal()] == 0) 
				edibleTimeGhosts[g.ordinal()] = EDIBLE_GHOSTS_TIME; 
		}
		
	}
	
	//presuponemos que si conoces un nodo puedes saber cual es el siguiente
	void mapValues(Game game) {
		
		int pacmanP = game.getPacmanCurrentNodeIndex();
		
		
		pillsNear = 0;
		pills.forEach((pos,active) -> {if(active && game.getShortestPathDistance(pacmanP, pos) <= NEAR_PILL)
			pillsNear++; });
		
		distanceToLair = game.getShortestPathDistance(pacmanP, lairPos);
		
		distanceToNearestCorner = 99999;
		powerPills.forEach((pos,active) -> { 
			int distance = game.getShortestPathDistance(pacmanP, pos);
			if(distance < distanceToNearestCorner)  distanceToNearestCorner = distance;
				});
		
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
			
	void ghostEaten(Game g, GHOST ghost) 
	{
		lairTimeGhosts[ghost.ordinal()] = LAIR_GHOSTS_TIME;
		edibleTimeGhosts[ghost.ordinal()] = 0;
		directionGhosts[ghost.ordinal()] = MOVE.NEUTRAL;
		timeSawGhosts[ghost.ordinal()] = 0;
		
	}
	
	void ghostsValues(Game game) {
		
		distanceGhosts = new double[] {-1,-1,-1,-1};
		
		for(GHOST g: GHOST.values()) {
			int index = g.ordinal();
			int pos = game.getGhostCurrentNodeIndex(g);
			if(pos != -1) {
				
				timeSawGhosts[index] = 0;
				//se peude cambiar por un get distance mas preciso
				distanceGhosts[index] = game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);				
				edibleTimeGhosts[index] = (double)game.getGhostEdibleTime(g);
				directionGhosts[index] = game.getGhostLastMoveMade(g);
			}
			else
			{
				timeSawGhosts[index]++;
				distanceGhosts[index] = -1;
				directionGhosts[index] = MOVE.NEUTRAL;
				
				if(edibleTimeGhosts[index] > 0) edibleTimeGhosts[index]--;	
				if(lairTimeGhosts[index] > 0) lairTimeGhosts[index]--;		
			}
				
		}
	}

	@Override
	public HashMap<String, Double> getFuzzyValues() {
		HashMap<String,Double> vars = new HashMap<String,Double>();
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"distance",   distanceGhosts[g.ordinal()]);
		}
		
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"edibleTime",  edibleTimeGhosts[g.ordinal()]);
		}
		
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"lairTime",  lairTimeGhosts[g.ordinal()]);
		}
		
		//se debe pasar como double auqnue sea un movimiento
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"direction",   (double)directionGhosts[g.ordinal()].ordinal());
		}
		
		//no se si este realmente hara falta
		for(GHOST g: GHOST.values()) {
			vars.put(g.name()+"saw",   distanceGhosts[g.ordinal()]);
		}
		
		
		
		for(int i = 0; i < 4; ++i) {
			vars.put("PP" + Integer.toString(i), distancePP[i]);
		}
		
		vars.put("PillsNear", (double)pillsNear);
		vars.put("DistanceLair", (double)distanceToLair);
		vars.put("DistanceNearestCorner", (double)distanceToNearestCorner);

		
		return vars;
	}

}

