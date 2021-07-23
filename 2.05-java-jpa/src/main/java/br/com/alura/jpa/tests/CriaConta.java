package br.com.alura.jpa.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.model.Cliente;
import br.com.alura.jpa.model.Conta;

public class CriaConta {
	public static void main(String[] args) {
		Conta conta = new Conta();
		conta.setAgencia(1001);
		conta.setNumero(441019);

		Cliente cliente = new Cliente();
		cliente.setNome("Alexandre");
		cliente.setEndereco("Rua dos bobos, 0");
		cliente.setProfissao("Developer");
		cliente.setConta(conta);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		em.persist(conta);
		em.persist(cliente);

		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}
