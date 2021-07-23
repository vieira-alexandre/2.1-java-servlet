package br.com.alura.spring.data.servicos;

import br.com.alura.spring.data.dominio.entidades.Cargo;
import br.com.alura.spring.data.dominio.entidades.Funcionario;
import br.com.alura.spring.data.dominio.entidades.UnidadeTrabalho;
import br.com.alura.spring.data.dominio.repositorios.CargoRepository;
import br.com.alura.spring.data.dominio.repositorios.FuncionarioRepository;
import br.com.alura.spring.data.dominio.repositorios.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudFuncionarioService {

    private final FuncionarioRepository repository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
    private final CargoRepository cargoRepository;

    public CrudFuncionarioService(FuncionarioRepository repository, UnidadeTrabalhoRepository unidadeTrabalhoRepository, CargoRepository cargoRepository) {
        this.repository = repository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
        this.cargoRepository = cargoRepository;
    }

    public void inicial(Scanner sc) {
        boolean system = true;
        int action;

        do {
            System.out.println("Menu de Funcionarios\n=================");
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
        System.out.println("Id, CPF ou nome do funcionario:");
        boolean isNumero = false;
        Integer id = null;
        Optional<Funcionario> opt = null;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if(isNumero) {
            opt = repository.findById(id);
        }

        if(opt == null || !opt.isPresent()) {
            opt = repository.findByCpf(line);
        }

        if(opt == null || !opt.isPresent()) {
            opt = repository.findByNome(line);
        }

        if(opt.isPresent()) {
            System.out.println("Dados do funcionario:");
            System.out.println(opt.get());
        }
        else {
            System.out.println("Não encontrado");
        }

    }

    @Transactional
    private void excluir(Scanner sc) {
        System.out.println("Id ou nome do funcionario:");
        boolean isNumero = false;
        Integer id = null;
        Optional<Funcionario> opt = null;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if(isNumero) {
            opt = repository.findById(id);
        }

        if(opt == null || !opt.isPresent()) {
            opt = repository.findByCpf(line);
        }

        if(opt == null || !opt.isPresent()) {
            opt = repository.findByNome(line);
        }

        if(opt.isPresent()) {
            System.out.println("Dados do funcionario:");
            System.out.println(opt.get());
            System.out.print("Confirma a exclusão? Y / N: ");
            String confirmação = sc.nextLine();

            if(confirmação.equalsIgnoreCase("y")) {
                repository.delete(opt.get());
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
        Iterable<Funcionario> lista = repository.findAll();

        lista.forEach(x -> sb.append(x.toSimpleString() + "\n"));

        System.out.println("Lista de funcionarios\n===============");
        System.out.println(sb);
    }

    @Transactional
    private void salvar(Scanner sc) {
        Funcionario funci = new Funcionario();
        System.out.print("Nome: ");
        funci.setNome(sc.nextLine());

        System.out.print("CPF: ");
        funci.setCpf(sc.nextLine());

        System.out.print("Salario: ");
        funci.setSalario(BigDecimal.valueOf(sc.nextDouble()));
        sc.nextLine();

        System.out.print("Data da contratacao: ");
        funci.setDataContratacao(LocalDate.parse(sc.nextLine()));

        System.out.print("Id do cargo: ");
        Optional<Cargo> optCargo = cargoRepository.findById(sc.nextInt());
        sc.nextLine();

        while(!optCargo.isPresent()) {
            System.out.println("Cargo nao encontrado. Digite novamente.");
            optCargo = cargoRepository.findById(sc.nextInt());
            sc.nextLine();
        }

        funci.setCargo(optCargo.get());


        boolean mais = true;

        do {
            System.out.print("Id da Unidade de trabalho: ");
            Integer id = sc.nextInt();
            sc.nextLine();

            Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(id);

            while (!unidade.isPresent()) {
                System.out.println("Unidade de trabalho invalida, digite novamente.");
                System.out.print("Id da Unidade de trabalho: ");
                id = sc.nextInt();
                sc.nextLine();
                unidade = unidadeTrabalhoRepository.findById(id);
            }

            funci.addUnidadesTrabalho(unidade.get());

            System.out.print("Outra unidade de trabalho? Y / N: ");
            String line = sc.nextLine();

            if(!line.equalsIgnoreCase("y"))
                mais = false;

        }while (mais);


        repository.save(funci);
        System.out.println("Salvo");
    }

    @Transactional
    private void atualizar(Scanner sc) {
        System.out.println("Id, CPF ou nome do funcionario:");
        boolean isNumero = false;
        Integer id = null;
        Optional<Funcionario> opt = null;

        String line = sc.nextLine();

        try {
            id = Integer.parseInt(line);
            isNumero = true;
        } catch (NumberFormatException e) {

        }

        if(isNumero) {
            opt = repository.findById(id);
        }

        if(opt == null || !opt.isPresent()) {
            opt = repository.findByCpf(line);
        }

        if(opt == null || !opt.isPresent()) {
            opt = repository.findByNome(line);
        }

        if(opt.isPresent()) {
            Funcionario funci = opt.get();
            System.out.print("Nome: ");
            funci.setNome(sc.nextLine());

            System.out.print("CPF: ");
            funci.setCpf(sc.nextLine());

            System.out.print("Salario: ");
            funci.setSalario(BigDecimal.valueOf(sc.nextDouble()));
            sc.nextLine();

            System.out.print("Data da contratacao: ");
            funci.setDataContratacao(LocalDate.parse(sc.nextLine()));

            System.out.print("Id do cargo: ");
            Optional<Cargo> optCargo = cargoRepository.findById(sc.nextInt());
            sc.nextLine();

            while(!optCargo.isPresent()) {
                System.out.println("Cargo nao encontrado. Digite novamente.");
                optCargo = cargoRepository.findById(sc.nextInt());
                sc.nextLine();
            }

            funci.setCargo(optCargo.get());

            boolean mais = true;
            funci.limpaUnidadesTrabalho();

            do {
                System.out.print("Id da Unidade de trabalho: ");
                Integer idUnidade = sc.nextInt();
                sc.nextLine();

                Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(idUnidade);

                while (!unidade.isPresent()) {
                    System.out.println("Unidade de trabalho invalida, digite novamente.");
                    System.out.print("Id da Unidade de trabalho: ");
                    idUnidade = sc.nextInt();
                    sc.nextLine();
                    unidade = unidadeTrabalhoRepository.findById(idUnidade);
                }

                funci.addUnidadesTrabalho(unidade.get());

                System.out.print("Outra unidade de trabalho? Y / N: ");
                String line1 = sc.nextLine();

                if(!line1.equalsIgnoreCase("y"))
                    mais = false;

            }while (mais);


            repository.save(funci);
            System.out.println("Atualizado");
        }
        else {
            System.out.println("Não encontrado");
        }
    }
}
