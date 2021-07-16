package br.com.alura.jpa.tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.alura.jpa.model.Movimentacao;

public class TesteJPQL {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		String jpql = "from Movimentacao m Where m.conta.id = 1 Order by m.valor desc";

		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);

		List<Movimentacao> resultList = query.getResultList();

		for (Movimentacao m : resultList) {
			System.out.println("=======================================");
			System.out.println("Descricao: " + m.getDescricao());
			System.out.println("Tipo: " + m.getTipoMovimentcao());
			System.out.println("Valor: " + m.getValor());
		}

		em.close();
		emf.close();
	}
}
