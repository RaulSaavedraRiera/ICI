package es.ucm.fdi.ici.c2223.practica2.grupo08;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.AwayNearestChasingGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.EvadeCellAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.EvadeCornerAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.EvadePowerPillAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoGroupEdibleGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoNearestChasingGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoNearestEdibleGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoSecureZone;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoToNearestPillAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoToNearestPowerPillAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.ChasePillTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.ChasingGhostNearTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.EdibleGhostNearTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.FindGhostTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.FleeCornerTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.FleeGhostTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.FleeLairNoGhostTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.FleeLairTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.FleePPNoGhostTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.FleePPTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.GroupEdibleGhostNearTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.MultipleChasingGhostNearTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.NoChasingGhostNearTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.NoEdibleGhostNearTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.OneChasingGhostTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.OneEdibleGhostNearTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.PowerPillAvailableTransition;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions.PowerPillUnavailableTransition;
import es.ucm.fdi.ici.fsm.CompoundState;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import es.ucm.fdi.ici.fsm.Transition;
import es.ucm.fdi.ici.fsm.observers.ConsoleFSMObserver;
import es.ucm.fdi.ici.fsm.observers.GraphFSMObserver;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;


public class MsPacMan extends PacmanController {

	FSM fsm;
	
	public MsPacMan() {
	
		setName("MsPacManIfUrNasty");
		
		fsm = new FSM("MsPacMan");
		
		fsm.addObserver(new ConsoleFSMObserver("MsPacMan"));
		GraphFSMObserver observer = new GraphFSMObserver(fsm.toString());
		fsm.addObserver(observer);

		

		FSM cfsmChasingGhost = new FSM("FSM_HasChasingGhost");
		CompoundState chasingGhost = new CompoundState("HasChasingGhost", cfsmChasingGhost);
		FSM cfsmNoChasingGhost = new FSM("FSM_NoChasingGhost");
		CompoundState noChasingGhost = new CompoundState("NoChasingGhost", cfsmNoChasingGhost);
		
		FSM cfsmOneChasingGhost = new FSM("FSM_OneChasingGhost");
		CompoundState oneChasingGhost = new CompoundState("OneChasingGhost", cfsmOneChasingGhost);
		FSM cfsmMultipleChasingGhosts = new FSM("FSM_MultipleChasingGhosts");
		CompoundState multipleChasingGhosts = new CompoundState("MultipleChasingGhosts", cfsmMultipleChasingGhosts);

		FSM cfsmEdibleGhosts = new FSM("FSM_EdibleGhosts");
		CompoundState edibleGhosts = new CompoundState("EdibleGhosts", cfsmEdibleGhosts);
		FSM cfsmNoEdibleGhosts = new FSM("FSM_NoEdibleGhosts");
		CompoundState noEdibleGhosts = new CompoundState("NoEdibleGhosts", cfsmNoEdibleGhosts);
		

		//SimpleState find2Ghost = new SimpleState("Find2ndGhost", new GoSecondNearestChasingGhostAction());
		SimpleState fleeGhost = new SimpleState("FleeGhost", new AwayNearestChasingGhostAction());
		SimpleState fleeCorner = new SimpleState("FleeCorner", new EvadeCornerAction());
		SimpleState fleeLair = new SimpleState("FleeLair", new EvadeCellAction());
		SimpleState fleePowerPill = new SimpleState("FleePowerPill", new EvadePowerPillAction());

		SimpleState chasePowerPill = new SimpleState("ChasePowerPill", new GoToNearestPowerPillAction());
		SimpleState fleeToSafeZone = new SimpleState("FleeToSafeZone", new GoSecureZone());
		
		SimpleState chaseEdibleGhost = new SimpleState("ChaseEdibleGhost", new GoNearestEdibleGhostAction());
		SimpleState chaseEdibleGroup = new SimpleState("ChaseEdibleGroup", new GoGroupEdibleGhostAction());

		SimpleState fleePowerPillNoGhost = new SimpleState("FleePowerPill(NoGhost)", new EvadePowerPillAction());
		SimpleState fleeLairNoGhost = new SimpleState("FleeLair(NoGhost)", new EvadeCellAction());
		SimpleState chaseNearestPill = new SimpleState("ChaseNearestPill", new GoToNearestPillAction());
		SimpleState findGhost = new SimpleState("FindGhost", new GoNearestChasingGhostAction());
		
	
		createObserver(cfsmChasingGhost);
		createObserver(cfsmNoChasingGhost);
		createObserver(cfsmOneChasingGhost);
		createObserver(cfsmMultipleChasingGhosts);
		createObserver(cfsmEdibleGhosts);
		createObserver(cfsmNoEdibleGhosts);
//		GraphFSMObserver chasingGhostObserver = new GraphFSMObserver(cfsmChasingGhost.toString());
//		cfsmChasingGhost.addObserver(observer);
//		
//		GraphFSMObserver noChasingGhostObserver = new GraphFSMObserver(cfsmNoChasingGhost.toString());
//		cfsmNoChasingGhost.addObserver(observer);
//		
//		GraphFSMObserver oneChasingGhostObserver = new GraphFSMObserver(cfsmOneChasingGhost.toString());
//		cfsmOneChasingGhost.addObserver(observer);
//		
//		GraphFSMObserver multipleChasingGhostsObserver = new GraphFSMObserver(cfsmMultipleChasingGhosts.toString());
//		cfsmMultipleChasingGhosts.addObserver(observer);
//		
//		GraphFSMObserver edibleGhostObserver = new GraphFSMObserver(cfsmEdibleGhosts.toString());
//		cfsmEdibleGhosts.addObserver(observer);
//		
//		GraphFSMObserver noEdibleGhostObserver = new GraphFSMObserver(cfsmNoEdibleGhosts.toString());
//		cfsmNoEdibleGhosts.addObserver(observer);
//		

		Transition chasingGhostTransition = new ChasingGhostNearTransition();
		Transition noChasingGhostTransition = new NoChasingGhostNearTransition();
		
		Transition multipleChasingGhostsTransition = new MultipleChasingGhostNearTransition();
		Transition oneChasingGhostTransition = new OneChasingGhostTransition();

		Transition edibleGhostsTransition = new EdibleGhostNearTransition();
		Transition noEdibleGhostsTransition = new NoEdibleGhostNearTransition();
		
		
		//Transition fleeCellToFind2GhostTransition = new Find2ndGhostTransition("Flee Lair -> Find 2nd Ghost");
		//Transition fleeGhostToFind2GhostTransition = new Find2ndGhostTransition("Flee Ghost -> Find 2nd Ghost");
		//Transition fleeCornerToFind2GhostTransition = new Find2ndGhostTransition("Flee Corner -> Find 2nd Ghost");
		//Transition fleePPToFind2GhostTransition = new Find2ndGhostTransition("Flee Power Pill -> Find 2nd Ghost");

		Transition fleeCellToFleeGhostTransition = new FleeGhostTransition("Flee Lair -> Flee Ghost");
		//Transition find2GhostToFleeGhostTransition = new FleeGhostTransition("Find 2nd Ghost -> Flee Ghost");
		Transition fleeCornerToFleeGhostTransition = new FleeGhostTransition("Flee Corner -> Flee Ghost");
		Transition fleePPToFleeGhostTransition = new FleeGhostTransition("Flee Power Pill -> Flee Ghost");
		
		Transition fleeGhostToFleeLairTransition = new FleeLairTransition(" Flee Ghost -> Flee Lair");
		//Transition find2GhostToFleeLairTransition = new FleeLairTransition("Find 2nd Ghost -> Flee Lair");
		Transition fleePPToFleeLairTransition = new FleeLairTransition("Flee Power Pill -> Flee Lair");
		
		Transition fleeGhostToFleeCornerTransition = new FleeCornerTransition(" Flee Ghost -> Flee Corner");
		//Transition find2GhostToFleeCornerTransition = new FleeCornerTransition("Find 2nd Ghost -> Flee Corner");
		Transition fleePPToFleeCornerTransition = new FleeCornerTransition("Flee Power Pill -> Flee Corner");
		
		Transition fleeGhostToFleePPTransition = new FleePPTransition(" Flee Ghost -> Flee Power Pill");
		//Transition find2GhostToFleePPTransition = new FleePPTransition("Find 2nd Ghost -> Flee Power Pill");
		Transition fleeLairToFleePPTransition = new FleePPTransition("Flee Lair -> Flee Power Pill");
		

		Transition chasePPToFleeToSafeZoneTransition = new PowerPillUnavailableTransition();
		Transition FleeToSafeZoneToChasePPTransition = new PowerPillAvailableTransition();
		

		Transition chaseEdibleToChaseEdibleGroupTransition = new GroupEdibleGhostNearTransition();
		Transition chaseEdibleGroupToChaseEdibleTransition = new OneEdibleGhostNearTransition();
		

		Transition chasePillToFleePPTransition = new FleePPNoGhostTransition("Chase Pill -> Flee Power Pill");
		Transition findGhostToFleePPTransition = new FleePPNoGhostTransition("Find Ghost -> Flee Power Pill");

		Transition chasePillToFleeLairTransition = new FleeLairNoGhostTransition("Chase Pill -> Flee Lair");
		Transition findGhostToFleeLairTransition = new FleeLairNoGhostTransition("Find Ghost -> Flee Lair");
		
		Transition fleeCellToChasePillTransition = new ChasePillTransition("Flee Lair -> Chase Pill");
		Transition findGhostToChasePillTransition = new ChasePillTransition("Find Ghost -> Chase Pill");
		Transition fleePPToChasePillTransition = new ChasePillTransition("Flee Power Pill -> Chase Pill");
		
		Transition fleeCellToFindGhostTransition = new FindGhostTransition("Flee Lair -> Find Ghost");
		Transition chasePillToFindGhostTransition = new FindGhostTransition("Chase Pill -> Find Ghost");
		Transition fleePPToFindGhostTransition = new FindGhostTransition("Flee Power Pill -> Find Ghost");
		

		fsm.add(chasingGhost, noChasingGhostTransition, noChasingGhost);
		fsm.add(noChasingGhost, chasingGhostTransition, chasingGhost);

		
		cfsmChasingGhost.add(oneChasingGhost, multipleChasingGhostsTransition, multipleChasingGhosts);
		cfsmChasingGhost.add(multipleChasingGhosts, oneChasingGhostTransition, oneChasingGhost);
		

		cfsmNoChasingGhost.add(noEdibleGhosts, edibleGhostsTransition, edibleGhosts);
		cfsmNoChasingGhost.add(edibleGhosts, noEdibleGhostsTransition, noEdibleGhosts);
		
		
		//cfsmOneChasingGhost.add(fleeLair, fleeCellToFind2GhostTransition, find2Ghost);
		//cfsmOneChasingGhost.add(fleeGhost, fleeGhostToFind2GhostTransition, find2Ghost);
		//cfsmOneChasingGhost.add(fleeCorner, fleeCornerToFind2GhostTransition, find2Ghost);
		//cfsmOneChasingGhost.add(fleePowerPill, fleePPToFind2GhostTransition, find2Ghost);

		cfsmOneChasingGhost.add(fleeLair, fleeCellToFleeGhostTransition, fleeGhost);
		//cfsmOneChasingGhost.add(find2Ghost, find2GhostToFleeGhostTransition, fleeGhost);
		cfsmOneChasingGhost.add(fleeCorner, fleeCornerToFleeGhostTransition, fleeGhost);
		cfsmOneChasingGhost.add(fleePowerPill, fleePPToFleeGhostTransition, fleeGhost);

		cfsmOneChasingGhost.add(fleeGhost, fleeGhostToFleeLairTransition, fleeLair);
		//cfsmOneChasingGhost.add(find2Ghost, find2GhostToFleeLairTransition, fleeLair);
		cfsmOneChasingGhost.add(fleePowerPill, fleePPToFleeLairTransition, fleeLair);

		cfsmOneChasingGhost.add(fleeGhost, fleeGhostToFleeCornerTransition, fleeCorner);
		//cfsmOneChasingGhost.add(find2Ghost, find2GhostToFleeCornerTransition, fleeCorner);
		cfsmOneChasingGhost.add(fleePowerPill, fleePPToFleeCornerTransition, fleeCorner);

		cfsmOneChasingGhost.add(fleeGhost, fleeGhostToFleePPTransition, fleePowerPill);
		//cfsmOneChasingGhost.add(find2Ghost, find2GhostToFleePPTransition, fleePowerPill);
		cfsmOneChasingGhost.add(fleeLair, fleeLairToFleePPTransition, fleePowerPill);
		

		cfsmMultipleChasingGhosts.add(chasePowerPill, chasePPToFleeToSafeZoneTransition, fleeToSafeZone);
		cfsmMultipleChasingGhosts.add(fleeToSafeZone, FleeToSafeZoneToChasePPTransition, chasePowerPill);

		
		cfsmEdibleGhosts.add(chaseEdibleGhost, chaseEdibleToChaseEdibleGroupTransition, chaseEdibleGroup);
		cfsmEdibleGhosts.add(chaseEdibleGroup, chaseEdibleGroupToChaseEdibleTransition, chaseEdibleGhost);

		
		cfsmNoEdibleGhosts.add(chaseNearestPill, chasePillToFleePPTransition, fleePowerPillNoGhost);
		cfsmNoEdibleGhosts.add(findGhost, findGhostToFleePPTransition, fleePowerPillNoGhost);

		cfsmNoEdibleGhosts.add(chaseNearestPill, chasePillToFleeLairTransition, fleeLairNoGhost);
		cfsmNoEdibleGhosts.add(findGhost, findGhostToFleeLairTransition, fleeLairNoGhost);

		cfsmNoEdibleGhosts.add(fleeLairNoGhost, fleeCellToChasePillTransition, chaseNearestPill);
		cfsmNoEdibleGhosts.add(findGhost, findGhostToChasePillTransition, chaseNearestPill);
		cfsmNoEdibleGhosts.add(fleePowerPillNoGhost, fleePPToChasePillTransition, chaseNearestPill);

		cfsmNoEdibleGhosts.add(fleeLairNoGhost, fleeCellToFindGhostTransition, findGhost);
		cfsmNoEdibleGhosts.add(chaseNearestPill, chasePillToFindGhostTransition, findGhost);
		cfsmNoEdibleGhosts.add(fleePowerPillNoGhost, fleePPToFindGhostTransition, findGhost);
		
		
		fsm.ready(noChasingGhost);
		cfsmChasingGhost.ready(oneChasingGhost);
		cfsmNoChasingGhost.ready(noEdibleGhosts);
		cfsmOneChasingGhost.ready(fleeGhost);
		cfsmMultipleChasingGhosts.ready(fleeToSafeZone);
		cfsmEdibleGhosts.ready(chaseEdibleGhost);
		cfsmNoEdibleGhosts.ready(chaseNearestPill);
		
		
		JFrame frame = new JFrame();
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
<<<<<<< HEAD
		main.add(observer.getAsPanel(true, null), BorderLayout.SOUTH);
//		main.add(chasingGhostObserver.getAsPanel(true, null), BorderLayout.SOUTH);	// Poner varios en North los muestra en el mismo panel. Queda bien, solo hay que decidir cuales separar, si queremos separar alguno.
//		main.add(noChasingGhostObserver.getAsPanel(true, null), BorderLayout.SOUTH);
//		main.add(oneChasingGhostObserver.getAsPanel(true, null), BorderLayout.SOUTH);
//		main.add(multipleChasingGhostsObserver.getAsPanel(true, null), BorderLayout.SOUTH);
//		main.add(edibleGhostObserver.getAsPanel(true, null), BorderLayout.SOUTH);
//		main.add(noEdibleGhostObserver.getAsPanel(true, null), BorderLayout.SOUTH);
=======
		// main.add(observer.getAsPanel(true, null), BorderLayout.SOUTH);
		// main.add(chasingGhostObserver.getAsPanel(true, null), BorderLayout.NORTH);	// Poner varios en North los muestra en el mismo panel. Queda bien, solo hay que decidir cuales separar, si queremos separar alguno.
		// main.add(noChasingGhostObserver.getAsPanel(true, null), BorderLayout.SOUTH);
		main.add(oneChasingGhostObserver.getAsPanel(true, null), BorderLayout.NORTH);
		main.add(multipleChasingGhostsObserver.getAsPanel(true, null), BorderLayout.WEST);
		main.add(edibleGhostObserver.getAsPanel(true, null), BorderLayout.SOUTH);
		main.add(noEdibleGhostObserver.getAsPanel(true, null), BorderLayout.EAST);
>>>>>>> 8487989dcb7b7e0ea853e2f736c817d43227a6d2
		
		frame.getContentPane().add(main);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void preCompute(String opponent) {
		fsm.reset();
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		Input in = new MsPacManInput(game);
		return fsm.run(in);
	}
	
	void createObserver(FSM target)
	{
		target.addObserver(new ConsoleFSMObserver("MsPacMan"));
	}
	

}
