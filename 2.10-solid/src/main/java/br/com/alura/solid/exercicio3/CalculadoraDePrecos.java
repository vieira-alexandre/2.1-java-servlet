package br.com.alura.solid.exercicio3;

public class CalculadoraDePrecos {
    private final TabelaDePreco tabelaDePreco;
    private final Envio envio;

    public CalculadoraDePrecos(TabelaDePreco tabelaDePreco, Envio envio) {
        this.tabelaDePreco = tabelaDePreco;
        this.envio = envio;
    }

    public double calcula(Compra produto) {

        double desconto = tabelaDePreco.calculaDescontoPara(produto.getValor());
        double frete = envio.calculaPara(produto.getCidade());

        return produto.getValor() * (1-desconto) + frete;
    }
}