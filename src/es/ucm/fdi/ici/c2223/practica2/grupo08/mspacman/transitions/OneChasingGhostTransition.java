package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class OneChasingGhostTransition implements Transition {

	
	public OneChasingGhostTransition() {		
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		return input.chasingGhostsInRange() == 1;
		
	}

	@Override
	public String toString() {
		return String.format("Pacman near ONE chasing Ghost");
	}

}
