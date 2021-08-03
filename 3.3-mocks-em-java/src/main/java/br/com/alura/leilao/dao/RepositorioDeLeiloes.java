package br.com.alura.leilao.dao;

import br.com.alura.leilao.dominio.Leilao;

import java.util.List;

public interface RepositorioDeLeiloes {
    void salva(Leilao leilao);

    List<Leilao> encerrados();

    List<Leilao> correntes();

    void atualiza(Leilao leilao);
}
