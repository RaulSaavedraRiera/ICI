package es.ucm.fdi.ici.c2223.practica3.grupo08;

import es.ucm.fdi.ici.c2223.practica1.grupo12.Ghosts1;
import pacman.Executor;
import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;

public class ExecutorTest {
	public static void main(String[] args) {
		Executor executor = new Executor.Builder()
				.setTickLimit(4000)
				.setGhostPO(false)
				.setPacmanPO(false)
				.setVisual(true)
				.setScaleFactor(2.0)
				.build();
		// PacmanController pacMan = new HumanController(new KeyBoardInput());
		PacmanController pacMan = new MsPacMan();
		GhostController ghosts = new Ghosts1();

		System.out.println(executor.runGame(pacMan, ghosts, 20));
	}
}
