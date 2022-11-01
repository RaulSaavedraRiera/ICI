package es.ucm.fdi.ici.c2223.practica2.grupo08;

import java.awt.Dimension;
import java.util.EnumMap;

import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.CheckAvailableDirectionAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.GoToDirectionAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.GoToNearestPPAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.GoToObjectiveAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.RunAwayToGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.SearchObjectiveCloseToPacmanAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.CurrentDirectionFreeTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.DirectionFreeTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostArriveAfterPacmanToPP;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostEdibleAndPacmanWillEat;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostEdibleAndPacmanWontReach;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostEdiblePacmanWillReachAllEdible;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostEdiblePacmanWillReachNoEdibleGhost;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostHasArrivedOrDoesNotHaveObjective;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostHasObjectiveTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostIsNotPursuerAndExitsRangeTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostIsPursuerOrEntersRangeTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsArriveBeforePacManToPPill;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsEdibleTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsInIntersectionTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsNotEdibleAndPacManFarPPill;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsNotEdibleTransition;
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

	private GhostData ghostData;
	
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

	EnumMap<GHOST, FSM> fsms;

	public Ghosts() {
		super();

		setName("Ghost 12");

		setTeam("Team 12");

		ghostData = new GhostData();

		fsms = new EnumMap<GHOST, FSM>(GHOST.class);

		for (GHOST ghost : GHOST.values()) {

			FSM fsm = new FSM(ghost.name());
			fsm.addObserver(new ConsoleFSMObserver(ghost.name()));
			GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
			fsm.addObserver(graphObserver);

			SimpleState chase = new SimpleState(new ChaseAction(ghost, ghostData));
			SimpleState runAway = new SimpleState(new RunAwayAction(ghost, ghostData));
			SimpleState goToNearestPP = new SimpleState(new GoToNearestPPAction(ghost, ghostData));
			SimpleState runToNearestGhost = new SimpleState(new RunAwayToGhostAction(ghost, ghostData));
			SimpleState goToCurrentObjective = new SimpleState(new GoToObjectiveAction(ghost, ghostData));

			SimpleState checkDirections = new SimpleState(new CheckAvailableDirectionAction(ghost, ghostData));
			SimpleState goToLeftPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.LEFT, ghostData));
			SimpleState goToRightPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.RIGHT, ghostData));
			SimpleState goToUpPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.UP, ghostData));
			SimpleState goToDownPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.DOWN, ghostData));

			SimpleState selectRandomZoneNearPacman = new SimpleState(new SearchObjectiveCloseToPacmanAction(ghost, ghostData));
			SimpleState goToObjective = new SimpleState(new GoToObjectiveAction(ghost, ghostData));

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
			GhostsNotEdibleTransition notEdible = new GhostsNotEdibleTransition(ghost);
			PacManNearPPillTransition near = new PacManNearPPillTransition();
			GhostsNotEdibleAndPacManFarPPill toChaseTransition = new GhostsNotEdibleAndPacManFarPPill(ghost);

			CurrentDirectionFreeTransition currentDirTrans = new CurrentDirectionFreeTransition(ghost, ghostData);
			DirectionFreeTransition dirFreeRight = new DirectionFreeTransition(ghost, ghostData, MOVE.RIGHT);
			DirectionFreeTransition dirFreeLeft = new DirectionFreeTransition(ghost, ghostData, MOVE.LEFT);
			DirectionFreeTransition dirFreeUp = new DirectionFreeTransition(ghost, ghostData, MOVE.UP);
			DirectionFreeTransition dirFreeDown = new DirectionFreeTransition(ghost, ghostData, MOVE.DOWN);
			
			GhostsInIntersectionTransition ghostInIntersection = new GhostsInIntersectionTransition(ghost);
			
			GhostHasArrivedOrDoesNotHaveObjective ghostArrivedOrNotObjective = new GhostHasArrivedOrDoesNotHaveObjective(ghost, ghostData);
			GhostHasObjectiveTransition ghostHasObjective = new GhostHasObjectiveTransition(ghost, ghostData);
			GhostIsNotPursuerAndExitsRangeTransition ghostExitsAndNoPursuer = new GhostIsNotPursuerAndExitsRangeTransition(ghost);
			GhostIsPursuerOrEntersRangeTransition ghostEntersOrPursuer = new GhostIsPursuerOrEntersRangeTransition(ghost);
			
			GhostEdibleAndPacmanWillEat ghostEdibleAndPacmanReachs = new GhostEdibleAndPacmanWillEat(ghost);
			GhostEdibleAndPacmanWontReach ghostEdibleAndPacmanWontReach = new GhostEdibleAndPacmanWontReach(ghost);
			GhostEdiblePacmanWillReachAllEdible ghostEdibleAndNoGhostToRun = new GhostEdiblePacmanWillReachAllEdible(ghost);
			GhostEdiblePacmanWillReachNoEdibleGhost ghostEdibleAndGhostToRun = new GhostEdiblePacmanWillReachNoEdibleGhost(ghost);
			
			GhostsArriveBeforePacManToPPill ghostArrivesFirstToPP = new GhostsArriveBeforePacManToPPill(ghost);
			GhostArriveAfterPacmanToPP ghostArrivesLastToPP = new GhostArriveAfterPacmanToPP(ghost);
					
			//IMPORTANTE: mantener el orden de las transiciones de checkDirections, hace que primero compruebe la direccion actual y luego el resto
			pacmanLejosPPFSM.add(checkDirections, currentDirTrans, goToLeftPath);
			pacmanLejosPPFSM.add(checkDirections, currentDirTrans, goToUpPath);
			pacmanLejosPPFSM.add(checkDirections, currentDirTrans, goToDownPath);
			pacmanLejosPPFSM.add(checkDirections, currentDirTrans, goToRightPath);
			
			pacmanLejosPPFSM.add(checkDirections, dirFreeLeft, goToLeftPath);
			pacmanLejosPPFSM.add(checkDirections, dirFreeUp, goToUpPath);
			pacmanLejosPPFSM.add(checkDirections, dirFreeDown, goToDownPath);
			pacmanLejosPPFSM.add(checkDirections, dirFreeRight, goToRightPath);
			
			pacmanLejosPPFSM.add(goToLeftPath, ghostInIntersection, checkDirections);
			pacmanLejosPPFSM.add(goToRightPath, ghostInIntersection, checkDirections);
			pacmanLejosPPFSM.add(goToUpPath, ghostInIntersection, checkDirections);
			pacmanLejosPPFSM.add(goToDownPath, ghostInIntersection, checkDirections);
			pacmanLejosPPFSM.ready(checkDirections);
			
			pacmanCercaPPFSM.add(runAway, ghostArrivesFirstToPP, goToNearestPP);
			pacmanCercaPPFSM.add(goToNearestPP, ghostArrivesLastToPP, runAway);
			pacmanCercaPPFSM.ready(goToNearestPP);
			
			interceptoresFSM.add(selectRandomZoneNearPacman, ghostHasObjective, goToCurrentObjective);
			interceptoresFSM.add(goToCurrentObjective, ghostArrivedOrNotObjective, selectRandomZoneNearPacman);
			interceptoresFSM.ready(selectRandomZoneNearPacman);
			
			perseguidoresFSM.add(pacmanCercaPP, near, pacmanLejosPP);
			perseguidoresFSM.add(pacmanLejosPP, toChaseTransition, pacmanCercaPP);
			perseguidoresFSM.ready(pacmanLejosPP);
			
			huirDePacmanFSM.add(runToNearestGhost, ghostEdibleAndNoGhostToRun, goToNearestPP);
			huirDePacmanFSM.add(goToNearestPP, ghostEdibleAndGhostToRun, runToNearestGhost);
			huirDePacmanFSM.ready(goToNearestPP);
			
			noComestiblesFSM.add(perseguidores, ghostExitsAndNoPursuer, interceptores);
			noComestiblesFSM.add(interceptores, ghostEntersOrPursuer, perseguidores);
			noComestiblesFSM.ready(perseguidores);
			
			comestiblesFSM.add(chase, ghostEdibleAndPacmanReachs, huirDePacman);
			comestiblesFSM.add(huirDePacman, ghostEdibleAndPacmanWontReach, chase);
			comestiblesFSM.ready(huirDePacman);
			
			fsm.add(comestibles, notEdible, noComestibles);
			fsm.add(noComestibles, edible, comestibles);
			fsm.ready(noComestibles);

			graphObserver.showInFrame(new Dimension(800, 600));

			fsms.put(ghost, fsm);
		}
	}

	public void preCompute(String opponent) {
    	for(FSM fsm: fsms.values())
    		fsm.reset();
    	
		ghostData = new GhostData();
    }
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {

		EnumMap<GHOST, MOVE> result = new EnumMap<GHOST, MOVE>(GHOST.class);

		GhostsInput in = new GhostsInput(game, ghostData);

		for (GHOST ghost : GHOST.values()) {
			FSM fsm = fsms.get(ghost);
			MOVE move = fsm.run(in);
			result.put(ghost, move);
		}

		return result;
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

		// recorre la ruta de mspacman hasta la PP mas cercana e intenta ver si llega a
		// algun punto antes que ella
		for (int node : route) {
			int ghostDist = game.getShortestPathDistance(ghostNode, node, ghostLastMoveMade);
			int pacmanDist = game.getShortestPathDistance(pacmanNode, node, pacmanLastMoveMade);

			if (ghostDist < pacmanDist) {
				destNode = pacmanNode;
				break;
			}
		}

		move = game.getNextMoveTowardsTarget(ghostNode, destNode, ghostLastMoveMade, DM.PATH);

		return move;
	}
}
