package br.com.alura.leilao.dominio;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario("Joao"), 2000));
        assertEquals(1, leilao.getLances().size());
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario(1, "Steve Jobs"), 2000));
        leilao.propoe(new Lance(new Usuario(2, "Steve Wozniak"), 4000));
        leilao.propoe(new Lance(new Usuario(3, "John Sculley"), 3000));

        List<Lance> lista = leilao.getLances();

        assertEquals(3, lista.size());
        assertEquals(2000.0, lista.get(0).getValor(), 0.00001);
        assertEquals(4000.0, lista.get(1).getValor(), 0.00001);
        assertEquals(3000.0, lista.get(2).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        Usuario jobs = new Usuario(1, "Steve Jobs");
        Usuario woz = new Usuario(2, "Steve Wozniak");
        Usuario sculley = new Usuario(3, "John Sculley");

        leilao.propoe(new Lance(jobs, 2000));
        leilao.propoe(new Lance(sculley, 3000));
        leilao.propoe(new Lance(woz, 4000));
        leilao.propoe(new Lance(woz, 5000));

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

        Usuario jobs = new Usuario(1, "Steve Jobs");
        Usuario woz = new Usuario(2, "Steve Wozniak");

        long limiteDeLances = leilao.getLimiteLances();
        double ultimoLance = 0;
        int nLances = 0;


        for(int i = 1; i <= limiteDeLances * 2; i++){
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
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        Usuario jobs = new Usuario(1, "Steve Jobs");
        Usuario woz = new Usuario(2, "Steve Wozniak");

        leilao.propoe(new Lance(jobs, 500));
        leilao.propoe(new Lance(woz, 1000));
        leilao.propoe(new Lance(jobs, 1500));
        leilao.propoe(new Lance(woz, 2000));
        leilao.propoe(new Lance(jobs, 3000));


        leilao.dobraLance(woz);

        List<Lance> lista = leilao.getLances();

        assertEquals(6, lista.size());
        assertEquals(4000.0, lista.get(5).getValor(), 0.00001);
    }

    @Test
    public void naoDeveDobrarSeNaoHouverLanceAnteriorDoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        Usuario jobs = new Usuario(1, "Steve Jobs");
        Usuario woz = new Usuario(2, "Steve Wozniak");
        Usuario sculley = new Usuario(3, "John Sculley");

        leilao.propoe(new Lance(jobs, 500));
        leilao.propoe(new Lance(woz, 1000));
        leilao.propoe(new Lance(jobs, 1500));
        leilao.propoe(new Lance(woz, 2000));


        leilao.dobraLance(sculley);

        List<Lance> lista = leilao.getLances();

        assertEquals(4, lista.size());
        assertEquals(2000.0, lista.get(3).getValor(), 0.00001);
    }

}
