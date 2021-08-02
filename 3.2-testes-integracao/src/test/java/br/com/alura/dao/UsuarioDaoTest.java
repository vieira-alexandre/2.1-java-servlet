package br.com.alura.dao;

import br.com.alura.dominio.Usuario;
import org.hibernate.Session;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UsuarioDaoTest {
    private static UsuarioDao dao;
    private static Session session;

    @BeforeClass
    public static void init() {
        session = new CriadorDeSessao().getSession();
        dao = new UsuarioDao(session);
    }

    @AfterClass
    public static void finish() {
        session.close();
    }

    @Before
    public void setUp() {
        session.beginTransaction();
    }

    @After
    public void clean() {
        session.getTransaction().rollback();
    }

    @Test
    public void deveEncontrarPeloNomeEEmail() {
        Usuario novoUsuario = new Usuario("Joao Silva", "joao@silva.com");
        dao.salvar(novoUsuario);

        Usuario usuarioDoBanco = dao.porNomeEEmail("Joao Silva", "joao@silva.com");

        assertThat(usuarioDoBanco.getNome(), equalTo("Joao Silva"));
        assertThat(usuarioDoBanco.getEmail(), equalTo("joao@silva.com"));
    }
    @Test
    public void deveRetornarNuloSeUsuarioNaoExistirPorNomeEEmail() {
        Usuario usuario = dao.porNomeEEmail("joao santos", "joao@santos.com");
        assertThat(usuario, equalTo(null));
    }

    @Test
    public void deveDeletarUmUsario() {
        Usuario novoUsuario = new Usuario("Joao Silva", "joao@silva.com");

        dao.salvar(novoUsuario);
        session.flush();
        session.clear();

        Usuario usuarioDoBanco = dao.porNomeEEmail("Joao Silva", "joao@silva.com");
        assertThat(usuarioDoBanco.getNome(), equalTo("Joao Silva"));
        assertThat(usuarioDoBanco.getEmail(), equalTo("joao@silva.com"));


        dao.deletar(usuarioDoBanco);
        session.flush();
        session.clear();

        Usuario usuarioDeletado = dao.porNomeEEmail("Joao Silva", "joao@silva.com");
        assertThat(usuarioDeletado, equalTo(null));
    }

    @Test
    public void deveAlterarUmUsario() {
        Usuario novoUsuario = new Usuario("Joao Silva", "joao@silva.com");

        dao.salvar(novoUsuario);
        session.flush();
        session.clear();

        Usuario usuarioDoBanco = dao.porNomeEEmail("Joao Silva", "joao@silva.com");
        assertThat(usuarioDoBanco.getNome(), equalTo("Joao Silva"));
        assertThat(usuarioDoBanco.getEmail(), equalTo("joao@silva.com"));


        usuarioDoBanco.setNome("Maria Silva");
        usuarioDoBanco.setEmail("maria@silva.com");
        dao.atualizar(usuarioDoBanco);
        session.flush();
        session.clear();

        Usuario usuarioDeletado = dao.porNomeEEmail("Joao Silva", "joao@silva.com");
        assertThat(usuarioDoBanco.getNome(), equalTo("Maria Silva"));
        assertThat(usuarioDoBanco.getEmail(), equalTo("maria@silva.com"));
    }
}
