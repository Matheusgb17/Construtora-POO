package construtora.view;

import construtora.model.dao.ClienteDAO;
import construtora.model.entity.Usuario;
import construtora.model.dao.UsuarioDAO;
import construtora.model.entity.Cliente;

public class Main {

    public static void main(String[] args) {
        /*System.out.println("Hello World!");
        //Usuario carlin = new Usuario(12, "Carlinhos", "12312312311", "(11)96640-1121", "Inhai123", "Operador");
        UsuarioDAO dao = new UsuarioDAO();
        //System.out.println(dao.create(carlin));
        Usuario user = dao.find("12312312311");
        System.out.println(user.getNome()); */
        
        Cliente saiko = new Cliente("Ativo", 0, "SaikoMene", "22222222222", "11999999999", "Hello", "Cliente");
        ClienteDAO cdao = new ClienteDAO();
        cdao.create(saiko);
    }
}
