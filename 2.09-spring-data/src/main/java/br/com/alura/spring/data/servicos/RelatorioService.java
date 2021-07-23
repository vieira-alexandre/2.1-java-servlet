package br.com.alura.spring.data.servicos;

import br.com.alura.spring.data.dominio.entidades.Funcionario;
import br.com.alura.spring.data.dominio.projecoes.FuncionarioProjecao;
import br.com.alura.spring.data.dominio.repositorios.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {
    private final FuncionarioRepository repository;

    public RelatorioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public void inicial(Scanner sc) {
        boolean system = true;
        int action;

        do {
            System.out.println("Menu de relatórios\n=======================");
            System.out.println("1 - Funcionario por data de contratacao");
            System.out.println("2 - Funcionario com salarios");
            System.out.println("0 - Sair");
            action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1:
                    buscaFuncionarioPorDataContratacao(sc);
                    break;

                case 2:
                    listaFuncionarioPorSalario(sc);
                    break;

                default:
                    system = false;
                    break;
            }

            if (system) {
                System.out.print("Pressione enter para continuar...");
                sc.nextLine();
                System.out.println("\n");
            }
        } while (system);
    }

    private void listaFuncionarioPorSalario(Scanner sc) {
        List<FuncionarioProjecao> lista = repository.listaFuncionarioSalario();
        System.out.println("Lista de funcionarios com seus salarios\n=========================================");



        lista.forEach(x -> {
            System.out.printf("Funcionario{id=%d, nome=\'%s\', salario=%.2f}\n", x.getId(), x.getNome(), x.getSalario());
        });

        System.out.println("\n");
    }

    public void buscaFuncionarioPorDataContratacao(Scanner sc) {
        System.out.print("Filtrar a partir de qual data de contratacao: ");
        LocalDate data = LocalDate.parse(sc.nextLine());

        List<Funcionario> funcis = repository.buscarDataContratacaoMaior(data);

        System.out.println("Funcionarios contratados após: " + data + "\n=========================================");
        System.out.println(Arrays.toString(funcis.toArray()) + "\n");
    }
}
