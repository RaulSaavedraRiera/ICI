package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.rules.RulesInput;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class MsPacManInput extends RulesInput {

	final int DIST_NEAR_PILL = 30;
	final int NEAR_CHASING_GHOST = 40;
	final int NEAR_EDIBLE_GHOST = 60;
	final int MID_CHASING_GHOST = 70;
	final int TIME_EDIBLE_GHOST_LIMIT = 6;
	
	
	final int NEAR_PP_DISTANCE = 30;
	final int NEAR_CORNER_DISTANCE = 30;
	final int NEAR_LAIR_DISTANCE = 30;
	final int ENOUGH_PILLS_NEAR = 20;
	

	
	private int jailIndex = -1;
	private int distanceToLair;
	private int distanceToNearestCorner;
	
	private int nearestChasingGhostNotBehindIndex; // Fantasma mas cercano que persigue pero no en la misma dir
	private int nearestChasingGhostAnyDirIndex;
	private int nearestEdibleGhostIndex;
	private int nearestPowerPillIndex;
	//private int nearestPillIndex;
	
	//para saber nº fantasmas hay cerca
	private int chasingGhostNear;
	private int edibleGhostNear;
	private int chasingGhostMid;
	
	private int distanceToNearestChasingGhostNotBehind;
	private int distanceToNearestChasingGhostAnyDir;
	
	private int distanceToNearestPowerPill;
	//creo que es mas relevante saber cuantas pills hay cerca
	private int numPillsNear;
	
	private boolean pacmanInIntersection;
	private boolean canTakePP;
	private boolean ghostFollowsPacman;

	public MsPacManInput(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<String> getFacts() {
		
		Vector<String> facts = new Vector<String>();

//		facts.add(String.format("(BLINKY (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.BLINKY.ordinal()]));
//		facts.add(String.format("(INKY (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.INKY.ordinal()]));
//		facts.add(String.format("(PINKY (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.PINKY.ordinal()]));
//		facts.add(String.format("(CLYDE (edibleTime %d))",  (int)this.ghostsEdibleTime[GHOST.SUE.ordinal()]));
//
//		facts.add(String.format("(BLINKY (lairTime %d))",  (int)this.ghostsLairTime[GHOST.BLINKY.ordinal()]));
//		facts.add(String.format("(INKY (lairTime %d))",  (int)this.ghostsLairTime[GHOST.INKY.ordinal()]));
//		facts.add(String.format("(PINKY (lairTime %d))",  (int)this.ghostsLairTime[GHOST.PINKY.ordinal()]));
//		facts.add(String.format("(CLYDE (lairTime %d))",  (int)this.ghostsLairTime[GHOST.SUE.ordinal()]));
//
//		facts.add(String.format("(BLINKY (index %d))",  (int)this.ghostsPos[GHOST.BLINKY.ordinal()]));
//		facts.add(String.format("(INKY (index %d))",  (int)this.ghostsPos[GHOST.INKY.ordinal()]));
//		facts.add(String.format("(PINKY (index %d))",  (int)this.ghostsPos[GHOST.PINKY.ordinal()]));
//		facts.add(String.format("(CLYDE (index %d))",  (int)this.ghostsPos[GHOST.SUE.ordinal()]));
//
//		facts.add(String.format("(BLINKY (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.BLINKY.ordinal()]));
//		facts.add(String.format("(INKY (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.INKY.ordinal()]));
//		facts.add(String.format("(PINKY (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.PINKY.ordinal()]));
//		facts.add(String.format("(CLYDE (distToPacman %d))",  (int)this.ghostsDistanceToPacMan[GHOST.SUE.ordinal()]));
		
		
		facts.add(String.format("(PACMAN (NEAR_PP_DISTANCE %d))", (int) this.NEAR_PP_DISTANCE));
		facts.add(String.format("(PACMAN (NEAR_CORNER_DISTANCE %d))", (int) this.NEAR_CORNER_DISTANCE));
		facts.add(String.format("(PACMAN (NEAR_LAIR_DISTANCE %d))", (int) this.NEAR_LAIR_DISTANCE));
		facts.add(String.format("(PACMAN (ENOUGH_PILLS_NEAR %d))", (int) this.ENOUGH_PILLS_NEAR));
		
		
		facts.add(String.format("(PACMAN (distToLair %d))", (int) this.distanceToLair));
		facts.add(String.format("(PACMAN (distToNearestCorner %d))", (int) this.distanceToNearestCorner));
		facts.add(String.format("(PACMAN (distToNearestPP %d))", (int) this.distanceToNearestPowerPill));

		
		//facts.add(String.format("(PACMAN (distToNearestChasingNotBehind %d))", this.distanceToNearestChasingGhostNotBehind));
		//facts.add(String.format("(PACMAN (distToNearestChasingAnyDir %d))", this.distanceToNearestChasingGhostAnyDir));
		
		facts.add(String.format("(PACMAN (nearChasingGhosts %d))", (int) this.chasingGhostNear));
		facts.add(String.format("(PACMAN (nearEdibleGhosts %d))", (int) this.edibleGhostNear));
		
		facts.add(String.format("(PACMAN (numPillsNear %d))", (int) this.numPillsNear));
		
		facts.add(String.format("(PACMAN (pacmanInIntersection %s))", this.pacmanInIntersection));
		facts.add(String.format("(PACMAN (pacmanCanTakePP %s))", this.canTakePP));
		facts.add(String.format("(PACMAN (ghostFollowsPacman %s))", this.ghostFollowsPacman));
		
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
		
	}
	
	void GhostsInfo() {
		
//		//var generales
//		for (GHOST g : GHOST.values()) {
//			ghostsEdibleTime[g.ordinal()] = game.getGhostEdibleTime(g);
//			ghostsLairTime[g.ordinal()] = game.getGhostLairTime(g);
//			ghostsPos[g.ordinal()] = game.getGhostCurrentNodeIndex(g);
//			
//		if(game.getGhostLastMoveMade(g) != null)
//			ghostsDistanceToPacMan[g.ordinal()] = game.getShortestPathDistance(
//					game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(g));
//		else 
//			ghostsDistanceToPacMan[g.ordinal()] = game.getShortestPathDistance(
//					game.getGhostCurrentNodeIndex(g), game.getPacmanCurrentNodeIndex());
//		}
//		
		
		chasingGhostNear = chasingGhostMid = 0;
		distanceToNearestChasingGhostAnyDir = 99999;
		int d;
		for (GHOST g : GHOST.values()) {
			
			if( game.getGhostLairTime(g) == 0) {
				if(game.getGhostEdibleTime(g) == 0)
				{
					
					try {
						d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade());
					}
					catch(Exception e) {
						d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g));
						
					}
				
					if(d <= NEAR_CHASING_GHOST)
						chasingGhostNear++;
					else if(d <= MID_CHASING_GHOST)
						chasingGhostMid++;
				}
				else
				{
					d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade());
					
					if(d <= NEAR_EDIBLE_GHOST)
						edibleGhostNear++;
				}
			}
		
			
		}
		
		ghostFollowsPacman = false;
		if(chasingGhostNear == 1) {
			int distance = 99999; 			
			for (GHOST g : GHOST.values()) 
				if(game.getGhostEdibleTime(g) == 0 && game.getGhostLairTime(g) == 0) {
					d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(g), game.getPacmanLastMoveMade());
					if(d < distance)
					{
						distance = d;
						ghostFollowsPacman = game.getGhostLastMoveMade(g) == game.getPacmanLastMoveMade();
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
		for(int c : game.getPowerPillIndices()) {
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
			if(game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), p) <= DIST_NEAR_PILL)
				numPillsNear++;
		}
		
		
		pacmanInIntersection = game.getNeighbouringNodes(game.getPacmanCurrentNodeIndex()).length > 2;
		
		canTakePP = false;
		
		for(int p : game.getActivePowerPillsIndices()) {
			int d = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), p, game.getPacmanLastMoveMade());
			boolean valid = true;
			for (GHOST g : GHOST.values()) {
				if(game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) >= TIME_EDIBLE_GHOST_LIMIT)
					if (game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), p, game.getGhostLastMoveMade(g)) <= d){
						valid = false;
						break;
					}
			}
			
			if(valid){
				canTakePP = true;
				break;
			}
				
		}
		
	}
	

}
