package es.ucm.fdi.ici.c2223.practica4.grupo08;

import java.io.File;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts.MaxActionSelector;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManInput;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions.ChaseGhostPA;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions.ChasePillPA;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions.ChasePowerPillPA;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions.DefaultRandomPA;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions.RunAwayCornerPA;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions.RunAwayLairPA;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions.RunAwayNearestChasingGhostAnyDirPA;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	private static final String RULES_PATH = "bin" + File.separator + "es" + File.separator + "ucm" + File.separator
			+ "fdi" + File.separator + "ici" + File.separator + "c2223" + File.separator + "practica4" + File.separator
			+ "grupo08" + File.separator + "pacman" + File.separator;
	
	FuzzyEngine fuzzyEngine;
	MsPacManFuzzyMemory fuzzyMemory;
	
	public MsPacMan()
	{
		setName("MsPacManIfUrNasty");
		setTeam("Team 08");
		
		fuzzyMemory = new MsPacManFuzzyMemory();

		Action[] actions = 
			{ 
					new RunAwayCornerPA(fuzzyMemory),
					new RunAwayLairPA(fuzzyMemory),
					new ChasePillPA(fuzzyMemory),
					new ChasePowerPillPA(fuzzyMemory),
					new ChaseGhostPA(fuzzyMemory),
					new DefaultRandomPA(fuzzyMemory),
					new RunAwayNearestChasingGhostAnyDirPA(fuzzyMemory)
				};
		
		//ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","MsPacManRules");
		fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"mspacman.fcl","FuzzyMsPacMan", new MaxActionSelector(actions));
		//fuzzyEngine.addObserver(observer);
		
		
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		//aqui va a haber que diferenciar si se llama nuevo mapa o no, de momento un booleano
		
		
		MsPacManInput input = new MsPacManInput(game,fuzzyMemory);
		
		input.parseInput();
		fuzzyMemory.getInput(input);
		
		HashMap<String, Double> fvars = input.getFuzzyValues();
		fvars.putAll(fuzzyMemory.getFuzzyValues());
		
		return fuzzyEngine.run(fvars,game);
	}

}
