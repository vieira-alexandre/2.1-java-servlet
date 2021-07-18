package br.com.alura.forum.api.controllers;

import br.com.alura.forum.api.dto.request.TopicoRequest;
import br.com.alura.forum.api.dto.response.TopicoResponse;
import br.com.alura.forum.modelo.entities.Topico;
import br.com.alura.forum.modelo.repositories.CursoRepository;
import br.com.alura.forum.modelo.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoResponse> lista(String nomeCurso) {
        System.out.println("Listar");
        if(nomeCurso == null) {
            List<Topico> topicos = repository.findAll();
            return TopicoResponse.converter(topicos);
        }
        else {
            List<Topico> topicos = repository.findByCurso_Nome(nomeCurso);
            return TopicoResponse.converter(topicos);
        }


    }

    @PostMapping
    public ResponseEntity<TopicoResponse> cadastrar(@RequestBody @Valid TopicoRequest topicoRequest) {
        Topico topico = topicoRequest.converter(cursoRepository);
        repository.save(topico);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponse(topico));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<TopicoResponse> get(@PathVariable Long id) {
        System.out.println("Get");

        Topico topico = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new TopicoResponse(topico));
    }
}
