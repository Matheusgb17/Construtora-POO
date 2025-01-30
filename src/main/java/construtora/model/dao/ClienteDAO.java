package construtora.model.dao;

import construtora.model.entity.Cliente;
import construtora.model.entity.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    /**
     * Variável necessária para acessar a tabela de usuário referente ao cliente, uma vez que este é herança.
     */
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final String tableName = "cliente";

    private Connection conexao;

    /**
     * Construtor da classe
     */
    public ClienteDAO () {
        this.conexao = Conexao.getConexao();
    }

    /**
     * Adicionar um cliente ao banco de dados
     * @param cliente TAD do cliente a ser inserido
     */
    public  int create(Cliente cliente) {
        /* Inserindo os dados base na tabela de usuários.
         * Isso é possível pois Cliente herda de Usuario. */
        int codigoGerado = -1;
        cliente.setPapel(getTableName());
        int usuarioId = this.usuarioDAO.create(cliente);


        if (usuarioId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA DE USUÁRIOS ===");
            return -1;
        }

        String sql = "INSERT INTO " + this.tableName + " (usuario_id, status) VALUES (?, ?);";

        try (
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            stmt.setInt(1, usuarioId);
            stmt.setString(2, cliente.getStatus());

            stmt.executeUpdate();

            System.out.println("Cliente inserido com sucesso!");

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

    /**
     * Retorna um cliente com base no ID dele
     * @param id ID do cliente que quer se achar
     * @return TAD do cliente achado ou NULL
     */
    public Cliente find(int id) {
        String sqlCliente = "SELECT cliente.id, cliente.status, usuario.nome, usuario.cpf, usuario.telefone, usuario.senha, usuario.papel FROM cliente, usuario WHERE cliente.id = ? AND usuario.id = cliente.usuario_id";

        try (
                PreparedStatement stmtCliente = conexao.prepareStatement(sqlCliente)) {

            stmtCliente.setInt(1, id);

            try (ResultSet rsCliente = stmtCliente.executeQuery()) {
                if (rsCliente.next()) {
                    Cliente cliente = new Cliente(
                            rsCliente.getString("status"),
                            rsCliente.getInt("id"),
                            rsCliente.getString("nome"),
                            rsCliente.getString("cpf"),
                            rsCliente.getString("telefone"),
                            rsCliente.getString("senha"),
                            rsCliente.getString("papel")
                    );

                    return cliente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Substitua isso por um logger
        }

        return null;
    }

    /**
     * Retorna um cliente com base no CPF dele
     * @param cpf CPF do cliente que quer se achar
     * @return TAD do cliente achado ou NULL
     */
    public Cliente find(String cpf) {
        String sqlUsuario = "SELECT * FROM " + this.usuarioDAO.getTableName() + " WHERE cpf = ?";
        String sqlCliente = "SELECT * FROM " + this.getTableName() + " WHERE usuario_id = ?";

        try (
                PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario);
                PreparedStatement stmtCliente = conexao.prepareStatement(sqlCliente)) {

            // Busca na tabela de usuários
            stmtUsuario.setString(1, cpf);
            ResultSet rsUsuario = stmtUsuario.executeQuery();

            if (!rsUsuario.next()) {
                return null; // Se o usuário não existir
            }

            // Pega o id do usuário
            int idUsuario = rsUsuario.getInt("id");

            // Busca na tabela de clientes
            stmtCliente.setInt(1, idUsuario);
            ResultSet rsCliente = stmtCliente.executeQuery();

            if (rsCliente.next()) {
                // Se o cliente existir, cria o objeto Cliente
                Cliente cliente = new Cliente(
                        rsCliente.getString("status"),
                        rsCliente.getInt("id"),
                        rsUsuario.getString("nome"),
                        rsUsuario.getString("cpf"),
                        rsUsuario.getString("telefone"),
                        rsUsuario.getString("senha"),
                        rsUsuario.getString("papel")
                );
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Atualiza as informações de um determinado cliente
     *
     * @param cliente TAD do cliente já com os dados atualizados
     */
    public void update(Cliente cliente) {
        String sqlFindUsuarioId = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        String sqlUsuario = "UPDATE " + this.usuarioDAO.getTableName()
                + " SET nome = ?, cpf = ?, telefone = ?, senha = ?, papel = ? "
                + "WHERE id = ?";
        String sqlCliente = "UPDATE " + this.getTableName()
                + " SET status = ? WHERE id = ?";

        try ( PreparedStatement stmtFindUsuarioId = conexao.prepareStatement(sqlFindUsuarioId); PreparedStatement stmtUsuario = conexao.prepareStatement(sqlUsuario); PreparedStatement stmtCliente = conexao.prepareStatement(sqlCliente)) {

            // Buscar o usuario_id correspondente ao cliente
            stmtFindUsuarioId.setInt(1, cliente.getId());
            ResultSet rs = stmtFindUsuarioId.executeQuery();

            if (!rs.next()) {
                throw new SQLException("Cliente com ID " + cliente.getId() + " não encontrado.");
            }
            int usuarioId = rs.getInt("usuario_id");

            // Atualiza os dados do usuário
            stmtUsuario.setString(1, cliente.getNome());
            stmtUsuario.setString(2, cliente.getCpf());
            stmtUsuario.setString(3, cliente.getTelefone());
            stmtUsuario.setString(4, cliente.getSenha());
            stmtUsuario.setString(5, cliente.getPapel());
            stmtUsuario.setInt(6, usuarioId);
            stmtUsuario.executeUpdate();

            // Atualiza os dados do cliente
            stmtCliente.setString(1, cliente.getStatus());
            stmtCliente.setInt(2, cliente.getId());
            stmtCliente.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Retorna todos os clientes cadastrados no banco
     *
     * @return ArrayList com todos os dados dos clientes
     */
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.id AS cliente_id, c.status, u.id AS usuario_id, u.nome, u.cpf, u.telefone, u.senha, u.papel "
                + "FROM " + this.getTableName() + " c "
                + "JOIN " + this.usuarioDAO.getTableName() + " u ON c.usuario_id = u.id";

        try ( PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("status"),
                        rs.getInt("cliente_id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("papel")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }


    /**
     * Deletar um cliente de acordo com um ID de cliente
     *
     * @param id ID do cliente a ser deletado
     */
    public void delete(int id) {
        String sql = "SELECT usuario_id FROM " + this.getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // Busca o `usuario_id` correspondente ao `id` do cliente
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int usuarioId = rs.getInt("usuario_id");

                // Deleta o usuário associado, cascata garante que cliente será deletado
                this.usuarioDAO.delete(usuarioId);
            } else {
                System.out.println("Cliente não encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletar um cliente de acordo com um CPF
     *
     * @param cpf CPF do cliente a ser deletado
     */
    public void delete(String cpf) {
        Usuario usuario = this.usuarioDAO.find(cpf);
        if (usuario != null) {
            // Deleta o usuário associado, cascata garante que cliente será deletado
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