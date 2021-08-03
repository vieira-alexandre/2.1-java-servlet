package br.com.alura.leilao.servico;

import br.com.alura.leilao.builder.CriadorDeLeilao;
import br.com.alura.leilao.dao.RepositorioDeLeiloes;
import br.com.alura.leilao.dao.RepositorioDePagamentos;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Pagamento;
import br.com.alura.leilao.dominio.Usuario;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class GeradorDePagamentosTest {

    @Test
    public void deveGerarPagamentoParaUmLeilaoEncerrado() {
        RepositorioDePagamentos mockPagamentos = mock(RepositorioDePagamentos.class);
        RepositorioDeLeiloes mockLeiloes = mock(RepositorioDeLeiloes.class);
        Avaliador avaliador = new Avaliador();

        Leilao leilao = new CriadorDeLeilao().para("Playstation 5")
                .lance(new Usuario("Jose Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        when(mockLeiloes.encerrados()).thenReturn(List.of(leilao));
       
        GeradorDePagamentos geradorDePagamentos = new GeradorDePagamentos(mockLeiloes, mockPagamentos, avaliador);
        geradorDePagamentos.gera();

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
        verify(mockPagamentos).salva(argumento.capture());

        Pagamento pagamento = argumento.getValue();

        assertThat(pagamento.getValor(), equalTo(2500.00));
    }
}
