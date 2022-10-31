package es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.transitions;

import es.ucm.fdi.ici.Input;
import es.ucm.fdi.ici.c2223.practica2.grupo08.mspacman.MsPacManInput;
import es.ucm.fdi.ici.fsm.Transition;

public class FleeLairNoGhostTransition implements Transition {
	
	String id;
	
	public FleeLairNoGhostTransition(String idd) {	
		id = idd;
	}

	@Override
	public boolean evaluate(Input in) {
		MsPacManInput input = (MsPacManInput)in;

		boolean nearCell = new CellNearTransition().evaluate(in);
		boolean nearPillCluster = new PillClusterNearTransition().evaluate(in);
		boolean nearPP = new PowerPillNearTransition().evaluate(in);
		
		return nearCell && !nearPillCluster && !nearPP;
		
	}

	@Override
	public String toString() {
		return String.format(id);
	}

}
