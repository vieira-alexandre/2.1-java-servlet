package br.com.alura.dao;

import br.com.alura.dominio.Leilao;
import br.com.alura.dominio.Usuario;
import org.hibernate.Session;
import org.junit.*;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LeilaoDaoTest {
    private static LeilaoDao dao;
    private static Session session;
    private Usuario usuario;

    @BeforeClass
    public static void init() {
        session = new CriadorDeSessao().getSession();
        dao = new LeilaoDao(session);
    }

    @AfterClass
    public static void finish() {
        session.close();
    }

    @Before
    public void setUp() {
        session.beginTransaction();
        usuario = new Usuario("Alexandre", "alexandre@email.com");
        UsuarioDao usuarioDao = new UsuarioDao(session);
        usuarioDao.salvar(usuario);
    }

    @After
    public void clean() {
        session.getTransaction().rollback();
    }

    @Test
    public void deveContarLeiloesAtivos() {
        Leilao ativo = new Leilao("PS5", 3500.0, usuario, false);
        Leilao encerrado = new Leilao("Xbox One", 1000.0, usuario, true);
        encerrado.encerra();

        dao.salvar(ativo);
        dao.salvar(encerrado);

        assertThat(dao.total(), equalTo(1L));
    }

    @Test
    public void deveContarNenhumLeiloesAtivos() {
        Leilao leilao1 = new Leilao("PS5", 3500.0, usuario, false);
        Leilao leilao2 = new Leilao("Xbox One", 1000.0, usuario, true);
        leilao1.encerra();
        leilao2.encerra();

        dao.salvar(leilao1);
        dao.salvar(leilao2);

        assertThat(dao.total(), equalTo(0L));
    }

    @Test
    public void deveContarLeiloesDeItemsNovos() {
        Leilao leilao1 = new Leilao("PS5", 3500.0, usuario, false);
        Leilao leilao2 = new Leilao("Xbox One", 1000.0, usuario, true);
        Leilao leilao3 = new Leilao("Echo Dot", 50.0, usuario, true);

        dao.salvar(leilao1);
        dao.salvar(leilao2);
        dao.salvar(leilao3);

        assertThat(dao.novos().size(), equalTo(1));
    }

    @Test
    public void deveContarLeiloesAntigosComMaisDeSeteDias() {
        Leilao leilao1 = new Leilao("PS5", 3500.0, usuario, false);
        Leilao leilao2 = new Leilao("Xbox One", 1000.0, usuario, true);
        Leilao leilao3 = new Leilao("Echo Dot", 50.0, usuario, true);

        Calendar dezDiasAtras = Calendar.getInstance();
        dezDiasAtras.add(Calendar.DAY_OF_MONTH, -10);
        leilao1.setDataAbertura(dezDiasAtras);
        leilao3.setDataAbertura(dezDiasAtras);

        dao.salvar(leilao1);
        dao.salvar(leilao2);
        dao.salvar(leilao3);

        assertThat(dao.antigos().size(), equalTo(2));
    }

    @Test
    public void deveContarLeiloesAntigosComExatamenteSeteDias() {
        Leilao leilao1 = new Leilao("PS5", 3500.0, usuario, false);
        Leilao leilao2 = new Leilao("Xbox One", 1000.0, usuario, true);
        Leilao leilao3 = new Leilao("Echo Dot", 50.0, usuario, true);

        Calendar seteDiasAtras = Calendar.getInstance();
        seteDiasAtras.add(Calendar.DAY_OF_MONTH, -7);
        leilao1.setDataAbertura(seteDiasAtras);

        dao.salvar(leilao1);
        dao.salvar(leilao2);
        dao.salvar(leilao3);

        assertThat(dao.antigos().size(), equalTo(1));
    }

    @Test
    public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {
        Leilao leilao1 = new Leilao("PS5", 3500.0, usuario, false);
        Leilao leilao2 = new Leilao("Xbox One", 1000.0, usuario, true);
        Leilao leilao3 = new Leilao("Echo Dot", 50.0, usuario, true);

        Calendar seteDiasAtras = Calendar.getInstance();
        seteDiasAtras.add(Calendar.DAY_OF_MONTH, -7);
        leilao1.setDataAbertura(seteDiasAtras);

        dao.salvar(leilao1);
        dao.salvar(leilao2);
        dao.salvar(leilao3);

        assertThat(dao.antigos().size(), equalTo(1));
    }
}
