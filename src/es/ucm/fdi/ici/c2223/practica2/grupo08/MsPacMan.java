package es.ucm.fdi.ici.c2223.practica2.grupo08;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.AwayNearestChasingGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.EvadeCellAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.EvadeCornerAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.EvadePowerPillAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoSecondNearestChasingGhostAction;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.actions.GoToNearestPowerPillAction;
import es.ucm.fdi.ici.fsm.FSM;
import es.ucm.fdi.ici.fsm.SimpleState;
import pacman.controllers.PacmanController;
import pacman.game.Constants.MOVE;
import pacman.game.Game;


public class MsPacMan extends PacmanController {

	FSM fsm;
	
	public MsPacMan() {
	
		setName("MsPacManIfUrNasty");
		
		fsm = new FSM("MsPacMan");

		FSM cfsmChasingGhost = new FSM("HasChasingGhost");
		FSM cfsmNoChasingGhost = new FSM("NoChasingGhost");
		
		FSM cfsmOneChasingGhost = new FSM("OneChasingGhost");
		FSM cfsmMultipleChasingGhosts = new FSM("MultipleChasingGhosts");

		FSM cfsmEdibleGhosts = new FSM("EdibleGhosts");
		FSM cfsmNoEdibleGhosts = new FSM("NoEdibleGhosts");

		SimpleState find2Ghost = new SimpleState("Find2ndGhost", new GoSecondNearestChasingGhostAction());
		SimpleState fleeGhost = new SimpleState("FleeGhost", new AwayNearestChasingGhostAction());
		SimpleState fleeCorner = new SimpleState("FleeCorner", new EvadeCornerAction());
		SimpleState fleeLair = new SimpleState("FleeLair", new EvadeCellAction());
		SimpleState fleePowerPill = new SimpleState("FleePowerPill", new EvadePowerPillAction());

		SimpleState chasePowerPill = new SimpleState("ChasePowerPill", new GoToNearestPowerPillAction());
		
		/*
		GraphFSMObserver observer = new GraphFSMObserver(fsm.toString());
		fsm.addObserver(observer);
		
		SimpleState stateExample = new SimpleState("stateExample", new RandomAction());
		
		Transition transitionExample = new RandomTransition(0);
		
		// ---
		
		FSM compoundFSMExample = new FSM("CompundExample");
		GraphFSMObserver compundObserver = new GraphFSMObserver(compoundFSMExample.toString());
		compoundFSMExample.addObserver(compundObserver);
		
		SimpleState stateChildExample = new SimpleState("stateChildExample", new RandomAction());
		SimpleState stateChildExample2 = new SimpleState("stateChildExample2", new RandomAction());

		Transition transitionChildExample = new RandomTransition(0.1);
		Transition transitionChildExample2 = new RandomTransition(0.2);

		compoundFSMExample.add(stateChildExample, transitionChildExample, stateChildExample2);
		compoundFSMExample.add(stateChildExample2, transitionChildExample2, stateChildExample);
		
		fsm.add(stateExample, transitionExample, stateChildExample);
		
		fsm.ready(stateExample);
		
		JFrame frame = new JFrame();
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(observer.getAsPanel(true, null), BorderLayout.SOUTH);
		main.add(compundObserver.getAsPanel(true, null), BorderLayout.NORTH);
		
		frame.getContentPane().add(main);
		frame.pack();
		frame.setVisible(true);
		*/
		
	}
	
	public void preCompute(String opponent) {
		fsm.reset();
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		Input in = new MsPacManInput(game);
		return fsm.run(in);
	}
	

}
