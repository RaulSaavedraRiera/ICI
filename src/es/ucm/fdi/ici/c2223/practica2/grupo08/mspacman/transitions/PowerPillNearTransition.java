package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.fsm.Transition;

public class PowerPillNearTransition implements Transition {

	private double probability;
	public PowerPillNearTransition(double probability) {
		this.probability = probability;
	}

	@Override
	public boolean evaluate(Input in) {
		return Math.random() < this.probability;
	}

	@Override
	public String toString() {
		return String.format("Random Transition: %s", this.probability);
	}
}
