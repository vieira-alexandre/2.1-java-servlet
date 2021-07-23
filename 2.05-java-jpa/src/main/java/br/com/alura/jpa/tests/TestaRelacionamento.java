package br.com.alura.jpa.tests;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.model.Cliente;
import br.com.alura.jpa.model.Conta;
import br.com.alura.jpa.model.Movimentacao;
import br.com.alura.jpa.model.TipoMovimentacao;

public class TestaRelacionamento {
	public static void main(String[] args) {
		Conta conta = new Conta();
		conta.setAgencia(1001);
		conta.setNumero(441019);
		conta.setSaldo(0.0);

		Cliente cliente = new Cliente();
		cliente.setNome("Alexandre");
		cliente.setEndereco("Rua dos bobos, 0");
		cliente.setProfissao("Developer");

		Movimentacao mov = new Movimentacao();
		mov.setValor(new BigDecimal(200));
		mov.setTipoMovimentcao(TipoMovimentacao.ENTRADA);
		mov.setData(LocalDateTime.now());
		mov.setDescricao("Salário");
		mov.setConta(conta);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		em.persist(conta);
		em.persist(mov);

		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}
