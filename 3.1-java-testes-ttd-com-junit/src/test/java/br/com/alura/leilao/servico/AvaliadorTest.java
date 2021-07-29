package br.com.alura.leilao.servico;

import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Leilao;
import br.com.alura.leilao.dominio.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AvaliadorTest {

    @Test
    public void deveVerificarLanceEmOrdemCrescente() {
        // cenario: 3 lances em ordem crescente
        Usuario joao = new Usuario("Joao");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");


        leilao.propoe(new Lance(maria,450));
        leilao.propoe(new Lance(joao,500));
        leilao.propoe(new Lance(jose,550));

        // executando a acao
        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double menorEsperado = 450;
        double maiorEsperado = 550;
        double mediaEsperada = 500;
        double quantidadeLances = 3;

        Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.000001);
        Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.000001);
        Assert.assertEquals(mediaEsperada, leiloeiro.getMediaLance(), 0.000001);
        Assert.assertEquals(quantidadeLances, leiloeiro.getQuantidade(), 0.000001);
    }
}
