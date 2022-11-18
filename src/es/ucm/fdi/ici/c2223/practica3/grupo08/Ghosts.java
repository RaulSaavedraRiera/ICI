package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.ChaseJunctionsAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.GoToNearestPPAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.GoToObjectiveAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.RunAwayToGhostAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.SearchObjectiveCloseToPacmanAction;
import es.ucm.fdi.ici.rules.RuleEngine;
import es.ucm.fdi.ici.rules.RulesAction;
import es.ucm.fdi.ici.rules.RulesInput;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	private static final String RULES_PATH = "es" + File.separator + "ucm" + File.separator + "fdi" + File.separator
			+ "ici" + File.separator + "c2223" + File.separator + "practica3" + File.separator + "grupo08"
			+ File.separator;

	private HashMap<String, RulesAction> actions;
	private GhostData gData;
	private JunctionManager junctionManager;

	EnumMap<GHOST, RuleEngine> ghostRuleEngines;

	public Ghosts() {
		super();

		setName("Los Bellacos de Atocha");

		setTeam("Team 08");

		actions = new HashMap<String, RulesAction>();

		gData = new GhostData();

		junctionManager = new JunctionManager();

		// chase action
		RulesAction BLINKYchases = new ChaseJunctionsAction(GHOST.BLINKY, gData, junctionManager);
		actions.put("BLINKYchases", BLINKYchases);
		RulesAction PINKYchases = new ChaseJunctionsAction(GHOST.PINKY, gData, junctionManager);
		actions.put("PINKYchases", PINKYchases);
		RulesAction INKYchases = new ChaseJunctionsAction(GHOST.INKY, gData, junctionManager);
		actions.put("INKYchases", INKYchases);
		RulesAction SUEchases = new ChaseJunctionsAction(GHOST.SUE, gData, junctionManager);
		actions.put("SUEchases", SUEchases);

		// runaway action
		RulesAction BLINKYrunAway = new RunAwayAction(GHOST.BLINKY, gData);
		actions.put("BLINKYrunAway", BLINKYrunAway);
		RulesAction PINKYrunAway = new RunAwayAction(GHOST.PINKY, gData);
		actions.put("BLINKYrunAway", PINKYrunAway);
		RulesAction INKYrunAway = new RunAwayAction(GHOST.INKY, gData);
		actions.put("BLINKYrunAway", INKYrunAway);
		RulesAction SUErunAway = new RunAwayAction(GHOST.SUE, gData);
		actions.put("BLINKYrunAway", SUErunAway);

		// runaway to ghost action
		RulesAction BLINKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.BLINKY, gData);
		actions.put("BLINKYrunAwayToGhost", BLINKYrunAwayToGhost);
		RulesAction PINKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.PINKY, gData);
		actions.put("PINKYrunAwayToGhost", PINKYrunAwayToGhost);
		RulesAction INKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.INKY, gData);
		actions.put("INKYrunAwayToGhost", INKYrunAwayToGhost);
		RulesAction SUErunAwayToGhost = new RunAwayToGhostAction(GHOST.SUE, gData);
		actions.put("SUErunAwayToGhost", SUErunAwayToGhost);

		// search objective action
		RulesAction BLINKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.BLINKY, gData);
		actions.put("BLINKYsearchsObjective", BLINKYsearchsObjective);
		RulesAction PINKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.INKY, gData);
		actions.put("PINKYsearchsObjective", PINKYsearchsObjective);
		RulesAction INKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.PINKY, gData);
		actions.put("INKYsearchsObjective", INKYsearchsObjective);
		RulesAction SUEsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.BLINKY, gData);
		actions.put("SUEsearchsObjective", SUEsearchsObjective);

		// go to objective action
		RulesAction BLINKgoesToObjective = new GoToObjectiveAction(GHOST.BLINKY, gData);
		actions.put("BLINKgoesToObjective", BLINKgoesToObjective);
		RulesAction PINKYgoesToObjective = new GoToObjectiveAction(GHOST.INKY, gData);
		actions.put("PINKYgoesToObjective", PINKYgoesToObjective);
		RulesAction INKYgoesToObjective = new GoToObjectiveAction(GHOST.PINKY, gData);
		actions.put("INKYgoesToObjective", INKYgoesToObjective);
		RulesAction SUEgoesToObjective = new GoToObjectiveAction(GHOST.BLINKY, gData);
		actions.put("SUEgoesToObjective", SUEgoesToObjective);

		// go to nearest PP action
		RulesAction BLINKgoToNearestPP = new GoToNearestPPAction(GHOST.BLINKY, gData);
		actions.put("BLINKgoToNearestPP", BLINKgoToNearestPP);
		RulesAction PINKYgoToNearestPP = new GoToNearestPPAction(GHOST.INKY, gData);
		actions.put("PINKYgoToNearestPP", PINKYgoToNearestPP);
		RulesAction INKYgoToNearestPP = new GoToNearestPPAction(GHOST.PINKY, gData);
		actions.put("INKYgoToNearestPP", INKYgoToNearestPP);
		RulesAction SUEgoToNearestPP = new GoToNearestPPAction(GHOST.BLINKY, gData);
		actions.put("SUEgoToNearestPP", SUEgoToNearestPP);

		ghostRuleEngines = new EnumMap<GHOST, RuleEngine>(GHOST.class);
		for (GHOST ghost : GHOST.values()) {
			String rulesFile = String.format("%sRulesGhost.clp", RULES_PATH);
			RuleEngine engine = new RuleEngine(ghost.name(), rulesFile, actions);
			ghostRuleEngines.put(ghost, engine);

			// add observer to every Ghost
			// ConsoleRuleEngineObserver observer = new
			// ConsoleRuleEngineObserver(ghost.name(), true);
			// engine.addObserver(observer);
		}
	}

	public void preCompute(String opponent) {

		gData.reset();
		junctionManager.reset();
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		// Process input
		RulesInput input = new GhostsInput(game, gData);
		// load facts
		// reset the rule engines
		for (RuleEngine engine : ghostRuleEngines.values()) {
			engine.reset();
			engine.assertFacts(input.getFacts());
		}

		EnumMap<GHOST, MOVE> result = new EnumMap<GHOST, MOVE>(GHOST.class);
		for (GHOST ghost : GHOST.values()) {
			RuleEngine engine = ghostRuleEngines.get(ghost);
			MOVE move = engine.run(game);
			result.put(ghost, move);
		}

		return result;
	}

}
