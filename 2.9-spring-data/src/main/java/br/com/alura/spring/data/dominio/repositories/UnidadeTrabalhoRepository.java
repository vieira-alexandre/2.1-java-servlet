package br.com.alura.spring.data.dominio.repositories;

import br.com.alura.spring.data.dominio.model.UnidadeTrabalho;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnidadeTrabalhoRepository extends CrudRepository<UnidadeTrabalho, Integer> {
    Optional<UnidadeTrabalho> findByDescricao(String descricao);
}
