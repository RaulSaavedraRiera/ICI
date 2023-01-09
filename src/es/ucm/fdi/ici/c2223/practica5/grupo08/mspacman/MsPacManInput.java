package es.ucm.fdi.ici.c2223.practica5.grupo08.mspacman;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.cbr.CBRInput;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacManInput extends CBRInput {

	public MsPacManInput(Game game) {
		super(game);
		
	}
	
	final int DISTANCE_PILL_NEAR = 35;

	Integer score;
	Integer time;
	Integer livesRemaining;
	Integer pPillsRemaining;
	
	Integer nearestPPill;
	Integer nearesPill;
	Integer pillsNear;
	Integer pillsRemaining;
	
	Integer nearestGhostDistanceChasing;
	Integer nearestGhostDistanceEdible;
	Integer timeNearestEdibleGhost;
	
	MOVE currentDirPacman;
	
	@Override
	public void parseInput() {
		
		computeNearestGhostsDistance(game);
		
		computeNearestPPill(game);
		computeNearestPill(game);
		computePillsData(game);
		
		time = game.getTotalTime();
		score = game.getScore();
		livesRemaining = game.getPacmanNumberOfLivesRemaining();
		pPillsRemaining = game.getActivePowerPillsIndices().length;
		
		currentDirPacman = game.getPacmanLastMoveMade();
	}

	@Override
	public CBRQuery getQuery() {
		MsPacManDescription description = new MsPacManDescription();
		
		description.setScore(score);
		description.setTime(time);
		description.setLivesRemaining(livesRemaining);
		description.setPPillsRemaining(pPillsRemaining);
		
		description.setNearestPPill(nearestPPill);
		description.setNearestPill(nearesPill);
		description.setPillsNear(pillsNear);
		description.setPillsRemaining(pillsRemaining);
		
		description.setNearestGhostDistanceChasing(nearestGhostDistanceChasing);
		description.setNearestGhostDistanceEdible(nearestGhostDistanceEdible);
		description.setTimeNearestEdibleGhost(timeNearestEdibleGhost);
		
		description.setCurrentDirPacman(currentDirPacman);

		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}
	
	private void computeNearestGhostsDistance(Game game) {
		nearestGhostDistanceChasing = Integer.MAX_VALUE;
		nearestGhostDistanceEdible = Integer.MAX_VALUE;
		
		GHOST nearestC = null; GHOST nearestE = null;
		
		for(GHOST g: GHOST.values()) {
			int pos = game.getGhostCurrentNodeIndex(g);
			int distance; 
			if(pos != -1) 
				distance = (int)game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			else
				distance = Integer.MAX_VALUE;
			
			
			if(game.getGhostEdibleTime(g) > 0)
			{
				if(distance < nearestGhostDistanceEdible)
				{
					nearestGhostDistanceEdible = distance;
					//nearest = g;
				}
			}
			
			else
			{
				if(distance < nearestGhostDistanceChasing)
				{
					nearestGhostDistanceChasing = distance;
					//nearest = g;
				}
			}
			
		}
	}
	
	//de momento no tenemos en cuenta distancia al fantasma
	private void computeNearestPPill(Game game) {
		nearestPPill = Integer.MAX_VALUE;
		for(int pos: game.getActivePowerPillsIndices()) {
			int distance = (int)game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			//if(distance < nearestGhost)
			nearestPPill = distance;
		}
	}
	
	private void computeNearestPill(Game game) {
		nearestPPill = Integer.MAX_VALUE;
		for(int pos: game.getActivePillsIndices()) {
			int distance = (int)game.getDistance(game.getPacmanCurrentNodeIndex(), pos, DM.PATH);
			//if(distance < nearestGhost)
			nearestPPill = distance;
		}
	}
	
	private void computePillsData(Game game) {
		
		pillsRemaining = game.getActivePillsIndices().length;
		pillsNear = 0;
		
		for(int p : game.getActivePillsIndices()) {
			if(DISTANCE_PILL_NEAR >= game.getDistance(game.getPacmanCurrentNodeIndex(), p, DM.PATH))
				pillsNear++;
		}
	
	}
}
