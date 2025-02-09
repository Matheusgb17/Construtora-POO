package construtora.model.dao;

import construtora.model.entity.Administrador;
import construtora.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    /**
     * Variável necessária para acessar a tabela de usuário referente ao administrador, uma vez que este é herança.
     */
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    private final String tableName = "administrador";
    
    private Connection conexao;
    
    /**
     * Construtor da classe
     */
    public AdministradorDAO () {
        this.conexao = Conexao.getConexao();
    }
    
    /**
     * Adicionar um administrador ao banco de dados
     * @param administrador TAD do administrador a ser inserido
     */
    public void create (Administrador administrador) {
        administrador.setPapel(this.getTableName());
        
        /* Inserindo os dados base na tabela de usuários.
         * Isso é possível pois Administrador herda de Usuario. */
        int usuarioId = this.usuarioDAO.create(administrador);
        
        if (usuarioId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA DE USUÁRIOS ===");
            return;
        }
        
        String sql = "INSERT INTO " + this.tableName + " (usuario_id, cargo) VALUES (?, ?);";
        
        try (
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {
            
            stmt.setInt(1, usuarioId);
            stmt.setString(2, administrador.getCargo());
            
            stmt.executeUpdate();
            
            System.out.println("Administrador inserido com sucesso!");
            
            conexao.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna um administrador com base no ID dele
     * @param id ID do administrador que quer se achar
     * @return TAD do administrador achado ou NULL
     */
    public Administrador find(int id) {
        String sqlAdministrador = "SELECT administrador.id, administrador.cargo, usuario.nome, usuario.cpf, usuario.telefone, usuario.senha, usuario.papel FROM administrador, usuario WHERE administrador.id = ? AND usuario.id = administrador.usuario_id"; 

        try (
             PreparedStatement stmtAdministrador = conexao.prepareStatement(sqlAdministrador)) {

            stmtAdministrador.setInt(1, id);

            try (ResultSet rsAdministrador = stmtAdministrador.executeQuery()) {
                if (rsAdministrador.next()) {
                    Administrador administrador = new Administrador(
                        rsAdministrador.getString("cargo"),
                        rsAdministrador.getInt("id"),
                        rsAdministrador.getString("nome"),
                        rsAdministrador.getString("cpf"),
                        rsAdministrador.getString("telefone"),
                        rsAdministrador.getString("senha"),
                        rsAdministrador.getString("papel")
                    );
                    
                    conexao.close();

                    return administrador;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua isso por um logger
        }

        return null;
    }

    /**
     * Retorna um administrador com base no CPF dele
     * @param cpf CPF do administrador que quer se achar
     * @return TAD do administrador achado ou NULL
     */
    public Administrador find(String cpf) {
        String sqlUsuario = "SELECT * FROM " + this.usuarioDAO.getTableName() + " WHERE cpf = ?";
        String sqlAdministrador = "SELECT * FROM " + this.getTableName() + " WHERE usuario_id = ?";

        try (
             PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario);
             PreparedStatement stmtAdministrador = conexao.prepareStatement(sqlAdministrador)) {

            // Busca na tabela de usuários
            stmtUsuario.setString(1, cpf);
            ResultSet rsUsuario = stmtUsuario.executeQuery();

            if (!rsUsuario.next()) {
                return null; // Se o usuário não existir
            }

            // Pega o id do usuário
            int idUsuario = rsUsuario.getInt("id");

            // Busca na tabela de administradores
            stmtAdministrador.setInt(1, idUsuario);
            ResultSet rsAdministrador = stmtAdministrador.executeQuery();

            if (rsAdministrador.next()) {
                // Se o administrador existir, cria o objeto Administrador
                Administrador administrador = new Administrador(
                    rsAdministrador.getString("cargo"),
                    rsAdministrador.getInt("id"),
                    rsUsuario.getString("nome"),
                    rsUsuario.getString("cpf"),
                    rsUsuario.getString("telefone"),
                    rsUsuario.getString("senha"),
                    rsUsuario.getString("papel")
                );
                
                conexao.close();
                return administrador;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Atualiza as informações de um determinado administrador
     *
     * @param administrador TAD do administrador já com os dados atualizados
     */
    public void update(Administrador administrador) {
        String sqlFindUsuarioId = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        String sqlUsuario = "UPDATE " + this.usuarioDAO.getTableName()
                + " SET nome = ?, cpf = ?, telefone = ?, senha = ?, papel = ? "
                + "WHERE id = ?";
        String sqlAdministrador = "UPDATE " + this.getTableName()
                + " SET cargo = ? WHERE id = ?";

        try ( PreparedStatement stmtFindUsuarioId = conexao.prepareStatement(sqlFindUsuarioId); PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario); PreparedStatement stmtAdministrador = conexao.prepareStatement(sqlAdministrador)) {

            // Buscar o usuario_id correspondente ao administrador
            stmtFindUsuarioId.setInt(1, administrador.getId());
            ResultSet rs = stmtFindUsuarioId.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Administrador com ID " + administrador.getId() + " não encontrado.");
            }
            int usuarioId = rs.getInt("usuario_id");

            // Atualiza os dados do usuário
            stmtUsuario.setString(1, administrador.getNome());
            stmtUsuario.setString(2, administrador.getCpf());
            stmtUsuario.setString(3, administrador.getTelefone());
            stmtUsuario.setString(4, administrador.getSenha());
            stmtUsuario.setString(5, administrador.getPapel());
            stmtUsuario.setInt(6, usuarioId);
            stmtUsuario.executeUpdate();

            // Atualiza os dados do administrador
            stmtAdministrador.setString(1, administrador.getCargo());
            stmtAdministrador.setInt(2, administrador.getId());
            stmtAdministrador.executeUpdate();
            
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna todos os administradores cadastrados no banco
     *
     * @return ArrayList com todos os dados dos administradores
     */
    public List<Administrador> findAll() {
        List<Administrador> administradores = new ArrayList<>();
        String sql = "SELECT a.id AS administrador_id, a.cargo, u.id AS usuario_id, u.nome, u.cpf, u.telefone, u.senha, u.papel "
                + "FROM " + this.getTableName() + " a "
                + "JOIN " + this.usuarioDAO.getTableName() + " u ON a.usuario_id = u.id";

        try ( PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrador administrador = new Administrador(
                        rs.getString("cargo"),
                        rs.getInt("administrador_id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("papel")
                );
                administradores.add(administrador);
            }
            
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return administradores;
    }

    /**
     * Deletar um administrador de acordo com um ID de administrador
     *
     * @param id ID do administrador a ser deletado
     */
    public void delete(int id) {
        String sql = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // Busca o `usuario_id` correspondente ao `id` do administrador
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");

                // Deleta o usuário associado, cascata garante que administrador será deletado
                this.usuarioDAO.delete(usuarioId);
            } else {
                System.out.println("Administrador não encontrado com o ID fornecido.");
            }
            
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletar um administrador de acordo com um CPF
     *
     * @param cpf CPF do administrador a ser deletado
     */
    public void delete(String cpf) {
        Usuario usuario = this.usuarioDAO.find(cpf);
        if (usuario != null) {
            // Deleta o usuário associado, cascata garante que administrador será deletado
            this.usuarioDAO.delete(usuario.getId());
        } else {
            System.out.println("Usuário não encontrado com o CPF fornecido.");
        }
    }

    // Getters e setters
    public String getTableName() {
        return tableName;
    }
    
    public void close() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
