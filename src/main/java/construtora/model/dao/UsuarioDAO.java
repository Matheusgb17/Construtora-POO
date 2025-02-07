package construtora.model.dao;

import construtora.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final String tableName = "usuario";
    
    /**
     * Criar um novo usuário no banco de dados
     * @param usuario TAD Usuário a ser inserido
     * @return ID do usuário inserido em caso de sucesso, -1 em caso de erro.
     */
    public int create(Usuario usuario) {
        String sql = "INSERT INTO " + this.getTableName() + " (nome, cpf, telefone, senha, papel) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getPapel());

            stmt.executeUpdate();

            // Obter o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1; // Retorna -1 em caso de erro
    }
    
    /**
     * Buscar um usuário pelo seu ID
     * @param id ID do usuário a ser buscado
     * @return TAD do usuário encontrado ou NULL
     */
    public Usuario find (int id) {
        String sql = "SELECT * FROM " + this.getTableName() + " WHERE id = ?;";
        
        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"), 
                        rs.getString("nome"), 
                        rs.getString("cpf"), 
                        rs.getString("telefone"), 
                        rs.getString("senha"), 
                        rs.getString("papel")
                );
                return usuario;
            }
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Retorna o usuário com base no CPF dele.
     * @param cpf CPF do usuário buscado
     * @return TAD do usuário encontrado ou NULL
     */
    public Usuario find (String cpf) {
        String sql = "SELECT * FROM " + this.getTableName() + " WHERE cpf = ?;";
        
        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {
            
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"), 
                        rs.getString("nome"), 
                        rs.getString("cpf"), 
                        rs.getString("telefone"), 
                        rs.getString("senha").trim(), 
                        rs.getString("papel")
                );
                return usuario;
            }
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    /**
     * Atualiza o usuário passado por parâmetro
     * @param usuario Usuário a ser atualizado
     */
    public void update(Usuario usuario) {
        String sql = "UPDATE " + this.getTableName() + " SET nome = ?, cpf = ?, telefone = ?, senha = ?, papel = ? WHERE id = ?";
        
        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getPapel());
            stmt.setInt(6, usuario.getId());

            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deleta o usuário de acordo com o ID passado
     * @param id ID do usuário a ser deletado
     */
    public void delete (int id) {
        String sql = "DELETE FROM " + this.getTableName() + " WHERE id = ?;";
        
        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Usuário removido com sucesso!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Remover um usuário do banco de dados de acordo com o CPF
     * @param cpf CPF do usuário que se quer deletar
     */
    public void delete (String cpf) {
        String sql = "DELETE FROM " + this.getTableName() + " WHERE cpf = ?;";
        
        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Usuário removido com sucesso!");
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Encontra todos os usuários cadastrados no banco de dados
     * @return Lista de todos os usuários encontrados
     */
    public List<Usuario> findAll () {
        String sql = "SELECT * FROM " + this.getTableName();
        List<Usuario> usuarios = new ArrayList<>();
        
        try (
                Connection conexao = Conexao.getConexao();
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
            ) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"), 
                        rs.getString("nome"), 
                        rs.getString("cpf"), 
                        rs.getString("telefone"), 
                        rs.getString("senha"), 
                        rs.getString("papel")
                );
                
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    // Getters e setters
    public String getTableName() {
        return tableName;
    }
}
