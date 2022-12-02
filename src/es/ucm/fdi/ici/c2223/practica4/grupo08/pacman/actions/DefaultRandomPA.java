package es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.actions;

import java.awt.Color;
import java.util.Map;

import es.ucm.fdi.ici.Action;
import es.ucm.fdi.ici.c2223.practica4.grupo08.pacman.MsPacManFuzzyMemory;
import pacman.game.Constants;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class DefaultRandomPA implements Action {

	final int LIMIT_EDIBLE_TIME = 5;
	MsPacManFuzzyMemory memory;
	
		public DefaultRandomPA(MsPacManFuzzyMemory mem) {
			memory = mem;
		}

		@Override
		public MOVE execute(Game game) {
	       
			int memGhostPos;
			for	(GHOST g : Constants.GHOST.values()) {
				memGhostPos = (int)memory.lastPosGhost[g.ordinal()];
			}

			int memEdibleTime;
			for	(GHOST g : Constants.GHOST.values()) {
				memEdibleTime = (int)memory.edibleTimeGhosts[g.ordinal()];
			}
			
			MOVE memGhostDir;
			for	(GHOST g : Constants.GHOST.values()) {
				memGhostDir = memory.lastDirectionGhosts[g.ordinal()];
			}
			
			double memLairTime;
			for	(GHOST g : Constants.GHOST.values()) {
				memLairTime = memory.lairTimeGhosts[g.ordinal()];
			}
			
			int memPill;
			boolean memPillActive;
			for(Map.Entry<Integer, Boolean> i : memory.pills.entrySet()) {
				memPill = i.getKey();
				memPillActive = i.getValue();
			}
			
			int memPowerPill;
			boolean memPowerPillActive;
			for(Map.Entry<Integer, Boolean> i : memory.powerPills.entrySet()) {
				memPowerPill = i.getKey();
				memPowerPillActive = i.getValue();
			}
			
			
			return MOVE.NEUTRAL;
				
		}
		
		@Override
		public String getActionId() {
			return "PacmanDefaultRandom";
			
		}
		
}
