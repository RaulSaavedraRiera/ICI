package es.ucm.fdi.ici.c2223.practica5.grupo08.mspacman;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;

public class MsPacManDescription implements CaseComponent {

	//variables para gestion de casos
	Integer id;	
	Integer score;
	Integer time;
	Integer livesRemaining;
	Integer pPillsRemaining;
	
	//variables relacionadas con las posiciones
	Integer nearestPPill;
	Integer nearestPill;
	Integer pillsNear;
	Integer pillsRemaining;
	
	//variables relacionadas con los fantasmas
	Integer nearestGhostDistanceChasing;
	Integer nearestGhostDistanceEdible;
	Integer timeNearestEdibleGhost;
	
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	public Integer getLivesRemaining() {
		return livesRemaining;
	}

	public void setLivesRemaining(Integer livesRemaining) {
		this.livesRemaining = livesRemaining;
	}

	public Integer getpPillsRemaining() {
		return pPillsRemaining;
	}

	public void setpPillsRemaining(Integer pPillsRemaining) {
		this.pPillsRemaining = pPillsRemaining;
	}

	public Integer getNearestPPill() {
		return nearestPPill;
	}

	public void setNearestPPill(Integer nearestPPill) {
		this.nearestPPill = nearestPPill;
	}

	public Integer getNearestPill() {
		return nearestPill;
	}

	public void setNearestPill(Integer nearesPill) {
		this.nearestPill = nearesPill;
	}

	public Integer getPillsNear() {
		return pillsNear;
	}

	public void setPillsNear(Integer pillsNear) {
		this.pillsNear = pillsNear;
	}

	public Integer getPillsRemaining() {
		return pillsRemaining;
	}

	public void setPillsRemaining(Integer pillsRemaining) {
		this.pillsRemaining = pillsRemaining;
	}

	public Integer getNearestGhostDistanceChasing() {
		return nearestGhostDistanceChasing;
	}

	public void setNearestGhostDistanceChasing(Integer nearestGhostDistanceChasing) {
		this.nearestGhostDistanceChasing = nearestGhostDistanceChasing;
	}

	public Integer getNearestGhostDistanceEdible() {
		return nearestGhostDistanceEdible;
	}

	public void setNearestGhostDistanceEdible(Integer nearestGhostDistanceEdible) {
		this.nearestGhostDistanceEdible = nearestGhostDistanceEdible;
	}

	public Integer getTimeNearestEdibleGhost() {
		return timeNearestEdibleGhost;
	}

	public void setTimeNearestEdibleGhost(Integer timeNearestEdibleGhost) {
		this.timeNearestEdibleGhost = timeNearestEdibleGhost;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", MsPacManDescription.class);
	}

	@Override
	public String toString() {
		return "MsPacManDescription [id=" + id + ", score=" + score + ", time=" + time +
				", livesRemaining=" + livesRemaining + ", pPillsRemaining=" + pPillsRemaining +
				", nearestPPill="+ nearestPPill + ", nearestPill=" + nearestPill + ", pillsNear="+ pillsNear + ", pillsRemaining=" + pillsRemaining 
				+ ", nearestGhostDistanceChasing=" + nearestGhostDistanceChasing + ", nearestGhostDistanceEdible=" + nearestGhostDistanceEdible +  
				", timeNearestEdibleGhost=" + timeNearestEdibleGhost + "]";
	}


	
	

}
