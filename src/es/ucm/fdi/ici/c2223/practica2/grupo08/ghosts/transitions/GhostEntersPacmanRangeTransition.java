package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostEntersPacmanRangeTransition implements Transition {

	GHOST ghost;

	public GhostEntersPacmanRangeTransition(GHOST ghost) {
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		
		GhostsInput input = (GhostsInput) in;
		
		int distanceToPacman;
		
		try {
			distanceToPacman = input.getDistanceToPacman(ghost);
		}
		
		catch(Exception e)
		{
			distanceToPacman = 0;
		}
		
		return distanceToPacman <= input.getGHOST_RANGE();
	}

	@Override
	public String toString() {
		return "Ghost is edible";
	}

}
