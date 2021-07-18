package br.com.alura.forum.api.controllers;

import br.com.alura.forum.api.dto.request.AtualizacaoTopicoRequest;
import br.com.alura.forum.api.dto.request.TopicoRequest;
import br.com.alura.forum.api.dto.response.DetalhesTopicoResponse;
import br.com.alura.forum.api.dto.response.TopicoResponse;
import br.com.alura.forum.modelo.entities.Topico;
import br.com.alura.forum.modelo.repositories.CursoRepository;
import br.com.alura.forum.modelo.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoResponse> lista(String nomeCurso) {
        System.out.println("Listar");
        if (nomeCurso == null) {
            List<Topico> topicos = repository.findAll();
            return TopicoResponse.converter(topicos);
        } else {
            List<Topico> topicos = repository.findByCurso_Nome(nomeCurso);
            return TopicoResponse.converter(topicos);
        }


    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicoResponse> cadastrar(@RequestBody @Valid TopicoRequest topicoRequest) {
        Topico topico = topicoRequest.converter(cursoRepository);
        repository.save(topico);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponse(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoResponse> detalhar(@PathVariable Long id) {
        System.out.println("Get");
        Optional<Topico> topico = repository.findById(id);

        if(topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesTopicoResponse(topico.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> atualizar(@PathVariable Long id,
                                             @RequestBody @Valid AtualizacaoTopicoRequest topicoRequest) {
        Optional<Topico> opt = repository.findById(id);

        if(opt.isPresent()) {
            Topico topico = opt.get();
            topicoRequest.atualizar(topico);
            return ResponseEntity.ok(new TopicoResponse(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Optional<Topico> opt = repository.findById(id);

        if(opt.isPresent()) {
            repository.delete(opt.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
