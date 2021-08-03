package br.com.alura.leilao.servico;

import br.com.alura.leilao.builder.CriadorDeLeilao;
import br.com.alura.leilao.dao.RepositorioDeLeiloes;
import br.com.alura.leilao.dao.RepositorioDePagamentos;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Pagamento;
import br.com.alura.leilao.dominio.Usuario;
import br.com.alura.leilao.infra.Relogio;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class GeradorDePagamentosTest {

    private RepositorioDePagamentos mockPagamentos;
    private RepositorioDeLeiloes mockLeiloes;
    private GeradorDePagamentos geradorDePagamentos;

    @Before
    public void methodSetup() {
        mockPagamentos = mock(RepositorioDePagamentos.class);
        mockLeiloes = mock(RepositorioDeLeiloes.class);
        geradorDePagamentos = new GeradorDePagamentos(mockLeiloes, mockPagamentos, new Avaliador());
    }

    @Test
    public void deveGerarPagamentoParaUmLeilaoEncerrado() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 5")
                .lance(new Usuario("Jose Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        when(mockLeiloes.encerrados()).thenReturn(List.of(leilao));

        geradorDePagamentos.gera();

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
        verify(mockPagamentos).salva(argumento.capture());

        Pagamento pagamento = argumento.getValue();

        assertThat(pagamento.getValor(), equalTo(2500.00));
    }

    @Test
    public void deveEmpurrarParaProximoDiaUtil() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 5")
                .lance(new Usuario("Jose Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        Relogio mockRelogio = mock(Relogio.class);
        GeradorDePagamentos gerador = new GeradorDePagamentos(mockLeiloes, mockPagamentos, new Avaliador(), mockRelogio);

        Calendar sabado = Calendar.getInstance();
        sabado.set(2012, Calendar.APRIL, 8);
        when(mockRelogio.hoje()).thenReturn(sabado);

        when(mockLeiloes.encerrados()).thenReturn(List.of(leilao));

        gerador.gera();

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
        verify(mockPagamentos).salva(argumento.capture());



        Pagamento pagamento = argumento.getValue();

        assertThat(pagamento.getData().get(Calendar.DAY_OF_WEEK), equalTo(Calendar.MONDAY));
        assertThat(pagamento.getData().get(Calendar.DAY_OF_MONTH), equalTo(9));
    }
}
