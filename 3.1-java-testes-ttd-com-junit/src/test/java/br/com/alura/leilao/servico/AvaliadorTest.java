package br.com.alura.leilao.servico;

import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Usuario;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AvaliadorTest {

    @Test
    public void deveVerificarLanceEmOrdemCrescente() {
        // cenario: 3 lances em ordem crescente
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");


        leilao.propoe(new Lance(maria, 450));
        leilao.propoe(new Lance(joao, 500));
        leilao.propoe(new Lance(jose, 550));

        // executando a acao
        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double menorEsperado = 450;
        double maiorEsperado = 550;

        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.000001);
        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.000001);
    }

    @Test
    public void deveVerificarLanceEmOrdemAleatoria() {
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");
        double lance1 = 200;
        double lance2 = 450;
        double lance3 = 120;
        double lance4 = 700;
        double lance5 = 630;
        double lance6 = 230;


        Leilao leilao = new Leilao("Playstation 3 Novo");


        leilao.propoe(new Lance(maria, lance1));
        leilao.propoe(new Lance(joao, lance2));
        leilao.propoe(new Lance(jose, lance3));
        leilao.propoe(new Lance(joao, lance4));
        leilao.propoe(new Lance(jose, lance5));
        leilao.propoe(new Lance(maria, lance6));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);


        assertEquals(lance3, leiloeiro.getMenorLance(), 0.00001);
        assertEquals(lance4, leiloeiro.getMaiorLance(), 0.00001);
    }

    @Test
    public void deveVerificarLanceEmOrdemDecrescente() {
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");
        double lance1 = 400;
        double lance2 = 300;
        double lance3 = 200;
        double lance4 = 100;


        Leilao leilao = new Leilao("Playstation 3 Novo");


        leilao.propoe(new Lance(maria, lance1));
        leilao.propoe(new Lance(joao, lance2));
        leilao.propoe(new Lance(jose, lance3));
        leilao.propoe(new Lance(joao, lance4));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);


        assertEquals(lance4, leiloeiro.getMenorLance(), 0.00001);
        assertEquals(lance1, leiloeiro.getMaiorLance(), 0.00001);
    }

    @Test
    public void deveVerificarLeilaoComApenasUmLance() {
        Usuario joao = new Usuario("Joao");
        Leilao leilao = new Leilao("Playstation 3 Novo");
        leilao.propoe(new Lance(joao, 1000));

        // executando a acao
        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double menorEsperado = 1000;
        double maiorEsperado = 1000;

        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.000001);
        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.000001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");
        double lance1 = 450;
        double lance2 = 500;
        double lance3 = 600;
        double lance4 = 700;


        Leilao leilao = new Leilao("Playstation 3 Novo");


        leilao.propoe(new Lance(maria, lance1));
        leilao.propoe(new Lance(joao, lance2));
        leilao.propoe(new Lance(maria, lance3));
        leilao.propoe(new Lance(joao, lance4));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(lance4, maiores.get(0).getValor(), 0.00001);
        assertEquals(lance3, maiores.get(1).getValor(), 0.00001);
        assertEquals(lance2, maiores.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLancesEmOrdemCrescenteAleatoria() {
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");
        double lance1 = 200;
        double lance2 = 450;
        double lance3 = 120;
        double lance4 = 700;
        double lance5 = 630;
        double lance6 = 230;


        Leilao leilao = new Leilao("Playstation 3 Novo");


        leilao.propoe(new Lance(maria, lance1));
        leilao.propoe(new Lance(joao, lance2));
        leilao.propoe(new Lance(jose, lance3));
        leilao.propoe(new Lance(joao, lance4));
        leilao.propoe(new Lance(jose, lance5));
        leilao.propoe(new Lance(maria, lance6));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(lance4, maiores.get(0).getValor(), 0.00001);
        assertEquals(lance5, maiores.get(1).getValor(), 0.00001);
        assertEquals(lance2, maiores.get(2).getValor(), 0.00001);
    }

}
