package br.com.alura.solid.exercicio4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercicio4 {
    public static void main(String[] args) {
        Fatura fatura = new Fatura("Alexandre", 1499.90);

        List<Boleto> boletos = new ArrayList<>();

        boletos.add(new Boleto(1000));
        boletos.add(new Boleto(499.89));

        ProcessadorDeBoletos processadorDeBoletos = new ProcessadorDeBoletos();
        processadorDeBoletos.processa(boletos, fatura);

        System.out.println(fatura);

        fatura = new Fatura("Alexandre", 1499.90);
        boletos = new ArrayList<>();

        boletos.add(new Boleto(1000));
        boletos.add(new Boleto(500));

        processadorDeBoletos.processa(boletos, fatura);

        System.out.println(fatura);

        fatura = new Fatura("Alexandre", 1499.90);
        boletos = new ArrayList<>();

        boletos.add(new Boleto(1000));
        boletos.add(new Boleto(499.90));

        processadorDeBoletos.processa(boletos, fatura);

        System.out.println(fatura);
    }
}
