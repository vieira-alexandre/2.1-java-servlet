package br.com.alura.leilao.servico;

import br.com.alura.leilao.builder.CriadorDeLeilao;
import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.dao.RepositorioDeLeiloes;
import br.com.alura.leilao.dominio.Leilao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmSemanaAntes() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes mock = mock(LeilaoDao.class);
        when(mock.correntes()).thenReturn(leiloesAntigos);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mock);
        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(2));
        assertThat(leilao1.isEncerrado(), equalTo(true));
        assertThat(leilao2.isEncerrado(), equalTo(true));
    }

    @Test
    public void naoDeveEncerrarLeiloesQueComecaramOntem() {
        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(ontem).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        RepositorioDeLeiloes mock = mock(LeilaoDao.class);
        when(mock.correntes()).thenReturn(leiloesAntigos);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mock);
        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(0));
        assertThat(leilao1.isEncerrado(), equalTo(false));
        assertThat(leilao2.isEncerrado(), equalTo(false));
    }

    @Test
    public void naoDeveFazerNadaSeNaoHouverLeilao() {

        RepositorioDeLeiloes mock = mock(LeilaoDao.class);
        when(mock.correntes()).thenReturn(new ArrayList<Leilao>());

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(mock);
        encerrador.encerra();

        assertThat(encerrador.getTotalEncerrados(), equalTo(0));
    }
}
