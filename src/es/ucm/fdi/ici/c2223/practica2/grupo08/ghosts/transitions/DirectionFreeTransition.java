package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class DirectionFreeTransition implements Transition {

	private GhostData gData;
	private MOVE moveToCheck; 
	private GHOST ghost;
	
	public DirectionFreeTransition(GHOST ghost, GhostData ghostData, MOVE move) {
		super();
		
		gData = ghostData;
		moveToCheck = move;
		this.ghost = ghost;
	}


	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		
		int ghostNode = input.getGhostPositions()[ghost.ordinal()];
		
		CurrentDirectionFreeTransition currentDir = new CurrentDirectionFreeTransition(ghost, gData);
		
		for (MOVE m : input.getPossibleDirections(ghost)) 
		{
			if (m == moveToCheck) 
				return !gData.junctionMarks.get(ghostNode).containsKey(moveToCheck);
		}
		
		return false;
	}


	@Override
	public String toString() {
		return "Direccion libre: " + moveToCheck;
	}

	
	
}
