package br.com.alura.leilao.servico;

import br.com.alura.leilao.dao.RepositorioDeLeiloes;
import br.com.alura.leilao.dao.RepositorioDePagamentos;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Pagamento;
import br.com.alura.leilao.infra.Relogio;
import br.com.alura.leilao.infra.RelogioDoSistema;

import java.util.Calendar;
import java.util.List;

public class GeradorDePagamentos {

    private final RepositorioDeLeiloes repositorioDeLeiloes;
    private final RepositorioDePagamentos repositorioDePagamentos;
    private final Avaliador avaliador;
    private final Relogio relogio;


    public GeradorDePagamentos(RepositorioDeLeiloes repositorioDeLeiloes, RepositorioDePagamentos repositorioDePagamentos, Avaliador avaliador) {
        this(repositorioDeLeiloes, repositorioDePagamentos, avaliador, new RelogioDoSistema());
    }

    public GeradorDePagamentos(RepositorioDeLeiloes repositorioDeLeiloes, RepositorioDePagamentos repositorioDePagamentos, Avaliador avaliador, Relogio relogio) {
        this.repositorioDeLeiloes = repositorioDeLeiloes;
        this.repositorioDePagamentos = repositorioDePagamentos;
        this.avaliador = avaliador;
        this.relogio = relogio;
    }


    public void gera() {
        List<Leilao> leiloesEncerrados = repositorioDeLeiloes.encerrados();

        for(Leilao leilao : leiloesEncerrados) {
            this.avaliador.avalia(leilao);

            Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), primeiroDiaUtil());
            this.repositorioDePagamentos.salva(novoPagamento);
        }
    }

    private Calendar primeiroDiaUtil() {
        Calendar data = relogio.hoje();
        int diaDaSemana = data.get(Calendar.DAY_OF_WEEK);

        if(diaDaSemana == Calendar.SATURDAY) data.add(Calendar.DAY_OF_MONTH, 2);
        else if(diaDaSemana == Calendar.SUNDAY) data.add(Calendar.DAY_OF_MONTH, 1);

        return data;
    }
}
