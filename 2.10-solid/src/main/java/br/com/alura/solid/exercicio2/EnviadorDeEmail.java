package br.com.alura.solid.exercicio2;

public class EnviadorDeEmail implements AcaoAposGerarNotaFiscal {
    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("envia email da nf " + nf.getId());
    }
}