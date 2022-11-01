package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import java.util.ArrayList;

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
	
	public DirectionFreeTransition(GHOST ghost, ArrayList<GhostData> ghostData, MOVE move) {
		super();

		gData = ghostData.get(0);
		moveToCheck = move;
		this.ghost = ghost;
	}


	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		
		int ghostNode = input.getGhostPositions()[ghost.ordinal()];
		
		for (MOVE m : input.getPossibleDirections(ghost)) 
		{
			if (m == moveToCheck && gData.isDirectionAvailable(ghostNode, moveToCheck)) 
			{
				gData.markDirection(ghostNode, moveToCheck);
					
				return true;
			}
		}
		
		return false;
	}


	@Override
	public String toString() {
		return "Direccion libre: " + moveToCheck;
	}

	
	
}
