package br.com.alura.forum.modelo.repositories;

import br.com.alura.forum.modelo.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNome(String nomeCurso);
}
