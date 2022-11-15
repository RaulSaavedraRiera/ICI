package es.ucm.fdi.ici.c2223.practica3.grupo08;

import java.util.EnumMap;
import java.util.HashMap;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica3.grupo08.GhostData;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.ChaseAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.GoToNearestPPAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.GoToObjectiveAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.RunAwayAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.RunAwayToGhostAction;
import es.ucm.fdi.ici.c2223.practica3.grupo08.ghosts.actions.SearchObjectiveCloseToPacmanAction;
import pacman.controllers.GhostController;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController {

	private HashMap<String, Action> actions;
	
	public Ghosts() {
		super();

		setName("Los Bellacos de Atocha");

		setTeam("Team 08");
		
		actions = new HashMap<String, Action>();
		
		GhostData gData = new GhostData();
		
		//chase action
		Action BLINKYchases = new ChaseAction(GHOST.BLINKY, gData);
		actions.put("BLINKYchases", BLINKYchases);
		Action PINKYchases = new ChaseAction(GHOST.PINKY, gData);
		actions.put("PINKYchases", PINKYchases);
		Action INKYchases = new ChaseAction(GHOST.INKY, gData);
		actions.put("INKYchases", INKYchases);
		Action SUEchases = new ChaseAction(GHOST.SUE, gData);
		actions.put("SUEchases", SUEchases);

		//runaway action
		Action BLINKYrunAway = new RunAwayAction(GHOST.BLINKY, gData);
		actions.put("BLINKYrunAway", BLINKYrunAway);
		Action PINKYrunAway = new RunAwayAction(GHOST.PINKY, gData);
		actions.put("BLINKYrunAway", PINKYrunAway);
		Action INKYrunAway = new RunAwayAction(GHOST.INKY, gData);
		actions.put("BLINKYrunAway", INKYrunAway);
		Action SUErunAway = new RunAwayAction(GHOST.SUE, gData);
		actions.put("BLINKYrunAway", SUErunAway);
		
		//runaway to ghost action
		Action BLINKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.BLINKY, gData);
		actions.put("BLINKYrunAwayToGhost", BLINKYrunAwayToGhost);
		Action PINKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.PINKY, gData);
		actions.put("PINKYrunAwayToGhost", PINKYrunAwayToGhost);
		Action INKYrunAwayToGhost = new RunAwayToGhostAction(GHOST.INKY, gData);
		actions.put("INKYrunAwayToGhost", INKYrunAwayToGhost);
		Action SUErunAwayToGhost = new RunAwayToGhostAction(GHOST.SUE, gData);
		actions.put("SUErunAwayToGhost", SUErunAwayToGhost);
		
		//search objective action
		Action BLINKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.BLINKY, gData);
		actions.put("BLINKYsearchsObjective", BLINKYsearchsObjective);
		Action PINKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.INKY, gData);
		actions.put("PINKYsearchsObjective", PINKYsearchsObjective);
		Action INKYsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.PINKY, gData);
		actions.put("INKYsearchsObjective", INKYsearchsObjective);
		Action SUEsearchsObjective = new SearchObjectiveCloseToPacmanAction(GHOST.BLINKY, gData);
		actions.put("SUEsearchsObjective", SUEsearchsObjective);
		
		//go to objective action
		Action BLINKgoesToObjective = new GoToObjectiveAction(GHOST.BLINKY, gData);
		actions.put("BLINKgoesToObjective", BLINKgoesToObjective);
		Action PINKYgoesToObjective = new GoToObjectiveAction(GHOST.INKY, gData);
		actions.put("PINKYgoesToObjective", PINKYgoesToObjective);
		Action INKYgoesToObjective = new GoToObjectiveAction(GHOST.PINKY, gData);
		actions.put("INKYgoesToObjective", INKYgoesToObjective);
		Action SUEgoesToObjective = new GoToObjectiveAction(GHOST.BLINKY, gData);
		actions.put("SUEgoesToObjective", SUEgoesToObjective);
		
		//go to nearest PP action
		Action BLINKgoToNearestPP = new GoToNearestPPAction(GHOST.BLINKY, gData);
		actions.put("BLINKgoToNearestPP", BLINKgoToNearestPP);
		Action PINKYgoToNearestPP = new GoToNearestPPAction(GHOST.INKY, gData);
		actions.put("PINKYgoToNearestPP", PINKYgoToNearestPP);
		Action INKYgoToNearestPP = new GoToNearestPPAction(GHOST.PINKY, gData);
		actions.put("INKYgoToNearestPP", INKYgoToNearestPP);
		Action SUEgoToNearestPP = new GoToNearestPPAction(GHOST.BLINKY, gData);
		actions.put("SUEgoToNearestPP", SUEgoToNearestPP);		
	}
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		return null;
	}

}
