package br.com.alura.spring.data.dominio.repositories;

import br.com.alura.spring.data.dominio.model.Cargo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer> {
    Optional<Cargo> findByDescricao(String descricao);
}
