package br.com.alura.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import br.com.alura.dominio.Lance;
import br.com.alura.dominio.Leilao;
import br.com.alura.dominio.Usuario;

@SuppressWarnings("deprecation")
public class CriadorDeSessao {

	private static AnnotationConfiguration config;
	private static SessionFactory sf;
	
	public Session getSession() {
		if(sf == null) {
			sf = getConfig().buildSessionFactory();
		}
		
		return sf.openSession();
	}

	public Configuration getConfig() {
		if(config == null) {
			config = new AnnotationConfiguration()
		    .addAnnotatedClass(Lance.class)
		    .addAnnotatedClass(Leilao.class)
		    .addAnnotatedClass(Usuario.class)
			.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
			.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/teste-integracao?serverTimezone=UTC")
			.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
			.setProperty("hibernate.connection.username", "alex")
			.setProperty("hibernate.connection.password", "alex")
			.setProperty("hibernate.show_sql", "true");
		}
		return config;
	}
}
