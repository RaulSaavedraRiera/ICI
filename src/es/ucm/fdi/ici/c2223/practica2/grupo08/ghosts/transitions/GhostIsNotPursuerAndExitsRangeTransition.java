package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostIsNotPursuerAndExitsRangeTransition implements Transition  {

	GHOST ghost;
	public GhostIsNotPursuerAndExitsRangeTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}



	@Override
	public boolean evaluate(Input in) {
		
		boolean isPursuer = new GhostIsPursuerTransition(ghost).evaluate(in);
		boolean entersRange = new GhostEntersPacmanRangeTransition(ghost).evaluate(in);
		
		return !isPursuer && !entersRange;
	}



	@Override
	public String toString() {
		return "Ghost is not pursuer and exits range";
	}

	
	
}
