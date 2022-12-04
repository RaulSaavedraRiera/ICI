package es.ucm.fdi.ici.c2223.practica4.grupo08.ghosts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class MsPacmanPredictor {

	private ArrayList<Integer> msPacmanPosiblePos;
	private ArrayList<MOVE> msPacmanPosibleDirs;

	Game game;
	GhostsFuzzyMemory ghostMem;

	public MsPacmanPredictor(Game game, GhostsFuzzyMemory mem) {
		// TODO Auto-generated constructor stub
		this.game = game;
		this.ghostMem = mem;

		msPacmanPosiblePos = new ArrayList<Integer>();
		msPacmanPosibleDirs = new ArrayList<MOVE>();
	}

	//calcula las posibles posiciones de pacman
	public void calculate() {
		if (msPacmanPosiblePos.size() == 0) {
			int pacmanLastPos = ghostMem.getPacmanLastPosition();
			for (int pos : game.getNeighbouringNodes(pacmanLastPos, ghostMem.getPacmanLastDirection())) 
			{
				msPacmanPosiblePos.add(pos);
				msPacmanPosibleDirs.add(game.getMoveToMakeToReachDirectNeighbour(pacmanLastPos, pos));
				
				GameView.addPoints(game, Color.GREEN, pos);
			}
		}

		else {
			ArrayList<Integer> posiblePos = (ArrayList<Integer>) msPacmanPosiblePos.clone();
			ArrayList<MOVE> posibleDirs = (ArrayList<MOVE>) msPacmanPosibleDirs.clone();
			
			msPacmanPosiblePos.clear();
			msPacmanPosibleDirs.clear();
	
			int cont = 0;
			for (int p : posiblePos) 
			{
				for (int pos : game.getNeighbouringNodes(p, posibleDirs.get(cont))) 
				{
					msPacmanPosiblePos.add(pos);
					msPacmanPosibleDirs.add(game.getMoveToMakeToReachDirectNeighbour(p, pos));
					
					GameView.addPoints(game, Color.GREEN, pos);
				}
				
				cont++;
			}
		}
	}
	
	//resetea las posiciones de pacman
	public void reset() 
	{
		msPacmanPosiblePos = new ArrayList<Integer>();
		msPacmanPosibleDirs = new ArrayList<MOVE>();
	}

	public List<Integer> getMsPacmanPossiblePos() 
	{
		return msPacmanPosiblePos;
	}
}
