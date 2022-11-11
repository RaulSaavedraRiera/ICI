package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	HashMap<String, Action> map;
	
	public MsPacMan() {
		
		setName("MsPacManIfUrNasty");
		setTeam("Team 08");
		
		map = new HashMap<String,Action>();

		//Fi1l Actions

		Action BLINKYchases = new ChaseActionCGHOST. BLINKY);
		Action INKYchases = new ChaseActionCGHOST. INKY);

		Action PINKYchases = new ChaseActionCGHOST. PINKY);
		Action SUEchases = new ChaseActionCGHOST. SUE);

		Action BLINKYrunsAway = new RunAwayActionCGHOST. BLINKY);
		Action INKYrunsAway = new RunAwayActionCGHOST. INKY);
		Action PINKYrunsAway = new RunAwayActionCGHOST. PINKY);
		Action SUErunsAway = new RunAwayActionCGHOST. SUE);

		map.put("", BLINKYaction);
		.put("PINKYchases", PINKYchases);

		map

		map.
		.putC"BLINKYrunsAway", BLINKYrunsAway);

		map

		map.
		map.
		map.

		put("BLINKYchases”, BLINKYchases);
		putC"INKYchases", INKYchases);

		putC"SUEchases", SUEchases);
		putC"INKYrunsAway", INKYrunsAway);

		putC"PINKYrunsAway", PINKYrunsAway);
		putC"SUErunsAmay", SUErunsAway);
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		return null;
	}

}
