package br.com.alura.forum.api.dto.request;

import br.com.alura.forum.modelo.entities.Topico;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class AtualizacaoTopicoRequest {
    @NotBlank
    @Length(min = 5, max = 255)
    private String titulo;

    @NotBlank  @Length(min = 10)
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizar(Topico topico) {
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
