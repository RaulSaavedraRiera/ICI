package es.ucm.fdi.ici.c2223.practica4.grupo08;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsFuzzyMemory;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.MaxActionSelector;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.actions.RunAwayAction;
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

	public Ghosts() {
		super();
		setName("Los Bellacos de Atocha");

		setTeam("Team 08");

		Action[] actions = 
			{ 
				new ChaseAction(GHOST.BLINKY, fuzzyMemory), new ChaseAction(GHOST.PINKY, fuzzyMemory), new ChaseAction(GHOST.INKY, fuzzyMemory), new ChaseAction(GHOST.SUE, fuzzyMemory),
				new RunAwayAction(GHOST.BLINKY, fuzzyMemory), new RunAwayAction(GHOST.PINKY, fuzzyMemory), new RunAwayAction(GHOST.INKY, fuzzyMemory), new RunAwayAction(GHOST.SUE, fuzzyMemory)
			};

		ActionSelector actionSelector = new MaxActionSelector(actions);

		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("Ghost", "FuzzyGhosts");
		
		fuzzyEngines = new FuzzyEngine[4];
		
		fuzzyEngines[GHOST.BLINKY.ordinal()] = new FuzzyEngine("Blinky", RULES_PATH + "BLINKY.fcl", "FuzzyGhosts", actionSelector);
		fuzzyEngines[GHOST.BLINKY.ordinal()].addObserver(observer);

		fuzzyEngines[GHOST.PINKY.ordinal()] = new FuzzyEngine("Pinky", RULES_PATH + "PINKY.fcl", "FuzzyGhosts", actionSelector);
		fuzzyEngines[GHOST.PINKY.ordinal()].addObserver(observer);

		fuzzyEngines[GHOST.INKY.ordinal()] = new FuzzyEngine("Inky", RULES_PATH + "INKY.fcl", "FuzzyGhosts", actionSelector);
		fuzzyEngines[GHOST.INKY.ordinal()].addObserver(observer);

		fuzzyEngines[GHOST.SUE.ordinal()] = new FuzzyEngine("Sue", RULES_PATH + "SUE.fcl", "FuzzyGhosts", actionSelector);
		fuzzyEngines[GHOST.SUE.ordinal()].addObserver(observer);
		
		fuzzyMemory = new GhostsFuzzyMemory();
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		GhostsInput input = new GhostsInput(game, fuzzyMemory);
		input.parseInput();
		fuzzyMemory.getInput(input);

		EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

		for (GHOST g : GHOST.values())
		{
			HashMap<String, Double> fvars = input.getFuzzyValues();
			fvars.putAll(fuzzyMemory.getFuzzyValues());
			fvars.putAll(input.getFuzzyValues(g));

			moves.put(g, fuzzyEngines[g.ordinal()].run(fvars, game));
		}
		
		return moves;
	}

}
