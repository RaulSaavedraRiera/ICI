package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChaseEdiblePA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChaseGhostPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChaseGroupGhostsPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChasePillPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChasePowerPillPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayCornerPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayLairPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayNearestChasingGhostAnyDirPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayNearestChasingGhostNonFollowPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayPowerPillPA;
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

		Action RunAwayLair = new RunAwayLairPA();
		Action RunAwayCorner = new RunAwayCornerPA();
		Action RunAwayNearestchasingGhostNonFollow = new RunAwayNearestChasingGhostNonFollowPA();
		Action RunAwayNearestChasingGhostAnyDir = new RunAwayNearestChasingGhostAnyDirPA();
		Action ChaseEdible = new ChaseEdiblePA();
		Action ChaseEdibleGroup = new ChaseGroupGhostsPA();
		Action RunAwayPowerPill = new RunAwayPowerPillPA();
		Action ChasePowerPill = new ChasePowerPillPA();
		Action ChasePill = new ChasePillPA();
		Action ChaseGhost = new ChaseGhostPA();
				
	
		map.put("RunAwayLair", RunAwayLair);
		map.put("RunAwayCorner", RunAwayCorner);
		map.put("RunAwayNearestChasingGhostNonFollow", RunAwayNearestchasingGhostNonFollow);
		map.put("RunAwayNearestChasingGhostAnyDir", RunAwayNearestChasingGhostAnyDir);

		map.put("ChaseEdible", ChaseEdible);
		map.put("ChaseEdibleGroup", ChaseEdibleGroup);
		map.put("RunAwayPowerPill", RunAwayPowerPill);
		map.put("ChasePowerPill", ChasePowerPill);
		map.put("ChasePill", ChasePill);
		map.put("ChaseGhost", ChaseGhost);

		
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		return null;
	}

}
