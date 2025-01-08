package construtora.model.dao;

import construtora.model.entity.Usuario;
import construtora.model.entity.Construtor;
import construtora.model.entity.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ConstrutorDAO construtorDAO = new ConstrutorDAO();
    
    private final String tableName = "funcionario";
    
    private Connection conexao;
    
    public FuncionarioDAO () {
        this.conexao = Conexao.getConexao();
    }
    
    public void create (Funcionario funcionario) {
        /* Como vai criar um novo funcionário, vamos adicioná-lo
        na tabela de usuários. */
        int usuarioId = this.usuarioDAO.create(funcionario);
        
        if (usuarioId == -1) {
            
        }
    }
    
    
    
    
    
    
    
    
    
    // Getters e setters
    public String getTableName() {
        return tableName;
    }
    
}
