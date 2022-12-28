package es.ucm.fdi.ici.c2223.practica5.grupo08.mspacman;

import java.util.Vector;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.method.retain.StoreCasesMethod;
import pacman.game.Game;

public class MsPacManStorageManager {

	Game game;
	CBRCaseBase caseBase;
	Vector<CBRCase> buffer;

	private final static int TIME_WINDOW = 3;
	
	public MsPacManStorageManager()
	{
		this.buffer = new Vector<CBRCase>();
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void setCaseBase(CBRCaseBase caseBase)
	{
		this.caseBase = caseBase;
	}
	
	public void reviseAndRetain(CBRCase newCase)
	{			
		this.buffer.add(newCase);
		
		//Buffer not full yet.
		if(this.buffer.size()<TIME_WINDOW)
			return;
		
		
		CBRCase bCase = this.buffer.remove(0);
		reviseCase(bCase);
		retainCase(bCase);
		
	}
	
	//isPacmanDead * ((puntosFinales - puntosIniciales) + PPComidas * 100 ) / (5 - nFantasmasComidos)
	private void reviseCase(CBRCase bCase) {
		MsPacManDescription description = (MsPacManDescription)bCase.getDescription();
		int oldScore = description.getScore();
		int currentScore = game.getScore();
		
		int pacmanDead = 1; 
		if(game.getPacmanNumberOfLivesRemaining() < description.getLivesRemaining()) 
			pacmanDead = 0;
		
		int scoreValue = currentScore - oldScore;
		
		int pPillsValue = game.getActivePowerPillsIndices().length - description.getpPillsRemaining();
		
		int pointsFromEats = scoreValue - (pPillsValue*50 + 
				((game.getActivePillsIndices().length - description.pillsRemaining - pPillsValue) * 10));
		
		int ghostsEat = 0;
		if(pointsFromEats != 0)
		{
			switch(pointsFromEats) {
			case 200:
				ghostsEat = 1;
				break;
			case 400:
				ghostsEat = 2;
				break;
			case 800:
				ghostsEat = 3;
				break;
			case 1600:
				ghostsEat = 4;
				break;
			}
			
		}
		
		MsPacManResult result = (MsPacManResult)bCase.getResult();
		result.setScore(pacmanDead * ((scoreValue + pPillsValue)/(5-ghostsEat)));	
	}
	
	private void retainCase(CBRCase bCase)
	{
		//Store the old case right now into the case base
		//Alternatively we could store all them when game finishes in close() method
		
		//here you should also check if the case must be stored into persistence (too similar to existing ones, etc.)
		
		StoreCasesMethod.storeCase(this.caseBase, bCase);
	}

	public void close() {
		for(CBRCase oldCase: this.buffer)
		{
			reviseCase(oldCase);
			retainCase(oldCase);
		}
		this.buffer.removeAllElements();
	}

	public int getPendingCases() {
		return this.buffer.size();
	}
}
