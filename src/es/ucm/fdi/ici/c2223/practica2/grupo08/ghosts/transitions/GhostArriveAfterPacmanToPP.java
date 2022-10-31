package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;

public class GhostArriveAfterPacmanToPP implements Transition {
	GHOST ghost;
	public GhostArriveAfterPacmanToPP(GHOST ghost){
		super();
		this.ghost = ghost;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		GhostsArriveBeforePacManToPPill arriveBefore = new GhostsArriveBeforePacManToPPill(ghost);
		return !arriveBefore.evaluate(input);
	}}
