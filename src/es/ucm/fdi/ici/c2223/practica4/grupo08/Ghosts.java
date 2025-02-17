package es.ucm.fdi.ici.c2223.practica4.grupo08;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsFuzzyMemory;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.JunctionManager;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.MaxActionSelector;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.MsPacmanPredictor;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.GoToLairAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.GoToNearestPPAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.ProtectEdibleAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.RunAwayToGhostAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.SearchForPacmanAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.TrapCornerAction;
import es.ucm.fdi.ici.fuzzy.ActionSelector;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	private static final String RULES_PATH = "bin" + File.separator + "es" + File.separator + "ucm" + File.separator
			+ "fdi" + File.separator + "ici" + File.separator + "c2223" + File.separator + "practica4" + File.separator
			+ "grupo08" + File.separator + "ghosts" + File.separator;

	FuzzyEngine[] fuzzyEngines;

	GhostsFuzzyMemory fuzzyMemory;
	MsPacmanPredictor pacmanPredictor;
	JunctionManager junctManager;

	public Ghosts() {
		super();
		setName("Los Bellacos de Atocha");

		setTeam("Team 08");

		fuzzyMemory = new GhostsFuzzyMemory();
		junctManager = new JunctionManager();

		Action[] actions = { new ChaseAction(GHOST.BLINKY, fuzzyMemory, junctManager), new ChaseAction(GHOST.PINKY, fuzzyMemory, junctManager),
				new ChaseAction(GHOST.INKY, fuzzyMemory, junctManager), new ChaseAction(GHOST.SUE, fuzzyMemory, junctManager),
				new RunAwayAction(GHOST.BLINKY, fuzzyMemory), new RunAwayAction(GHOST.PINKY, fuzzyMemory),
				new RunAwayAction(GHOST.INKY, fuzzyMemory), new RunAwayAction(GHOST.SUE, fuzzyMemory),
				new SearchForPacmanAction(GHOST.BLINKY, fuzzyMemory),
				new SearchForPacmanAction(GHOST.PINKY, fuzzyMemory), new SearchForPacmanAction(GHOST.INKY, fuzzyMemory),
				new SearchForPacmanAction(GHOST.SUE, fuzzyMemory), new GoToLairAction(GHOST.BLINKY),
				new GoToLairAction(GHOST.PINKY), new GoToLairAction(GHOST.INKY), new GoToLairAction(GHOST.SUE),
				new GoToNearestPPAction(GHOST.BLINKY, fuzzyMemory), new GoToNearestPPAction(GHOST.PINKY, fuzzyMemory),
				new GoToNearestPPAction(GHOST.INKY, fuzzyMemory), new GoToNearestPPAction(GHOST.SUE, fuzzyMemory),
				new RunAwayToGhostAction(GHOST.BLINKY), new RunAwayToGhostAction(GHOST.PINKY),
				new RunAwayToGhostAction(GHOST.INKY), new RunAwayToGhostAction(GHOST.SUE),
				new TrapCornerAction(GHOST.BLINKY), new TrapCornerAction(GHOST.PINKY), new TrapCornerAction(GHOST.INKY),
				new TrapCornerAction(GHOST.SUE), new ProtectEdibleAction(GHOST.BLINKY),
				new ProtectEdibleAction(GHOST.PINKY), new ProtectEdibleAction(GHOST.INKY),
				new ProtectEdibleAction(GHOST.SUE) };

		ActionSelector actionSelector = new MaxActionSelector(actions);

		ConsoleFuzzyEngineObserver BLINKYobserver = new ConsoleFuzzyEngineObserver("Blinky", "BLINKYRules");
		ConsoleFuzzyEngineObserver PINKYobserver = new ConsoleFuzzyEngineObserver("Pinky", "PINKYRules");
		ConsoleFuzzyEngineObserver INKYobserver = new ConsoleFuzzyEngineObserver("Inky", "INKYRules");
		ConsoleFuzzyEngineObserver SUEobserver = new ConsoleFuzzyEngineObserver("Sue", "SUERules");

		fuzzyEngines = new FuzzyEngine[4];

		fuzzyEngines[GHOST.BLINKY.ordinal()] = new FuzzyEngine("Blinky", RULES_PATH + "BLINKY.fcl", "FuzzyBlinky",
				actionSelector);
		fuzzyEngines[GHOST.BLINKY.ordinal()].addObserver(BLINKYobserver);

		fuzzyEngines[GHOST.PINKY.ordinal()] = new FuzzyEngine("Pinky", RULES_PATH + "PINKY.fcl", "FuzzyPinky",
				actionSelector);
		fuzzyEngines[GHOST.PINKY.ordinal()].addObserver(PINKYobserver);

		fuzzyEngines[GHOST.INKY.ordinal()] = new FuzzyEngine("Inky", RULES_PATH + "INKY.fcl", "FuzzyInky",
				actionSelector);
		fuzzyEngines[GHOST.INKY.ordinal()].addObserver(INKYobserver);

		fuzzyEngines[GHOST.SUE.ordinal()] = new FuzzyEngine("Sue", RULES_PATH + "SUE.fcl", "FuzzySue", actionSelector);
		fuzzyEngines[GHOST.SUE.ordinal()].addObserver(SUEobserver);
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		if (pacmanPredictor == null)
			pacmanPredictor = new MsPacmanPredictor(game, fuzzyMemory);
		else
			pacmanPredictor.setGame(game);

		GhostsInput input = new GhostsInput(game, fuzzyMemory, pacmanPredictor, junctManager);
		input.parseInput();
		fuzzyMemory.getInput(input);

		EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

		for (GHOST g : GHOST.values()) {
			HashMap<String, Double> fvars = input.getFuzzyValues();
			fvars.putAll(fuzzyMemory.getFuzzyValues());
			fvars.putAll(input.getFuzzyValues(g));

			moves.put(g, fuzzyEngines[g.ordinal()].run(fvars, game));
		}

		return moves;
	}
	
	public void preCompute(String opponent) {

		junctManager.reset();
		fuzzyMemory.reset();
		
		if (pacmanPredictor != null)
			pacmanPredictor.reset();
	}

}
