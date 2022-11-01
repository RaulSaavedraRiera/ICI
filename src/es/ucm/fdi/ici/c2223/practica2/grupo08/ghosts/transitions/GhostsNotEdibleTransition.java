package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostsNotEdibleTransition implements Transition  {

	GHOST ghost;
	public GhostsNotEdibleTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}



	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput)in;
		
		boolean edible = new GhostsEdibleTransition(ghost).evaluate(input);
		
		return !edible;
	}



	@Override
	public String toString() {
		return "Ghost is not edible";
	}

	
	
}
