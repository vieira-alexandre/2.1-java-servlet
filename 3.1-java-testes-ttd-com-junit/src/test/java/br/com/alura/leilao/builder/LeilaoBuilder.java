package br.com.alura.leilao.builder;

import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Usuario;

public class LeilaoBuilder {
    private Leilao leilao;

    public LeilaoBuilder para(String descricao) {
        this.leilao = new Leilao(descricao);
        return this;
    }

    public LeilaoBuilder lance(Usuario usuario, double valor) {
        leilao.propoe(new Lance(usuario, valor));
        return this;
    }

    public Leilao build() {
        return this.leilao;
    }
}
