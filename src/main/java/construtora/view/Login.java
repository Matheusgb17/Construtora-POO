package construtora.view;

import java.util.*;

import construtora.model.service.*;
import construtora.model.entity.*;
import construtora.model.dao.*;
import construtora.controller.*;

import static utils.PasswordUtils.criptografarSenha;
public class Login {

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        LoginDAO loginDAO = new LoginDAO();
        AdministradorService administradorService = new AdministradorService();
        UsuarioController usuarioController = new UsuarioController();
        Cliente cliente = new Cliente();
        int opcao;
        do {
            System.out.println("Bem-vindo ao Sistema!\n");
            System.out.println("1 - Login.");
            System.out.println("2 - Encerrar Sistema.");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    scanner.nextLine();
                    // Solicita CPF e senha
                    System.out.print("Informe seu CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Informe sua senha: ");
                    String senha = scanner.nextLine();

                    //criptografa a senha digitada
                    senha = criptografarSenha(senha);

                    // Verifica a autenticação no Banco de Dados com a senha criptografada
                    Usuario usuario = loginDAO.autenticar(cpf, senha);

                    if (usuario != null) {
                        System.out.println("Login realizado com sucesso!");
                        System.out.println("Bem-vindo, " + usuario.getNome());

                        //redirecionar para o sistema após o login
                        switch (usuario.getPapel()) {
                            case "cliente":
                                //controller de cliente
                                usuarioController.uController(usuario);
                                break;
                            case "engenheiro":
                                //controller de engenheiro
                                usuarioController.uController(usuario);
                                break;
                            case "construtor":
                                //controller de construtor
                                usuarioController.uController(usuario);
                                break;
                            case "administrador":
                                //cpmtroller de administrador
                                usuarioController.uController(usuario);
                                break;
                            default:
                                System.out.println("Acesso negado.\n");
                        }

                    } else {
                        System.out.println("CPF ou senha inválidos!\n");
                    }
                break;
                case 2:
                    System.out.print("Saindo...");
                break;
            }
        } while (opcao != 2);

        scanner.close();
    }
}
