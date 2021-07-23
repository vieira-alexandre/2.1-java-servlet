package br.com.alura.solid.exercicio3;

public class FreteTransportadora implements Envio {

    @Override
    public double calculaPara(String cidade) {
        if("uberlandia".equalsIgnoreCase(cidade)) {
            return 10;
        }
        return 25;
    }
}