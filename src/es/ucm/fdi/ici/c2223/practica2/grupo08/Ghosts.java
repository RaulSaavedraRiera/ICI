package es.ucm.fdi.ici.c2223.practica2.grupo08;

import java.util.ArrayList;
import java.util.EnumMap;

import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.GhostsInput;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.actions.ChaseJunctionsAction;
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
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.GhostsNotEdibleTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.PacManFarPPillTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.ghosts.transitions.PacManNearPPillTransition;
import es.ucm.fdi.ici.fsm.CompoundState;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	private GhostData ghostData;
	ArrayList<GhostData> ghostDataRef;

	JunctionManager juncManager;
	
	EnumMap<GHOST, FSM> fsms;

	public Ghosts() {
		super();

		setName("Los Bellacos de Atochas");

		setTeam("Team 08");

		ghostData = new GhostData();
		juncManager = new JunctionManager();
		ghostDataRef = new ArrayList<GhostData>();
		ghostDataRef.add(ghostData);

		fsms = new EnumMap<GHOST, FSM>(GHOST.class);

		for (GHOST ghost : GHOST.values()) {

			FSM fsm = new FSM(ghost.name());
//			fsm.addObserver(new ConsoleFSMObserver(ghost.name()));
//			GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
//			fsm.addObserver(graphObserver);

			SimpleState chase = new SimpleState(new ChaseAction(ghost, ghostDataRef));
			SimpleState chaseJunction = new SimpleState(new ChaseJunctionsAction(ghost, ghostDataRef, juncManager));
			SimpleState runAway = new SimpleState(new RunAwayAction(ghost, ghostDataRef));
			SimpleState goToNearestPP = new SimpleState(new GoToNearestPPAction(ghost, ghostDataRef));
			SimpleState runToNearestGhost = new SimpleState(new RunAwayToGhostAction(ghost, ghostDataRef));

			SimpleState checkDirections = new SimpleState(new CheckAvailableDirectionAction(ghost, ghostDataRef));
			SimpleState goToLeftPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.LEFT, ghostDataRef));
			SimpleState goToRightPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.RIGHT, ghostDataRef));
			SimpleState goToUpPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.UP, ghostDataRef));
			SimpleState goToDownPath = new SimpleState(new GoToDirectionAction(ghost, MOVE.DOWN, ghostDataRef));

			SimpleState selectRandomZoneNearPacman = new SimpleState(new SearchObjectiveCloseToPacmanAction(ghost, ghostDataRef));
			SimpleState goToObjective = new SimpleState(new GoToObjectiveAction(ghost, ghostDataRef));

			FSM perseguidoresFSM = new FSM("FSM_Perseguidores");
			createFSMObserver(perseguidoresFSM, ghost);
			CompoundState perseguidores = new CompoundState("Estado_Perseguidores", perseguidoresFSM);
			
			FSM interceptoresFSM = new FSM("FSM_Interceptores");
			createFSMObserver(interceptoresFSM, ghost);
			CompoundState interceptores = new CompoundState("Estado_Interceptores", interceptoresFSM);

			FSM pacmanCercaPPFSM = new FSM("FSM_PacmanCercaPP");
			createFSMObserver(pacmanCercaPPFSM, ghost);
			CompoundState pacmanCercaPP = new CompoundState("Estado_PacmanCercaPPFSM", pacmanCercaPPFSM);
			
			FSM pacmanLejosPPFSM = new FSM("FSM_PacmanLejosPP");
			createFSMObserver(pacmanLejosPPFSM, ghost);
			CompoundState pacmanLejosPP = new CompoundState("Estado_PacmanLejosPP", pacmanLejosPPFSM);

			FSM comestiblesFSM = new FSM("FSM_Comestibles");
			createFSMObserver(comestiblesFSM, ghost);
			CompoundState comestibles = new CompoundState("Estado_Comestibles", comestiblesFSM);
			
			FSM noComestiblesFSM = new FSM("FSM_NoComestibles");
			createFSMObserver(noComestiblesFSM, ghost);
			CompoundState noComestibles = new CompoundState("Estado_NoComestibles", noComestiblesFSM);

			FSM huirDePacmanFSM = new FSM("FSM_HuirDePacman");
			createFSMObserver(huirDePacmanFSM, ghost);
			CompoundState huirDePacman = new CompoundState("Estado_HuirDePacman", huirDePacmanFSM);

			GhostsEdibleTransition edible = new GhostsEdibleTransition(ghost);
			GhostsNotEdibleTransition notEdible = new GhostsNotEdibleTransition(ghost);
			PacManNearPPillTransition nearPP = new PacManNearPPillTransition();
			PacManFarPPillTransition notNearPP = new PacManFarPPillTransition();

			CurrentDirectionFreeTransition currentDirFreeRight = new CurrentDirectionFreeTransition(ghost, ghostDataRef, MOVE.RIGHT);
			CurrentDirectionFreeTransition currentDirFreeLeft = new CurrentDirectionFreeTransition(ghost, ghostDataRef, MOVE.LEFT);
			CurrentDirectionFreeTransition currentDirFreeUp = new CurrentDirectionFreeTransition(ghost, ghostDataRef, MOVE.UP);
			CurrentDirectionFreeTransition currentDirFreeDown = new CurrentDirectionFreeTransition(ghost, ghostDataRef, MOVE.DOWN);
			DirectionFreeTransition dirFreeRight = new DirectionFreeTransition(ghost, ghostDataRef, MOVE.RIGHT);
			DirectionFreeTransition dirFreeLeft = new DirectionFreeTransition(ghost, ghostDataRef, MOVE.LEFT);
			DirectionFreeTransition dirFreeUp = new DirectionFreeTransition(ghost, ghostDataRef, MOVE.UP);
			DirectionFreeTransition dirFreeDown = new DirectionFreeTransition(ghost, ghostDataRef, MOVE.DOWN);
			
			GhostsInIntersectionTransition ghostInIntersection = new GhostsInIntersectionTransition(ghost);
			
			GhostHasArrivedOrDoesNotHaveObjective ghostArrivedOrNotObjective = new GhostHasArrivedOrDoesNotHaveObjective(ghost, ghostDataRef);
			GhostHasObjectiveTransition ghostHasObjective = new GhostHasObjectiveTransition(ghost, ghostDataRef);
			GhostIsNotPursuerAndExitsRangeTransition ghostExitsAndNoPursuer = new GhostIsNotPursuerAndExitsRangeTransition(ghost);
			GhostIsPursuerOrEntersRangeTransition ghostEntersOrPursuer = new GhostIsPursuerOrEntersRangeTransition(ghost);
			
			GhostEdibleAndPacmanWillEat ghostEdibleAndPacmanReachs = new GhostEdibleAndPacmanWillEat(ghost);
			GhostEdibleAndPacmanWontReach ghostEdibleAndPacmanWontReach = new GhostEdibleAndPacmanWontReach(ghost);
			GhostEdiblePacmanWillReachAllEdible ghostEdibleAndNoGhostToRun = new GhostEdiblePacmanWillReachAllEdible(ghost);
			GhostEdiblePacmanWillReachNoEdibleGhost ghostEdibleAndGhostToRun = new GhostEdiblePacmanWillReachNoEdibleGhost(ghost);
			
			GhostsArriveBeforePacManToPPill ghostArrivesFirstToPP = new GhostsArriveBeforePacManToPPill(ghost);
			GhostArriveAfterPacmanToPP ghostArrivesLastToPP = new GhostArriveAfterPacmanToPP(ghost);
					
			//IMPORTANTE: mantener el orden de las transiciones de checkDirections, hace que primero compruebe la direccion actual y luego el resto
			//Nota: originalmente esta FSM iba a tener el mismo comportamiento que el JunctionManager, sin embargo, por problemas
			//en la instancias de clases dentro del input no hemos podido mantenerlo, aun asi se queda la estructura original
			pacmanLejosPPFSM.add(checkDirections, currentDirFreeLeft, goToLeftPath);
			pacmanLejosPPFSM.add(checkDirections, currentDirFreeUp, goToUpPath);
			pacmanLejosPPFSM.add(checkDirections, currentDirFreeDown, goToDownPath);
			pacmanLejosPPFSM.add(checkDirections, currentDirFreeRight, goToRightPath);
			
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
			
			interceptoresFSM.add(selectRandomZoneNearPacman, ghostHasObjective, goToObjective);
			interceptoresFSM.add(goToObjective, ghostArrivedOrNotObjective, selectRandomZoneNearPacman);
			interceptoresFSM.ready(selectRandomZoneNearPacman);
			
//			perseguidoresFSM.add(pacmanCercaPP, notNearPP, pacmanLejosPP);
//			perseguidoresFSM.add(pacmanLejosPP, nearPP, pacmanCercaPP);
//			perseguidoresFSM.ready(pacmanCercaPP);
			
			perseguidoresFSM.add(pacmanCercaPP, notNearPP, chaseJunction);
			perseguidoresFSM.add(chaseJunction, nearPP, pacmanCercaPP);
			perseguidoresFSM.ready(chaseJunction);
			
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

			//graphObserver.showInFrame(new Dimension(800, 600));

			fsms.put(ghost, fsm);
		}
	}
	
	public void createFSMObserver(FSM target, GHOST ghost) 
	{
//		target.addObserver(new ConsoleFSMObserver(ghost.name()));
//		GraphFSMObserver graphObserver = new GraphFSMObserver(ghost.name());
//		target.addObserver(graphObserver);
		
//		graphObserver.showInFrame(new Dimension(800, 600));
	}

	public void preCompute(String opponent) {
    	for(FSM fsm: fsms.values())
    		fsm.reset();
    	
		ghostData = new GhostData();
		ghostDataRef.clear();
		ghostDataRef.add(ghostData);
		
		juncManager = new JunctionManager();
    }
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {

		EnumMap<GHOST, MOVE> result = new EnumMap<GHOST, MOVE>(GHOST.class);

		ghostData.update();
		GhostsInput in = new GhostsInput(game, ghostDataRef);

		for (GHOST ghost : GHOST.values()) {
			FSM fsm = fsms.get(ghost);
			MOVE move = fsm.run(in);
			result.put(ghost, move);
		}

		return result;
	}
}