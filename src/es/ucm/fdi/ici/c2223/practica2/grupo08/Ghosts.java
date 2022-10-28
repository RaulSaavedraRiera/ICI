package es.ucm.fdi.ici.c2223.practica2.grupo08;

import java.awt.Dimension;
import java.util.EnumMap;
import java.util.Random;

import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.GoToDirectionAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.GoToNearestPPAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.GoToObjectiveAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.RunAwayToGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsEdibleTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsNotEdibleAndPacManFarPPill;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.PacManNearPPillTransition;
import es.ucm.fdi.ici.fsm.CompoundState;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.fsm.observers.ConsoleFSMObserver;
import es.ucm.fdi.ici.fsm.observers.GraphFSMObserver;
import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

	EnumMap<GHOST,FSM> fsms;
	
	public Ghosts() {
		super();

		setName("Ghost 12");

		setTeam("Team 12");
		
		fsms = new EnumMap<GHOST,FSM>(GHOST.class);
		
		for(GHOST ghost: GHOST.values()) {
			
			FSM fsm = new FSM(ghost.name());
			fsm.addObserver(new ConsoleFSMObserver(ghost.name()));
			GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
			fsm.addObserver(graphObserver);

			
			SimpleState chase = new SimpleState(new ChaseAction(ghost));
			SimpleState runAway = new SimpleState(new RunAwayAction(ghost));
			SimpleState goToNearestPP = new SimpleState(new GoToNearestPPAction(ghost));
			SimpleState runToNearestGhost = new SimpleState(new RunAwayToGhostAction(ghost));
			SimpleState goToCurrentObjective = new SimpleState(new GoToObjectiveAction(ghost));
			
			SimpleState goToLeftPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.LEFT));
			SimpleState goToRightPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.RIGHT));
			SimpleState goToUpPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.UP));
			SimpleState goToDownPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.DOWN));
			
			FSM perseguidoresFSM = new FSM("FSM_Perseguidores");
			CompoundState perseguidores = new CompoundState("Estado_Perseguidores", perseguidoresFSM);
			FSM interceptoresFSM = new FSM("FSM_Interceptores");
			CompoundState interceptores = new CompoundState("Estado_Interceptores", interceptoresFSM);
			
			FSM pacmanCercaPPFSM = new FSM("FSM_PacmanCercaPP");
			CompoundState pacmanCercaPP = new CompoundState("Estado_PacmanCercaPPFSM", pacmanCercaPPFSM);
			FSM pacmanLejosPPFSM = new FSM("FSM_PacmanLejosPP");
			CompoundState pacmanLejosPP = new CompoundState("Estado_PacmanLejosPP", pacmanLejosPPFSM);
			
			FSM comestiblesFSM = new FSM("FSM_Comestibles");
			CompoundState comestibles = new CompoundState("Estado_Comestibles", comestiblesFSM);
			FSM noComestiblesFSM = new FSM("FSM_NoComestibles");
			CompoundState noComestibles = new CompoundState("Estado_NoComestibles", noComestiblesFSM);

			FSM huirDePacmanFSM = new FSM("FSM_HuirDePacman");
			CompoundState huirDePacman = new CompoundState("Estado_HuirDePacman", huirDePacmanFSM);
			
			GhostsEdibleTransition edible = new GhostsEdibleTransition(ghost);
			PacManNearPPillTransition near = new PacManNearPPillTransition();
			GhostsNotEdibleAndPacManFarPPill toChaseTransition = new GhostsNotEdibleAndPacManFarPPill(ghost);
			
			perseguidoresFSM.add(pacmanCercaPP, near, pacmanLejosPP);
			perseguidoresFSM.add(pacmanLejosPP, toChaseTransition, pacmanCercaPP);
			fsm.add(comestibles, edible, noComestibles);
//			fsm.add(chase, near, runAway);
//			fsm.add(runAway, toChaseTransition, chase);
//			
//			fsm.ready(chase);
			
			graphObserver.showInFrame(new Dimension(800,600));
			
			fsms.put(ghost, fsm);
		}
	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		
		
	}

	// Devuelve la power pill mas cercana al nodo dado, teniendo en cuenta el ultimo
	// movimiento realizado
	private int getClosestPowerPill(Game game, int nodeFrom, MOVE lastMove) {

		int[] pPills = game.getActivePowerPillsIndices();

		int minDistPillNode = 0;
		int minDist = Integer.MAX_VALUE;
		for (int pillNode : pPills) {
			int dist = game.getShortestPathDistance(nodeFrom, pillNode, lastMove);

			if (dist < minDist) {
				minDist = dist;
				minDistPillNode = pillNode;
			}
		}
		
		
		return minDistPillNode;
	}

	private MOVE getMoveToPacmanDirection(Game game, int ghostNode, int pacmanNode, MOVE ghostLastMoveMade,
			MOVE pacmanLastMoveMade, GHOST ghostype) {
		
		MOVE move = ghostLastMoveMade;
		int destNode = pacmanNode;

		int closestPP = getClosestPowerPill(game, pacmanNode, pacmanLastMoveMade);
		
		int[] route = game.getShortestPath(pacmanNode, closestPP, pacmanLastMoveMade);
		
		//recorre la ruta de mspacman hasta la PP mas cercana e intenta ver si llega a algun punto antes que ella
		for (int node : route) 
		{
			int ghostDist = game.getShortestPathDistance(ghostNode, node, ghostLastMoveMade);
			int pacmanDist = game.getShortestPathDistance(pacmanNode, node, pacmanLastMoveMade);
			
			if (ghostDist < pacmanDist) 
			{
				destNode = pacmanNode;
				break;
			}
		}

		move = game.getNextMoveTowardsTarget(ghostNode, destNode, ghostLastMoveMade, DM.PATH);

		return move;
	}
}
