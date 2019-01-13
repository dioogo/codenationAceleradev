package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.pojo.Player;
import br.com.codenation.pojo.Team;
import br.com.codenation.utils.PlayersUtils;
import br.com.codenation.utils.TeamsUtils;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private Map<Long, Team> teams = new HashMap<>();
	
	// Para unit tests
	public Map<Long, Team> getTeams() {
		return teams;
	}
	
	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		if(teams.containsKey(id)) {
			throw new IdentificadorUtilizadoException();
		}
		final Team newTeam = new Team(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
		teams.put(id, newTeam);
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		if(PlayersUtils.getAllPlayers(teams).stream().filter(player -> player.getId().equals(id)).findFirst().isPresent()) {
			throw new IdentificadorUtilizadoException();
		}
		
		Team team = TeamsUtils.getTeam(teams, idTime);
		
		Player newPlayer = new Player(id, nome, dataNascimento, nivelHabilidade, salario);
		team.getPlayers().put(id, newPlayer);
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		Optional<Team> teamWithPlayer = teams.values().stream().filter(team -> 
			team.getPlayers().containsKey(idJogador)).findFirst();
		
		if(!teamWithPlayer.isPresent()) {
			throw new JogadorNaoEncontradoException();
		}
		Team team = teamWithPlayer.get();
		Player newCaptain = team.getPlayers().get(idJogador);
		
		team.setCaptain(newCaptain);
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		Team team = TeamsUtils.getTeam(teams, idTime);
		
		if(team.getCaptain() == null) {
			throw new CapitaoNaoInformadoException();
		}
		
		return team.getCaptain().getId();
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		return PlayersUtils.getPlayer(teams, idJogador).getName();
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		Team foundTeam = TeamsUtils.getTeam(teams, idTime);
		
		return foundTeam.getName();
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		List<Player> teamPlayers = PlayersUtils.getTeamPlayers(teams, idTime, Player::compareById);
		
		return teamPlayers.stream().map(p -> p.getId()).collect(Collectors.toList());
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		return PlayersUtils.getFirstTeamPlayerIdByOrder(teams, idTime, Player::compareByBestPlayer);
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		return PlayersUtils.getFirstTeamPlayerIdByOrder(teams, idTime, Player::compareByOldestPlayer);
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		List<Long> orderedTeams = new ArrayList<>(teams.keySet());
		Collections.sort(orderedTeams);
		return orderedTeams;
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		return PlayersUtils.getFirstTeamPlayerIdByOrder(teams, idTime, Player::compareByBiggestSalary);
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		return PlayersUtils.getPlayer(teams, idJogador).getSalary();
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		List<Player> allPlayers = PlayersUtils.getAllPlayers(teams);
		
		if(allPlayers.size() == 0) {
			return new ArrayList<>();
		}
		
		Collections.sort(allPlayers, Player::compareByBestPlayer);

		if(top.compareTo(allPlayers.size()) > 0) {
			return allPlayers.stream().map(p -> p.getId()).collect(Collectors.toList()); 
		} else {
			return allPlayers.subList(0, top).stream().map(p -> p.getId()).collect(Collectors.toList());
		}
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		Team homeTeam = TeamsUtils.getTeam(teams, timeDaCasa);
		Team awayTeam = TeamsUtils.getTeam(teams, timeDeFora);
		
		if(homeTeam.getPrimaryUniformColor().equalsIgnoreCase(awayTeam.getPrimaryUniformColor())) {
			return awayTeam.getSecondaryUniformColor();
		} else {
			return awayTeam.getPrimaryUniformColor();
		}
	}
}
