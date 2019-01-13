package br.com.codenation.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.pojo.Player;
import br.com.codenation.pojo.Team;

public class PlayersUtils {

	public static List<Player> getAllPlayers(Map<Long, Team> teams) {
		List<Player> allPlayers = new ArrayList<>();
		
		teams.values().stream().forEach(team -> {
			allPlayers.addAll(team.getPlayers().values());
		});
		
		return allPlayers;
	}
	
	public static Player getPlayer(Map<Long, Team> teams, Long playerId) {
		Optional<Player> player = PlayersUtils.getAllPlayers(teams).stream().filter(p -> p.getId().equals(playerId)).findFirst();
		
		if(!player.isPresent()) {
			throw new JogadorNaoEncontradoException();
		}
		
		return player.get();
	}

	public static List<Player> getTeamPlayers(Map<Long, Team> teams, Long teamId, Comparator<Player> comparator) {
		Team foundTeam = TeamsUtils.getTeam(teams, teamId);
		
		List<Player> teamPlayers = new ArrayList<>(foundTeam.getPlayers().values());
		if(teamPlayers.size() == 0) {
			throw new JogadorNaoEncontradoException();
		}
		
		Collections.sort(teamPlayers, comparator);
		
		return teamPlayers;
	}
	
	public static Long getFirstTeamPlayerIdByOrder(Map<Long, Team> teams, Long teamId, Comparator<Player> comparator) {
		Team foundTeam = TeamsUtils.getTeam(teams, teamId);
		
		List<Player> teamPlayers = new ArrayList<>(foundTeam.getPlayers().values());
		if(teamPlayers.size() == 0) {
			throw new JogadorNaoEncontradoException();
		}
		
		Collections.sort(teamPlayers, comparator);
		
		return teamPlayers.get(0).getId();
	}
	
}
