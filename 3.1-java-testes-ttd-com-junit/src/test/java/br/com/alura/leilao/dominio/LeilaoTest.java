package br.com.alura.leilao.dominio;

import br.com.alura.leilao.builder.LeilaoBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        assertEquals(1, leilao.getLances().size());
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15")
                .lance(jobs, 2000)
                .lance(woz, 4000)
                .lance(sculley, 3000)
                .build();


        List<Lance> lista = leilao.getLances();

        assertEquals(3, lista.size());
        assertEquals(2000.0, lista.get(0).getValor(), 0.00001);
        assertEquals(4000.0, lista.get(1).getValor(), 0.00001);
        assertEquals(3000.0, lista.get(2).getValor(), 0.00001);
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

        assertEquals(3, lista.size());
        assertEquals(2000.0, lista.get(0).getValor(), 0.00001);
        assertEquals(3000.0, lista.get(1).getValor(), 0.00001);
        assertEquals(4000.0, lista.get(2).getValor(), 0.00001);
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

        assertEquals(nLances, lista.size());
        assertEquals(ultimoLance, lista.get(lista.size() - 1).getValor(), 0.00001);
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

        assertEquals(6, lista.size());
        assertEquals(4000.0, lista.get(5).getValor(), 0.00001);
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

        assertEquals(4, lista.size());
        assertEquals(2000.0, lista.get(3).getValor(), 0.00001);
    }

}
