package br.com.alura.leilao.dominio;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.desafios.MyMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    private Usuario jobs;
    private Usuario woz;
    private Usuario sculley;

    @Before
    public void setUp() {
        this.jobs = new Usuario(1, "Steve Jobs");
        this.woz = new Usuario(2, "Steve Wozniak");
        this.sculley = new Usuario(3, "John Sculley");
    }


    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15").build();
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(jobs, 2000));
        assertThat(leilao.getLances().size(), equalTo(1));
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15")
                .lance(jobs, 2000)
                .lance(woz, 4000)
                .lance(sculley, 3000)
                .build();


        List<Lance> lista = leilao.getLances();

        assertThat(lista.size(), equalTo(3));
        assertThat(lista.get(0).getValor(), equalTo(2000.0));
        assertThat(lista.get(1).getValor(), equalTo(4000.0));
        assertThat(lista.get(2).getValor(), equalTo(3000.0));
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15")
                .lance(jobs, 2000)
                .lance(sculley, 3000)
                .lance(woz, 4000)
                .lance(woz, 5000)
                .build();

        List<Lance> lista = leilao.getLances();

        assertThat(lista.size(), equalTo(3));
        assertThat(lista.get(0).getValor(), equalTo(2000.0));
        assertThat(lista.get(1).getValor(), equalTo(3000.0));
        assertThat(lista.get(2).getValor(), equalTo(4000.0));
    }

    @Test
    public void naoDeveAceitarMaisLancesQueOLimiteParaMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        long limiteDeLances = leilao.getLimiteLances();
        double ultimoLance = 0;
        int nLances = 0;


        for (int i = 1; i <= limiteDeLances * 2; i++) {
            double valorLance = i * 1000;
            Lance lance = new Lance(i % 2 == 0 ? woz : jobs, valorLance);
            leilao.propoe(lance);
            ultimoLance = valorLance;
            nLances++;
        }


        //devem ser ignorados
        leilao.propoe(new Lance(jobs, ultimoLance + 1000));
        leilao.propoe(new Lance(jobs, ultimoLance + 2000));

        List<Lance> lista = leilao.getLances();

        assertThat(lista.size(), equalTo(nLances));
        assertThat(lista.get(lista.size()-1).getValor(), equalTo(ultimoLance));
    }

    @Test
    public void deveDobrarUltimoLanceDoUsuario() {
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15")
                .lance(jobs, 500)
                .lance(woz, 1000)
                .lance(jobs, 1500)
                .lance(woz, 2000)
                .lance(jobs, 3000)
                .build();

        leilao.dobraLance(woz);

        List<Lance> lista = leilao.getLances();

        assertThat(lista.size(), equalTo(6));
        assertThat(lista.get(5).getValor(), equalTo(4000.00));
    }

    @Test
    public void naoDeveDobrarSeNaoHouverLanceAnteriorDoUsuario() {
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15")
                .lance(jobs, 500)
                .lance(woz, 1000)
                .lance(jobs, 1500)
                .lance(woz, 2000)
                .build();

        leilao.dobraLance(sculley);

        List<Lance> lista = leilao.getLances();

        assertThat(lista.size(), equalTo(4));
        assertThat(lista.get(3).getValor(), equalTo(2000.00));
    }

    @Test
    public void testeSeTemUmLance() {
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15")
                .lance(jobs, 500)
                .lance(woz, 1000)
                .lance(jobs, 1500)
                .lance(woz, 2000)
                .build();

        assertThat(leilao, MyMatcher.temUmLance(new Lance(jobs, 500)));
    }
}
