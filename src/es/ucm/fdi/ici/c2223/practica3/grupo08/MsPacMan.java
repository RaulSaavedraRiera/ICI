package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.io.File;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChaseEdiblePA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChaseGhostPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChaseGroupGhostsPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChaseNearestPillPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChasePillPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.ChasePowerPillPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayCornerPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayLairPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayNearestChasingGhostAnyDirPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayNearestChasingGhostNonFollowPA;
import es.ucm.fdi.ici.c2223.practica3.grupo08.mspacman.actions.RunAwayPowerPillPA;
import es.ucm.fdi.ici.rules.RuleEngine;
import es.ucm.fdi.ici.rules.RulesAction;
import es.ucm.fdi.ici.rules.RulesInput;
import es.ucm.fdi.ici.rules.observers.ConsoleRuleEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	HashMap<String, RulesAction> map;
	RuleEngine engine;
	private static final String RULES_PATH = "es"+File.separator+"ucm"+File.separator+"fdi"+File.separator+"ici"+File.separator+"practica3"+File.separator+"grupo08"+File.separator;
	
	public MsPacMan() {
		
		setName("MsPacManIfUrNasty");
		setTeam("Team 08");
		
		map = new HashMap<String,RulesAction>();

		//Fi1l Actions

		RulesAction RunAwayLair = new RunAwayLairPA();
		RulesAction RunAwayCorner = new RunAwayCornerPA();
		RulesAction RunAwayNearestchasingGhostNonFollow = new RunAwayNearestChasingGhostNonFollowPA();
		RulesAction RunAwayNearestChasingGhostAnyDir = new RunAwayNearestChasingGhostAnyDirPA();
		RulesAction RunAwayPowerPill = new RunAwayPowerPillPA();
		RulesAction ChaseEdible = new ChaseEdiblePA();
		RulesAction ChaseEdibleGroup = new ChaseGroupGhostsPA();	
		RulesAction ChasePowerPill = new ChasePowerPillPA();
		RulesAction ChasePill = new ChasePillPA();
		RulesAction ChaseNearestPill = new ChaseNearestPillPA();
		RulesAction ChaseGhost = new ChaseGhostPA();
				
	
		map.put("RunAwayLair", RunAwayLair);
		map.put("RunAwayCorner", RunAwayCorner);
		map.put("RunAwayNearestChasingGhostNonFollow", RunAwayNearestchasingGhostNonFollow);
		map.put("RunAwayNearestChasingGhostAnyDir", RunAwayNearestChasingGhostAnyDir);
		map.put("ChaseEdible", ChaseEdible);
		map.put("ChaseEdibleGroup", ChaseEdibleGroup);
		map.put("RunAwayPowerPill", RunAwayPowerPill);
		map.put("ChasePowerPill", ChasePowerPill);
		map.put("ChasePill", ChasePill);
		map.put("ChaseNearestPill", ChaseNearestPill);
		map.put("ChaseGhost", ChaseGhost);


		String rulesFile = String.format("RulesPacman.clp", RULES_PATH, "mspacman");
		RuleEngine engine  = new RuleEngine("MsPacMan",rulesFile, map);
		
		//add observer to every Ghost
		//ConsoleRuleEngineObserver observer = new ConsoleRuleEngineObserver(ghost.name(), true);
		//engine.addObserver(observer);
		
		//add observer only to BLINKY
		ConsoleRuleEngineObserver observer = new ConsoleRuleEngineObserver("msPacMan", true);
		engine.addObserver(observer);
		
		
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {

		// Process input
		RulesInput input = new MsPacManInput(game);
		// load facts
		// reset the rule engines

		engine.reset();
		engine.assertFacts(input.getFacts());

		return engine.run(game);

	}

}
