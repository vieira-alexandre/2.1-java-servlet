package br.com.alura.spring.data.dominio.services;

import br.com.alura.spring.data.dominio.model.Funcionario;
import br.com.alura.spring.data.dominio.repositories.FuncionarioRepository;
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
            System.out.println("0 - Sair");
            action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1:
                    buscaFuncionarioPorDataContratacao(sc);
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

    public void buscaFuncionarioPorDataContratacao(Scanner sc) {
        System.out.print("Filtrar a partir de qual data de contratacao: ");
        LocalDate data = LocalDate.parse(sc.nextLine());

        List<Funcionario> funcis = repository.buscarDataContratacaoMaior(data);

        System.out.println("Funcionarios contratados após: " + data + "\n=========================================");
        System.out.println(Arrays.toString(funcis.toArray()) + "\n");
    }
}
