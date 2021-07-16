package br.com.zup.teste.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class AutoCorrecao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer nota;

	@OneToOne
	@JoinColumn(name = "resposta_aluno_id")
	private RespostaAluno respostaAluno;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public RespostaAluno getRespostaAluno() {
		return respostaAluno;
	}

	public void setRespostaAluno(RespostaAluno respostaAluno) {
		this.respostaAluno = respostaAluno;
	}
}
