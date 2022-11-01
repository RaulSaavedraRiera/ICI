package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostHasArrivedOrDoesNotHaveObjective implements Transition {

	GHOST ghost;
	GhostData gData;
	public GhostHasArrivedOrDoesNotHaveObjective(GHOST ghost, GhostData ghostData) {
		super();
		this.ghost = ghost;
		gData = ghostData;
	}
	
	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput)in;
		
		GhostHasArrivedToObjectiveTransition arrivedToObjective = new GhostHasArrivedToObjectiveTransition(ghost);
		GhostHasObjectiveTransition hasObjective = new GhostHasObjectiveTransition(ghost, gData);
		
		return arrivedToObjective.evaluate(input) || !hasObjective.evaluate(input);
	}

	@Override
	public String toString() {
		return "Ghost has arrived to its objective or does not have one";
	}

	
	
}
