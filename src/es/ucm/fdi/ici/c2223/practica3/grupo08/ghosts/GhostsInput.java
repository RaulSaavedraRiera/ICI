package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts;

import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.rules.RulesInput;
import jess.Fact;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class GhostsInput extends RulesInput {

	private boolean BLINKYedible;
	private boolean INKYedible;
	private boolean PINKYedible;
	private boolean SUEedible;
	private int minPacmanDistancePPill;
	
	private boolean[] thereIsAnotherGhostNotEdible;
	private boolean[] thereIsAnotherGhostEdible;
	private boolean[] thereIsAnotherGhostInLair;

	private int[] ghostPositions;
	private int[] minGhostsDistancePPill;
	private int[] distanceToPacman;
	private int[] distanceToLair;
	
	private boolean BLINKYhasObjective;
	private boolean PINKYhasObjective;

	
	private final int GHOST_RANGE = 50;
	private final int PACMAN_MAX_DIST_TO_PP = 40;
	private final int SAFETY_DISTANCE_WHEN_EDIBLE = 20;
	private final int SURE_DEATH_DISTANCE = 100;
	private final int ORBITING_DISTANCE = 30;
	private final int CHASING_TIME_LIMIT = 30;
	private int BLINKYremainingTime;
	private int INKYremainingTime;
	private int PINKYremainingTime;
	private int SUEremainingTime;
	private int SUEchasingTime;
	
	public GhostsInput(Game game) {
		super(game);
	}

	@Override
	public Collection<String> getFacts() {
		Vector<String> facts = new Vector<String>();
		
		facts.add(String.format("(BLINKY (edible %s))", this.BLINKYedible));
		facts.add(String.format("(INKY (edible %s))", this.INKYedible));
		facts.add(String.format("(PINKY (edible %s))", this.PINKYedible));
		facts.add(String.format("(SUE (edible %s))", this.SUEedible));
		
		facts.add(String.format("(BLINKY (minBLINKYDistancePPill %d))", (int)this.minGhostsDistancePPill[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (minINKYDistancePPill %d))", (int)this.minGhostsDistancePPill[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (minPINKYDistancePPill %d))", (int)this.minGhostsDistancePPill[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (minSUEDistancePPill %d))", (int)this.minGhostsDistancePPill[GHOST.SUE.ordinal()]));
		
		facts.add(String.format("(MSPACMAN (mindistancePPill %d))", (int)this.minPacmanDistancePPill));
		
		facts.add(String.format("(BLINKY (anotherGhostNotEdible %s))", (boolean)this.thereIsAnotherGhostNotEdible[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (anotherGhostNotEdible %s))", (boolean)this.thereIsAnotherGhostNotEdible[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (anotherGhostNotEdible %s))", (boolean)this.thereIsAnotherGhostNotEdible[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (anotherGhostNotEdible %s))", (boolean)this.thereIsAnotherGhostNotEdible[GHOST.SUE.ordinal()]));
		
		facts.add(String.format("(BLINKY (anotherGhostEdible %s))", (boolean)this.thereIsAnotherGhostEdible[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (anotherGhostEdible %s))", (boolean)this.thereIsAnotherGhostEdible[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (anotherGhostEdible %s))", (boolean)this.thereIsAnotherGhostEdible[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (anotherGhostEdible %s))", (boolean)this.thereIsAnotherGhostEdible[GHOST.SUE.ordinal()]));
		
		facts.add(String.format("(BLINKY (anotherGhostInLair %s))", (boolean)this.thereIsAnotherGhostInLair[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (anotherGhostInLair %s))", (boolean)this.thereIsAnotherGhostInLair[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (anotherGhostInLair %s))", (boolean)this.thereIsAnotherGhostInLair[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (anotherGhostInLair %s))", (boolean)this.thereIsAnotherGhostInLair[GHOST.SUE.ordinal()]));
		
		facts.add(String.format("(BLINKY (distanceToPacman %d))", (int)this.distanceToPacman[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (distanceToPacman %d))", (int)this.distanceToPacman[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (distanceToPacman %d))", (int)this.distanceToPacman[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (distanceToPacman %d))", (int)this.distanceToPacman[GHOST.SUE.ordinal()]));		
		
		facts.add(String.format("(BLINKY (distanceToLair %d))", (int)this.distanceToLair[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(INKY (distanceToLair %d))", (int)this.distanceToLair[GHOST.INKY.ordinal()]));
		facts.add(String.format("(PINKY (distanceToLair %d))", (int)this.distanceToLair[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(SUE (distanceToLair %d))", (int)this.distanceToLair[GHOST.SUE.ordinal()]));
		
		facts.add(String.format("(BLINKY (remainingTime %d))", (int)this.BLINKYremainingTime));
		facts.add(String.format("(INKY (remainingTime %d))", (int)this.INKYremainingTime));
		facts.add(String.format("(PINKY (remainingTime %d))", (int)this.PINKYremainingTime));
		facts.add(String.format("(SUE (remainingTime %d))", (int)this.SUEremainingTime));
		
		facts.add(String.format("(BLINKY (position %d))", (int)this.ghostPositions[GHOST.BLINKY.ordinal()]));
		facts.add(String.format("(PINKY (position %d))", (int)this.ghostPositions[GHOST.PINKY.ordinal()]));
		facts.add(String.format("(INKY (position %d))", (int)this.ghostPositions[GHOST.INKY.ordinal()]));
		facts.add(String.format("(SUE (position %d))", (int)this.ghostPositions[GHOST.SUE.ordinal()]));
		
		facts.add(String.format("(BLINKY (hasObjective %s))", (boolean)this.BLINKYhasObjective));
		facts.add(String.format("(PINKY (hasObjective %s))", (boolean)this.PINKYhasObjective));
		
		facts.add(String.format("(SUE (chasingTime %s))", (int)this.SUEchasingTime));
		
		facts.add(String.format("(BLINKY (RANGE %d))", (int)this.GHOST_RANGE));
		facts.add(String.format("(INKY (RANGE %d))", (int)this.GHOST_RANGE));
		facts.add(String.format("(PINKY (RANGE %d))", (int)this.GHOST_RANGE));
		facts.add(String.format("(SUE (RANGE %d))", (int)this.GHOST_RANGE));
		
		facts.add(String.format("(BLINKY(PACMAN_MAX_DIST_TO_PP %d)", (int)this.PACMAN_MAX_DIST_TO_PP));
		facts.add(String.format("(INKY(PACMAN_MAX_DIST_TO_PP %d)", (int)this.PACMAN_MAX_DIST_TO_PP));
		facts.add(String.format("(PINKY(PACMAN_MAX_DIST_TO_PP %d)", (int)this.PACMAN_MAX_DIST_TO_PP));
		facts.add(String.format("(SUE(PACMAN_MAX_DIST_TO_PP %d)", (int)this.PACMAN_MAX_DIST_TO_PP));
		
		facts.add(String.format("(BLINKY(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int)this.SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(INKY(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int)this.SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(PINKY(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int)this.SAFETY_DISTANCE_WHEN_EDIBLE));
		facts.add(String.format("(SUE(SAFETY_DISTANCE_WHEN_EDIBLE %d))", (int)this.SAFETY_DISTANCE_WHEN_EDIBLE));
		
		facts.add(String.format("(BLINKY(SURE_DEATH_DISTANCE %d))", (int)this.SURE_DEATH_DISTANCE));
		facts.add(String.format("(INKY(SURE_DEATH_DISTANCE %d))", (int)this.SURE_DEATH_DISTANCE));
		facts.add(String.format("(PINKY(SURE_DEATH_DISTANCE %d))", (int)this.SURE_DEATH_DISTANCE));
		facts.add(String.format("(SUE(SURE_DEATH_DISTANCE %d))", (int)this.SURE_DEATH_DISTANCE));
		
		facts.add(String.format("(SUE(ORBITING_DISTANCE %d))", (int)this.ORBITING_DISTANCE));
		facts.add(String.format("(SUE(CHASING_TIME_LIMIT %d))", (int)this.CHASING_TIME_LIMIT));


		return facts;
	}
	
	public void parseFact(Fact actionFact) 
	{
		
	} 	

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub
		
	}

}
