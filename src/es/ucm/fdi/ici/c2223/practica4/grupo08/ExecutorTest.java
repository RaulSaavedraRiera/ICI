package es.ucm.fdi.ici.c2223.practica4.grupo08;

import es.ucm.fdi.ici.c2223.practica1.grupo12.MsPacManRunAway;
import es.ucm.fdi.ici.c2223.practica1.grupo12.PacManRandom;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;
import pacman.game.internal.POType;

public class ExecutorTest {
	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
				.setGhostPO(true)
				.setPacmanPO(true)
				.setPacmanPOvisual(true) // visualizaci�n
				.setGhostsPOvisual(true) // visualizaci�n
				.setPOType(POType.LOS)
				.setSightLimit(200)
				.setVisual(true)
				.setScaleFactor(2.0)
				.build();
		PacmanController pacMan = new PacManRandom();
		GhostController ghosts = new Ghosts();

		System.out.println(executor.runGame(pacMan, ghosts, 20));
	}
}
