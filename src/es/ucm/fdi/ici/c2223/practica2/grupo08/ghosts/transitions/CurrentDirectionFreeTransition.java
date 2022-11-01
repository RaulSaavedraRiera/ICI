package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions;

import java.util.ArrayList;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.fsm.Transition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class CurrentDirectionFreeTransition implements Transition {

	private GhostData gData;
	private GHOST ghost;
	private MOVE ghostNextMove;
	private MOVE moveToCheck;

	private int cont;

	public CurrentDirectionFreeTransition(GHOST ghost, ArrayList<GhostData> ghostData, MOVE moveToCheck) {
		super();

		gData = ghostData.get(0);
		this.ghost = ghost;

		this.moveToCheck = moveToCheck;

		cont = 0;
	}

	@Override
	public boolean evaluate(Input in) {
		GhostsInput input = (GhostsInput) in;
		
		int ghostNode = input.getGhostPositions()[ghost.ordinal()];
		ghostNextMove = gData.getGhostMove(ghost);

		if (moveToCheck == ghostNextMove) {

			for (MOVE m : input.getPossibleDirections(ghost)) {
				if (m == ghostNextMove && gData.isDirectionAvailable(ghostNode, ghostNextMove)) {
					gData.markDirection(ghostNode, ghostNextMove);

					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String toString() {
		cont++;
		return "Current Direction Free: " + ghostNextMove + ", Ghost: " + ghost.name() + " " + cont;
	}

}
