package br.com.alura.spring.data.dominio.repositorios;

import br.com.alura.spring.data.dominio.entidades.Funcionario;
import br.com.alura.spring.data.dominio.projecoes.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> {
    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByNome(String nome);

    List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal Salario, LocalDate data);

    @Query(nativeQuery = true, value = "SELECT * FROM funcionario f WHERE f.data_contratacao >= :data")
    List<Funcionario> buscarDataContratacaoMaior(LocalDate data);

    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionario f", nativeQuery = true)
    List<FuncionarioProjecao> listaFuncionarioSalario();
}
