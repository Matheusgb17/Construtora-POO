package construtora.controller;

import utils.*;
import construtora.model.service.*;
import construtora.model.entity.*;
import java.util.Scanner;

public class LoginController {
    private UsuarioService us = new UsuarioService();
    private EngenheiroService es = new EngenheiroService();
    private Scanner scanner = new Scanner(System.in);
    
    public Usuario login() {
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
}
