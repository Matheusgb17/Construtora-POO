package construtora.model.dao;

import construtora.model.entity.Construtor;
import construtora.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConstrutorDAO {
    /**
     * Variável necessária para acessar a tabela de usuário referente ao construtor, uma vez que este é herança.
     */
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    private final String tableName = "construtor";
    
    private Connection conexao;
    
    /**
     * Construtor da classe
     */
    public ConstrutorDAO () {
        this.conexao = Conexao.getConexao();
    }
    
    /**
     * Adicionar um construtor ao banco de dados
     * @param construtor TAD do construtor a ser inserido
     */
    public void create (Construtor construtor) {
        /* Inserindo os dados base na tabela de usuários.
         * Isso é possível pois Construtor herda de Usuario. */
        int usuarioId = this.usuarioDAO.create(construtor);
        
        if (usuarioId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA DE USUÁRIOS ===");
            return;
        }
        
        String sql = "INSERT INTO " + this.tableName + " (usuario_id, tipoServico) VALUES (?, ?);";
        
        try (
                PreparedStatement stmt = conexao.prepareStatement(sql)
            ) {
            
            stmt.setInt(1, usuarioId);
            stmt.setString(2, construtor.getTipoServico());
            
            stmt.executeUpdate();
            
            System.out.println("Construtor inserido com sucesso!");
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna um construtor com base no ID dele
     * @param id ID do construtor que quer se achar
     * @return TAD do construtor achado ou NULL
     */
    public Construtor find(int id) {
        String sqlConstrutor = "SELECT construtor.id, construtor.tipoServico, usuario.nome, usuario.cpf, usuario.telefone, usuario.senha, usuario.papel FROM construtor, usuario WHERE construtor.id = ? AND usuario.id = construtor.usuario_id"; 

        try (
             PreparedStatement stmtConstrutor = conexao.prepareStatement(sqlConstrutor)) {

            stmtConstrutor.setInt(1, id);

            try (ResultSet rsConstrutor = stmtConstrutor.executeQuery()) {
                if (rsConstrutor.next()) {
                    Construtor construtor = new Construtor(
                        rsConstrutor.getString("tipoServico"),
                        rsConstrutor.getInt("id"),
                        rsConstrutor.getString("nome"),
                        rsConstrutor.getString("cpf"),
                        rsConstrutor.getString("telefone"),
                        rsConstrutor.getString("senha"),
                        rsConstrutor.getString("papel")
                    );

                    return construtor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua isso por um logger
        }

        return null;
    }
    
    /**
     * Retorna um construtor com base no CPF dele
     * @param cpf CPF do construtor que quer se achar
     * @return TAD do construtor achado ou NULL
     */
    public Construtor find(String cpf) {
        String sqlUsuario = "SELECT * FROM " + this.usuarioDAO.getTableName() + " WHERE cpf = ?";
        String sqlConstrutor = "SELECT * FROM " + this.getTableName() + " WHERE usuario_id = ?";

        try (
             PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario);
             PreparedStatement stmtConstrutor = conexao.prepareStatement(sqlConstrutor)) {

            // Busca na tabela de usuários
            stmtUsuario.setString(1, cpf);
            ResultSet rsUsuario = stmtUsuario.executeQuery();

            if (!rsUsuario.next()) {
                return null; // Se o usuário não existir
            }

            // Pega o id do usuário
            int idUsuario = rsUsuario.getInt("id");

            // Busca na tabela de construtores
            stmtConstrutor.setInt(1, idUsuario);
            ResultSet rsConstrutor = stmtConstrutor.executeQuery();

            if (rsConstrutor.next()) {
                // Se o construtor existir, cria o objeto Construtor
                Construtor construtor = new Construtor(
                    rsConstrutor.getString("tipoServico"),
                    rsConstrutor.getInt("id"),
                    rsUsuario.getString("nome"),
                    rsUsuario.getString("cpf"),
                    rsUsuario.getString("telefone"),
                    rsUsuario.getString("senha"),
                    rsUsuario.getString("papel")
                );
                return construtor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Atualiza as informações de um determinado construtor
     *
     * @param construtor TAD do construtor já com os dados atualizados
     */
    public void update(Construtor construtor) {
        String sqlFindUsuarioId = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        String sqlUsuario = "UPDATE " + this.usuarioDAO.getTableName()
                + " SET nome = ?, cpf = ?, telefone = ?, senha = ?, papel = ? "
                + "WHERE id = ?";
        String sqlConstrutor = "UPDATE " + this.getTableName()
                + " SET tipoServico = ? WHERE id = ?";

        try ( PreparedStatement stmtFindUsuarioId = conexao.prepareStatement(sqlFindUsuarioId); PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario); PreparedStatement stmtConstrutor = conexao.prepareStatement(sqlConstrutor)) {

            // Buscar o usuario_id correspondente ao construtor
            stmtFindUsuarioId.setInt(1, construtor.getId());
            ResultSet rs = stmtFindUsuarioId.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Construtor com ID " + construtor.getId() + " não encontrado.");
            }
            int usuarioId = rs.getInt("usuario_id");

            // Atualiza os dados do usuário
            stmtUsuario.setString(1, construtor.getNome());
            stmtUsuario.setString(2, construtor.getCpf());
            stmtUsuario.setString(3, construtor.getTelefone());
            stmtUsuario.setString(4, construtor.getSenha());
            stmtUsuario.setString(5, construtor.getPapel());
            stmtUsuario.setInt(6, usuarioId);
            stmtUsuario.executeUpdate();

            // Atualiza os dados do construtor
            stmtConstrutor.setString(1, construtor.getTipoServico());
            stmtConstrutor.setInt(2, construtor.getId());
            stmtConstrutor.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retorna todos os construtores cadastrados no banco
     *
     * @return ArrayList com todos os dados dos construtores
     */
    public List<Construtor> findAll() {
        List<Construtor> construtores = new ArrayList<>();
        String sql = "SELECT c.id AS construtor_id, c.tipoServico, u.id AS usuario_id, u.nome, u.cpf, u.telefone, u.senha, u.papel "
                + "FROM " + this.getTableName() + " c "
                + "JOIN " + this.usuarioDAO.getTableName() + " u ON c.usuario_id = u.id";

        try ( PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Construtor construtor = new Construtor(
                        rs.getString("tipoServico"),
                        rs.getInt("construtor_id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("papel")
                );
                construtores.add(construtor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return construtores;
    }

    /**
     * Deletar um construtor de acordo com um ID de construtor
     *
     * @param id ID do construtor a ser deletado
     */
    public void delete(int id) {
        String sql = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // Busca o `usuario_id` correspondente ao `id` do construtor
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");

                // Deleta o usuário associado, cascata garante que construtor será deletado
                this.usuarioDAO.delete(usuarioId);
            } else {
                System.out.println("Construtor não encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletar um construtor de acordo com um CPF
     *
     * @param cpf CPF do construtor a ser deletado
     */
    public void delete(String cpf) {
        Usuario usuario = this.usuarioDAO.find(cpf);
        if (usuario != null) {
            // Deleta o usuário associado, cascata garante que construtor será deletado
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