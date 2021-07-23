package br.com.alura.solid.exercicio3;

public class TabelaDePrecoDiferenciada implements TabelaDePreco {
    @Override
    public double calculaDescontoPara(double valor) {
        if(valor>5000) return 0.1;
        if(valor>1000) return 0.05;
        return 0;
    }
}