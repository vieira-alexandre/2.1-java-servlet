package br.com.alura.solid.exercicio3;

public class FreteCorreios implements Envio {

    @Override
    public double calculaPara(String cidade) {
        if("SAO PAULO".equalsIgnoreCase(cidade)) {
            return 15;
        }
        return 30;
    }
}