package br.com.alura.solid.exercicio5;

public interface Conta {
    double getSaldo();
    void saca(double valor);
    void deposita(double valor);
    void rende();
}
