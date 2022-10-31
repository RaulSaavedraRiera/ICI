package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostsArriveBeforePacManToPPill implements Transition {
	
	GHOST ghost;
	public GhostsArriveBeforePacManToPPill(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		PacManNearPPillTransition near = new PacManNearPPillTransition();
		return near.evaluate(input) && (input.getMinPacmanDistancePPill() > input.getMinGhostDistancePPill(ghost));
	}
	
	@Override
	public String toString() {
		return "Ghost arrives before PacMan to Power Pill";
	}
}