package construtora.view;

import construtora.model.entity.Usuario;

import java.util.Scanner;

public class papelMenu {
    static Scanner scanner = new Scanner(System.in);
    public static void menu(Usuario usuario) {
        System.out.println("\nMenu " + usuario.getPapel());

        switch (usuario.getPapel()) {
            case "administrador":
                System.out.println("1 - Cadastros");
                System.out.println("2 - Obras");
                System.out.println("3 - Contratos");
                System.out.println("4 - Sair");
            break;

            case "engenheiro":
                //opcoes engenheiro
            break;

            case "construtor":
                //opcoes construtor
            break;

            case "cliente":
                //opcoes cliente
            break;
            default:
                System.out.println("Acesso negado!");
            break;
        }

    }

    public static int opcaoCadastroAdmin() {
        int opcao;
        do {
            System.out.println("Selecione o tipo de usuario: ");
            System.out.println("1 - Cliente");
            System.out.println("2 - Funcionario ");
            System.out.println("3 - Construtor");
            System.out.println("4 - Engenheiro");
            System.out.println("5 - Sair");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n1 - Adicionar cliente ");
                    System.out.println("2 - Alterar cliente ");
                    System.out.println("3 - Buscar cliente ");
                    System.out.println("4 - Excluir cliente ");
                    System.out.println("5 - Sair");
                    return opcao;
                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    System.out.println("Retornando...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        } while(opcao !=5);

        return opcao;
    }

}
