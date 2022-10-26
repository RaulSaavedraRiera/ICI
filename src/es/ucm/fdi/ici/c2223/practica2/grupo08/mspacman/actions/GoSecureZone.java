package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions;

import java.util.Random;

import es.ucm.fdi.ici.Action;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GoSecureZone implements Action {
	public GoSecureZone() {
		// TODO Auto-generated constructor stub
	}

    
	final int LIMITEDIBLETIME = 12;
	final int MAXPILLCHECKED = 8000;
	final int PILLLIMIT = 60;
	
	private Random rnd = new Random();
	
	//se tendria ue pedir el int obtenido por las condiciones de un sitio que si sea seguro
	@Override
	public MOVE execute(Game game) {
		return game.getApproximateNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), awayFromMultipleGhosts(game), game.getPacmanLastMoveMade(), DM.PATH);
	}

	@Override
	public String getActionId() {
		return "Go To Secure Zone";
	}
	
	public int awayFromMultipleGhosts(Game game) {

		int[] pacman = null;

		int[] ghostPacman = null;
		
		int pacmanPos = game.getPacmanCurrentNodeIndex();
		MOVE lastMove = game.getPacmanLastMoveMade();

		boolean valid;
		int i = 0;
		int p = -1;
		
		// para cada pildora
		do {
			p = game.getPillIndices()[rnd.nextInt(game.getPillIndices().length)];
			i++;
			// obtenemos la ruta
			pacman = game.getShortestPath(pacmanPos, p, lastMove);
			// para cada una suponemos que es valida
			valid = true;
			for (Constants.GHOST g : Constants.GHOST.values()) {
				// si el fantasma no esta encerrado y supone una amenaza comprobamos ruta con el
				if (game.getGhostLairTime(g) == 0 && game.getGhostEdibleTime(g) < LIMITEDIBLETIME) {
					try {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos,
								game.getGhostLastMoveMade(g));

					} catch (Exception e) {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos);
					}

					// en caso de que haya colision la marcamos como no valida y salimos
					if (!pillSecure(game, p) || collisionRoute(pacman, ghostPacman)) // la pildora esta lejos de
																						// fantasmas es segura
					{
						valid = false;
						break;
					}
				}

			}
		} while (!valid && i <= MAXPILLCHECKED);

		return p;

	}
	
boolean pillSecure(Game game, int pill) {
		
		boolean nonGhosts = true;

		for (Constants.GHOST g : Constants.GHOST.values()) {

			if (game.getGhostLairTime(g) > 0 && game.getGhostEdibleTime(g) < LIMITEDIBLETIME) {
				try {
					nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill,
							game.getGhostLastMoveMade(g)) > PILLLIMIT;
				} catch (Exception e) {
					nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(g), pill) > PILLLIMIT;
				}
				if (!nonGhosts)
					return false;
			}

		}

		return true;
	}

	boolean collisionRoute(int[] route, int[] otherRoute) {

		// para cada punto en la ruta
		for (int i = 0; i < route.length; i++)
			// comprobamos con los puntos de la otra ruta
			for (int j = 0; j < otherRoute.length; j++)

				if (route[i] == otherRoute[j] && j <= i)
					return true;

		return false;
	}
}
