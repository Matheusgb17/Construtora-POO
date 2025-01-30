package construtora.controller;

import construtora.model.entity.Cliente;
import construtora.model.entity.Usuario;
import construtora.model.service.AdministradorService;
import construtora.view.papelMenu;

import java.util.Scanner;


public class UsuarioController {
    Scanner scanner = new Scanner(System.in);
    AdministradorService adminService = new AdministradorService();
    Cliente cliente = new Cliente();

    public void uController(Usuario usuario) {
        int opcao;

        do {
            papelMenu.menu(usuario);
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    int opcao1 = papelMenu.opcaoCadastroAdmin();
                    if (opcao1 == 1) {
                        adminService.cadastrarCliente(cliente);
                    } else if (opcao1 == 2) {

                    } else if (opcao1 == 3) {

                    } else if (opcao1 == 4) {

                    } else {
                        break;
                    }
                    break;

            }
        } while (opcao != 4);

    }

}
