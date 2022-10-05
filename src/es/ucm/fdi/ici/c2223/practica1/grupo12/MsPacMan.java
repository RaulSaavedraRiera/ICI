package es.ucm.fdi.ici.c2223.practica1.grupo12;

import java.awt.Color;
import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

public class MsPacMan extends PacmanController {

	
	int runLimit = 70;	
	int eatTimeSecure = 10;
	
	
	int currentDistance, currentTime;
	
	int pacmanPos;
	
	GHOST[] ghosts = new GHOST[3];
	private Random rnd = new Random();
	
	GHOST target = null;
	
	private MOVE[] allMoves = MOVE.values();
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		pacmanPos = game.getPacmanCurrentNodeIndex();
		lastMove = game.getPacmanLastMoveMade();

		//obtenemos los fantasmas que pueden cazarte
		GetChasingGhosts(game, runLimit);
		//y el mas cercano para comer
		getNearestTargetGhost(game);
		
		//si no hay fantasmas peligrosos por la zona
		if(ghosts[0] == null)	
		{
			//si tampoco hay fantasma que podamos comer a pildora
			if(target == null)
				return toNearestPill(game, true);
			//si si vamos a por el
			else 
			{
				DrawPath(game, Color.RED, pacmanPos, game.getGhostCurrentNodeIndex(target), lastMove);
				return toNearestGhost(game);
			}
		}
			
		//si solo hay 1 fantasma peligroso
		else if(ghosts[1] == null)
		{
			//si aun será comible durante el tiempo de seguridad
			if(game.getGhostEdibleTime(ghosts[0]) > eatTimeSecure)
			{
				//vamos a por el target si hay
				if(target != null)
				{
					DrawPath(game, Color.RED, pacmanPos, game.getGhostCurrentNodeIndex(target), lastMove);
					return toNearestGhost(game);
				}
					
				//si no a por pildoras
				else
					return toNearestPill(game, false);
			}
			//si no
			else
			{
				//si hay una power pill lo suficientemente cerca
				int nearPowerPill = powerPillInRange(game);
				//va a ella
				if(nearPowerPill != -1)	
				{
					DrawPath(game, Color.GREEN, pacmanPos, nearPowerPill, lastMove);
					return game.getNextMoveTowardsTarget(pacmanPos, nearPowerPill, lastMove, DM.PATH);
				}
					
				//si no huye
				else
				{
					DrawPath(game, Color.BLUE, pacmanPos, game.getGhostCurrentNodeIndex(ghosts[0]), lastMove);
					return game.getNextMoveAwayFromTarget(pacmanPos,  game.getGhostCurrentNodeIndex(ghosts[0]), lastMove, DM.PATH);
				}
			}
		}
		
		//si hay mas de un fantasma en la zona
		else {
			int nearPowerPill = powerPillInRange(game);
			
			//si podemos llegar a una pildora antes que ambos fantasmas lo intentamos
			if(nearPowerPill != -1)
			{
				DrawPath(game, Color.GREEN, pacmanPos, nearPowerPill, lastMove);
				return game.getNextMoveTowardsTarget(pacmanPos, nearPowerPill, lastMove, DM.PATH);
			}
			
			else
			{
				return awayFromMultipleGhosts(game);
			}
		}
		
		
		
		
	}
	
	public void DrawPath(Game game, Color color, int p, int to, MOVE last)
	{
		GameView.addPoints(game, color, game.getShortestPath(p,
				to, last));
	}
	
	//metodos para legibilidad del codigo
	public MOVE toNearestGhost(Game game)
	{
		try
		{
		GameView.addPoints(game, Color.RED, game.getShortestPath(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(target), game.getPacmanLastMoveMade()));
		} catch(Exception e) {};
		
		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(target), game.getPacmanLastMoveMade(), DM.MANHATTAN);
	}
	

	public MOVE toNearestPill(Game game, boolean secure)
	{
		try
		{
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
					getNearestPill(game, game.getPacmanCurrentNodeIndex(),  game.getActivePillsIndices(), secure),
					DM.PATH);
		}
		catch(Exception e)
		{
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
					getNearestPill(game, game.getPacmanCurrentNodeIndex(),  game.getActivePillsIndices(), secure), game.getPacmanLastMoveMade(),
					DM.PATH);		
		}
	}
	
	
	public MOVE awayFromMultipleGhosts(Game game)
	{
		
		int pacman[] = null; 
		int ghostPP[] = null;
		int ghostPacman[] = null;
		
		
		int p;
		
		do {
			p = game.getActivePillsIndices()[rnd.nextInt(game.getActivePillsIndices().length)];
			pacman = game.getShortestPath(game.getPacmanCurrentNodeIndex(), p ,lastMove);
			
			for(GHOST g : ghosts)
			{	
				if(g != null)
				{
					try {
						ghostPP = game.getShortestPath(game.getGhostCurrentNodeIndex(g), p,	game.getGhostLastMoveMade(g));
					} catch (Exception e) {
						ghostPP = game.getShortestPath(game.getGhostCurrentNodeIndex(g), p);
					}

					try {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos, game.getGhostLastMoveMade(g));

					} catch (Exception e) {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g),pacmanPos);
					}
					
					if (collisionRoute(pacman, ghostPacman) || collisionRoute(pacman, ghostPP))
					{						
						p = -1;
						break;
					}
				}
			}
			
		} while(p == -1);
		
		
		if(p != -1)
			{
			DrawPath(game, Color.PINK, pacmanPos, p, lastMove);
			return game.getNextMoveTowardsTarget(pacmanPos, p, lastMove, DM.PATH);
			}
		else
			return allMoves[rnd.nextInt(allMoves.length)];
		
		
		
	}
	
	
	//metodos para la obtención de información del mapa
	
	public void GetChasingGhosts(Game game, int limit)
	{
		ghosts[0] = ghosts[1] = ghosts[2] = null;
		
		for(int i = 0; i < 3; i++)
			getNearestChasingGhost(game, limit, i);
	}
	
	public void getNearestChasingGhost(Game game, int limit, int index)
	{
		GHOST nearest = null;
		int currentDistance;
		
		//por cada uno de los fantasmas
		for (Constants.GHOST ghostType : Constants.GHOST.values()) {
			//si no es comible y no se ha seleccionado ya
			if(ghostType != ghosts[0] && ghostType != ghosts[1] && game.getGhostEdibleTime(ghostType) <= 0)
			{
				//comprobamos si esta mas cerca que el limite actual
				currentDistance = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghostType));
				if (currentDistance < limit && currentDistance != 0) {
					//en caso correcto lo guardamos
					nearest = ghostType;
					limit = currentDistance;
				}
			}
			
		}
		
		ghosts[index] = nearest;
			
		
	}
	
	
	
	public void getNearestTargetGhost(Game game) //sin limite de seguridad , int eatLimit
	{
		
			GHOST nearest = null;
			int currentDistance, currentTime, bestDistance = -1;
			
			
			//por cada uno de los fantasmas
			for (Constants.GHOST ghostType : Constants.GHOST.values()) {
				currentTime = game.getGhostEdibleTime(ghostType);
				//si es comible
				if (currentTime > 0 ) {
					// vemos la distancia a la que esta de pacman
					currentDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
							game.getGhostCurrentNodeIndex(ghostType), game.getPacmanLastMoveMade());
					
					//si es posible comerlo y menor que el anterior //+ currentDistance/3 < currentTimr
					if ((currentDistance != 0 && currentDistance < currentTime) && (currentDistance < bestDistance || bestDistance == -1)) {
						// en caso correcto lo guardamos
						nearest = ghostType;
						bestDistance = currentDistance;
					}
				}
			}
			target = nearest;		
		
	}
	
	
	public int getNearestPill(Game game, int pacManPos, int[] pills, boolean secure)
	{
		if(pills.length == 0)
			return -1;
		
		//variables para controlar las posiciones
		int distance = 9999, to = -1, currentDistance;
		
		for(int pill : pills) //comprobamos para las pill cual es la mas cercana
		{
			currentDistance = 
					game.getShortestPathDistance(pacManPos, pill, game.getPacmanLastMoveMade());
			
			if(currentDistance < distance && (!secure || pillSecure(game, pill)))
			{
				to = pill;
				distance = currentDistance;
			}
		}
		
		//devolvemos la pill mas cercana		
		
		if(secure && to == -1)
			getNearestPill(game, pacManPos, pills, false);
		
			return to;
	}
	
	
	
	
	//metodos para comprobacion de rutas validas
	
	boolean pillSecure(Game game, int pill)
	{
		boolean nonGhosts = true;
		
		for (Constants.GHOST ghostType : Constants.GHOST.values()) {
			
			try{
			 nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), pill, game.getGhostLastMoveMade(ghostType)) > runLimit;
			} 
			catch(Exception e)
			{
				nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), pill) > runLimit;
			}
			
			if(!nonGhosts) break;
		}

		return nonGhosts;
	}
	
	
	int powerPillInRange(Game game)
	{
		int nearestPill = -1, currentDistance = -1;
		
		//para cada pildora probamos con todos los fantasmas
		for(int p : game.getActivePowerPillsIndices())
		{
			//obtenemos la ruta de pacman a la pildora 
			int pacman[] = game.getShortestPath(game.getPacmanCurrentNodeIndex(), p ,lastMove);
			int ghostPP[] = null;
			int ghostPacman[] = null;
			
			boolean available = true;
			for(GHOST g : ghosts)
			{	
				if(g != null)
				{
					try {
						ghostPP = game.getShortestPath(game.getGhostCurrentNodeIndex(g), p,	game.getGhostLastMoveMade(g));
					} catch (Exception e) {
						ghostPP = game.getShortestPath(game.getGhostCurrentNodeIndex(g), p);
					}

					try {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g), pacmanPos, game.getGhostLastMoveMade(g));

					} catch (Exception e) {
						ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(g),pacmanPos);
					}
					
					if (collisionRoute(pacman, ghostPacman) || collisionRoute(pacman, ghostPP))
					{
						available = false;
						break;
					}
				}
			}
			
			if(available)
			{
				int distanceToPill = game.getShortestPathDistance(pacmanPos, p, lastMove);
				
				if(nearestPill == -1 || currentDistance > distanceToPill)
				{
					nearestPill = p; currentDistance = distanceToPill;
				}
			}
		}
		
		return nearestPill;
	}
	
	boolean collisionRoute(int[] route, int[] otherRoute)
	{
		
		//para cada punto en la ruta
		for (int i = 0; i < route.length; i++)
			//comprobamos con los puntos de la otra ruta
			for(int j = 0; j < otherRoute.length; j++)	
				
				if(route[i] == otherRoute[j] && j <= i)
					return true;
			
		return false;
	}
	
	
}
