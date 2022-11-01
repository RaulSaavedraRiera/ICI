package es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions;

import java.util.ArrayList;
import java.util.Random;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class SearchObjectiveCloseToPacmanAction implements Action {

    GHOST ghost;
    GhostData gData;
	public SearchObjectiveCloseToPacmanAction(GHOST ghost, ArrayList<GhostData> ghostData) {
		this.ghost = ghost;
		gData = ghostData.get(0);
	}

	@Override
	public MOVE execute(Game game) {

		Boolean close = false;
		Random rnd = new Random();
		
		int pacmanNode = game.getPacmanCurrentNodeIndex();

		int randomNode = -1;

		int[] junctions = game.getJunctionIndices();

		while (!close) {

			int randomIndex = rnd.nextInt(junctions.length);
			randomNode = junctions[randomIndex];

			int distance = game.getShortestPathDistance(randomNode, pacmanNode);

			if (distance < 50 * 2)
				close = true;
		}

		if (gData.getGhostObjective(ghost) == -1) 
			gData.setGhostObjective(ghost, randomNode);
		
        return MOVE.NEUTRAL;
	}

	@Override
	public String getActionId() {
		return ghost + "searchsAnObjective";
	}

	

}
