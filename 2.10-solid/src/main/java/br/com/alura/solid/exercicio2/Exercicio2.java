package br.com.alura.solid.exercicio2;

import java.util.Arrays;

public class Exercicio2 {
    public static void main(String[] args) {
        Fatura fatura = new Fatura();
        fatura.setCliente("João");
        fatura.setValorMensal(1975.85);

        NotaFiscalDao nfdao = new NotaFiscalDao();
        EnviadorDeEmail email = new EnviadorDeEmail();

        GeradorDeNotaFiscal gerador = new GeradorDeNotaFiscal(Arrays.asList(nfdao, email));
        System.out.println("Gerador 1\n================");
        gerador.gera(fatura);
        System.out.println("================\n");

        fatura = new Fatura();
        fatura.setCliente("Maria");
        fatura.setValorMensal(3215.90);

        gerador = new GeradorDeNotaFiscal(Arrays.asList(nfdao));
        System.out.println("Gerador 2\n================");
        gerador.gera(fatura);
        System.out.println("================\n");

        fatura = new Fatura();
        fatura.setCliente("Pedro");
        fatura.setValorMensal(378.90);

        gerador = new GeradorDeNotaFiscal(Arrays.asList(email));
        System.out.println("Gerador 3\n================");
        gerador.gera(fatura);
        System.out.println("================\n");

    }
}
