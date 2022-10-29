package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class CurrentDirectionFreeTransition implements Transition {

	private GhostData gData; 
	private GHOST ghost;
	
	public CurrentDirectionFreeTransition(GHOST ghost, GhostData ghostData) {
		super();
		
		gData = ghostData;
		this.ghost = ghost;
	}


	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		
		int ghostNode = input.getGhostPositions()[ghost.ordinal()];
		MOVE ghostNextMove = gData.getGhostMove(ghost);
		
		boolean doesDirExists = false;
		
		for (MOVE m : input.getPossibleDirections(ghost)) 
		{
			if (m == ghostNextMove) 
				return !gData.junctionMarks.get(ghostNode).containsKey(ghostNextMove);
		}
		
		return false;
	}


	@Override
	public String toString() {
		return "Current Direction Free";
	}

	
	
}
