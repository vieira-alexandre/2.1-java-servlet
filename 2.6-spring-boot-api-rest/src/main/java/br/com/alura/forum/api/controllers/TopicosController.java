package br.com.alura.forum.api.controllers;

import br.com.alura.forum.api.dto.request.TopicoRequest;
import br.com.alura.forum.api.dto.response.TopicoResponse;
import br.com.alura.forum.modelo.entities.Topico;
import br.com.alura.forum.modelo.repositories.CursoRepository;
import br.com.alura.forum.modelo.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController(value = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoResponse> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = repository.findAll();
            return TopicoResponse.converter(topicos);
        } else {
            List<Topico> topicos = repository.findByCurso_Nome(nomeCurso);
            return TopicoResponse.converter(topicos);
        }
    }

    @PostMapping
    public ResponseEntity<TopicoResponse> cadastrar(@RequestBody TopicoRequest topicoRequest) {
        Topico topico = topicoRequest.converter(cursoRepository);
        repository.save(topico);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponse(topico));
    }
}
