package construtora.controller;

import utils.*;
import construtora.model.service.*;
import construtora.model.entity.*;
import java.util.Scanner;

public class LoginController {
    private UsuarioService us = new UsuarioService();
    private EngenheiroService es = new EngenheiroService();
    private ConstrutorService cons = new ConstrutorService();
    private FuncionarioService fs = new FuncionarioService();
    private AdministradorService as = new AdministradorService();
    private ClienteService cs = new ClienteService();
    private Scanner scanner = new Scanner(System.in);
    
    public Usuario opcoesLogin () {
        Usuario usuarioLogado = new Usuario();
        int opcao;
        
        do {
            System.out.println("1 - Login");
            System.out.println("2 - Sair");
            System.out.print("Digite: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1 -> {
                    usuarioLogado = this.login();
                }
                
                case 2 -> {
                    System.out.println("Saindo!");
                    System.exit(0);
                }
            }
        }
        while (opcao != 2 && usuarioLogado == null);
        
        return usuarioLogado;
    }
    
    public Usuario login() {
        /* Limpando o buffer do teclado. */
        if (scanner.hasNextLine()) scanner.nextLine();
        
        System.out.println("===== LOGIN =====");

        System.out.print("CPF: ");
        String cpf = this.scanner.nextLine();

        System.out.print("Senha: ");
        String senha = this.scanner.nextLine();
        
        if (autenticar(cpf, senha)) {
            Usuario u = us.buscarUsuario(cpf);
            return u;
        }
        
        return null;
    }
    
    public boolean autenticar(String cpf, String senha) {
        Usuario usuario = this.us.buscarUsuario(cpf);

        if (usuario == null) {
            return false;
        }

        String senhaCriptografada = PasswordUtils.criptografarSenha(senha);
        
        return senhaCriptografada.equals(usuario.getSenha());
    }
    
    public Engenheiro recuperarEngenheiroLogado (Usuario usuarioLogado) {
        Engenheiro engenheiroLogado = es.recuperarEngenheiro(usuarioLogado.getCpf());
        return engenheiroLogado;
    }
    
    public Construtor recuperarConstrutorLogado (Usuario usuarioLogado) {
        Construtor construtorLogado = cons.recuperarConstrutor(usuarioLogado.getCpf());
        return construtorLogado;
    }
    
    public Funcionario recuperarFuncionarioLogado (Usuario usuarioLogado) {
        Funcionario funcionarioLogado = fs.recuperarFuncionario(usuarioLogado.getCpf());
        return funcionarioLogado;
    }
    
    public Administrador recuperarAdministradorLogado (Usuario usuarioLogado) {
        Administrador administradorLogado = as.recuperarAdministrador(usuarioLogado.getCpf());
        return administradorLogado;
    }
    
    public Cliente recuperarClienteLogado (Usuario usuarioLogado){
        Cliente clienteLogado = cs.recuperarCliente(usuarioLogado.getCpf());
        return clienteLogado;
    }

    
}
