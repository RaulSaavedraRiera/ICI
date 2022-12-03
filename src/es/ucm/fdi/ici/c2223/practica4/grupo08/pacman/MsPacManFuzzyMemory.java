package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman;

import java.util.HashMap;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManFuzzyMemory {
	
	//variables constantes respecto a los fantasmas
	public final int LAIR_TIME = 39;
	public final int EDIBLE_GHOSTS_TIME = 199;
	
	
	public final int MEDIUM_CONFIDENCE = 65;
	public final int LOW_CONFIDENCE = 35;
	
	public int LAIR_INDEX;
	//memoria donde almacenamos la informacion
	HashMap<String,Double> mem;
	//informacion que debe ser almacenada del mapa	
	public HashMap<Integer,Boolean> pills;
	public HashMap<Integer,Boolean> powerPills;
	
	//confianza, probablemente con esto vale en vez de ultima vez visto
	double[] confidence = {100,100,100,100};
	//variables sobre los fantasmas que debemos almacenar en el tiempo
	public double[] lairTimeGhosts;	
	public double[] edibleTimeGhosts;	
	public double[] lastPosGhost;	
	public MOVE[] lastDirectionGhosts;
	
	public int currentMaze = -1;
	
	public MsPacManFuzzyMemory() {
		mem = new HashMap<String,Double>();
		lairTimeGhosts = new double[]{LAIR_TIME, LAIR_TIME,LAIR_TIME, LAIR_TIME};
		edibleTimeGhosts = new double[]{0, 0,0, 0};
		lastDirectionGhosts = new MOVE[] {MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL, MOVE.NEUTRAL};
	}
	
	public void setMapValues(int mazeIndex, int lairIndex, int[] pills, int[]ppills) {
		currentMaze = mazeIndex;
		LAIR_INDEX = lairIndex;
		
		 this.pills = new HashMap<Integer,Boolean>();
		 this.powerPills = new HashMap<Integer,Boolean>();
		
		for(int p : pills) 
			this.pills.put(p, true);
		for(int p : ppills) 
			this.powerPills.put(p, true);
		
		lastPosGhost = new double[]{LAIR_INDEX, LAIR_INDEX, LAIR_INDEX, LAIR_INDEX};
	}
	
	public void getInput(MsPacManInput input)
	{
		
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
		
			confidenceGhosts();
			
			confidence[g.ordinal()] = conf;
			lairTimeGhosts[g.ordinal()] = lairTimeghosts;
			edibleTimeGhosts[g.ordinal()] = edibleGhostTime;
		
		}

	}
	
	
	void confidenceGhosts() {
		int highGhosts = 0, mediumGhosts = 0, lowGhosts = 0;
		
		for(double cG : confidence)
		{
			if(cG >= this.MEDIUM_CONFIDENCE)
				highGhosts++;
			else if(cG >= this.LOW_CONFIDENCE)
				mediumGhosts++;
			else
				lowGhosts++;
		}
		
		for(int i = 1; i <=4; ++i) {
			if(i == highGhosts)
				mem.put("G" + Integer.toString(i) + "CONFIDENCE_high", 1.);
			else
				mem.put("G" + Integer.toString(i) + "CONFIDENCE_high", 0.);
			
			if(i == mediumGhosts)
				mem.put("G" + Integer.toString(i) + "CONFIDENCE_medium", 1.);
			else
				mem.put("G" + Integer.toString(i) + "CONFIDENCE_medium", 0.);
			
			if(i == lowGhosts)
				mem.put("G" + Integer.toString(i) + "CONFIDENCE_low", 1.);
			else
				mem.put("G" + Integer.toString(i) + "CONFIDENCE_low", 0.);
		}
	}
	
	public HashMap<String, Double> getFuzzyValues() {
		return mem;
	}

}
