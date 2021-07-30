package br.com.alura.leilao.desafios;

public class AnoBissexto {

    public static boolean ehBissexto(int ano) {
        return ano % 400 == 0 ||
                (ano % 4 == 0 && ano % 100 != 0);
    }
}
