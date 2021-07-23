package br.com.alura.spring.data.servicos;

import br.com.alura.spring.data.dominio.entidades.Funcionario;
import br.com.alura.spring.data.dominio.repositorios.FuncionarioRepository;
import br.com.alura.spring.data.specifications.FuncionarioSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioDinamicoService {
    private final FuncionarioRepository repository;

    public RelatorioDinamicoService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public void inicial(Scanner sc) {
        System.out.print("Digite o nome: ");
        String nome = sc.nextLine();

        if(nome.equalsIgnoreCase("NULL")) {
            nome = null;
        }

        System.out.print("Digite o cpf: ");
        String cpf = sc.nextLine();
        if(cpf.equalsIgnoreCase("NULL")) {
            cpf = null;
        }

        System.out.print("Digite o salario: ");
        Double salario = sc.nextDouble();
        sc.nextLine();

        if(salario == 0) {
            salario = null;
        }

        System.out.print("Digite a data de contratacao: ");
        String stringData = sc.nextLine();
        LocalDate dataContratacao;

        if(stringData.equalsIgnoreCase("NULL"))
            dataContratacao = null;
        else
            dataContratacao = LocalDate.parse(stringData);

        List<Funcionario> lista = repository.findAll(Specification
                .where(FuncionarioSpecification.nome(nome))
                .or(FuncionarioSpecification.cpf(cpf))
                .or(FuncionarioSpecification.salario(salario))
                .or(FuncionarioSpecification.dataContratacao(dataContratacao))
        );

        System.out.println("Relatório de funcionários\n============================");
        lista.forEach(System.out::println);
        System.out.print("Tecle enter para continuar...");
        sc.nextLine();
    }
}
