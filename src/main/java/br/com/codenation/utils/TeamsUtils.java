package br.com.codenation.utils;

import java.util.Map;

import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.pojo.Team;

public class TeamsUtils {
	
	public static Team getTeam(Map<Long, Team> teams, Long teamId) {
		if(!teams.containsKey(teamId)) {
			throw new TimeNaoEncontradoException();
		}
		
		return teams.get(teamId);
	}
}
