package es.ucm.fdi.ici.c2223.practica4.grupo08;

import es.ucm.fdi.ici.c2223.practica1.grupo12.GhostsRandom;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;
import pacman.game.internal.POType;

public class ExecutorTest {
	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
				.setGhostPO(false)
				.setPacmanPO(false)
				.setPacmanPOvisual(true) // visualizaci�n
				.setGhostsPOvisual(false) // visualizaci�n
				.setPOType(POType.LOS)
				.setSightLimit(200)
				.setVisual(true)
				.setScaleFactor(2.0)
				.build();
		PacmanController pacMan = new MsPacMan();
		GhostController ghosts = new GhostsRandom();

		System.out.println(executor.runGame(pacMan, ghosts, 40));
	}
}
