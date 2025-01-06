package construtora.model.dao;

import construtora.model.entity.Cliente;
import construtora.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    /**
     * Variável necessária para acessar a tabela de usuário referente ao cliente, uma vez que este é herança.
     */
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    private final String tableName = "cliente";
    
    /**
     * Adicionar um cliente ao banco de dados
     * @param cliente TAD do cliente a ser inserido
     */
    public void create (Cliente cliente) {
        /* Inserindo os dados base na tabela de usuários.
         * Isso é possível pois Cliente herda de Usuario. */
        int usuarioId = this.usuarioDAO.create(cliente);
        
        if (usuarioId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA DE USUÁRIOS ===");
            return;
        }
        
        String sql = "INSERT INTO " + this.tableName + " (usuario_id, status) VALUES (?, ?);";
        
        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {
            
            stmt.setInt(1, usuarioId);
            stmt.setString(2, cliente.getStatus());
            
            stmt.executeUpdate();
            
            System.out.println("Cliente inserido com sucesso!");
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna um cliente com base no ID dele
     * @param id ID do cliente que quer se achar
     * @return TAD do cliente achado ou NULL
     */
    public Cliente find (int id) {
        String sqlUsuario = "SELECT * FROM " + this.usuarioDAO.getTableName() + " WHERE id = ?";
        String sqlCliente = "SELECT * FROM " + this.getTableName() + " WHERE usuario_id = ?";
        
        try (
                Connection conn = Conexao.getConexao();
                PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
                PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {

            stmtUsuario.setInt(1, id);
            ResultSet rsUsuario = stmtUsuario.executeQuery();
            if (!rsUsuario.next()) return null;

            // Buscar na tabela `clientes`
            stmtCliente.setInt(1, id);
            ResultSet rsCliente = stmtCliente.executeQuery();
            
            if (rsCliente.next()) {
                Cliente cliente = new Cliente(
                    rsUsuario.getString("status"), 
                    rsUsuario.getInt("id"), 
                    rsUsuario.getString("nome"), 
                    rsUsuario.getString("cpf"), 
                    rsUsuario.getString("telefone"), 
                    rsUsuario.getString("senha"), 
                    rsUsuario.getString("papel")
                );
                
                return cliente;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Retorna um cliente com base no CPF dele
     * @param cpf CPF do cliente que quer se achar
     * @return TAD do cliente achado ou NULL
     */
    public Cliente find (String cpf) {
        String sqlUsuario = "SELECT * FROM " + this.usuarioDAO.getTableName() + " WHERE id = ?";
        String sqlCliente = "SELECT * FROM " + this.getTableName() + " WHERE usuario_id = ?";
        
        try (
                Connection conn = Conexao.getConexao();
                PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
                PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
            
            /* Devemos primeiro achar o ID do usuário para achar o
            registro correspondente na tabela de clientes. */
            Usuario user = this.usuarioDAO.find(cpf);
            int id = user.getId();
            
            // Busca na tabela de usuários
            stmtUsuario.setInt(1, id);
            ResultSet rsUsuario = stmtUsuario.executeQuery();
            if (!rsUsuario.next()) return null;

            // Buscar na tabela de clientes
            stmtCliente.setInt(1, id);
            ResultSet rsCliente = stmtCliente.executeQuery();
            
            if (rsCliente.next()) {
                Cliente cliente = new Cliente(
                    rsUsuario.getString("status"), 
                    rsUsuario.getInt("id"), 
                    rsUsuario.getString("nome"), 
                    rsUsuario.getString("cpf"), 
                    rsUsuario.getString("telefone"), 
                    rsUsuario.getString("senha"), 
                    rsUsuario.getString("papel")
                );
                
                return cliente;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Cliente> findAll () {
        List<Cliente> clientes = new ArrayList<>();
        
        return clientes;
    }
    
    /**
     * Deletar um cliente de acordo com um ID
     * @param id ID do cliente a ser deletado
     */
    public void delete (int id) {
        this.usuarioDAO.delete(id);
        // O método de deleção deve ser CASCADE para isso funcionar
    }
    
    /**
     * Deletar um cliente de acordo com um CPF
     * @param cpf CPF do cliente a ser deletado
     */
    public void delete (String cpf) {
        this.usuarioDAO.delete(cpf);
        // O método de deleção deve ser CASCADE para isso funcionar
    }
    
    // Getters e setters
    public String getTableName() {
        return tableName;
    }
}
