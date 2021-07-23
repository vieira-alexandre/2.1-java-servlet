package br.com.alura.spring.data;

import br.com.alura.spring.data.servicos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private final CrudCargoService crudCargoService;
	private final CrudUnidadeTrabalhoService crudUnidadeTrabalhoService;
	private final CrudFuncionarioService crudFuncionarioService;
	private final RelatorioService relatorioService;
	private static ConfigurableApplicationContext ctx;
	private final RelatorioDinamicoService relatorioDinamicoService;

	public SpringDataApplication(CrudCargoService crudCargoService,
								 CrudUnidadeTrabalhoService crudUnidadeTrabalhoService,
								 CrudFuncionarioService crudFuncionarioService,
								 RelatorioService relatorioService,
								 RelatorioDinamicoService relatorioDinamicoService) {
		this.crudCargoService = crudCargoService;
		this.crudUnidadeTrabalhoService = crudUnidadeTrabalhoService;
		this.crudFuncionarioService = crudFuncionarioService;
		this.relatorioService = relatorioService;
		this.relatorioDinamicoService = relatorioDinamicoService;
	}

	public static void main(String[] args) {
		ctx = SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		boolean system = true;
		int action;

		do {
			System.out.println("Digite uma opção:\n=================");
			System.out.println("1 - Cargos");
			System.out.println("2 - Unidade de trabalho");
			System.out.println("3 - Funcionarios");
			System.out.println("4 - Relatórios");
			System.out.println("5 - Relatório dinamico");
			System.out.println("0 - Sair");
			action = sc.nextInt();
			System.out.println(action);
			sc.nextLine();

			switch (action) {
				case 1:
					crudCargoService.inicial(sc);
					break;

				case 2:
					crudUnidadeTrabalhoService.inicial(sc);
					break;

				case 3:
					crudFuncionarioService.inicial(sc);
					break;

				case 4:
					relatorioService.inicial(sc);
					break;

				case 5:
					relatorioDinamicoService.inicial(sc);
					break;

				default:
					system = false;
					break;
			}
		} while (system);

		sc.close();
		System.exit(0);
	}
}
