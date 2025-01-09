package construtora.model.dao;

import construtora.model.entity.Administrador;
import construtora.model.entity.Cliente;
import construtora.model.entity.Recebimento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecebimentoDAO {

    private final TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    private final String tableName = "recebimento";

    private Connection conexao;

    public RecebimentoDAO() {
        this.conexao = Conexao.getConexao();
    }

    public void create(Recebimento recebimento) {
        int transferenciaId = transferenciaDAO.create(recebimento);

        if (transferenciaId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA DE TRANSFERÊNCIAS ===");
            return;
        }

        String sql = "INSERT INTO " + this.tableName + " (transferencia_id, cliente_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, transferenciaId);
            stmt.setInt(2, recebimento.getCliente().getId());

            stmt.executeUpdate();

            System.out.println("Recebimento inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Recebimento find(int id) {
        String sqlRecebimento = "SELECT r.cliente_id, t.valor, t.data, t.administrador_id FROM " + this.tableName + " r JOIN transferencia t ON r.transferencia_id = t.id WHERE r.transferencia_id = ?";

        try (PreparedStatement stmtRecebimento = conexao.prepareStatement(sqlRecebimento)) {

            stmtRecebimento.setInt(1, id);

            try (ResultSet rsRecebimento = stmtRecebimento.executeQuery()) {
                if (rsRecebimento.next()) {
                    int clienteId = rsRecebimento.getInt("cliente_id");
                    float valor = rsRecebimento.getFloat("valor");
                    LocalDate data = rsRecebimento.getDate("data").toLocalDate();
                    int administradorId = rsRecebimento.getInt("administrador_id");

                    Cliente cliente = clienteDAO.find(clienteId);
                    Administrador administrador = new AdministradorDAO().find(administradorId);

                    return new Recebimento(cliente, id, valor, data, administrador);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Recebimento> findAll() {
        List<Recebimento> recebimentos = new ArrayList<>();
        String sql = "SELECT r.transferencia_id, r.cliente_id, t.valor, t.data, t.administrador_id FROM " + this.tableName + " r JOIN transferencia t ON r.transferencia_id = t.id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("transferencia_id");
                int clienteId = rs.getInt("cliente_id");
                float valor = rs.getFloat("valor");
                LocalDate data = rs.getDate("data").toLocalDate();
                int administradorId = rs.getInt("administrador_id");

                Cliente cliente = clienteDAO.find(clienteId);
                Administrador administrador = new AdministradorDAO().find(administradorId);

                recebimentos.add(new Recebimento(cliente, id, valor, data, administrador));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recebimentos;
    }

    public void update(Recebimento recebimento) {
        transferenciaDAO.update(recebimento);

        String sql = "UPDATE " + this.tableName + " SET cliente_id = ? WHERE transferencia_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, recebimento.getCliente().getId());
            stmt.setInt(2, recebimento.getId());

            stmt.executeUpdate();

            System.out.println("Recebimento atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM " + this.tableName + " WHERE transferencia_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();

            transferenciaDAO.deleteById(id);

            System.out.println("Recebimento deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Recebimento> findByClienteId(int clienteId) {
        List<Recebimento> recebimentos = new ArrayList<>();
        String sql = "SELECT r.transferencia_id, t.valor, t.data, t.administrador_id FROM " + this.tableName + " r JOIN transferencia t ON r.transferencia_id = t.id WHERE r.cliente_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("transferencia_id");
                    float valor = rs.getFloat("valor");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    int administradorId = rs.getInt("administrador_id");

                    Cliente cliente = clienteDAO.find(clienteId);
                    Administrador administrador = new AdministradorDAO().find(administradorId);

                    recebimentos.add(new Recebimento(cliente, id, valor, data, administrador));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recebimentos;
    }

    public String getTableName() {
        return tableName;
    }
}
