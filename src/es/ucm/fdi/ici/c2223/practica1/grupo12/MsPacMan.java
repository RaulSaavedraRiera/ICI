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

	//peta cuano 3 fantasmas no hay ruta segura de huida, dejamos que devuelva aleatorio o como ademas de esto da un fallo mas que no se cual es
	//no detecta 2 fantasmas a veces
	//no ataca bien
	
	int normalLimit = 60;
	int eatTimeSecure = 10;
	
	
	int currentDistance, currentTime;
	
	GHOST[] ghosts = new GHOST[3];
	private Random rnd = new Random();
	
	GHOST target = null;
	
	@Override
	public MOVE getMove(Game game, long timeDue) {

		// usamos limit ya que si esta mas alla de este no llegaremos por tiempo * creo
		GetChasingGhosts(game, normalLimit);		
		
		//caso especial dond ehay mas de 3 fantasmas en la zona
		if(ghosts[2] != null)
		{
			if(game.getActivePowerPillsIndices().length > 0 && pillInRange(game)) { //si la powerPill esta a alcance la cogemos
				System.out.println("comible - 3 fantasma - powerPill");
				return toNearestPowerPill(game, false);
			}
			
			else {
				System.out.println("comible - 3 fantasma - huir");
				return awayFromThreeGhosts(game);
			}
		
		}			
		

		if (ghosts[0] == null || game.isGhostEdible(ghosts[0])) // si pacman puede comer al fantasma mas cercano o no
																// hay
		{
			// si no hay fantasmas a por pildora
			if (ghosts[0] == null) // en vez de a pill mas cercana a una pill que este en zona sin fantasmas
				return toNearestPill(game, true);
			// hay fantasmas que te pueden comer cerca
			else {
				
				
				// obtiene la distancia hasta el primer fantasma
				//currentDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
						//game.getGhostCurrentNodeIndex(ghosts[0]), game.getPacmanLastMoveMade());

				// if (game.getGhostEdibleTime(ghosts[0]) > currentDistance) {  da tiempo a el 1er fantasma (currentDistance +
																// currentDistance / 2)
				
				currentDistance = game.getShortestPathDistance( game.getGhostCurrentNodeIndex(ghosts[0]),
						game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());
				
				
				if(currentDistance < eatTimeSecure) //distancia del fantasma mas cercano a ti es menor que la limite
				{					
					
					if(ghosts[1] == null) //si hay un solo fantasma que te puede atacar huye
					{
						System.out.println("comible - 1 fantasma - huir");
						return awayNearestGhost(game);
					}						
					else //si hay dos huye de los dos
					{
						System.out.println("comible - 2 fantasma - huir");
						return awayFromTwoGhosts(game);
					}
						
				}	
				//else if(currentDistance < game.getGhostEdibleTime(ghosts[0]) + eatTimeSecure) //distancia del fantasma mas cercano es menor que la segura
				//{
					
				//}
				else//si es seguro
				{
					getNearestTargetGhost(game); //obtenemos el target
					
					if(target == null) //si no hay objetivo valido 
					{
						return toNearestPill(game, false);
					}
					else
					{
						System.out.println("comible - atacar");
						return toNearestGhost(game);
					}
				}

			}
		} 
		else { //si hay fantasma y pacman no puede comerselo
			
			if(game.getActivePowerPillsIndices().length == 0) //si no hay power pills huye
			{
				System.out.println("no comible - 1 fantasma - noPowerPills - huir");
				return awayNearestGhost(game);
			}
				
			
			else {
				
				if (pillInRange(game)) {	
					System.out.println("no comible - 1 fantasma - powerPill");
					return toNearestPowerPill(game, false);
				}	
				
				else { // si llega fantasma antes					
					if(ghosts[1] == null) //si solo hay 1 se huye de este
					{
						System.out.println("no comible - 1 fantasma - powerPillLejos - Huir");	
						return awayNearestGhost(game);
					}
					else //si no se huye de 2
					{
						System.out.println("no comible - 2 fantasma - powerPillLejos - Huir");	
						return awayFromTwoGhosts(game);
					}					
				}
		
			}
		}
		
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
	
	public MOVE awayNearestGhost(Game game)
	{
		GameView.addPoints(game, Color.BLUE, game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[0]),
				game.getPacmanCurrentNodeIndex()));
		
		return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(ghosts[0]), game.getPacmanLastMoveMade(), DM.PATH);
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
	
	public MOVE toNearestPowerPill(Game game, boolean secure)
	{
		
		GameView.addPoints(game, Color.GREEN, game.getShortestPath(game.getPacmanCurrentNodeIndex(), 
				getNearestPill(game, game.getPacmanCurrentNodeIndex(),  game.getActivePowerPillsIndices(), secure), game.getPacmanLastMoveMade()));
		
		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
				getNearestPill(game, game.getPacmanCurrentNodeIndex(),  game.getActivePowerPillsIndices(), secure), game.getPacmanLastMoveMade(),
				DM.PATH);
	}
	
	public MOVE awayFromTwoGhosts(Game game)
	{
		int pill;
		int pills[] = game.getActivePillsIndices();
		int pacmanPill[];
		
		//obtenemos rutas de los fantasmas
		int g1Pacman[] = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[0]),
			game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghosts[0]));
				
		int g2Pacman[] = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[1]),
			game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghosts[1]));
		do
		{
			do
			{
			 pill = pills[rnd.nextInt(pills.length)];				
			} while(game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghosts[0]), pill, game.getGhostLastMoveMade(ghosts[0])) > normalLimit &&
					game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghosts[1]), pill, game.getGhostLastMoveMade(ghosts[1])) > normalLimit);
			
			pacmanPill = game.getShortestPath(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());
			
		} while(!collisionRoute(pacmanPill, g1Pacman) && !collisionRoute(pacmanPill, g2Pacman));
		//si no pongo limtie cuando pase 40 ms se tira a algun aleatorio o da error?
		
		
		GameView.addPoints(game, Color.CYAN, game.getShortestPath(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade()));
		
		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade(), DM.PATH);
	}
	
	public MOVE awayFromThreeGhosts(Game game)
	{
		int pill;
		int pills[] = game.getActivePillsIndices();
		int pacmanPill[];
		
		//obtenemos rutas de los fantasmas
		int g1Pacman[] = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[0]),
			game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghosts[0]));
				
		int g2Pacman[] = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[1]),
			game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghosts[1]));
		
		int g3Pacman[] = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[2]),
				game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghosts[2]));
		do
		{
			do
			{
			 pill = pills[rnd.nextInt(pills.length)];				
			} while(game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghosts[0]), pill, game.getGhostLastMoveMade(ghosts[0])) > normalLimit &&
					game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghosts[1]), pill, game.getGhostLastMoveMade(ghosts[1])) > normalLimit &&
					game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghosts[2]), pill, game.getGhostLastMoveMade(ghosts[2])) > normalLimit);
			
			pacmanPill = game.getShortestPath(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade());
			
		} while(!collisionRoute(pacmanPill, g1Pacman) && !collisionRoute(pacmanPill, g2Pacman) && !collisionRoute(pacmanPill, g3Pacman));
		//si no pongo limtie cuando pase 40 ms se tira a algun aleatorio o da error?
		
		
		GameView.addPoints(game, Color.GRAY, game.getShortestPath(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade()));
		
		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), pill, game.getPacmanLastMoveMade(), DM.PATH);
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
			//si es del tipo indicado y no es el priemro en caso de buscar el segundo fantasma
			if(ghostType != ghosts[0] && ghostType != ghosts[1])
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
		float dist;
		try {
			dist = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
					game.getGhostCurrentNodeIndex(target));
		}
		catch(Exception e)
		{
			dist = 0;
		}
		 
		//si el target actual ya no es valido
		if(target == null || (game.getGhostEdibleTime(target) < dist || dist == 0))
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
			System.out.println("cambiado target");
			target = nearest;		
		}
	}
	
	
	public int getNearestPill(Game game, int pacManPos, int[] pills, boolean secure)
	{
		
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
			 nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), pill, game.getGhostLastMoveMade(ghostType)) > normalLimit;
			} 
			catch(Exception e)
			{
				nonGhosts = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghostType), pill) > normalLimit;
			}
			
			if(!nonGhosts) break;
		}

		return nonGhosts;
	}
	
	boolean pillInRange(Game game)
	{
		// obtenemos ruta a pildora, ruta del fantasma a pacman y a pildora * fantasma a
				// ti da igual porque puede estar colocado de una manera aunque este mas cerca
				// que pildora huyas de el

				int pacman[] = game.getShortestPath(game.getPacmanCurrentNodeIndex(),
						getNearestPill(game, game.getPacmanCurrentNodeIndex(), game.getActivePowerPillsIndices(), false),
						game.getPacmanLastMoveMade());

				int ghostPP[] = null;
				int ghostPacman[] = null;

				// para caso inciial donde no hay momento antes
				try {
					ghostPP = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[0]),
							getNearestPill(game, game.getPacmanCurrentNodeIndex(), game.getActivePowerPillsIndices(), false),
							game.getGhostLastMoveMade(ghosts[0]));
				} catch (Exception e) {
					ghostPP = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[0]),
							getNearestPill(game, game.getPacmanCurrentNodeIndex(), game.getActivePowerPillsIndices(), false));
				}

				try {
					ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[0]),
							game.getPacmanCurrentNodeIndex(), game.getGhostLastMoveMade(ghosts[0]));

				} catch (Exception e) {
					ghostPacman = game.getShortestPath(game.getGhostCurrentNodeIndex(ghosts[0]),
							game.getPacmanCurrentNodeIndex());
				}
				

				// si esta mas cerca de la pildora que el fantasma, y el fantasma mas lejos de
				// Pacman que Pacman de la pildora. y no se choca con el de camino a ninguna
				// va a powerPill
				if ((pacman.length < ghostPP.length && pacman.length < ghostPacman.length)
						|| (!collisionRoute(pacman, ghostPP) && !collisionRoute(pacman, ghostPacman)))
						return true;
				
				else return false;
	}
	
	boolean collisionRoute(int[] route, int[] otherRoute)
	{
		
		//para cada punto en la ruta
		for (int i = 0; i < route.length; i++)
		{	
			//comprobamos con los puntos de la otra ruta
			for(int j = 0; j < otherRoute.length; j++)
			{
				
				if(i == j && j <= i)
					return true;
			}
			
			
		}
		
		return false;
	}
	
	
}
