package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.rules.RulesInput;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class MsPacManInput extends RulesInput {

	final int dist_near_pill = 30;
	
	
	// Estos hacen falta para rules si lo vamos a sacar de GhostInput? Yo los he creado por si acaso
	private int[] ghostsEdibleTime = new int[4];
	private int[] ghostsLairTime = new int[4];
	private int[] ghostsPos = new int[4];
	private int[] ghostsDistanceToPacMan = new int[4];
	
	private int jailIndex = -1;
	private int distanceToLair;
	ArrayList<Integer> corners = new ArrayList<Integer>();
	private int distanceToNearestCorner;
	
	private int nearestChasingGhostNotBehindIndex; // Fantasma mas cercano que persigue pero no en la misma dir
	private int nearestChasingGhostAnyDirIndex;
	private int nearestEdibleGhostIndex;
	private int nearestPowerPillIndex;
	//private int nearestPillIndex;
	
	private int distanceToNearestChasingGhostNotBehind;
	private int distanceToNearestChasingGhostAnyDir;
	private int distanceToNearestEdible;
	private int distanceToNearestPowerPill;
	//creo que es mas relevante saber cuantas pills hay cerca
	private int numPillsNear;

	public MsPacManInput(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<String> getFacts() {
		
		Vector<String> facts = new Vector<String>();

		facts.add(String.format("(BLINKY (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(CLYDE (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (lairTime %d))",  (int)this.ghostsLairTime[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (lairTime %d))",  (int)this.ghostsLairTime[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (lairTime %d))",  (int)this.ghostsLairTime[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(CLYDE (lairTime %d))",  (int)this.ghostsLairTime[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (index %d))",  (int)this.ghostsPos[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (index %d))",  (int)this.ghostsPos[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (index %d))",  (int)this.ghostsPos[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(CLYDE (index %d))",  (int)this.ghostsPos[GHOST.SUE.ordinal()]));

		facts.add(String.format("(BLINKY (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(CLYDE (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.SUE.ordinal()]));

		facts.add(String.format("(MSPACMAN (distToLair %d))", this.distanceToLair));
		facts.add(String.format("(MSPACMAN (distToNearestCorner %d))", this.distanceToNearestCorner));
		facts.add(String.format("(MSPACMAN (distToNearestChasingNotBehind %d))", this.distanceToNearestChasingGhostNotBehind));
		facts.add(String.format("(MSPACMAN (distToNearestChasingAnyDir %d))", this.distanceToNearestChasingGhostAnyDir));
		facts.add(String.format("(MSPACMAN (distToNearestEdible %d))", this.distanceToNearestEdible));
		facts.add(String.format("(MSPACMAN (distToNearestPP %d))", this.distanceToNearestPowerPill));
		facts.add(String.format("(MSPACMAN (numPillsNear %d))", this.numPillsNear));
		
		return facts;
	}

	@Override
	public void parseInput() {
		
		DataValues();		
		
		MapInfo();
		
		GhostsInfo();
	
	}
	
	//solo se haran una vez por partida
	void DataValues() {
		if (jailIndex == -1 && game.getGhostLairTime(GHOST.BLINKY) <= 0) 
			jailIndex = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
		
		if (corners.isEmpty()) {
			for(int i = 0; i < game.getActivePowerPillsIndices().length; i++) {
				corners.add(game.getActivePowerPillsIndices()[i]);
			}
		}
	}
	
	void GhostsInfo() {
		
		//var generales
		for (GHOST g : GHOST.values()) {
			ghostsEdibleTime[g.ordinal()] = game.getGhostEdibleTime(g);
			ghostsLairTime[g.ordinal()] = game.getGhostLairTime(g);
			ghostsPos[g.ordinal()] = game.getGhostCurrentNodeIndex(g);
			
		if(game.getGhostLastMoveMade(g) != null)
			ghostsDistanceToPacMan[g.ordinal()] = game.getShortestPathDistance(
					game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
		else 
			ghostsDistanceToPacMan[g.ordinal()] = game.getShortestPathDistance(
					game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex());
		}
		
		
		distanceToNearestChasingGhostAnyDir = distanceToNearestEdible = 99999;
		int d;
		for (GHOST g : GHOST.values()) {
			if(game.getGhostEdibleTime(g) > 0)
			{
				d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade());
				if(distanceToNearestEdible > d) {
					distanceToNearestEdible = d;//
					//entiendo que index es el nodo donde esta
					nearestEdibleGhostIndex = game.getGhostCurrentNodeIndex(g);
				}
					
			}
			else
			{
				d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade());
				if(distanceToNearestChasingGhostAnyDir > d) {
					distanceToNearestChasingGhostAnyDir = d;
					nearestChasingGhostAnyDirIndex = game.getGhostCurrentNodeIndex(g);
				}
				
				if (distanceToNearestChasingGhostNotBehind > d && game.getGhostLastMoveMade(g) != game.getPacmanLastMoveMade()) {
					distanceToNearestChasingGhostNotBehind = d;
					nearestChasingGhostNotBehindIndex = game.getGhostCurrentNodeIndex(g);
				}
			}
			
		}
	}
	
	void MapInfo() {
		 
		if(jailIndex != -1)
			distanceToLair = game.getShortestPathDistance(jailIndex, game.getPacmanCurrentNodeIndex());
		else
			distanceToLair = 100;
		
		//no se esta teniendo en cuenta el ultimo movimiento realizado
		
		//podriamos usar corners el pp indices
		distanceToNearestCorner = 99999;
		for(int c : corners) {
			int d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), c);			
			if(d < distanceToNearestCorner)
				distanceToNearestCorner = d;
		}
		
		distanceToNearestPowerPill = 99999;
		for(int c : game.getActivePowerPillsIndices()) {
			int d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), c);			
			if(d < distanceToNearestPowerPill) {
				distanceToNearestPowerPill = d;
				nearestPowerPillIndex = c;
			}
				
		}
		
		
		numPillsNear = 0;
		for(int p : game.getActivePillsIndices()) {
			if(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), p) <= dist_near_pill)
				numPillsNear++;
		}
		
		
		
	}
	

}
