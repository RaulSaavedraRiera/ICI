package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.example;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
public class PacManNearPPillTransition implements Transition {

	public static double thresold = 30;
	
	public PacManNearPPillTransition() {
		super();
	}


	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		return input.getMinPacmanDistancePPill() < thresold;
	}


	@Override
	public String toString() {
		return "MsPacman near PPill";
	}

	
	
}