package construtora.model.dao;

import construtora.model.entity.Engenheiro;
import construtora.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EngenheiroDAO {
    /**
     * Variável necessária para acessar a tabela de usuário referente ao engenheiro, uma vez que este é herança.
     */
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    private final String tableName = "engenheiro";
    
    private Connection conexao;
    
    /**
     * Construtor da classe
     */
    public EngenheiroDAO() {
        this.conexao = Conexao.getConexao();
    }
    
    /**
     * Adicionar um engenheiro ao banco de dados
     * @param engenheiro TAD do engenheiro a ser inserido
     */
    public int create(Engenheiro engenheiro) {
        int codigoGerado = -1;
        // Inserindo os dados base na tabela de usuários.
        int usuarioId = this.usuarioDAO.create(engenheiro);
        
        if (usuarioId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA DE USUÁRIOS ===");
            return codigoGerado;
        }
        
        String sql = "INSERT INTO " + this.tableName + " (usuario_id, crea) VALUES (?, ?);";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, engenheiro.getCrea());
            stmt.executeUpdate();
            
            System.out.println("Engenheiro inserido com sucesso!");
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                codigoGerado = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoGerado;
    }
    
    /**
     * Retorna um engenheiro com base no ID dele
     * @param id ID do engenheiro que quer se achar
     * @return TAD do engenheiro achado ou NULL
     */
    public Engenheiro find(int id) {
        String sqlEngenheiro = "SELECT engenheiro.id, engenheiro.crea, usuario.nome, usuario.cpf, usuario.telefone, usuario.senha, usuario.papel " +
                               "FROM engenheiro, usuario WHERE engenheiro.id = ? AND usuario.id = engenheiro.usuario_id"; 

        try (PreparedStatement stmtEngenheiro = conexao.prepareStatement(sqlEngenheiro)) {
            stmtEngenheiro.setInt(1, id);
            try (ResultSet rsEngenheiro = stmtEngenheiro.executeQuery()) {
                if (rsEngenheiro.next()) {
                    Engenheiro engenheiro = new Engenheiro(
                        rsEngenheiro.getString("crea"),
                        rsEngenheiro.getInt("id"),
                        rsEngenheiro.getString("nome"),
                        rsEngenheiro.getString("cpf"),
                        rsEngenheiro.getString("telefone"),
                        rsEngenheiro.getString("senha"),
                        rsEngenheiro.getString("papel")
                    );
                    return engenheiro;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Retorna um engenheiro com base no CPF dele
     * @param cpf CPF do engenheiro que quer se achar
     * @return TAD do engenheiro achado ou NULL
     */
    public Engenheiro find(String cpf) {
        String sqlUsuario = "SELECT * FROM " + this.usuarioDAO.getTableName() + " WHERE cpf = ?";
        String sqlEngenheiro = "SELECT * FROM " + this.getTableName() + " WHERE usuario_id = ?";

        try (PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario);
             PreparedStatement stmtEngenheiro = conexao.prepareStatement(sqlEngenheiro)) {

            // Busca na tabela de usuários
            stmtUsuario.setString(1, cpf);
            ResultSet rsUsuario = stmtUsuario.executeQuery();

            if (!rsUsuario.next()) {
                return null;
            }

            int idUsuario = rsUsuario.getInt("id");

            // Busca na tabela de engenheiros
            stmtEngenheiro.setInt(1, idUsuario);
            ResultSet rsEngenheiro = stmtEngenheiro.executeQuery();

            if (rsEngenheiro.next()) {
                Engenheiro engenheiro = new Engenheiro(
                    rsEngenheiro.getString("crea"),
                    rsEngenheiro.getInt("id"),
                    rsUsuario.getString("nome"),
                    rsUsuario.getString("cpf"),
                    rsUsuario.getString("telefone"),
                    rsUsuario.getString("senha"),
                    rsUsuario.getString("papel")
                );
                return engenheiro;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Atualiza as informações de um determinado engenheiro
     * @param engenheiro TAD do engenheiro já com os dados atualizados
     */
    public void update(Engenheiro engenheiro) {
        String sqlFindUsuarioId = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        String sqlUsuario = "UPDATE " + this.usuarioDAO.getTableName() +
                            " SET nome = ?, cpf = ?, telefone = ?, senha = ?, papel = ? WHERE id = ?";
        String sqlEngenheiro = "UPDATE " + this.getTableName() + " SET crea = ? WHERE id = ?";

        try (PreparedStatement stmtFindUsuarioId = conexao.prepareStatement(sqlFindUsuarioId);
             PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario);
             PreparedStatement stmtEngenheiro = conexao.prepareStatement(sqlEngenheiro)) {

            // Buscar o usuario_id correspondente ao engenheiro
            stmtFindUsuarioId.setInt(1, engenheiro.getId());
            ResultSet rs = stmtFindUsuarioId.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Engenheiro com ID " + engenheiro.getId() + " não encontrado.");
            }
            int usuarioId = rs.getInt("usuario_id");

            // Atualiza os dados do usuário
            stmtUsuario.setString(1, engenheiro.getNome());
            stmtUsuario.setString(2, engenheiro.getCpf());
            stmtUsuario.setString(3, engenheiro.getTelefone());
            stmtUsuario.setString(4, engenheiro.getSenha());
            stmtUsuario.setString(5, engenheiro.getPapel());
            stmtUsuario.setInt(6, usuarioId);
            stmtUsuario.executeUpdate();

            // Atualiza os dados do engenheiro
            stmtEngenheiro.setString(1, engenheiro.getCrea());
            stmtEngenheiro.setInt(2, engenheiro.getId());
            stmtEngenheiro.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna todos os engenheiros cadastrados no banco
     * @return ArrayList com todos os dados dos engenheiros
     */
    public List<Engenheiro> findAll() {
        List<Engenheiro> engenheiros = new ArrayList<>();
        String sql = "SELECT e.id AS engenheiro_id, e.crea, u.id AS usuario_id, u.nome, u.cpf, u.telefone, u.senha, u.papel " +
                     "FROM " + this.getTableName() + " e " +
                     "JOIN " + this.usuarioDAO.getTableName() + " u ON e.usuario_id = u.id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Engenheiro engenheiro = new Engenheiro(
                        rs.getString("crea"),
                        rs.getInt("engenheiro_id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("papel")
                );
                engenheiros.add(engenheiro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return engenheiros;
    }

    /**
     * Deletar um engenheiro de acordo com um ID
     * @param id ID do engenheiro a ser deletado
     */
    public void delete(int id) {
        String sql = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                this.usuarioDAO.delete(usuarioId);
            } else {
                System.out.println("Engenheiro não encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletar um engenheiro de acordo com um CPF
     * @param cpf CPF do engenheiro a ser deletado
     */
    public void delete(String cpf) {
        Usuario usuario = this.usuarioDAO.find(cpf);
        if (usuario != null) {
            this.usuarioDAO.delete(usuario.getId());
        } else {
            System.out.println("Usuário não encontrado com o CPF fornecido.");
        }
    }
    
    
    
    // Getters e setters
    public String getTableName() {
        return tableName;
    }
}