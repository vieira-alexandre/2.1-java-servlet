package br.com.alura.leilao.servico;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AvaliadorTest {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void setUp() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario(1, "Joao");
        this.jose = new Usuario(2, "José");
        this.maria = new Usuario(3, "Maria");
    }

    @Test
    public void deveVerificarLanceEmOrdemCrescente() {
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
                .lance(maria, 450)
                .lance(joao, 500)
                .lance(jose, 550)
                .build();

        leiloeiro.avalia(leilao);

        double menorEsperado = 450;
        double maiorEsperado = 550;

        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.000001);
        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.000001);
    }

    @Test
    public void deveVerificarLanceEmOrdemAleatoria() {
        double lance1 = 200;
        double lance2 = 450;
        double lance3 = 120;
        double lance4 = 700;
        double lance5 = 630;
        double lance6 = 230;

        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
                .lance(maria, lance1)
                .lance(joao, lance2)
                .lance(jose, lance3)
                .lance(joao, lance4)
                .lance(jose, lance5)
                .lance(maria, lance6)
                .build();

        leiloeiro.avalia(leilao);


        assertEquals(lance3, leiloeiro.getMenorLance(), 0.00001);
        assertEquals(lance4, leiloeiro.getMaiorLance(), 0.00001);
    }

    @Test
    public void deveVerificarLanceEmOrdemDecrescente() {
        double lance1 = 400;
        double lance2 = 300;
        double lance3 = 200;
        double lance4 = 100;

        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
                .lance(maria, lance1)
                .lance(joao, lance2)
                .lance(jose, lance3)
                .lance(joao, lance4)
                .build();

        leiloeiro.avalia(leilao);

        assertEquals(lance4, leiloeiro.getMenorLance(), 0.00001);
        assertEquals(lance1, leiloeiro.getMaiorLance(), 0.00001);
    }

    @Test
    public void deveVerificarLeilaoComApenasUmLance() {
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
                .lance(joao, 1000)
                .build();

        leiloeiro.avalia(leilao);

        double menorEsperado = 1000;
        double maiorEsperado = 1000;

        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.000001);
        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.000001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        double lance1 = 450;
        double lance2 = 500;
        double lance3 = 600;
        double lance4 = 700;

        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
                .lance(maria, lance1)
                .lance(joao, lance2)
                .lance(maria, lance3)
                .lance(joao, lance4)
                .build();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(lance4, maiores.get(0).getValor(), 0.00001);
        assertEquals(lance3, maiores.get(1).getValor(), 0.00001);
        assertEquals(lance2, maiores.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLancesEmOrdemCrescenteAleatoria() {
        double lance1 = 200;
        double lance2 = 450;
        double lance3 = 120;
        double lance4 = 700;
        double lance5 = 630;
        double lance6 = 230;

        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
                .lance(maria, lance1)
                .lance(joao, lance2)
                .lance(jose, lance3)
                .lance(joao, lance4)
                .lance(jose, lance5)
                .lance(maria, lance6)
                .build();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(lance4, maiores.get(0).getValor(), 0.00001);
        assertEquals(lance5, maiores.get(1).getValor(), 0.00001);
        assertEquals(lance2, maiores.get(2).getValor(), 0.00001);
    }

}
