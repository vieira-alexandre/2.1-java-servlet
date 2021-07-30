package br.com.alura.leilao.desafios;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatematicaMalucaTest {

    @Test
    public void verificaMaiorQueTrinta() {
        int n = 50;
        assertEquals(n * 4, MatematicaMaluca.contaMaluca(n));
    }

    @Test
    public void verificaMaiorQueDez() {
        int n = 30;
        assertEquals(n * 3, MatematicaMaluca.contaMaluca(n));
    }

    @Test
    public void verificaIgualDez() {
        int n = 10;
        assertEquals(n * 2, MatematicaMaluca.contaMaluca(n));
    }

    @Test
    public void verificaMenorQueDez() {
        int n = 9;
        assertEquals(n * 2, MatematicaMaluca.contaMaluca(n));
    }
}
