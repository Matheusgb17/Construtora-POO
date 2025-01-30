package construtora.model.service;

import construtora.model.dao.ClienteDAO;
import construtora.model.dao.UsuarioDAO;
import construtora.model.entity.*;
import java.util.Scanner;
import static utils.PasswordUtils.criptografarSenha;


public class AdministradorService {
    Scanner scanner = new Scanner(System.in);
    ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastrarCliente (Cliente cliente) {

        //Informando os dados do cliente
        scanner.nextLine();
        System.out.println("Digite o nome do cliente: ");
        cliente.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do cliente: ");
        cliente.setCpf(scanner.nextLine());
        System.out.println("Digite o telefone do cliente: ");
        cliente.setTelefone(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = cliente.getTelefone().substring(cliente.getTelefone().length() - 4);

        //setando senha criptografada
        cliente.setSenha(senha);

        cliente.setStatus("ativo");

        int retornoId = clienteDAO.create(cliente);
        if (retornoId > 0) {
            cliente.setId(retornoId);
        }

    }

    public void alterarCliente (Cliente cliente) {

        //Informando os dados do cliente
        scanner.nextLine();
        System.out.println("Digite o nome do cliente: ");
        cliente.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do cliente: ");
        cliente.setCpf(scanner.nextLine());
        System.out.println("Digite o telefone do cliente: ");
        cliente.setTelefone(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = cliente.getTelefone().substring(cliente.getTelefone().length() - 4);

        //setando senha criptografada
        cliente.setSenha(senha);

        cliente.setStatus("ativo");

        int retornoId = clienteDAO.create(cliente);
        if (retornoId > 0) {
            cliente.setId(retornoId);
        }

    }

    public void buscarCliente (Cliente cliente) {

        //Informando os dados do cliente
        scanner.nextLine();
        System.out.println("Digite o nome do cliente: ");
        cliente.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do cliente: ");
        cliente.setCpf(scanner.nextLine());
        System.out.println("Digite o telefone do cliente: ");
        cliente.setTelefone(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = cliente.getTelefone().substring(cliente.getTelefone().length() - 4);

        //setando senha criptografada
        cliente.setSenha(senha);

        cliente.setStatus("ativo");

        int retornoId = clienteDAO.create(cliente);
        if (retornoId > 0) {
            cliente.setId(retornoId);
        }

    }

    public void excluirCliente (Cliente cliente) {

        //Informando os dados do cliente
        scanner.nextLine();
        System.out.println("Digite o nome do cliente: ");
        cliente.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do cliente: ");
        cliente.setCpf(scanner.nextLine());
        System.out.println("Digite o telefone do cliente: ");
        cliente.setTelefone(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = cliente.getTelefone().substring(cliente.getTelefone().length() - 4);

        //setando senha criptografada
        cliente.setSenha(senha);

        cliente.setStatus("ativo");

        int retornoId = clienteDAO.create(cliente);
        if (retornoId > 0) {
            cliente.setId(retornoId);
        }

    }
}
