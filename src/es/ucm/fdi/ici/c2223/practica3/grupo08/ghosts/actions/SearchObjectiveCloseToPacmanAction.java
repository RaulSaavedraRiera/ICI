package es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions;

import java.util.Random;

import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.rules.RulesAction;
import jess.Fact;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class SearchObjectiveCloseToPacmanAction implements RulesAction {

    GHOST ghost;
    GhostData gData;
	public SearchObjectiveCloseToPacmanAction(GHOST ghost, GhostData ghostData) {
		this.ghost = ghost;
		gData = ghostData;
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

	@Override
	public void parseFact(Fact actionFact) {
		// TODO Auto-generated method stub
		
	}

	

}
