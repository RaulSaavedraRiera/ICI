package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
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

		actions = new HashMap<String, Action>();

		gData = new GhostData();

		junctionManager = new JunctionManager();

		// chase action
		Action BLINKYchases = new ChaseJunctionsAction(GHOST.BLINKY, gData, junctionManager);
		actions.put("BLINKYchases", BLINKYchases);
		Action PINKYchases = new ChaseJunctionsAction(GHOST.PINKY, gData, junctionManager);
		actions.put("PINKYchases", PINKYchases);
		Action INKYchases = new ChaseJunctionsAction(GHOST.INKY, gData, junctionManager);
		actions.put("INKYchases", INKYchases);
		Action SUEchases = new ChaseJunctionsAction(GHOST.SUE, gData, junctionManager);
		actions.put("SUEchases", SUEchases);

		// runaway action
		Action BLINKYrunAway = new RunAwayAction(GHOST.BLINKY, gData);
		actions.put("BLINKYrunAway", BLINKYrunAway);
		Action PINKYrunAway = new RunAwayAction(GHOST.PINKY, gData);
		actions.put("BLINKYrunAway", PINKYrunAway);
		Action INKYrunAway = new RunAwayAction(GHOST.INKY, gData);
		actions.put("BLINKYrunAway", INKYrunAway);
		Action SUErunAway = new RunAwayAction(GHOST.SUE, gData);
		actions.put("BLINKYrunAway", SUErunAway);

		// runaway to ghost action
		Action BLINKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.BLINKY, gData);
		actions.put("BLINKYrunAwayToGhost", BLINKYrunAwayToGhost);
		Action PINKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.PINKY, gData);
		actions.put("PINKYrunAwayToGhost", PINKYrunAwayToGhost);
		Action INKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.INKY, gData);
		actions.put("INKYrunAwayToGhost", INKYrunAwayToGhost);
		Action SUErunAwayToGhost = new RunAwayToGhostAction(GHOST.SUE, gData);
		actions.put("SUErunAwayToGhost", SUErunAwayToGhost);

		// search objective action
		Action BLINKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.BLINKY, gData);
		actions.put("BLINKYsearchsObjective", BLINKYsearchsObjective);
		Action PINKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.INKY, gData);
		actions.put("PINKYsearchsObjective", PINKYsearchsObjective);
		Action INKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.PINKY, gData);
		actions.put("INKYsearchsObjective", INKYsearchsObjective);
		Action SUEsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.BLINKY, gData);
		actions.put("SUEsearchsObjective", SUEsearchsObjective);

		// go to objective action
		Action BLINKgoesToObjective = new GoToObjectiveAction(GHOST.BLINKY, gData);
		actions.put("BLINKgoesToObjective", BLINKgoesToObjective);
		Action PINKYgoesToObjective = new GoToObjectiveAction(GHOST.INKY, gData);
		actions.put("PINKYgoesToObjective", PINKYgoesToObjective);
		Action INKYgoesToObjective = new GoToObjectiveAction(GHOST.PINKY, gData);
		actions.put("INKYgoesToObjective", INKYgoesToObjective);
		Action SUEgoesToObjective = new GoToObjectiveAction(GHOST.BLINKY, gData);
		actions.put("SUEgoesToObjective", SUEgoesToObjective);

		// go to nearest PP action
		Action BLINKgoToNearestPP = new GoToNearestPPAction(GHOST.BLINKY, gData);
		actions.put("BLINKgoToNearestPP", BLINKgoToNearestPP);
		Action PINKYgoToNearestPP = new GoToNearestPPAction(GHOST.INKY, gData);
		actions.put("PINKYgoToNearestPP", PINKYgoToNearestPP);
		Action INKYgoToNearestPP = new GoToNearestPPAction(GHOST.PINKY, gData);
		actions.put("INKYgoToNearestPP", INKYgoToNearestPP);
		Action SUEgoToNearestPP = new GoToNearestPPAction(GHOST.BLINKY, gData);
		actions.put("SUEgoToNearestPP", SUEgoToNearestPP);

		ghostRuleEngines = new EnumMap<GHOST, RuleEngine>(GHOST.class);
		for (GHOST ghost : GHOST.values()) {
			String rulesFile = String.format("RulesGhost.clp", RULES_PATH);
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
		RulesInput input = new GhostsInput(game);
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
