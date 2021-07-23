package br.com.alura.jpa.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.model.Cliente;
import br.com.alura.jpa.model.Conta;

public class CriaContaComSaldo {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Conta conta = new Conta();
		conta.setAgencia(1501);
		conta.setNumero(58169);
		conta.setSaldo(500.0);

		Cliente cliente = new Cliente();
		cliente.setNome("Thais");
		cliente.setEndereco("Rua dos bobos, 0");
		cliente.setProfissao("Gerente");
		cliente.setConta(conta);

		em.persist(conta);
		em.persist(cliente);

		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}
