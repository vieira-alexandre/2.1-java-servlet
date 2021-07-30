package br.com.alura.leilao.desafios;

import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Usuario;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FiltroDeLancesTest {

    @Test
    public void deveSelecionarLancesEntre1000E3000() {
        Usuario joao = new Usuario(1,"Joao");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao,2000), 
                new Lance(joao,1000),
                new Lance(joao,2500),
                new Lance(joao,1500),
                new Lance(joao,3000), 
                new Lance(joao, 800)));

        assertEquals(3, resultado.size());
        assertEquals(2000, resultado.get(0).getValor(), 0.00001);
        assertEquals(2500, resultado.get(1).getValor(), 0.00001);
        assertEquals(1500, resultado.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveSelecionarLancesEntre500E700() {
        Usuario joao = new Usuario(1,"Joao");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao,600),
                new Lance(joao,550),
                new Lance(joao,500),
                new Lance(joao,650),
                new Lance(joao,700), 
                new Lance(joao, 800)));

        assertEquals(3, resultado.size());
        assertEquals(600, resultado.get(0).getValor(), 0.00001);
        assertEquals(550, resultado.get(1).getValor(), 0.00001);
        assertEquals(650, resultado.get(2).getValor(), 0.00001);
    }

    @Test
    public void deveSelecionarLancesMaiorQue5000() {
        Usuario joao = new Usuario(1,"Joao");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao,5000),
                new Lance(joao,4900),
                new Lance(joao,5100),
                new Lance(joao,10000),
                new Lance(joao,1000),
                new Lance(joao,5500),
                new Lance(joao, 8000)));

        assertEquals(4, resultado.size());
        assertEquals(5100, resultado.get(0).getValor(), 0.00001);
        assertEquals(10000, resultado.get(1).getValor(), 0.00001);
        assertEquals(5500, resultado.get(2).getValor(), 0.00001);
        assertEquals(8000, resultado.get(3).getValor(), 0.00001);
    }

    @Test
    public void deveEliminarMenoresQue500() {
        Usuario joao = new Usuario(1,"Joao");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao,490),
                new Lance(joao, 80)));

        assertEquals(0, resultado.size());
    }

    @Test
    public void deveEliminarMenoresEntre700E1000() {
        Usuario joao = new Usuario(1,"Joao");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao,700),
                new Lance(joao,1000),
                new Lance(joao, 900)));

        assertEquals(0, resultado.size());
    }

    @Test
    public void deveEliminarMenoresEntre3000E5000() {
        Usuario joao = new Usuario("Joao");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao,3000),
                new Lance(joao,4000),
                new Lance(joao, 5000)));

        assertEquals(0, resultado.size());
    }
}