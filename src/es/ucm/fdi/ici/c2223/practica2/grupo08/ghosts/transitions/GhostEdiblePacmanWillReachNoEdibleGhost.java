package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants;
import pacman.game.Constants.GHOST;

public class GhostEdiblePacmanWillReachNoEdibleGhost implements Transition {
	GHOST ghost;
	public GhostEdiblePacmanWillReachNoEdibleGhost(GHOST ghost) {
		super();
		this.ghost  = ghost;
	}
	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		GhostEdibleAndPacmanWillEat willEat = new GhostEdibleAndPacmanWillEat(ghost);
		for (Constants.GHOST ghostType : Constants.GHOST.values()) {
			GhostsEdibleTransition ghostEdible = new GhostsEdibleTransition(ghostType);
			if(ghostType != ghost) {
				if(ghostEdible.evaluate(input)) {
					return willEat.evaluate(input);
				}
			}
		}

		return false;
	}
}
