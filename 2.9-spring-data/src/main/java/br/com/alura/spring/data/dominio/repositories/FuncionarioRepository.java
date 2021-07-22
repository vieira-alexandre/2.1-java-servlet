package br.com.alura.spring.data.dominio.repositories;

import br.com.alura.spring.data.dominio.model.Funcionario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByNome(String nome);
}
