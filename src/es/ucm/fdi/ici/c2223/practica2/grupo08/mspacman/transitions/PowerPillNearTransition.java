package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class PowerPillNearTransition implements Transition {

	final int MAX_DISTANCE_POWERPILL = 30;
	
	public PowerPillNearTransition() {		
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;
		return input.pacmanDistanceNearPowerPill() <= MAX_DISTANCE_POWERPILL;
		
	}

	@Override
	public String toString() {
		return String.format("PP near PacMan");
	}
}
