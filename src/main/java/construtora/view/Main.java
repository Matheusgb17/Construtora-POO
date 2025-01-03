package construtora.view;

import construtora.model.entity.Usuario;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Usuario carlin = new Usuario(12, "Carlinhos", "444.334.122-12", "(11)96640-1121", "Inhai123", "Operador");
        System.out.println("Senha: " + carlin.getSenha());
    }
}
