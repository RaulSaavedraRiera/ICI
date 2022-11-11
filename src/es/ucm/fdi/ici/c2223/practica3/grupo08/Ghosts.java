package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.util.EnumMap;

import es.ucm.fdi.ici.c2223.practica2.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.ChaseAction;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	
	public Ghosts() {
		super();

		setName("Los Bellacos de Atochas");

		setTeam("Team 08");
		
		GhostData gData;
		
		Action BLINKYchases = new ChaseAction(GHOST.BLINKY, )
	}
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		return null;
	}

}
