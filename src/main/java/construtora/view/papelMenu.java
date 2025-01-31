package construtora.view;

import construtora.model.entity.Usuario;

import java.util.Scanner;

public class papelMenu {
    static Scanner scanner = new Scanner(System.in);
    public static void menu(Usuario usuario) {
        System.out.println("\nMenu " + usuario.getPapel());

        switch (usuario.getPapel()) {
            case "administrador", "construtor":
                System.out.println("1 - Cadastros");
                System.out.println("2 - Obras");
                System.out.println("3 - Contratos");
                System.out.println("4 - Sair");
                break;

            case "engenheiro":
                //opcoes engenheiro
                break;

            case "cliente":
                //opcoes cliente
                break;
            default:
                System.out.println("Acesso negado!");
                break;
        }

    }

    public static int opcaoCadastroAdmin(Usuario usuario) {
        int opcao;
        do {
            System.out.println("Selecione o tipo de usuario: ");
            System.out.println("1 - Cliente");
            System.out.println("2 - Construtor");
            System.out.println("3 - Funcionario");
            System.out.println("4 - Engenheiro");
            System.out.println("5 - Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n1 - Adicionar cliente ");
                    System.out.println("2 - Sair");
                    opcao = scanner.nextInt();
                    return opcao;
                case 2:
                    System.out.println("\n1 - Adicionar construtor ");
                    System.out.println("2 - Sair");
                    opcao = scanner.nextInt();
                    if(opcao == 1) {
                        return 2;
                    } else if(opcao == 2) {
                        break;
                    }
                    return opcao;

                case 3:
                    System.out.println("\n1 - Adicionar funcionario ");
                    System.out.println("2 - Sair");
                    opcao = scanner.nextInt();
                    if(opcao == 1) {
                        return 3;
                    } else if(opcao == 2) {
                        break;
                    }
                    return opcao;

                case 4:

                    break;

                case 5:
                    System.out.println("Retornando...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        } while(opcao != 5);

        return opcao;
    }

    public static String consultarConstrutorCpf() {
        System.out.println("Digite o CPF do Construtor: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

}
