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
        
        LoginController lc = new LoginController();
        UsuarioController uc = new UsuarioController();
        AdministradorController ac = new AdministradorController();
        ClienteController cc = new ClienteController();
        ConstrutorController consc = new ConstrutorController();
        EngenheiroController ec = new EngenheiroController();
        FuncionarioController fc = new FuncionarioController();
        
        
        Usuario usuarioLogado = new Usuario();
        Administrador administradorLogado = new Administrador();
        Cliente clienteLogado = new Cliente();
        Construtor construtorLogado = new Construtor();
        Engenheiro engenheiroLogado = new Engenheiro();
        Funcionario funcionarioLogado = new Funcionario();
        
        int opcao = 0;
        
        /* 30% de chance de adicionar novos dados automáticos ao banco. */
        /*if (Math.random() < 0.30) {
            Seeder seeder = new Seeder();
            seeder.run();
        }*/
        
        System.out.println("===== CONSTRUTORA =====");
        
        /* Login. */
        do {
            System.out.println("1 - Login");
            System.out.println("2 - Sair");
            System.out.print("Digite: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1:
                    usuarioLogado = lc.login();
                    
                    opcao = usuarioLogado != null ? 0 : 1;
                break;
                
                case 2:
                    System.out.println("Saindo!");
                    System.exit(0);
                break;
            }
        }
        while (opcao != 2 && opcao != 0);
        
        /* Agora, separamos as funcionalidades de acordo com o papel do usuário. */
        switch (usuarioLogado.getPapel()) {
            case "administrador":
                MenuUtils.administradorMainMenu();
                System.out.print("Digite: ");
                opcao = scanner.nextInt();
            break;
            case "cliente":
                MenuUtils.clienteMainMenu();
                System.out.print("Digite: ");
                opcao = scanner.nextInt();
            break;
            case "construtor":
                MenuUtils.construtorMainMenu();
                System.out.print("Digite: ");
                opcao = scanner.nextInt();
            break;
            case "engenheiro":
                MenuUtils.engenheiroMainMenu();
                System.out.print("Digite: ");
                opcao = scanner.nextInt();
            break;
            case "funcionario":
                MenuUtils.funcionarioMainMenu();
                System.out.print("Digite: ");
                opcao = scanner.nextInt();
            break;
        }
    }
}
