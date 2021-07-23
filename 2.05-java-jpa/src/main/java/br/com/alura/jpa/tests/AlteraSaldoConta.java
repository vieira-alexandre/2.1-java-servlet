package br.com.alura.jpa.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.model.Conta;

public class AlteraSaldoConta {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		Conta conta = em.find(Conta.class, 2L);

		em.getTransaction().begin();
		conta.setSaldo(5000.0);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}
