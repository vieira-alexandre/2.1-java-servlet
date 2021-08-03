package br.com.alura.leilao.servico;

import br.com.alura.leilao.builder.CriadorDeLeilao;
import br.com.alura.leilao.dao.RepositorioDeLeiloes;
import br.com.alura.leilao.dominio.Leilao;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmSemanaAntes() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes mockRepository = mock(RepositorioDeLeiloes.class);
        when(mockRepository.correntes()).thenReturn(leiloesAntigos);

        EnviadorDeEmail mockMailSender = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mockRepository, mockMailSender);
        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(2));
        assertThat(leilao1.isEncerrado(), equalTo(true));
        assertThat(leilao2.isEncerrado(), equalTo(true));
        verify(mockRepository, times(1)).atualiza(leilao1);
        verify(mockRepository, times(1)).atualiza(leilao2);
    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramOntem() {
        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(ontem).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes mockRepository = mock(RepositorioDeLeiloes.class);
        when(mockRepository.correntes()).thenReturn(leiloesAntigos);

        EnviadorDeEmail mockMailSender = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mockRepository, mockMailSender);
        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(0));
        assertThat(leilao1.isEncerrado(), equalTo(false));
        assertThat(leilao2.isEncerrado(), equalTo(false));
    }

    @Test
    public void naoDeveFazerNadaSeNaoHouverLeilao() {

        RepositorioDeLeiloes mockRepository = mock(RepositorioDeLeiloes.class);
        when(mockRepository.correntes()).thenReturn(new ArrayList<Leilao>());

        EnviadorDeEmail mockMailSender = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mockRepository, mockMailSender);
        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(0));
    }

    @Test
    public void deveAtualizarLeiloesEncerrados() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();

        RepositorioDeLeiloes mockRepository = mock(RepositorioDeLeiloes.class);
        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1));

        EnviadorDeEmail mockMailSender = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mockRepository, mockMailSender);
        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(1));
        assertThat(leilao1.isEncerrado(), equalTo(true));
        verify(mockRepository, times(1)).atualiza(leilao1);
        verify(mockMailSender, times(1)).envia(leilao1);
    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramMenosDeUmaSemanaAtras() {

        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(ontem).constroi();

        RepositorioDeLeiloes mockRepository = mock(RepositorioDeLeiloes.class);
        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

        EnviadorDeEmail mockMailSender = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mockRepository, mockMailSender);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());

        verify(mockRepository, never()).atualiza(leilao1);
        verify(mockRepository, never()).atualiza(leilao2);
        verify(mockMailSender, never()).envia(leilao1);
        verify(mockMailSender, never()).envia(leilao2);
    }

    @Test
    public void deveEnviarEmailAposPersistirLeilaoEncerrado() {

        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -7);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(ontem).constroi();

        RepositorioDeLeiloes mockRepository = mock(RepositorioDeLeiloes.class);
        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

        EnviadorDeEmail mockMailSender = mock(EnviadorDeEmail.class);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mockRepository, mockMailSender);
        encerrador.encerra();

        InOrder inOrder = inOrder(mockRepository, mockMailSender);


        assertEquals(2, encerrador.getTotalEncerrados());
        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());

        inOrder.verify(mockRepository, times(1)).atualiza(leilao1);
        inOrder.verify(mockMailSender, times(1)).envia(leilao1);
        inOrder.verify(mockRepository, times(1)).atualiza(leilao2);
        inOrder.verify(mockMailSender, times(1)).envia(leilao2);
    }
}
