package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class OneEdibleGhostNearTransition implements Transition {

	
	public OneEdibleGhostNearTransition() {		
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		return !input.groupEdibleGhosts();
		
	}

	@Override
	public String toString() {
		return String.format("One Edible Ghost");
	}

}
