package br.com.alura.spring.data.dominio.repositories;

import br.com.alura.spring.data.dominio.model.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByNome(String nome);

    List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal Salario, LocalDate data);

    @Query(nativeQuery = true, value = "SELECT * FROM funcionario f WHERE f.data_contratacao >= :data")
    List<Funcionario> buscarDataContratacaoMaior(LocalDate data);
}
