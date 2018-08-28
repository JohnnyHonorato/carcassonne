package br.ufpb.dcx.aps.carcassone;

import java.util.ArrayList;

import br.ufpb.dcx.aps.carcassone.tabuleiro.TabuleiroFlexivel;
import br.ufpb.dcx.aps.carcassone.tabuleiro.Tile;

public class Partida {

	private BolsaDeTiles tiles;
	private Tile proximoTile;
	private TabuleiroFlexivel tabuleiro = new TabuleiroFlexivel("  ");
	private String status;
	private ArrayList<Jogador> jogadores;
	private int vezDoJogador = 0;
	private Turno turno;
	private ArrayList<Turno> turnos = new ArrayList<Turno>();

	Partida(BolsaDeTiles tiles, Cor... sequencia) {
		status = "Em_Andamento";
		jogadores = iniciaJogadores(sequencia);
		this.tiles = tiles;
		pegarProximoTile();
		turno = new Turno(proximoTile, jogadores.get(vezDoJogador), "Tile_Posicionado");	
		turnos.add(turno);
	}

	public ArrayList<Jogador> iniciaJogadores(Cor... sequencia) {
		ArrayList<Jogador> temporarioJogadores = new ArrayList<Jogador>();
		for (Cor cor : sequencia) {
			temporarioJogadores.add(new Jogador(cor.name()));
		}
		return temporarioJogadores;
	}

	public String relatorioPartida() {
		String oxi = "";
		for (Jogador j : jogadores) {
			if (j.equals(jogadores.get(jogadores.size() - 1))) {
				oxi = oxi + j.getCor() + "(" + j.getPontuacao() + "," + j.getMeeples() + ")";
			} else {
				oxi = oxi + j.getCor() + "(" + j.getPontuacao() + "," + j.getMeeples() + "); ";
			}
		}
		return "Status: " + status + "\nJogadores: " + oxi;
	}

	public String relatorioTurno() {
		if(status.equals("Partida_Finalizada")) throw new ExcecaoJogo("Partida finalizada");

		return "Jogador: " + turno.getJogador().getCor() + "\nTile: " + proximoTile + "\nStatus: " + turno.getStatus();
	}
	public String relatorioTabuleiro( String configuracao) {
		return configuracao;
	}

	public Partida girarTile() {
		if(status.equals("Tile_Posicionado")){
			throw new ExcecaoJogo("Não pode girar tile já posicionado");
		}
		if(status.equals("Partida_Finalizada")) {
			throw new ExcecaoJogo("Não pode girar tiles com a partida finalizada");
		}
		proximoTile.girar();
		return this;
	}

	private void pegarProximoTile() {
		proximoTile = tiles.pegar();
		proximoTile.reset();
	}

	public Partida finalizarTurno() {
		turno.setStatus("Finalizado");
		if(tiles.pegar() == null) {
			this.status = "Partida_Finalizada";
		}else {
			pegarProximoTile();
			this.turno = new Turno(proximoTile, jogadores.get(vezDoJogador++), "Início_Turno");
			turnos.add(turno);
		}
		return this;
	}

	public Partida posicionarTile(Tile tileReferencia, Lado ladoTileReferencia) {
		tabuleiro.posicionar(tileReferencia, ladoTileReferencia, proximoTile);
		return this;
	}

	public Partida posicionarMeepleEstrada(Lado lado) {
		return this;
	}

	public Partida posicionarMeepleCampo(Vertice vertice) {
		return this;
	}

	public Partida posicionarMeepleCidade(Lado lado) {
		return this;
	}

	public Partida posicionarMeepleMosteiro() {
		return this;
	}

	public String getEstradas() {
		return null;
	}

	public String getCampos() {
		return null;
	}

	public String getCidades() {
		return null;
	}

	public String getMosteiros() {
		return null;
	}


}
