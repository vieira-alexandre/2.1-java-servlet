package br.com.alura.solid.exercicio3;

public class Exercicio3 {
    public static void main(String[] args) {
        Compra compra = new Compra();
        compra.setCidade("Sao paulo");
        compra.setValor(50000.0);

        CalculadoraDePrecos calculadoraDePrecos = new CalculadoraDePrecos(new TabelaDePrecoDiferenciada(), new FreteTransportadora());

        System.out.println(calculadoraDePrecos.calcula(compra));
    }
}
