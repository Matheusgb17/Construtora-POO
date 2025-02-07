package construtora.controller;

import utils.*;
import construtora.model.service.*;
import construtora.model.entity.*;
import java.util.Scanner;

public class LoginController {
    private UsuarioService us = new UsuarioService();
    private Scanner scanner = new Scanner(System.in);
    
    public Usuario login() {
        System.out.println("===== LOGIN =====");

        System.out.print("CPF: ");
        String cpf = this.scanner.nextLine();

        System.out.print("Senha: ");
        String senha = this.scanner.nextLine();
        
        if (autenticar(cpf, senha)) {
            Usuario u = us.buscarUsuario(cpf);
            System.out.println(u.toString());
        }
        
        return null;
    }
    
    public boolean autenticar(String cpf, String senha) {
        Usuario usuario = this.us.buscarUsuario(cpf);

        if (usuario == null) {
            return false;
        }
        
        System.out.println(usuario.getSenha());

        //String senhaCriptografada = PasswordUtils.criptografarSenha(senha);
        
        String senhaCriptografada = PasswordUtils.criptografarSenha("15E2B0D3C33891EBB0F1EF609EC419420C20E320CE94C65FBC8C3312448EB225");
        
        System.out.println(senhaCriptografada);
        
        //System.out.println(senhaCriptografada.equals(usuario.getSenha()));
        
        return false;
    }
}
