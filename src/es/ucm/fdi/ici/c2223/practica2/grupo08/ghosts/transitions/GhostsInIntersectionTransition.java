package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostsInIntersectionTransition implements Transition {

	GHOST ghost;
	int cont;
	public GhostsInIntersectionTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
		
		cont = 0;
	}
	
	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput)in;
		
		int thisGhostNode = input.getGhostPositions()[ghost.ordinal()];
		
		int[] junctions = input.getJunctionsIndices();
		
		for (int i = 0; i < junctions.length; i++) 
		{
			if (junctions[i] == thisGhostNode) 
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		cont++;
		return "Ghost in intersection" + cont;
	}

	
	
}
