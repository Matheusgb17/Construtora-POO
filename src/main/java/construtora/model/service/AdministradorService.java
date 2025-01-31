package construtora.model.service;

import construtora.model.dao.ClienteDAO;
import construtora.model.dao.ConstrutorDAO;
import construtora.model.dao.FuncionarioDAO;
import construtora.model.dao.UsuarioDAO;
import construtora.model.entity.*;
import java.util.Scanner;
import static utils.PasswordUtils.criptografarSenha;


public class AdministradorService {
    Scanner scanner = new Scanner(System.in);
    ClienteDAO clienteDAO = new ClienteDAO();
    ConstrutorDAO construtorDAO = new ConstrutorDAO();
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();



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

    public void cadastrarFuncionario (Funcionario funcionario, Construtor construtor) {


        funcionario.setConstrutor(construtor);

        //Informando os dados do funcionario
        System.out.println("Digite o nome do funcionario: ");
        funcionario.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do funcionario: ");
        funcionario.setCpf(scanner.nextLine());
        System.out.println("Digite o telefone do funcionario: ");
        funcionario.setTelefone(scanner.nextLine());
        System.out.println("Digite o cargo do funcionario: ");
        funcionario.setCargo(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do funcionario, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = funcionario.getTelefone().substring(funcionario.getTelefone().length() - 4);

        //setando senha criptografada
        funcionario.setSenha(senha);


        int retornoId = funcionarioDAO.create(funcionario);
        if (retornoId > 0) {
            funcionario.setId(retornoId);
        }
    }

    public void cadastrarConstrutor (Construtor construtor) {

        //Informando os dados do cliente
        scanner.nextLine();
        System.out.println("Digite o nome do construtor: ");
        construtor.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do construtor: ");
        construtor.setCpf(scanner.nextLine());
        System.out.println("Digite o telefone do construtor: ");
        construtor.setTelefone(scanner.nextLine());
        System.out.println("Digite o tipo de serviço do construtor: ");
        construtor.setTipoServico(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = construtor.getTelefone().substring(construtor.getTelefone().length() - 4);

        //setando senha criptografada
        construtor.setSenha(senha);



        int retornoId = construtorDAO.create(construtor);
        if (retornoId > 0) {
            construtor.setId(retornoId);
        }

    }

}
