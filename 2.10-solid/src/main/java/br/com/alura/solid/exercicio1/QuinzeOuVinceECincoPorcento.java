package br.com.alura.solid.exercicio1;

public class QuinzeOuVinceECincoPorcento implements RegraDeCalculo {

    @Override
    public Double calcula(Funcionario funcionario) {
        if(funcionario.getSalarioBase() > 2000.0) {
            return funcionario.getSalarioBase() * 0.75;
        }
        else {
            return funcionario.getSalarioBase() * 0.85;
        }
    }
}
