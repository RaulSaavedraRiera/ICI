package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;

public class GoToNearestPillAction implements Action {
	public GoToNearestPillAction() {
		// TODO Auto-generated constructor stub
	}

    
	@Override
	public MOVE execute(Game game) {
		
		return game.getApproximateNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), getNearestPill(game), game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Go Tonearest Pill Action";
	}
	
	int getNearestPill(Game game) {
		
		int[] pills = game.getActivePillsIndices();
		if (pills.length == 0)
			return -1;

		// variables para controlar las posiciones
		int distance = 9999, to = -1, currentDistance;

		for (int pill : pills) // comprobamos para las pill cual es la mas cercana
		{
			currentDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());

			if (currentDistance < distance) {
				to = pill;
				distance = currentDistance;
			}
		}

		// devolvemos la pill mas cercana
		return to;
	}
}
