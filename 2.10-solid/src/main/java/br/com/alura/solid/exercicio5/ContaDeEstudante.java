package br.com.alura.solid.exercicio5;

public class ContaDeEstudante implements Conta{

    private int milhas;
    private GerenciadorDeSaldo gerenciadorDeSaldo;

    public ContaDeEstudante() {
        gerenciadorDeSaldo = new GerenciadorDeSaldo();
        this.milhas = 0;
    }

    @Override
    public void deposita(double valor) {
        this.gerenciadorDeSaldo.deposita(valor);
        this.milhas += (int)valor;
    }

    @Override
    public void saca(double valor) {
        this.gerenciadorDeSaldo.saca(valor);
    }

    @Override
    public double getSaldo() {
        return gerenciadorDeSaldo.getSaldo();
    }

    public int getMilhas() {
        return milhas;
    }

    @Override
    public void rende() {
        this.gerenciadorDeSaldo.rende(0);
    }

    @Override
    public String toString() {
        return "ContaDeEstudante{" +
                "milhas=" + milhas +
                ", saldo=" + gerenciadorDeSaldo.getSaldo() +
                '}';
    }
}
