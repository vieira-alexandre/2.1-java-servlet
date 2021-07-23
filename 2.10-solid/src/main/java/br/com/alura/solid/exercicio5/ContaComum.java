package br.com.alura.solid.exercicio5;

public class ContaComum implements Conta{

    private GerenciadorDeSaldo gerenciadorDeSaldo;

    public ContaComum() {
        this.gerenciadorDeSaldo = new GerenciadorDeSaldo();
    }

    @Override
    public void deposita(double valor) {
        this.gerenciadorDeSaldo.deposita(valor);
    }

    @Override
    public void saca(double valor) {
        this.gerenciadorDeSaldo.saca(valor);
    }

    @Override
    public void rende() {
        this.gerenciadorDeSaldo.rende(0.01);
    }

    @Override
    public double getSaldo() {
        return gerenciadorDeSaldo.getSaldo();
    }

    @Override
    public String toString() {
        return "ContaComum{" +
                "saldo=" + gerenciadorDeSaldo.getSaldo() +
                '}';
    }
}