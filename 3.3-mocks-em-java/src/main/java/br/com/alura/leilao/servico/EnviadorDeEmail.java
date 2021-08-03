package br.com.alura.leilao.servico;

import br.com.alura.leilao.dominio.Leilao;

public interface EnviadorDeEmail {
    void envia(Leilao leilao);
}