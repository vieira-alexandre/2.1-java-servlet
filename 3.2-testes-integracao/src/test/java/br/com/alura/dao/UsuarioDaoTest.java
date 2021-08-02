package br.com.alura.dao;

import br.com.alura.dominio.Usuario;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UsuarioDaoTest {
    private static UsuarioDao dao;
    private static Session session;

    @BeforeClass
    public static void setUp() {
        session = new CriadorDeSessao().getSession();
        dao = new UsuarioDao(session);
    }

    @AfterClass
    public static void finish() {
        session.close();
    }

    @Test
    public void deveEncontrarPeloNomeEEmail() {
        Usuario usuario = dao.porNomeEEmail("Joao Silva", "joao@silva.com");

        assertThat(usuario.getNome(), equalTo("Joao Silva"));
        assertThat(usuario.getEmail(), equalTo("joao@silva.com"));
    }

    @Test
    public void deveEncontrarPeloId() {
        Usuario usuario = dao.porId(1);

        assertThat(usuario.getNome(), equalTo("Alexandre Vieira"));
        assertThat(usuario.getEmail(), equalTo("alexandrer0x@hotmail.com"));
    }

    @Test
    public void deveReceberNuloSeUsuarioNaoExistirPorNomeEEmail() {
        Usuario usuario = dao.porNomeEEmail("joao santos", "joao@santos.com");
        assertThat(usuario, equalTo(null));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void deveLancarExcecaoSeUsuarioNaoExistirPorId() {
        Usuario usuario = dao.porId(2110365475);
        System.out.println(usuario);
    }
}
