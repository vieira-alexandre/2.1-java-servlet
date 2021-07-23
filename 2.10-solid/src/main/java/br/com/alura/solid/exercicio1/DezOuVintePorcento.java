package br.com.alura.solid.exercicio1;

public class DezOuVintePorcento implements RegraDeCalculo {

    @Override
    public Double calcula(Funcionario funcionario) {
        if(funcionario.getSalarioBase() > 3000.0) {
            return funcionario.getSalarioBase() * 0.8;
        }
        else {
            return funcionario.getSalarioBase() * 0.9;
        }
    }
}
