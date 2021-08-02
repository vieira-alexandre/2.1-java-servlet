package br.com.alura.leilao.dominio;

import java.util.Objects;

public class Lance {

	private Usuario usuario;
	private double valor;
	
	public Lance(Usuario usuario, double valor) {
		if(valor <= 0)
			throw new IllegalArgumentException("Lance não poder ser zero");

		this.usuario = usuario;
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public double getValor() {
		return valor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Lance lance = (Lance) o;
		return Double.compare(lance.valor, valor) == 0 && Objects.equals(usuario, lance.usuario);
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario, valor);
	}

	@Override
	public String toString() {
		return "Lance{" +
				"usuario=" + usuario +
				", valor=" + valor +
				'}';
	}
}
