package br.com.alura.jpa.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.model.Conta;

public class TestaEstados {
	public static void main(String[] args) {
		// Transient
		Conta conta = new Conta();
		conta.setTitular("João");
		conta.setAgencia(3847);
		conta.setNumero(441019);
		conta.setSaldo(300.0);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Transient -> Managed
		em.persist(conta);

		// Managed -> Removed
		em.remove(conta);

		em.getTransaction().commit();
		em.close();
		emf.close();
	}
}
