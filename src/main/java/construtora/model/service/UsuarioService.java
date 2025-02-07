package construtora.model.service;

import construtora.model.entity.Usuario;
import construtora.model.dao.UsuarioDAO;

public class UsuarioService {
    private UsuarioDAO ud = new UsuarioDAO();
    
    public Usuario buscarUsuario (String cpf) {
        Usuario usuario = this.ud.find(cpf);
        
        return usuario;
    }
}
