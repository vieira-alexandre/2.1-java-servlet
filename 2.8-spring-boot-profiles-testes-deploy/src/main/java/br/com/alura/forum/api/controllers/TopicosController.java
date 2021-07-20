package br.com.alura.forum.api.controllers;

import br.com.alura.forum.api.dto.request.AtualizacaoTopicoRequest;
import br.com.alura.forum.api.dto.request.TopicoRequest;
import br.com.alura.forum.api.dto.response.DetalhesTopicoResponse;
import br.com.alura.forum.api.dto.response.TopicoResponse;
import br.com.alura.forum.modelo.entities.Topico;
import br.com.alura.forum.modelo.repositories.CursoRepository;
import br.com.alura.forum.modelo.repositories.TopicoRepository;
import br.com.alura.forum.values.CacheId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = CacheId.LISTA_DE_TOPICOS)
    public Page<TopicoResponse> lista(@RequestParam(required = false) String nomeCurso,
                                      @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC,
                                              size = 5) Pageable paginacao) {

        Page<Topico> topicos;

        if (nomeCurso == null) {
            topicos = repository.findAll(paginacao);
        } else {
            topicos = repository.findByCurso_Nome(nomeCurso, paginacao);
        }

        return TopicoResponse.converter(topicos);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = CacheId.LISTA_DE_TOPICOS, allEntries = true)
    public ResponseEntity<TopicoResponse> cadastrar(@RequestBody @Valid TopicoRequest topicoRequest) {
        Topico topico = topicoRequest.converter(cursoRepository);
        repository.save(topico);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoResponse(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoResponse> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = repository.findById(id);

        if(topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesTopicoResponse(topico.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = CacheId.LISTA_DE_TOPICOS, allEntries = true)
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
    @CacheEvict(value = CacheId.LISTA_DE_TOPICOS, allEntries = true)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Optional<Topico> opt = repository.findById(id);

        if(opt.isPresent()) {
            repository.delete(opt.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
