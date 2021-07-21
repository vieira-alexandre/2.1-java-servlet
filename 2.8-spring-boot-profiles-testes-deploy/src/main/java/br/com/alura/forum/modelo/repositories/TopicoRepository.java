package br.com.alura.forum.modelo.repositories;


import br.com.alura.forum.modelo.entities.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
}
