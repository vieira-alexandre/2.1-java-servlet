package br.com.alura.jpa.tests;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.model.Categoria;
import br.com.alura.jpa.model.Conta;
import br.com.alura.jpa.model.Movimentacao;
import br.com.alura.jpa.model.TipoMovimentacao;

public class TestaRelacionamentoMovimentacaoCategoria {
	public static void main(String[] args) {
		Categoria categoria1 = new Categoria("Viagem");
		Categoria categoria2 = new Categoria("Negócios");

		Conta conta = new Conta();
		conta.setId(1L);

		Movimentacao mov = new Movimentacao();
		mov.setDescricao("Ida SP-NY");
		mov.setTipoMovimentcao(TipoMovimentacao.SAIDA);
		mov.setData(LocalDateTime.now());
		mov.setValor(new BigDecimal("849.90"));
		mov.setCategorias(Arrays.asList(categoria1, categoria2));
		mov.setConta(conta);

		Movimentacao mov2 = new Movimentacao();
		mov2.setDescricao("Volta NY-SP");
		mov2.setTipoMovimentcao(TipoMovimentacao.SAIDA);
		mov2.setData(LocalDateTime.now());
		mov2.setValor(new BigDecimal("1449.90"));
		mov2.setCategorias(Arrays.asList(categoria1, categoria2));
		mov2.setConta(conta);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		em.persist(categoria1);
		em.persist(categoria2);
		em.persist(mov);
		em.persist(mov2);

		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}
