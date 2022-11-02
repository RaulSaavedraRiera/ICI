package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import java.util.ArrayList;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostHasArrivedOrDoesNotHaveObjective implements Transition {

	private GHOST ghost;
	private GhostData gData;
	private ArrayList<GhostData> gDataRef;
	public GhostHasArrivedOrDoesNotHaveObjective(GHOST ghost, ArrayList<GhostData> ghostData) {
		super();
		this.ghost = ghost;
		gDataRef = ghostData;
		gData = ghostData.get(0);
	}
	
	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput)in;
		
		GhostHasArrivedToObjectiveTransition arrivedToObjective = new GhostHasArrivedToObjectiveTransition(ghost, gDataRef);
		GhostHasObjectiveTransition hasObjective = new GhostHasObjectiveTransition(ghost, gDataRef);
		
		return arrivedToObjective.evaluate(input) || !hasObjective.evaluate(input);
	}

	@Override
	public String toString() {
		return "Ghost has arrived to its objective or does not have one";
	}

	
	
}