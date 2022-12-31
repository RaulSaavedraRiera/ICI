package es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts;

import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.ici.cbr.CBRInput;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.Ghost;

public class GhostsInput extends CBRInput {

	GHOST ghost;

	public GhostsInput(Game game, GHOST g) {
		super(game);
		this.ghost = g;
	}

	Integer score;
	Integer time;
	Integer nearestPPill;
	Integer nearestPill;
	Integer nearestGhost;
	Integer distanceToPacman;
	Integer pillsLeft;
	Boolean edibleGhost;
	MOVE actualDir;

	@Override
	public void parseInput() {
		computeNearestGhost(game);
		computeNearestPPill(game);
		computeNearestPill(game);

		score = game.getScore();
		time = game.getTotalTime();

		actualDir = game.getGhostLastMoveMade(ghost);
		
		pillsLeft = game.getNumberOfActivePills();
		
		edibleGhost = game.isGhostEdible(ghost);
		distanceToPacman = game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex(), actualDir);
	}

	@Override
	public CBRQuery getQuery() {
		GhostsDescription description = new GhostsDescription();
		description.setScore(score);
		description.setTime(time);
		description.setNearestPPill(nearestPPill);
		description.setNearestPPill(nearestPill);
		description.setNearestGhost(nearestGhost);
		description.setDistanceToPacman(distanceToPacman);
		description.setPillsLeft(pillsLeft);
		description.setEdibleGhost(edibleGhost);
		description.setActualDir(actualDir);

		CBRQuery query = new CBRQuery();
		query.setDescription(description);
		return query;
	}

	private void computeNearestGhost(Game game) {
		nearestGhost = Integer.MAX_VALUE;
		
		GHOST nearest = null;
		for (GHOST g : GHOST.values()) {

			if (ghost != g) {
				int pos = game.getGhostCurrentNodeIndex(g);
				int distance;
				if (pos != -1)
					distance = (int) game.getDistance(game.getGhostCurrentNodeIndex(ghost), pos, DM.PATH);
				else
					distance = Integer.MAX_VALUE;
				if (distance < nearestGhost) {
					nearestGhost = distance;
					nearest = g;
				}
			}
		}
	}

	private void computeNearestPPill(Game game) {
		nearestPPill = Integer.MAX_VALUE;
		for (int pos : game.getPowerPillIndices()) {
			int distance = (int) game.getDistance(game.getGhostCurrentNodeIndex(ghost), pos, DM.PATH);
			if (distance < nearestGhost)
				nearestPPill = distance;
		}
	}
	
	private void computeNearestPill(Game game) {
		nearestPill = Integer.MAX_VALUE;
		for (int pos : game.getPillIndices()) {
			int distance = (int) game.getDistance(game.getGhostCurrentNodeIndex(ghost), pos, DM.PATH);
			if (distance < nearestGhost)
				nearestPill = distance;
		}
	}
}
