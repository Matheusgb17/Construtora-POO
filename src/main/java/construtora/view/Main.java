package construtora.view;

import java.util.*;
import com.mysql.cj.log.Log;
import construtora.model.service.*;
import construtora.model.entity.*;
import construtora.model.dao.*;
import construtora.controller.*;
import utils.*;


public class Main {
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        LoginController loginController = new LoginController();
        UsuarioController usuarioController = new UsuarioController();
        AdministradorController administradorController = new AdministradorController();
        ClienteController clienteController = new ClienteController();
        ConstrutorController construtorController = new ConstrutorController();
        EngenheiroController engenheiroController = new EngenheiroController();
        FuncionarioController funcionarioController = new FuncionarioController();
        
        
        Usuario usuarioLogado = new Usuario();
        Administrador administradorLogado = new Administrador();
        Cliente clienteLogado = new Cliente();
        Construtor construtorLogado = new Construtor();
        Engenheiro engenheiroLogado = new Engenheiro();
        Funcionario funcionarioLogado = new Funcionario();
        
        int opcao = 0;
        
        /* Antes de começar, rodamos o seeder para garantir que ao menos
         * o administrador padrão estará cadastrado. */
        if (administradorController.buscarAdministrador("99999999999") == null) {
            Seeder seeder = new Seeder();
            seeder.run();
        }
        
        System.out.println("===== CONSTRUTORA =====");
        
        /* Processo de login. */
        usuarioLogado = loginController.opcoesLogin();
        
        // Verifica se usuarioLogado não é nulo antes de chamar qualquer método nele
        if (usuarioLogado == null || usuarioLogado.getPapel() == null) {
            System.out.println("Erro: Usuário inválido! Finalizando...");
            System.exit(1);
        }
        
        /* Agora, separamos as funcionalidades de acordo com o papel do usuário. */
        do {
            switch (usuarioLogado.getPapel()) {
                case "administrador" -> {
                    administradorLogado = loginController.recuperarAdministradorLogado(usuarioLogado);
                    MenuUtils.administradorMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 9);
                    scanner.nextLine(); // Limpando o buffer antes de entrar na ação
                    administradorController.executarAcaoAdministrador(opcao, administradorLogado);
                }
                
                case "cliente" -> {
                    clienteLogado = loginController.recuperarClienteLogado(usuarioLogado);
                    MenuUtils.clienteMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 3);
                    scanner.nextLine(); // Limpando o buffer antes de entrar na ação
                    clienteController.executarAcaoCliente(opcao, clienteLogado);
                }
                
                case "construtor" -> {
                    construtorLogado = loginController.recuperarConstrutorLogado(usuarioLogado);
                    MenuUtils.construtorMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 1);
                    scanner.nextLine(); // Limpando o buffer antes de entrar na ação
                    construtorController.executarAcaoConstrutor(opcao, construtorLogado);
                }
                
                case "engenheiro" -> {
                    engenheiroLogado = loginController.recuperarEngenheiroLogado(usuarioLogado);
                    MenuUtils.engenheiroMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 1);
                    scanner.nextLine(); // Limpando o buffer antes de entrar na ação
                    engenheiroController.executarAcaoEngenheiro(opcao, engenheiroLogado);
                }
                
                case "funcionario" -> {
                    funcionarioLogado = loginController.recuperarFuncionarioLogado(usuarioLogado);
                    MenuUtils.funcionarioMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 1);
                    scanner.nextLine(); // Limpando o buffer antes de entrar na ação
                    funcionarioController.executarAcaoFuncionario(opcao, funcionarioLogado);
                }
            }
        } while (opcao != 0);
    }
    
    
}
