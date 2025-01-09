package construtora.model.dao;

import construtora.model.entity.Administrador;
import construtora.model.entity.Construtor;
import construtora.model.entity.Pagamento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    
    private final TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
    private final ConstrutorDAO construtorDAO = new ConstrutorDAO();
    
    private final String tableName = "pagamento";

    private Connection conexao;

    public PagamentoDAO() {
        this.conexao = Conexao.getConexao();
    }

    public void create(Pagamento pagamento) {
        int transferenciaId = transferenciaDAO.create(pagamento);

        if (transferenciaId == -1) {
            System.out.println("=== ERRO AO INSERIR NA TABELA DE TRANSFERÊNCIAS ===");
            return;
        }

        String sql = "INSERT INTO " + this.tableName + " (transferencia_id, construtor_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, transferenciaId);
            stmt.setInt(2, pagamento.getConstrutor().getId());

            stmt.executeUpdate();

            System.out.println("Pagamento inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pagamento find(int id) {
        String sqlPagamento = "SELECT p.construtor_id, t.valor, t.data, t.administrador_id FROM " + this.tableName + " p JOIN transferencia t ON p.transferencia_id = t.id WHERE p.transferencia_id = ?";

        try (PreparedStatement stmtPagamento = conexao.prepareStatement(sqlPagamento)) {

            stmtPagamento.setInt(1, id);

            try (ResultSet rsPagamento = stmtPagamento.executeQuery()) {
                if (rsPagamento.next()) {
                    int construtorId = rsPagamento.getInt("construtor_id");
                    float valor = rsPagamento.getFloat("valor");
                    LocalDate data = rsPagamento.getDate("data").toLocalDate();
                    int administradorId = rsPagamento.getInt("administrador_id");

                    Construtor construtor = construtorDAO.find(construtorId);
                    Administrador administrador = new AdministradorDAO().find(administradorId);

                    return new Pagamento(construtor, id, valor, data, administrador);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Pagamento> findAll() {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT p.transferencia_id, p.construtor_id, t.valor, t.data, t.administrador_id FROM " + this.tableName + " p JOIN transferencia t ON p.transferencia_id = t.id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("transferencia_id");
                int construtorId = rs.getInt("construtor_id");
                float valor = rs.getFloat("valor");
                LocalDate data = rs.getDate("data").toLocalDate();
                int administradorId = rs.getInt("administrador_id");

                Construtor construtor = construtorDAO.find(construtorId);
                Administrador administrador = new AdministradorDAO().find(administradorId);

                pagamentos.add(new Pagamento(construtor, id, valor, data, administrador));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamentos;
    }

    public void update(Pagamento pagamento) {
        transferenciaDAO.update(pagamento);

        String sql = "UPDATE " + this.tableName + " SET construtor_id = ? WHERE transferencia_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getConstrutor().getId());
            stmt.setInt(2, pagamento.getId());

            stmt.executeUpdate();

            System.out.println("Pagamento atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "SELECT transferencia_id FROM " + this.getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // Busca o `usuario_id` correspondente ao `id` do cliente
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int transferenciaId = rs.getInt("transferencia_id");

                // Deleta o usuário associado, cascata garante que cliente será deletado
                this.transferenciaDAO.deleteById(transferenciaId);
            } else {
                System.out.println("Pagamento não encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pagamento> findByConstrutorId(int construtorId) {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT p.transferencia_id, t.valor, t.data, t.administrador_id FROM " + this.tableName + " p JOIN transferencia t ON p.transferencia_id = t.id WHERE p.construtor_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, construtorId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("transferencia_id");
                    float valor = rs.getFloat("valor");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    int administradorId = rs.getInt("administrador_id");

                    Construtor construtor = construtorDAO.find(construtorId);
                    Administrador administrador = new AdministradorDAO().find(administradorId);

                    pagamentos.add(new Pagamento(construtor, id, valor, data, administrador));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamentos;
    }

    public String getTableName() {
        return tableName;
    }
} 
