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
	
	private int[] ghostPositions;
	private int[] minGhostsDistancePPill;
	private int[] distanceToPacman;
	
	private final int GHOST_RANGE = 50;
	private final int PACMAN_MAX_DIST_TO_PP = 40;
	private final int SAFETY_DISTANCE_WHEN_EDIBLE = 20;
	private int BLINKYremainingTime;
	private int INKYremainingTime;
	private int PINKYremainingTime;
	private int SUEremainingTime;
	
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
