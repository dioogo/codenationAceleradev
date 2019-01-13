package br.com.codenation;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.pojo.Player;
import br.com.codenation.pojo.Team;

@RunWith(JUnit4.class)
public class DesafioMeuTimeApplicationTest {
	
	@Test
	public void testIncluirTimeWithSuccess() throws Exception {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		Map<Long, Team> teams = application.getTeams();
		assertEquals(1, teams.size());
		Team createdTeam = teams.get((long) 1);		
		assertEquals("Grêmio", createdTeam.getName());
		assertEquals(LocalDate.of(1903, 9, 15), createdTeam.getCreationDate());
		assertEquals("Azul", createdTeam.getPrimaryUniformColor());
		assertEquals("Branco", createdTeam.getSecondaryUniformColor());
	}
	
	@Test(expected = IdentificadorUtilizadoException.class) 
	public void testIncluirTimeWithDuplicatedTeam() throws Exception {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
	}
	
	@Test
	public void testIncluirJogadorWithSuccess() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		application.incluirJogador(new Long(10), new Long(1), "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		
		Map<Long, Player> gremioPlayers = application.getTeams().get(new Long(1)).getPlayers();
		assertEquals(1, gremioPlayers.size());
		Player luan = gremioPlayers.get(new Long(10));
		assertEquals("Luan", luan.getName());
		assertEquals(LocalDate.of(1993, 3, 27), luan.getDateOfBirth());
		assertEquals(9, luan.getSkillLevel().intValue());
		assertEquals(new BigDecimal(700000), luan.getSalary());
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testIncluirJogadorWithSuccessNotFoundTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirJogador(new Long(10), new Long(1), "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
	}
	
	@Test(expected = IdentificadorUtilizadoException.class)
	public void testIncluirJogadorWithDuplicatedPlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), new Long(1), "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(10), new Long(1), "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
	}
	
	@Test(expected = IdentificadorUtilizadoException.class)
	public void testIncluirJogadorInAnotherTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(20), new Long(1), "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(10), new Long(2), "Luan Inter", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		
		application.incluirJogador(new Long(10), new Long(1), "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
	}
	
	@Test
	public void testDefinirCapitaoWithSuccessWithOnePlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), (long) 1, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		
		application.definirCapitao(new Long(10));
		assertEquals(10, application.getTeams().get((long) 1).getCaptain().getId().intValue());
	}
	
	@Test
	public void testDefinirCapitaoWithSuccessWithTwoPlayers() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), (long) 1, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), (long) 1, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.definirCapitao(new Long(10));
		assertEquals(10, application.getTeams().get((long) 1).getCaptain().getId().intValue());
	}
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void testDefinirCapitaoWithNotFoundPlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.definirCapitao(new Long(10));
	}
	
	@Test
	public void testBuscarCapitaoDoTimeWithSuccess() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime(1L, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(20L, 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.definirCapitao(20L);
		
		Long captainId = application.buscarCapitaoDoTime(new Long(1));
		assertEquals(new Long(20), captainId);
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarCapitaoDoTimeWithNotFoundTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.buscarCapitaoDoTime(1L);
	}
	
	@Test(expected = CapitaoNaoInformadoException.class)
	public void testBuscarCapitaoDoTimeWithNotFoundCaptain() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime(1L, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(20L, 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.buscarCapitaoDoTime(1L);
	}
	
	@Test
	public void testBuscarNomeJogadorWithSuccessWithOneTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		String playerName = application.buscarNomeJogador(new Long(20));
		
		assertEquals("Maicon", playerName);
	}
	
	@Test
	public void testBuscarNomeJogadorWithSuccessWithTwoTeams() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 4, new BigDecimal(150000));
		
		String playerName = application.buscarNomeJogador(new Long(15));
		
		assertEquals("Danilo", playerName);
	}
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void testBuscarNomeJogadorWithNotFoundPlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 4, new BigDecimal(150000));
		
		application.buscarNomeJogador(new Long(100));
	}
	
	@Test
	public void testBuscarNomeTimeWithSuccess() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		String teamName = application.buscarNomeTime(new Long(1));
		
		assertEquals("Grêmio", teamName);
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarNomeTimeWithNotFoundTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		application.buscarNomeTime(new Long(10));
	}
	
	@Test
	public void testBuscarJogadoresDoTimeWithSuccess() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		List<Long> players = application.buscarJogadoresDoTime(new Long(1));
		assertEquals(2, players.size());
		assertEquals(10, players.get(0).intValue());
		assertEquals(20, players.get(1).intValue());
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarJogadoresDoTimeWithNotFoundTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.buscarJogadoresDoTime(new Long(10));
	}
	
	@Test
	public void testBuscarMelhorJogadorDoTimeWithSuccess() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarMelhorJogadorDoTime(new Long(1));
		assertEquals(10, bestPlayerId.intValue());
	}
	
	@Test
	public void testBuscarMelhorJogadorDoTimeWithSuccessAndSameSkill() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(5), 1L, "Maicon", LocalDate.of(1985, 9, 14), 9, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarMelhorJogadorDoTime(new Long(1));
		assertEquals(5, bestPlayerId.intValue());
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarMelhorJogadorDoTimeWithNotFoundTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		application.buscarMelhorJogadorDoTime(new Long(10));
	}
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void testBuscarMelhorJogadorDoTimeWithNotFoundPlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		application.buscarMelhorJogadorDoTime(new Long(1));
	}
	
	@Test
	public void testBuscarJogadorMaisVelhoDoTimeWithSuccess() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarJogadorMaisVelho(new Long(1));
		assertEquals(20, bestPlayerId.intValue());
	}
	
	@Test
	public void testBuscarJogadorMaisVelhoDoTimeWithSuccessWith2PlayersBornInSameYear() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 1, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1985, 3, 22), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarJogadorMaisVelho(new Long(1));
		assertEquals(20, bestPlayerId.intValue());
	}
	
	@Test
	public void testBuscarJogadorMaisVelhoDoTimeWithSuccessWith2PlayersBornInSameYearAndMonth() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 3, 30), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1985, 3, 22), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarJogadorMaisVelho(new Long(1));
		assertEquals(30, bestPlayerId.intValue());
	}
	
	@Test
	public void testBuscarJogadorMaisVelhoDoTimeWithSuccessWith2PlayersBornInSameYearAndMonthAndDay() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L,  "Maicon", LocalDate.of(1985, 3, 30), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L,  "Everton", LocalDate.of(1985, 3, 30), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarJogadorMaisVelho(new Long(1));
		assertEquals(20, bestPlayerId.intValue());
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarJogadorMaisVelhoWithNotFoundTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		application.buscarJogadorMaisVelho(new Long(10));
	}
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void testBuscarJogadorMaisVelhoWithNotFoundPlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		application.buscarJogadorMaisVelho(new Long(1));
	}
	
	@Test
	public void testBuscarTimes() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 2, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		application.incluirTime((long) 1, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		
		List<Long> teamsIds = application.buscarTimes();
		
		assertEquals(2, teamsIds.size());
		assertEquals(1, teamsIds.get(0).intValue());
		assertEquals(2, teamsIds.get(1).intValue());
	}
	
	@Test
	public void testBuscarTimesWithNoTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		List<Long> teamsIds = application.buscarTimes();
		
		assertEquals(0, teamsIds.size());
	}
	
	@Test
	public void testBuscarJogadorMaiorSalarioWithSuccess() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(1000000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarJogadorMaiorSalario(new Long(1));
		assertEquals(20, bestPlayerId.intValue());
	}
	
	@Test
	public void testBuscarJogadorMaiorSalarioWithSuccessAndSameSalart() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(700000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		Long bestPlayerId = application.buscarJogadorMaiorSalario(new Long(1));
		assertEquals(10, bestPlayerId.intValue());
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarJogadorMaiorSalarioWithNotFoundTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(1000000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		
		application.buscarJogadorMaiorSalario(new Long(10));
	}
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void testBuscarJogadorMaiorSalarioWithNotFoundPlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		application.buscarJogadorMaiorSalario(new Long(1));
	}
	
	@Test
	public void testBuscarSalarioDoJogadorWithSuccessWithOneTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		BigDecimal playerSalary = application.buscarSalarioDoJogador(new Long(20));
		
		assertEquals(new BigDecimal(300000), playerSalary);
	}
	
	@Test
	public void testBuscarSalarioDoJogadorWithSuccessWithTwoTeams() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 4, new BigDecimal(150000));
		
		BigDecimal playerSalary = application.buscarSalarioDoJogador(new Long(15));
		
		assertEquals(new BigDecimal(150000), playerSalary);
	}
	
	@Test(expected = JogadorNaoEncontradoException.class)
	public void testBuscarSalarioDoJogadorWithNotFoundPlayer() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 4, new BigDecimal(150000));
		
		application.buscarSalarioDoJogador(new Long(100));
	}
	
	@Test
	public void testBuscarTopJogadores() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		application.incluirJogador(new Long(40), 1L, "Bressan", LocalDate.of(1993, 1, 15), 1, new BigDecimal(10));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 4, new BigDecimal(150000));

		List<Long> bestPlayersIds = application.buscarTopJogadores(5);
		assertEquals(5, bestPlayersIds.size());
		assertEquals(10, bestPlayersIds.get(0).intValue());
		assertEquals(30, bestPlayersIds.get(1).intValue());
		assertEquals(20, bestPlayersIds.get(2).intValue());
		assertEquals(15, bestPlayersIds.get(3).intValue());
		assertEquals(40, bestPlayersIds.get(4).intValue());
	}
	
	@Test
	public void testBuscarTopJogadoresWithPlayersWithSameSkill() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(100), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		application.incluirJogador(new Long(40), 1L, "Bressan", LocalDate.of(1993, 1, 15), 1, new BigDecimal(10));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 9, new BigDecimal(150000));

		List<Long> bestPlayersIds = application.buscarTopJogadores(3);
		assertEquals(3, bestPlayersIds.size());
		assertEquals(15, bestPlayersIds.get(0).intValue());
		assertEquals(100, bestPlayersIds.get(1).intValue());
		assertEquals(30, bestPlayersIds.get(2).intValue());
	}
	
	@Test
	public void testBuscarTopJogadoresWithAllPlayersWithSameSkill() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(100), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 9, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 9, new BigDecimal(500000));
		application.incluirJogador(new Long(40), 1L, "Bressan", LocalDate.of(1993, 1, 15), 9, new BigDecimal(10));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 9, new BigDecimal(150000));
		
		List<Long> bestPlayersIds = application.buscarTopJogadores(3);
		assertEquals(3, bestPlayersIds.size());
		assertEquals(15, bestPlayersIds.get(0).intValue());
		assertEquals(20, bestPlayersIds.get(1).intValue());
		assertEquals(30, bestPlayersIds.get(2).intValue());
	}
	
	@Test
	public void testBuscarTopJogadoresWithWithThreeTeamAndSomePlayersWithSameSkill() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(100), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		application.incluirJogador(new Long(40), 1L, "Bressan", LocalDate.of(1993, 1, 15), 1, new BigDecimal(10));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L,"Danilo", LocalDate.of(1988, 4, 3), 9, new BigDecimal(150000));
		
		application.incluirTime((long) 3, "São Paulo", LocalDate.of(1930, 1, 25), "Branco", "Vermelho");
		application.incluirJogador(new Long(5), 3L, "Hernanes", LocalDate.of(1985, 5, 29), 9, new BigDecimal(1000000));
		
		List<Long> bestPlayersIds = application.buscarTopJogadores(3);
		assertEquals(3, bestPlayersIds.size());
		assertEquals(5, bestPlayersIds.get(0).intValue());
		assertEquals(15, bestPlayersIds.get(1).intValue());
		assertEquals(100, bestPlayersIds.get(2).intValue());
	}
	
	@Test
	public void testBuscarTopJogadoresWithTopGreaterThenPlayersSize() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		application.incluirJogador(new Long(40), 1L, "Bressan", LocalDate.of(1993, 1, 15), 1, new BigDecimal(10));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 4, new BigDecimal(150000));
		
		List<Long> bestPlayersIds = application.buscarTopJogadores(10);
		assertEquals(5, bestPlayersIds.size());
		assertEquals(10, bestPlayersIds.get(0).intValue());
		assertEquals(30, bestPlayersIds.get(1).intValue());
		assertEquals(20, bestPlayersIds.get(2).intValue());
		assertEquals(15, bestPlayersIds.get(3).intValue());
		assertEquals(40, bestPlayersIds.get(4).intValue());
	}
	
	@Test
	public void testBuscarTopJogadoresWithTopSmallerThenPlayersSize() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		application.incluirJogador(new Long(10), 1L, "Luan", LocalDate.of(1993, 3, 27), 9, new BigDecimal(700000));
		application.incluirJogador(new Long(20), 1L, "Maicon", LocalDate.of(1985, 9, 14), 7, new BigDecimal(300000));
		application.incluirJogador(new Long(30), 1L, "Everton", LocalDate.of(1996, 3, 22), 8, new BigDecimal(500000));
		application.incluirJogador(new Long(40), 1L, "Bressan", LocalDate.of(1993, 1, 15), 1, new BigDecimal(10));
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");
		application.incluirJogador(new Long(15), 2L, "Danilo", LocalDate.of(1988, 4, 3), 4, new BigDecimal(150000));
		
		List<Long> bestPlayersIds = application.buscarTopJogadores(2);
		assertEquals(2, bestPlayersIds.size());
		assertEquals(10, bestPlayersIds.get(0).intValue());
		assertEquals(30, bestPlayersIds.get(1).intValue());
	}
	
	@Test
	public void testBuscarTopJogadoresWithNoPlayers() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Branco");
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");

		List<Long> bestPlayersIds = application.buscarTopJogadores(2);
		assertEquals(0, bestPlayersIds.size());
	}
	
	@Test
	public void testBuscarCorCamisaTimeDeForaWithSamePrimaryUniformColor() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Preto");
		
		application.incluirTime((long) 2, "Avai", LocalDate.of(1923, 9, 1), "aZuL", "Branco");

		String awayUniformColor = application.buscarCorCamisaTimeDeFora(new Long(1), new Long(2));
		assertEquals("Branco", awayUniformColor);
	}
	
	@Test
	public void testBuscarCorCamisaTimeDeForaWithDifferentPrimaryUniformColor() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Preto");
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");

		String awayUniformColor = application.buscarCorCamisaTimeDeFora(new Long(1), new Long(2));
		assertEquals("Vermelho", awayUniformColor);
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarCorCamisaTimeDeForaWithNotFoundHomeTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 2, "Inter", LocalDate.of(1909, 4, 4), "Vermelho", "Branco");

		application.buscarCorCamisaTimeDeFora(new Long(1), new Long(2));
	}
	
	@Test(expected = TimeNaoEncontradoException.class)
	public void testBuscarCorCamisaTimeDeForaWithNotFoundAwayTeam() {
		DesafioMeuTimeApplication application = new DesafioMeuTimeApplication();
		
		application.incluirTime((long) 1, "Grêmio", LocalDate.of(1903, 9, 15), "Azul", "Preto");
		
		application.buscarCorCamisaTimeDeFora(new Long(1), new Long(2));
	}
}
