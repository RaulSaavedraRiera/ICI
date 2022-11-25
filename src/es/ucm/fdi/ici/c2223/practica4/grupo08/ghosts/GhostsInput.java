package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.ici.fuzzy.FuzzyInput;
import pacman.game.Game;
import pacman.game.Constants.GHOST;

public class GhostsInput extends FuzzyInput{

	public GhostsInput(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isPacmanVisible()
	{
		return game.getPacmanCurrentNodeIndex() != -1;
	}

	@Override
	public Map<String, Double> getFuzzyValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseInput() {
		// TODO Auto-generated method stub
		
	}

}
