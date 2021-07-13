package br.com.zup.gerenciador.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Banco {
	private static List<Empresa> lista = new ArrayList<>();
	private static Integer sequence = 1;
	
	static {
		Empresa empresa = new Empresa();
		empresa.setNome("Alura");
		empresa.setId(Banco.sequence++);
		
		Empresa empresa2 = new Empresa();
		empresa2.setNome("Caelum");
		empresa2.setId(Banco.sequence++);
		
		lista.add(empresa);
		lista.add(empresa2);
	}
	
	public void adiciona(Empresa empresa) {
		empresa.setId(Banco.sequence++);
		Banco.lista.add(empresa);
	}
	
	public List<Empresa> getEmpresas() {
		return Banco.lista;
	}

	public void remove(Integer id) {
		Iterator<Empresa> it = lista.iterator();
		
		while(it.hasNext()) {
			Empresa empresa = it.next();
			if(empresa.getId().equals(id)) {
				it.remove();
				break;
			}
		}
	}

	public Empresa buscaPorId(Integer id) {
		for (Empresa empresa : lista) {
			if(empresa.getId().equals(id)) {
				return empresa;
			}
		}
		return null;
	}
}
