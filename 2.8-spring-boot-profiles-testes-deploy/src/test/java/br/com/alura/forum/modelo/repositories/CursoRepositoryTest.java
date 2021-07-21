package br.com.alura.forum.modelo.repositories;

import br.com.alura.forum.modelo.entities.Curso;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository repository;

    @Test
    public void findByNomeTest() {
        String nomeCurso = "HTML 5";
        Curso cursoTest = repository.findByNome(nomeCurso).orElse(null);

        assertNotNull(cursoTest);
        assertEquals(nomeCurso, cursoTest.getNome());

    }

    @Test
    public void findByNomeTestNotFound() {
        String nomeCurso = "Java";
        Curso cursoTest = repository.findByNome(nomeCurso).orElse(null);

        assertNull(cursoTest);
    }
}