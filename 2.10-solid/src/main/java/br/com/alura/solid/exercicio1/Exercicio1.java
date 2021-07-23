package br.com.alura.solid.exercicio1;

import java.time.LocalDate;

public class Exercicio1 {
    public static void main(String[] args) {
        Funcionario funci = new Funcionario();
        funci.setId(1);
        funci.setNome("Alexandre");
        funci.setDataDeAdmissao(LocalDate.parse("2021-01-01"));
        funci.setCargo(Cargo.DESENVOLVEDOR);
        funci.setSalarioBase(1000);

        System.out.printf("Salario: %.2f\n", funci.calculaSalario());
    }
}
