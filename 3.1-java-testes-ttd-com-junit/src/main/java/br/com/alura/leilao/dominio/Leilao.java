package br.com.alura.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Leilao {

	private String descricao;
	private List<Lance> lances;
	private final long limiteLances = 5;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}
	
	public void propoe(Lance lance) {
		Usuario usuario = lance.getUsuario();

		if(atingiuLimiteDeLances(usuario) || usuarioDeuUltimoLance(usuario))
			return;

		lances.add(lance);
	}


	public void dobraLance(Usuario usuario) {
		List<Lance> lancesDoUsuario = lances.stream().filter(x -> x.getUsuario().equals(usuario))
				.collect(Collectors.toList());

		if(lancesDoUsuario.isEmpty())
			return;

		double valorUltimo = lancesDoUsuario.get(lancesDoUsuario.size() - 1).getValor();
		propoe(new Lance(usuario, valorUltimo * 2));
	}

	private boolean usuarioDeuUltimoLance(Usuario usuario) {
		if(lances.isEmpty())
			return false;

		int ultimoIndice = lances.size() -1;
		Usuario usuarioUltimoLance = lances.get(ultimoIndice).getUsuario();

		return usuario.equals(usuarioUltimoLance);
	}

	private boolean atingiuLimiteDeLances(Usuario usuario) {
		long nLances = lances.stream().filter(x -> x.getUsuario().equals(usuario)).count();

		return  nLances == limiteLances;
	}

	public String getDescricao() {
		return descricao;
	}

	public long getLimiteLances() {
		return limiteLances;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}
}
