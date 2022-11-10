package es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman;
import java.util.Collection;
import java.util.Vector;

import es.ucm.fdi.ici.rules.RulesInput;
import pacman.game.Game;
import pacman.game.Constants.GHOST;

public class MsPacManInput extends RulesInput {

	// Estos hacen falta para rules si lo vamos a sacar de GhostInput?
	private int[] ghostsEdibleTime;
	private int[] ghostsLairTime;
	private int[] ghostsPos;
	private int[] ghostsDistanceToPacMan;
	
	private int lairIndex;
	private int distanceToLair;
	private int[] cornerIndexes;
	private int distanceToNearestCorner;
	
	private int nearestChasingGhostNotBehindIndex; // Fantasma mas cercano que persigue pero no en la misma dir
	private int nearestChasingGhostAnyDirIndex;
	private int nearestEdibleGhostIndex;
	private int nearestPowerPillIndex;
	private int nearestPillIndex;
	
	private int distanceToNearestChasingGhostNotBehind;
	private int distanceToNearestChasingGhostAnyDir;
	private int distanceToNearestEdible;
	private int distanceToNearestPowerPill;
	private int distanceToNearestPill;

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
		facts.add(String.format("(MSPACMAN (distToNearestPill %d))", this.distanceToNearestPill));
		
		return facts;
	}

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub
		
	}

}
