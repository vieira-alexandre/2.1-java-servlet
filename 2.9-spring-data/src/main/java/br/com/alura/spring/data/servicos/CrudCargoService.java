package br.com.alura.spring.data.servicos;

import br.com.alura.spring.data.dominio.entidades.Cargo;
import br.com.alura.spring.data.dominio.repositorios.CargoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CrudCargoService {
    private final CargoRepository repository;

    public CrudCargoService(CargoRepository repository) {
        this.repository = repository;
    }

    public void inicial(Scanner sc) {
        boolean system = true;
        int action;

        do {
            System.out.println("Menu de Cargos\n=================");
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
        System.out.println("Id ou nome do cargo:");
        boolean isNumero = false;
        Integer id = null;
        Optional<Cargo> opt;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if (isNumero) {
            opt = repository.findById(id);
        } else
            opt = repository.findByDescricao(line);

        if (opt.isPresent()) {
            System.out.println("Dados do cargo:");
            Cargo cargo = opt.get();
            System.out.println(cargo);
        } else {
            System.out.println("Não encontrado");
        }

    }

    @Transactional
    private void excluir(Scanner sc) {
        System.out.println("Id ou nome do cargo:");
        boolean isNumero = false;
        Integer id = null;
        Optional<Cargo> opt;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if (isNumero) {
            opt = repository.findById(id);
        } else
            opt = repository.findByDescricao(line);

        if (opt.isPresent()) {
            System.out.println("Dados do cargo:");
            Cargo cargo = opt.get();
            System.out.println(cargo);
            System.out.print("Confirma a exclusão? Y / N: ");
            String confirmação = sc.nextLine();

            if (confirmação.equalsIgnoreCase("y")) {
                repository.delete(cargo);
                System.out.println("Excluido");
            } else {
                System.out.println("Não excluido");
            }
        } else {
            System.out.println("Não encontrado");
        }
    }

    private void listar(Scanner sc) {
        StringBuilder sb = new StringBuilder();
        Iterable<Cargo> lista = repository.findAll();

        lista.forEach(x -> sb.append(x + "\n"));

        System.out.println("Lista de cargos\n===============");
        System.out.println(sb);
    }

    @Transactional
    private void salvar(Scanner sc) {
        System.out.print("Descricao do cargo: ");
        Cargo cargo = new Cargo();
        cargo.setDescricao(sc.nextLine());
        repository.save(cargo);
        System.out.println("Salvo");
    }

    @Transactional
    private void atualizar(Scanner sc) {
        System.out.print("Id ou nome atual do cargo:");
        boolean isNumero = false;
        Integer id = null;
        Optional<Cargo> opt;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if (isNumero) {
            opt = repository.findById(id);
        } else
            opt = repository.findByDescricao(line);

        if (opt.isPresent()) {
            System.out.print("Descricao do cargo: ");
            Cargo cargo = opt.get();
            cargo.setDescricao(sc.nextLine());
            repository.save(cargo);
            System.out.println("Atualizado");
        } else {
            System.out.println("Não encontrado");
        }

    }
}
