package br.com.alura.leilao.servico;

import br.com.alura.leilao.dao.RepositorioDeLeiloes;
import br.com.alura.leilao.dao.RepositorioDePagamentos;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Pagamento;

import java.util.Calendar;
import java.util.List;

public class GeradorDePagamentos {

    private final RepositorioDeLeiloes repositorioDeLeiloes;
    private final RepositorioDePagamentos repositorioDePagamentos;
    private final Avaliador avaliador;


    public GeradorDePagamentos(RepositorioDeLeiloes repositorioDeLeiloes, RepositorioDePagamentos repositorioDePagamentos, Avaliador avaliador) {
        this.repositorioDeLeiloes = repositorioDeLeiloes;
        this.repositorioDePagamentos = repositorioDePagamentos;
        this.avaliador = avaliador;
    }


    public void gera() {
        List<Leilao> leiloesEncerrados = repositorioDeLeiloes.encerrados();

        for(Leilao leilao : leiloesEncerrados) {
            this.avaliador.avalia(leilao);

            Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), Calendar.getInstance());
            this.repositorioDePagamentos.salva(novoPagamento);
        }
    }
}
