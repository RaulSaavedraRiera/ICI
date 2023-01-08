package es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts;

import java.util.Vector;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class GhostsResultUpdater {
	
	Vector<CBRCase> casesInBuffer;
	
	public GhostsResultUpdater() {
		this.casesInBuffer = new Vector<CBRCase>();
	}

	public void addCase(CBRCase c) 
	{
		casesInBuffer.add(c);
	}
	public void removeCase(CBRCase c) 
	{
		casesInBuffer.remove(c);
	}
	
	public void update(Game game) 
	{
		for (GHOST g : GHOST.values())
			if (game.wasGhostEaten(g))
				for (CBRCase c : casesInBuffer) 
				{
					GhostsDescription desc = (GhostsDescription)c.getDescription();
					desc.setGhostsEaten(desc.getGhostsEaten()+1);
					
					c.setDescription(desc);
				}
	
		if (game.wasPowerPillEaten())
			for (CBRCase c : casesInBuffer) 
			{
				GhostsDescription desc = (GhostsDescription)c.getDescription();
				desc.setPPEaten(desc.getGhostsEaten()+1);
				
				c.setDescription(desc);
			}
	}
	
	public void reset() 
	{
		this.casesInBuffer = new Vector<CBRCase>();
	}
}