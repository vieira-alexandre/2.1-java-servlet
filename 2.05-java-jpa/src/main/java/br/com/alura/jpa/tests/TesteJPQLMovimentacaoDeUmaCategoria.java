package br.com.alura.jpa.tests;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.alura.jpa.model.Categoria;
import br.com.alura.jpa.model.Movimentacao;

public class TesteJPQLMovimentacaoDeUmaCategoria {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();

		String jpql = "select m from Movimentacao m join m.categorias c Where c = :cat";

		Categoria cat = new Categoria();
		cat.setId(2L);

		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);

		query.setParameter("cat", cat);

		List<Movimentacao> resultList = query.getResultList();

		for (Movimentacao m : resultList) {
			sb.append("=======================================");
			sb.append("\nDescricao: " + m.getDescricao());
			sb.append("\nTipo: " + m.getTipoMovimentcao());
			sb.append("\nCategoria: " + Arrays.toString(m.getCategorias().toArray()));
			sb.append("\nValor: " + m.getValor() + "\n");
		}

		System.out.println(sb);

		em.close();
		emf.close();
	}
}
