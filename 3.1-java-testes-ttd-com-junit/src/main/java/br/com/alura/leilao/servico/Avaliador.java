package br.com.alura.leilao.servico;

import br.com.alura.leilao.dominio.Lance;
import br.com.alura.leilao.dominio.Leilao;

public class Avaliador {
    private double maior = Double.NEGATIVE_INFINITY;
    private double menor = Double.POSITIVE_INFINITY;
    private double media = 0;
    private double quantidade = 0;

    public void avalia(Leilao leilao) {
        int qtd = 0;
        double soma = 0;
        for(Lance lance: leilao.getLances()) {
            if(lance.getValor() > maior) maior = lance.getValor();
            if(lance.getValor() < menor) menor = lance.getValor();

            soma += lance.getValor();
            qtd++;
        }

        quantidade = qtd;

        if(qtd == 0)
            media = 0;
        else
            media = soma / qtd;
    }

    public double getMaiorLance() {
        return maior;
    }

    public double getMenorLance() {
        return menor;
    }

    public double getMediaLance() {
        return media;
    }

    public double getQuantidade() {
        return quantidade;
    }
}
