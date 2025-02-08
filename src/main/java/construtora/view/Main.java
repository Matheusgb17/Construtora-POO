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
        
        /* Processo de login. */
        usuarioLogado = lc.opcoesLogin();
        
        /* Agora, separamos as funcionalidades de acordo com o papel do usuário. */
        do {
            switch (usuarioLogado.getPapel()) {
                case "administrador" -> {
                    administradorLogado = lc.recuperarAdministradorLogado(usuarioLogado);
                    MenuUtils.administradorMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 9);
                    ac.executarAcaoAdministrador(opcao, administradorLogado);
                }
                
                case "cliente" -> {
                    MenuUtils.clienteMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 3);
                }
                
                case "construtor" -> {
                    construtorLogado = lc.recuperarConstrutorLogado(usuarioLogado);
                    MenuUtils.construtorMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 1);
                    consc.executarAcaoConstrutor(opcao, construtorLogado);
                }
                
                case "engenheiro" -> {
                    engenheiroLogado = lc.recuperarEngenheiroLogado(usuarioLogado);
                    MenuUtils.engenheiroMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 1);
                    ec.executarAcaoEngenheiro(opcao, engenheiroLogado);
                }
                
                case "funcionario" -> {
                    funcionarioLogado = lc.recuperarFuncionarioLogado(usuarioLogado);
                    MenuUtils.funcionarioMainMenu();
                    opcao = MenuUtils.selecionarOpcao(0, 1);
                }
            }
        } while (opcao != 0);
    }
    
    
}
