package br.com.alura.forum.api.dto.request;

import br.com.alura.forum.modelo.entities.Curso;
import br.com.alura.forum.modelo.entities.Topico;
import br.com.alura.forum.modelo.repositories.CursoRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;

public class TopicoRequest {

    @NotBlank @Length(min = 5, max = 255)
    private String titulo;

    @NotBlank  @Length(min = 10)
    private String mensagem;

    @NotBlank @Length(min = 5, max = 255)
    private String nomeCurso;

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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Topico converter(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(this.nomeCurso).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        return new Topico(titulo, mensagem, curso);
    }
}
