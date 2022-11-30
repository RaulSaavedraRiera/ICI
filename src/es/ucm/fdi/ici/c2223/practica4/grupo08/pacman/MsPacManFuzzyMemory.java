package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman;

import java.util.HashMap;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManFuzzyMemory {
	
	//variables constantes respecto a los fantasmas
	public final int LAIR_TIME = 3;
	public final int EDIBLE_GHOSTS_TIME = 10;
	//memoria donde almacenamos la informacion
	HashMap<String,Double> mem;
	//informacion que debe ser almacenada del mapa	
	public HashMap<Integer,Boolean> pills = new HashMap<Integer,Boolean>();
	public HashMap<Integer,Boolean> powerPills = new HashMap<Integer,Boolean>();
	
	//confianza, probablemente con esto vale en vez de ultima vez visto
	double[] confidence = {100,100,100,100};
	//variables sobre los fantasmas que debemos almacenar en el tiempo
	protected double[] lairTimeGhosts;	
	public double[] edibleTimeGhosts;	
	public double[] lastPosGhost;	
	public MOVE[] lastDirectionGhosts;
	
	public MsPacManFuzzyMemory() {
		mem = new HashMap<String,Double>();
	}
	
	public void getInput(MsPacManInput input)
	{
		
		
		if(input.firstInteractMap) {
			pills = input.pills;
			powerPills = input.powerPills;
		}
			
			
		for(GHOST g: GHOST.values()) {
			double conf = confidence[g.ordinal()];
			double lairTimeghosts = lairTimeGhosts[g.ordinal()];
//			double distanceGhost = distanceGhosts[g.ordinal()];
			double edibleGhostTime = edibleTimeGhosts[g.ordinal()];
//			MOVE directionGhost = directionGhosts[g.ordinal()];
			
			
			//si no han sido comidos se actualiza a los nuevos valores fijos de los fantasmas
			if(input.wasEaten(g)) {
				
				if(lairTimeghosts > 0)
					lairTimeghosts--;
				if(edibleGhostTime > 0)
					edibleGhostTime--;
			}
			
			
			//si esta en celda sabemos que de ahi no se va a mover hasta que acabe el tiempo
			if(input.isVisible(g) || lairTimeGhosts[g.ordinal()] > 0)
			{
				conf = 100;

			}
			else
			{
				conf = Double.max(0, conf-5);
			}
				
			
			mem.put(g.name()+"confidence", conf);
			mem.put(g.name()+"lairTime", lairTimeghosts);
			mem.put(g.name()+"edibleTime", edibleGhostTime);
		
			
			confidence[g.ordinal()] = conf;
			lairTimeGhosts[g.ordinal()] = lairTimeghosts;
			edibleTimeGhosts[g.ordinal()] = edibleGhostTime;
		
		}

	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}
	
	
	public void inicializeFuzzy(Game game) {
		edibleTimeGhosts = new double[] {0, 0, 0, 0};
		lairTimeGhosts = new double[] {LAIR_TIME, LAIR_TIME, LAIR_TIME, LAIR_TIME};
		lastPosGhost = new double[] {game.getCurrentMaze().lairNodeIndex, game.getCurrentMaze().lairNodeIndex, game.getCurrentMaze().lairNodeIndex, game.getCurrentMaze().lairNodeIndex};
		lastDirectionGhosts = new MOVE[]{};
	}
}
