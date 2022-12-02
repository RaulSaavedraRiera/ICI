package es.ucm.fdi.ici.c2223.practica4.grupo08;

import java.io.File;
import java.util.HashMap;

import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManInput;
import es.ucm.fdi.ici.fuzzy.FuzzyEngine;
import es.ucm.fdi.ici.fuzzy.observers.ConsoleFuzzyEngineObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController {

	private static final String RULES_PATH = "bin"+File.separator+"es"+File.separator+"ucm"+File.separator+"fdi"+File.separator+"ici"+File.separator+"practica4"+File.separator+"demofuzzy"+File.separator+"mspacman"+File.separator;
	FuzzyEngine fuzzyEngine;
	MsPacManFuzzyMemory fuzzyMemory;
	
	private boolean first = true;
	
	public MsPacMan()
	{
		setName("MsPacManIfUrNasty");
		setTeam("Team 08");

		 
		ConsoleFuzzyEngineObserver observer = new ConsoleFuzzyEngineObserver("MsPacMan","MsPacManRules");
		//fuzzyEngine = new FuzzyEngine("MsPacMan",RULES_PATH+"mspacman.fcl","FuzzyMsPacMan",actionSelector);
		fuzzyEngine.addObserver(observer);
		
		fuzzyMemory = new MsPacManFuzzyMemory();
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		//aqui va a haber que diferenciar si se llama nuevo mapa o no, de momento un booleano
		
		
		MsPacManInput input = new MsPacManInput(game,fuzzyMemory, first);; 
		if(first)
			first = false;
		
		input.parseInput();
		fuzzyMemory.getInput(input);
		
		HashMap<String, Double> fvars = input.getFuzzyValues();
		fvars.putAll(fuzzyMemory.getFuzzyValues());
		
		return fuzzyEngine.run(fvars,game);
	}

}
