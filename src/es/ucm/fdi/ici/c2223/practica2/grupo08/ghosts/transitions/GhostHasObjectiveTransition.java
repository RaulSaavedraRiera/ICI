package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import java.util.ArrayList;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostHasObjectiveTransition implements Transition {

	private GhostData gData;
	private GHOST ghost;
	
	public GhostHasObjectiveTransition(GHOST ghost, ArrayList<GhostData> ghostData) {
		super();
		
		gData = ghostData.get(0);
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		
		return gData.getGhostObjective(ghost) != -1;
	}


	@Override
	public String toString() {
		return "Ghost has objective";
	}

	
	
}
