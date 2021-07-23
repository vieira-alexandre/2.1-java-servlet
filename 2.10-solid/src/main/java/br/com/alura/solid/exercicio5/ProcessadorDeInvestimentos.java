package br.com.alura.solid.exercicio5;

import java.util.Arrays;
import java.util.List;

public class ProcessadorDeInvestimentos {

    public static void main(String[] args) {
        for (Conta conta : contasDoBanco()) {
            conta.rende();

            System.out.println(conta);
        }
    }

    private static List<Conta> contasDoBanco() {
        return Arrays.asList(umaContaCom(100), umaContaCom(500), contaDeEstudanteCom(100), contaDeEstudanteCom(500));
    }

    private static Conta contaDeEstudanteCom(double amount) {
        ContaDeEstudante c = new ContaDeEstudante();
        c.deposita(amount);
        return c;
    }

    private static Conta umaContaCom(double valor) {
        ContaComum c = new ContaComum();
        c.deposita(valor);
        return c;
    }
}
