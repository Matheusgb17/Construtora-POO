package construtora.model.dao;

import construtora.model.entity.Funcionario;
import construtora.model.entity.Construtor;
import construtora.model.entity.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ConstrutorDAO construtorDAO = new ConstrutorDAO();
    
    private final String tableName = "funcionario";
    private Connection conexao;

    public FuncionarioDAO() {
        this.conexao = Conexao.getConexao();
    }

    public int create(Funcionario funcionario) {
        /* Inserindo os dados base na tabela de usuários.
         * Isso é possível pois Funcionario herda de Usuario. */
        int codigoGerado = -1;
        funcionario.setPapel(getTableName());
        int usuarioId = this.usuarioDAO.create(funcionario);



        if (usuarioId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA USUARIO ===");
            return -1;
        }

        String sql = "INSERT INTO " + this.tableName + " (usuario_id, cargo, construtor_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, funcionario.getCargo());
            stmt.setInt(3, funcionario.getConstrutor().getId());
            stmt.executeUpdate();
            System.out.println("Funcionario inserido com sucesso!");
            // Obter o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                codigoGerado = rs.getInt(1);
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoGerado;
    }

    public Funcionario find(int id) {
        String sqlFuncionario = "SELECT f.id AS funcionario_id, f.cargo, f.construtor_id, u.id AS usuario_id, u.nome, u.cpf, u.telefone, u.senha, u.papel FROM funcionario f JOIN usuario u ON f.usuario_id = u.id WHERE f.id = ?";

        try (PreparedStatement stmtFuncionario = conexao.prepareStatement(sqlFuncionario)) {
            stmtFuncionario.setInt(1, id);
            try (ResultSet rs = stmtFuncionario.executeQuery()) {
                if (rs.next()) {
                    Construtor construtor = construtorDAO.find(rs.getInt("construtor_id"));
                    return new Funcionario(
                        rs.getString("cargo"),
                        construtor,
                        rs.getInt("funcionario_id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("papel")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Funcionario find(String cpf) {
        Usuario usuario = usuarioDAO.find(cpf);
        if (usuario == null) return null;

        String sqlFuncionario = "SELECT * FROM " + this.tableName + " WHERE usuario_id = ?";
        try (PreparedStatement stmtFuncionario = conexao.prepareStatement(sqlFuncionario)) {
            stmtFuncionario.setInt(1, usuario.getId());
            try (ResultSet rs = stmtFuncionario.executeQuery()) {
                if (rs.next()) {
                    Construtor construtor = construtorDAO.find(rs.getInt("construtor_id"));
                    return new Funcionario(
                        rs.getString("cargo"),
                        construtor,
                        rs.getInt("id"),
                        usuario.getNome(),
                        usuario.getCpf(),
                        usuario.getTelefone(),
                        usuario.getSenha(),
                        usuario.getPapel()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Funcionario funcionario) {
        String sqlFindUsuarioId = "SELECT usuario_id FROM " + this.tableName + " WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET nome = ?, cpf = ?, telefone = ?, senha = ?, papel = ? WHERE id = ?";
        String sqlFuncionario = "UPDATE " + this.tableName + " SET cargo = ?, construtor_id = ? WHERE id = ?";

        try (
            PreparedStatement stmtFindUsuarioId = conexao.prepareStatement(sqlFindUsuarioId);
            PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario);
            PreparedStatement stmtFuncionario = conexao.prepareStatement(sqlFuncionario)
        ) {
            stmtFindUsuarioId.setInt(1, funcionario.getId());
            ResultSet rs = stmtFindUsuarioId.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Funcionario com ID " + funcionario.getId() + " não encontrado.");
            }
            int usuarioId = rs.getInt("usuario_id");

            stmtUsuario.setString(1, funcionario.getNome());
            stmtUsuario.setString(2, funcionario.getCpf());
            stmtUsuario.setString(3, funcionario.getTelefone());
            stmtUsuario.setString(4, funcionario.getSenha());
            stmtUsuario.setString(5, funcionario.getPapel());
            stmtUsuario.setInt(6, usuarioId);
            stmtUsuario.executeUpdate();

            stmtFuncionario.setString(1, funcionario.getCargo());
            stmtFuncionario.setInt(2, funcionario.getConstrutor().getId());
            stmtFuncionario.setInt(3, funcionario.getId());
            stmtFuncionario.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Funcionario> findAll() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT f.id AS funcionario_id, f.cargo, f.construtor_id, u.id AS usuario_id, u.nome, u.cpf, u.telefone, u.senha, u.papel FROM funcionario f JOIN usuario u ON f.usuario_id = u.id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Construtor construtor = construtorDAO.find(rs.getInt("construtor_id"));
                Funcionario funcionario = new Funcionario(
                    rs.getString("cargo"),
                    construtor,
                    rs.getInt("funcionario_id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("senha"),
                    rs.getString("papel")
                );
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }

    public void delete(int id) {
        String sql = "SELECT usuario_id FROM " + this.tableName + " WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");
                usuarioDAO.delete(usuarioId);
            } else {
                System.out.println("Funcionario não encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String cpf) {
        Usuario usuario = usuarioDAO.find(cpf);
        if (usuario != null) {
            usuarioDAO.delete(usuario.getId());
        } else {
            System.out.println("Usuario não encontrado com o CPF fornecido.");
        }
    }

    public String getTableName() {
        return tableName;
    }
}
