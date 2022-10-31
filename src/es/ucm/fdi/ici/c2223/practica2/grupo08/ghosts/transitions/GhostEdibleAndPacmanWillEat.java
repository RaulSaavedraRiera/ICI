package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;

public class GhostEdibleAndPacmanWillEat implements Transition {

	GHOST ghost;
	public GhostEdibleAndPacmanWillEat(GHOST ghost) {
		super();
		this.ghost = ghost;
	}
	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		GhostsEdibleTransition edible = new GhostsEdibleTransition(ghost);
		float speed = 0.5f;
		return edible.evaluate(input) && (input.getDistanceToPacman(ghost) * speed * 0.5 < input.getRemainingTime(ghost) + input.getSAFETY_DISTANCE_WHEN_EDIBLE());
	}

}
