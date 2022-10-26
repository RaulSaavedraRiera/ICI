package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class CornerNearTransition implements Transition {

	final int MAX_DISTANCE_CORNER = 30;
	
	public CornerNearTransition() {		
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		return input.pacmanDistanceNearCorner() <= MAX_DISTANCE_CORNER;
		
	}

	@Override
	public String toString() {
		return String.format("Pacman near to Corner");
	}

}
