package es.ucm.fdi.ici.c2223.practica4.grupo08;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsFuzzyMemory;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.MaxActionSelector;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.ChaseAction;
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
	FuzzyEngine fuzzyEngine;
	GhostsFuzzyMemory fuzzyMemory;

	public Ghosts() {
		super();
		setName("Los Bellacos de Atocha");

		setTeam("Team 08");

		Action[] actions = { new ChaseAction(GHOST.BLINKY) };

		ActionSelector actionSelector = new MaxActionSelector(actions);

		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan", "MsPacManRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan", RULES_PATH + "mspacman.fcl", "FuzzyMsPacMan", actionSelector);
		fuzzyEngine.addObserver(observer);

		fuzzyMemory = new GhostsFuzzyMemory();
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		GhostsInput input = new GhostsInput(game, fuzzyMemory);
		input.parseInput();
		fuzzyMemory.getInput(input);

		HashMap<String, Double> fvars = input.getFuzzyValues();
		fvars.putAll(fuzzyMemory.getFuzzyValues());

		EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

		for (GHOST g : GHOST.values()) {
			moves.put(g, fuzzyEngine.run(fvars, game));
		}

		return moves;
	}

}
