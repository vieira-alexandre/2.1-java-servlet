package br.com.alura.leilao.servico;

import br.com.alura.leilao.builder.CriadorDeLeilao;
import br.com.alura.leilao.dao.RepositorioDeLeiloes;
import br.com.alura.leilao.dominio.Leilao;
import org.junit.Before;
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


    private RepositorioDeLeiloes mockRepository;
    private EnviadorDeEmail mockMailSender;
    private EncerradorDeLeilao encerrador;

    @Before
    public void methodSetup() {
        mockRepository = mock(RepositorioDeLeiloes.class);
        mockMailSender = mock(EnviadorDeEmail.class);
        encerrador = new EncerradorDeLeilao(mockRepository, mockMailSender);
    }


    @Test
    public void deveEncerrarLeiloesQueComecaramUmSemanaAntes() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        when(mockRepository.correntes()).thenReturn(leiloesAntigos);
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

        when(mockRepository.correntes()).thenReturn(leiloesAntigos);

        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(0));
        assertThat(leilao1.isEncerrado(), equalTo(false));
        assertThat(leilao2.isEncerrado(), equalTo(false));
    }

    @Test
    public void naoDeveFazerNadaSeNaoHouverLeilao() {

        when(mockRepository.correntes()).thenReturn(new ArrayList<Leilao>());

        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(0));
    }

    @Test
    public void deveAtualizarLeiloesEncerrados() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();

        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1));

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

        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

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

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(ontem).constroi();

        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

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

    @Test
    public void deveContinuarExecucaoQuandoDaoFalha() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();

        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        doThrow(new RuntimeException()).when(mockRepository).atualiza(leilao1);

        encerrador.encerra();

        verify(mockRepository).atualiza(leilao1);
        verify(mockMailSender, never()).envia(leilao1);

        verify(mockRepository).atualiza(leilao2);
        verify(mockMailSender).envia(leilao2);
    }

    @Test
    public void deveContinuarExecucaoQuandoEnviadorDeEmailsFalha() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();

        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        doThrow(new RuntimeException()).when(mockMailSender).envia(leilao1);

        encerrador.encerra();

        verify(mockRepository).atualiza(leilao2);
        verify(mockMailSender).envia(leilao2);
    }

    @Test
    public void deveDesistirSeDaoFalhaPraSempre() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();

        when(mockRepository.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        doThrow(new RuntimeException()).when(mockRepository).atualiza(any(Leilao.class));

        encerrador.encerra();

        verify(mockMailSender, never()).envia(any(Leilao.class));
    }
}
