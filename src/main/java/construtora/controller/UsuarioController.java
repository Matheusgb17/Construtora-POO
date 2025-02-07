package construtora.controller;

import construtora.model.dao.ConstrutorDAO;
import construtora.model.dao.FuncionarioDAO;
import construtora.model.entity.Cliente;
import construtora.model.entity.Construtor;
import construtora.model.entity.Funcionario;
import construtora.model.entity.Usuario;
import construtora.model.service.AdministradorService;
import construtora.view.papelMenu;

import java.util.Objects;
import java.util.Scanner;


import static construtora.view.papelMenu.consultarConstrutorCpf;


public class UsuarioController {
    Scanner scanner = new Scanner(System.in);
    AdministradorService adminService = new AdministradorService();
    Cliente cliente = new Cliente();
    Funcionario funcionario = new Funcionario();
    Construtor construtor = new Construtor();
    ConstrutorDAO construtorDAO = new ConstrutorDAO();

    public void uController(Usuario usuario) {
        int opcao;

        do {
            papelMenu.menu(usuario);
            opcao = scanner.nextInt();
            if (opcao == 4) {
                break;
            }
            switch (usuario.getPapel()) {
                case "administrador", "construtor":
                    opcao = papelMenu.opcaoCadastroAdmin(usuario);
                    if (opcao == 1) {
                        adminService.cadastrarCliente(cliente);
                    } else  if (opcao == 2) {
                        adminService.cadastrarConstrutor(construtor);
                    } else  if (opcao == 3) {
                        if (Objects.equals(usuario.getPapel(), "administrador")) {
                            String cpf = papelMenu.consultarConstrutorCpf();
                            construtor = construtorDAO.find(cpf);
                            adminService.cadastrarFuncionario(funcionario, construtor);
                        } else {
                            construtor = construtorDAO.find(usuario.getId());
                            adminService.cadastrarFuncionario(funcionario, construtor);
                        }
                    }
                    break;
                case "funcionario":
                    //controle de funções do funcionario

                    break;
                case "engenheiro":
                    //controle de funções do engenheiro

                    break;
                case "cliente":
                    //controle de funções do cliente

                    break;
                
                default:

                    break;
            }
        } while (true);

    }
}
