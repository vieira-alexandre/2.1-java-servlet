package br.com.alura.leilao.desafios;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnoBissextoTest {

    @Test
    public void multiploDe4EhBissexto() {
        List<Integer> anos = Arrays.asList(4, 8, 16, 32, 64, 128, 256,512, 1024, 1536, 2048, 2996);
        anos.forEach(ano -> assertTrue(AnoBissexto.ehBissexto(ano)));
    }

    @Test
    public void multiploDe100SoEhBissextoSeForMultiploDe400() {
        List<Integer> anos = Arrays.asList(100, 200, 300, 500, 600, 700, 900, 1000, 1100, 1300, 1400, 1500, 1700,
                1800, 1900, 2100, 2200, 2300, 2500, 2600, 2700, 2900, 3000);

        anos.forEach(ano -> assertFalse(AnoBissexto.ehBissexto(ano)));
    }

    @Test
    public void multiploDe400EhBissexto() {
        List<Integer> anos = Arrays.asList(400, 800, 1200, 1600, 2000, 2400, 2800);
        anos.forEach(ano -> assertTrue(AnoBissexto.ehBissexto(ano)));
    }

    @Test
    public void naoEhBissexto() {
        List<Integer> anos = Arrays.asList(1, 2, 3, 5, 6, 7, 9, 10, 1999, 2019, 2021, 2099);

        anos.forEach(ano -> assertFalse(AnoBissexto.ehBissexto(ano)));
    }
}
