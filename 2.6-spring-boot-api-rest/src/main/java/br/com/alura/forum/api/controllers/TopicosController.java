package br.com.alura.forum.api.controllers;

import br.com.alura.forum.api.dto.TopicoDto;
import br.com.alura.forum.modelo.entities.Topico;
import br.com.alura.forum.modelo.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicosController {

    @Autowired
    private TopicoRepository repository;

    @RequestMapping("/topicos")
    public List<TopicoDto> lista(String nomeCurso) {
        if(nomeCurso == null) {
            List<Topico> topicos = repository.findAll();
            return TopicoDto.converter(topicos);
        }
        else {
            List<Topico> topicos = repository.findByCurso_Nome(nomeCurso);
            return TopicoDto.converter(topicos);
        }
    }
}
