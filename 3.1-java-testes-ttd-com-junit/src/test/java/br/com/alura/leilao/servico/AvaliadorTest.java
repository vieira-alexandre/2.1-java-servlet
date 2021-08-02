package br.com.alura.leilao.servico;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

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

        assertThat(leiloeiro.getMenorLance(), equalTo(menorEsperado));
        assertThat(leiloeiro.getMaiorLance(), equalTo(maiorEsperado));
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

        assertThat(leiloeiro.getMenorLance(), equalTo(lance3));
        assertThat(leiloeiro.getMaiorLance(), equalTo(lance4));
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

        assertThat(leiloeiro.getMenorLance(), equalTo(lance4));
        assertThat(leiloeiro.getMaiorLance(), equalTo(lance1));
    }

    @Test
    public void deveVerificarLeilaoComApenasUmLance() {
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
                .lance(joao, 1000)
                .build();

        leiloeiro.avalia(leilao);

        double menorEsperado = 1000;
        double maiorEsperado = 1000;

        assertThat(leiloeiro.getMenorLance(), equalTo(menorEsperado));
        assertThat(leiloeiro.getMaiorLance(), equalTo(maiorEsperado));
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

        assertThat(maiores.size(), equalTo(3));
        assertThat(maiores, hasItems(
                new Lance(joao, lance2),
                new Lance(maria, lance3),
                new Lance(joao, lance4)
        ));
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

        assertThat(maiores.size(), equalTo(3));
        assertThat(maiores, hasItems(
                new Lance(joao, lance2),
                new Lance(joao, lance4),
                new Lance(jose, lance5)
        ));
    }

    @Test(expected = RuntimeException.class)
    public void naoDeveAvaliarUmLeilaoSemNenhumLance() {
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo").build();
        leiloeiro.avalia(leilao);
    }
}
