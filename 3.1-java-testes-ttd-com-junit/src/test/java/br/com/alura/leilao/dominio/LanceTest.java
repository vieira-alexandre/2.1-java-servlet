package br.com.alura.leilao.dominio;

import org.junit.Test;

public class LanceTest {
    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoParaLanceZero() {
        Usuario jobs = new Usuario(1, "Steve Jobs");
        Lance lance = new Lance(jobs, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoParaLanceNegativo() {
        Usuario jobs = new Usuario(1, "Steve Jobs");
        Lance lance = new Lance(jobs, -100);
    }
}
