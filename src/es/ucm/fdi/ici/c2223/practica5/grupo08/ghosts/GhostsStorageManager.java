package es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.method.retain.StoreCasesMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.GlobalSimilarityFunction;
import pacman.game.Game;

public class GhostsStorageManager {

	Game game;
	CBRCaseBase caseBase;
	Vector<CBRCase> buffer;

	private final static int TIME_WINDOW = 4;
	
	public GhostsStorageManager()
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
	
	public void reviseAndRetain(CBRCase newCase, GhostsResultUpdater resultUpdater)
	{			
		this.buffer.add(newCase);
		resultUpdater.addCase(newCase);
		
		//Buffer not full yet.
		if(this.buffer.size()<TIME_WINDOW)
			return;
		
		CBRCase bCase = this.buffer.remove(0);
		resultUpdater.removeCase(bCase);
		reviseCase(bCase);
		retainCase(bCase);
		
	}
	
	private void reviseCase(CBRCase bCase) {
		GhostsDescription description = (GhostsDescription)bCase.getDescription();
		int oldScore = description.getScore();
		int currentScore = game.getScore();
		int resultValue = (oldScore - currentScore) + description.getPPEaten() * 1000;
		
		GhostsResult result = (GhostsResult)bCase.getResult();
		result.setScore(resultValue);	
	}
	
	private void retainCase(CBRCase bCase)
	{
		//Store the old case right now into the case base
		//Alternatively we could store all them when game finishes in close() method
		
		//here you should also check if the case must be stored into persistence (too similar to existing ones, etc.)
		
		StoreCasesMethod.storeCase(this.caseBase, bCase);
	}
	
	public void deleteSimilarCases(NNConfig simConfig) 
	{
		List<CBRCase> similarCases = new ArrayList<CBRCase>();
		
		for (CBRCase c1 : caseBase.getCases()) 
		{
			for (CBRCase c2 : caseBase.getCases()) 
			{
				if (c1 != c2) 
				{
			        GlobalSimilarityFunction gsf = simConfig.getDescriptionSimFunction();
			        
			        double sim = gsf.compute(c1.getDescription(), c2.getDescription(), c1, c2, simConfig);
			        
			        //si se parecen mas de un limite se añade el de menor resultado para borrar
			        if (sim > 0.99) 
			        {
			        	double resultC1 = ((GhostsResult) c1.getResult()).getScore();
			        	double resultC2 = ((GhostsResult) c2.getResult()).getScore();
			        	
			        	if (resultC1 > resultC2)
			        		similarCases.add(c2);
			        	else 
			        		similarCases.add(c1);
			        }
				}
			}
		}
		
		caseBase.forgetCases(similarCases);
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
