package br.com.alura.spring.data.dominio.repositories;

import br.com.alura.spring.data.dominio.model.Cargo;
import org.springframework.data.repository.CrudRepository;

public interface CargoRepository extends CrudRepository<Cargo, Integer> {
}
