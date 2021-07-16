package br.com.alura.jpa.tests;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.model.Conta;

public class ApagaConta {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		Long id = sc.nextLong();

		em.getTransaction().begin();

		em.remove(em.find(Conta.class, id));

		em.getTransaction().commit();

		em.close();
		emf.close();
		sc.close();
	}
}
