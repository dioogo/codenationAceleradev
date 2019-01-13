package br.com.codenation.pojo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Team {

	private Long id;
	private String name;
	private LocalDate creationDate;
	private String primaryUniformColor;
	private String secondaryUniformColor;
	private Player captain;
	private Map<Long, Player> players = new HashMap<>();
	
	public Team(Long id, String name, LocalDate creationDate, String primaryUniformColor,
			String secondaryUniformColor) {
		super();
		this.id = id;
		this.name = name;
		this.creationDate = creationDate;
		this.primaryUniformColor = primaryUniformColor;
		this.secondaryUniformColor = secondaryUniformColor;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getPrimaryUniformColor() {
		return primaryUniformColor;
	}
	
	public void setPrimaryUniformColor(String primaryUniformColor) {
		this.primaryUniformColor = primaryUniformColor;
	}
	
	public String getSecondaryUniformColor() {
		return secondaryUniformColor;
	}
	
	public void setSecondaryUniformColor(String secondaryUniformColor) {
		this.secondaryUniformColor = secondaryUniformColor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Long, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Long, Player> players) {
		this.players = players;
	}
	
	public Player getCaptain() {
		return captain;
	}

	public void setCaptain(Player captain) {
		this.captain = captain;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", creationDate=" + creationDate + ", primaryUniformColor="
				+ primaryUniformColor + ", secondaryUniformColor=" + secondaryUniformColor + ", players=" + players
				+ "]";
	}

	public static class Comparators {
		public static final Comparator<Team> ID = (Team t1, Team t2) -> Long.compare(t1.id, t2.id);
	}
}
