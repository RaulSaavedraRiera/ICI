package es.ucm.fdi.ici.c2223.practica5.grupo08.ghosts;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.MOVE;

public class GhostsDescription implements CaseComponent {

	Integer id;

	Integer score;
	Integer time;
	Integer nearestPPill;
	Integer nearestPill;
	Integer nearestGhost;
	Integer distanceToPacman;
	Integer pillsLeft;
	Boolean edibleGhost;
	MOVE actualDir;
	
	Integer PPEaten = 0;
	Integer ghostsEaten = 0;
	
	public GhostsDescription() {
		// TODO Auto-generated constructor stub
		PPEaten = 0;
		ghostsEaten = 0;
	}
	
	public void setPPEaten(Integer pp) 
	{
		PPEaten = pp;
	}
	public Integer getPPEaten() 
	{
		return PPEaten;
	}
	
	public void setGhostsEaten(Integer ghost) 
	{
		ghostsEaten = ghost;
	}
	public Integer getGhostsEaten() 
	{
		return ghostsEaten;
	}

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

	public Integer getNearestPPill() {
		return nearestPPill;
	}

	public void setNearestPPill(Integer nearestPPill) {
		this.nearestPPill = nearestPPill;
	}

	public Integer getNearestGhost() {
		return nearestGhost;
	}

	public void setNearestGhost(Integer nearestGhost) {
		this.nearestGhost = nearestGhost;
	}

	public Boolean getEdibleGhost() {
		return edibleGhost;
	}

	public void setEdibleGhost(Boolean edibleGhost) {
		this.edibleGhost = edibleGhost;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", GhostsDescription.class);
	}

	@Override
	public String toString() {
		return "GhostsDescription [id=" + id + ", score=" + score + ", time=" + time + ", nearestPPill=" + nearestPPill
				+ ", nearestPill=" + nearestPill + ", distanceToPacman=" + distanceToPacman + ", nearestGhost="
				+ nearestGhost + ", pillsLeft=" + pillsLeft + ", edibleGhost=" + edibleGhost + ", edibleGhost="
				+ edibleGhost + ", actualDir=" + actualDir + "]";
	}

	public Integer getNearestPill() {
		return nearestPill;
	}

	public void setNearestPill(Integer nearestPill) {
		this.nearestPill = nearestPill;
	}

	public Integer getDistanceToPacman() {
		return distanceToPacman;
	}

	public void setDistanceToPacman(Integer distanceToPacman) {
		this.distanceToPacman = distanceToPacman;
	}

	public Integer getPillsLeft() {
		return pillsLeft;
	}

	public void setPillsLeft(Integer pillsLeft) {
		this.pillsLeft = pillsLeft;
	}

	public MOVE getActualDir() {
		return actualDir;
	}

	public void setActualDir(MOVE actualDir) {
		this.actualDir = actualDir;
	}

}
