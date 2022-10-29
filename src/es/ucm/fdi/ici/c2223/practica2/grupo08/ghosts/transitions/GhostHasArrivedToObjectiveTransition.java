package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostHasArrivedToObjectiveTransition implements Transition {

	private GHOST ghost;
	
	public GhostHasArrivedToObjectiveTransition(GHOST ghost) {
		super();
		
		this.ghost = ghost;
	}


	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		
		return input.getDistanceToObjective()[ghost.ordinal()] <= 4;
	}


	@Override
	public String toString() {
		return "Has arrived to its objective";
	}

	
	
}
