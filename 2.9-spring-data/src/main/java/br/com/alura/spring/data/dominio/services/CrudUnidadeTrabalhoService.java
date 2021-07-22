package br.com.alura.spring.data.dominio.services;

import br.com.alura.spring.data.dominio.model.UnidadeTrabalho;
import br.com.alura.spring.data.dominio.repositories.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudUnidadeTrabalhoService {
    private final UnidadeTrabalhoRepository repository;

    public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository repository) {
        this.repository = repository;
    }

    public void inicial(Scanner sc) {
        boolean system = true;
        int action;

        do {
            System.out.println("Menu de Unidade de Trabalho\n=======================");
            System.out.println("1 - Listar");
            System.out.println("2 - Incluir");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("5 - Detalhar");
            System.out.println("0 - Sair");
            action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1:
                    listar(sc);
                    break;
                case 2:
                    salvar(sc);
                    break;

                case 3:
                    atualizar(sc);
                    break;

                case 4:
                    excluir(sc);
                    break;

                case 5:
                    detalhar(sc);
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

    private void detalhar(Scanner sc) {
        System.out.println("Id ou nome da Unidade:");
        boolean isNumero = false;
        Integer id = null;
        Optional<UnidadeTrabalho> opt;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if(isNumero) {
            opt = repository.findById(id);
        } else
            opt = repository.findByDescricao(line);

        if(opt.isPresent()) {
            System.out.println("Dados da Unidade:");
            UnidadeTrabalho unidadeTrabalho = opt.get();
            System.out.println(unidadeTrabalho);
        }
        else {
            System.out.println("Não encontrado");
        }

    }

    @Transactional
    private void excluir(Scanner sc) {
        System.out.println("Id ou nome da Unidade:");
        boolean isNumero = false;
        Integer id = null;
        Optional<UnidadeTrabalho> opt;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if(isNumero) {
            opt = repository.findById(id);
        } else
            opt = repository.findByDescricao(line);

        if(opt.isPresent()) {
            System.out.println("Dados da Unidade:");
            UnidadeTrabalho unidadeTrabalho = opt.get();
            System.out.println(unidadeTrabalho);
            System.out.print("Confirma a exclusão? Y / N: ");
            String confirmação = sc.nextLine();

            if(confirmação.equalsIgnoreCase("y")) {
                repository.delete(unidadeTrabalho);
                System.out.println("Excluido");
            }
            else {
                System.out.println("Não excluido");
            }
        }
        else {
            System.out.println("Não encontrado");
        }
    }

    private void listar(Scanner sc) {
        StringBuilder sb = new StringBuilder();
        Iterable<UnidadeTrabalho> lista = repository.findAll();

        lista.forEach(x -> sb.append(x + "\n"));

        System.out.println("Lista de Unidades\n===================");
        System.out.println(sb);
    }

    @Transactional
    private void salvar(Scanner sc) {
        UnidadeTrabalho unidade = new UnidadeTrabalho();

        System.out.print("Descricao da Unidade: ");
        unidade.setDescricao(sc.nextLine());
        System.out.print("Endereco da Unidade: ");
        unidade.setEndereco(sc.nextLine());

        repository.save(unidade);
        System.out.println("Salvo");
    }

    @Transactional
    private void atualizar(Scanner sc) {
        System.out.print("Id ou nome atual da Unidade:");
        boolean isNumero = false;
        Integer id = null;
        Optional<UnidadeTrabalho> opt;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if(isNumero) {
            opt = repository.findById(id);
        } else
            opt = repository.findByDescricao(line);

        if(opt.isPresent()) {
            UnidadeTrabalho unidade = opt.get();
            System.out.print("Descricao da Unidade: ");
            unidade.setDescricao(sc.nextLine());

            System.out.print("Endereco da Unidade: ");
            unidade.setEndereco(sc.nextLine());

            repository.save(unidade);
            System.out.println("Atualizado");
        }
        else {
            System.out.println("Não encontrado");
        }

    }
}
