package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostIsPursuerTransition implements Transition  {

	GHOST ghost;
	public GhostIsPursuerTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}



	@Override
	public boolean evaluate(Input in) {
		
		return ghost == GHOST.BLINKY || ghost == GHOST.PINKY;
	}



	@Override
	public String toString() {
		return "Ghost is Pursuer";
	}

	
	
}
