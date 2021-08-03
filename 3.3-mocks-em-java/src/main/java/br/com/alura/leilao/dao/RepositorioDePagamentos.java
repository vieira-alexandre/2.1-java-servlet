package br.com.alura.leilao.dao;

import br.com.alura.leilao.dominio.Pagamento;

public interface RepositorioDePagamentos {
    void salva(Pagamento pagamento);
}
