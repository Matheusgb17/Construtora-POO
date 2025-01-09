package construtora.model.dao;

import construtora.model.entity.Contrato;
import construtora.model.entity.Engenheiro;
import construtora.model.entity.Construtor;
import construtora.model.entity.Obra;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {
    private final String tableName = "contrato";

    private final EngenheiroDAO engenheiroDAO = new EngenheiroDAO();
    private final ConstrutorDAO construtorDAO = new ConstrutorDAO();
    private final ObraDAO obraDAO = new ObraDAO();

    private final Connection conexao;

    public ContratoDAO() {
        this.conexao = Conexao.getConexao();
    }

    public void create(Contrato contrato) {
        String sql = "INSERT INTO " + this.tableName + " (data_inicio, data_fim, valor, engenheiro_id, construtor_id, obra_id) VALUES (?, ?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(contrato.getDataInicio()));
            stmt.setDate(2, Date.valueOf(contrato.getDataFim()));
            stmt.setFloat(3, contrato.getValor());
            stmt.setInt(4, contrato.getEngenheiro().getId());
            stmt.setInt(5, contrato.getConstrutor().getId());
            stmt.setInt(6, contrato.getObra().getId());

            stmt.executeUpdate();

            System.out.println("Contrato inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contrato find(int id) {
        String sql = "SELECT * FROM " + this.tableName + " WHERE id = ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Engenheiro engenheiro = engenheiroDAO.find(rs.getInt("engenheiro_id"));
                    Construtor construtor = construtorDAO.find(rs.getInt("construtor_id"));
                    Obra obra = obraDAO.find(rs.getInt("obra_id"));

                    return new Contrato(
                        rs.getInt("id"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        rs.getFloat("valor"),
                        engenheiro,
                        construtor,
                        obra
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Contrato> findAll() {
        List<Contrato> contratos = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + ";";

        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Engenheiro engenheiro = engenheiroDAO.find(rs.getInt("engenheiro_id"));
                Construtor construtor = construtorDAO.find(rs.getInt("construtor_id"));
                Obra obra = obraDAO.find(rs.getInt("obra_id"));

                Contrato contrato = new Contrato(
                    rs.getInt("id"),
                    rs.getDate("data_inicio").toLocalDate(),
                    rs.getDate("data_fim").toLocalDate(),
                    rs.getFloat("valor"),
                    engenheiro,
                    construtor,
                    obra
                );

                contratos.add(contrato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratos;
    }

    public void update(Contrato contrato) {
        String sql = "UPDATE " + this.tableName + " SET data_inicio = ?, data_fim = ?, valor = ?, engenheiro_id = ?, construtor_id = ?, obra_id = ? WHERE id = ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(contrato.getDataInicio()));
            stmt.setDate(2, Date.valueOf(contrato.getDataFim()));
            stmt.setFloat(3, contrato.getValor());
            stmt.setInt(4, contrato.getEngenheiro().getId());
            stmt.setInt(5, contrato.getConstrutor().getId());
            stmt.setInt(6, contrato.getObra().getId());
            stmt.setInt(7, contrato.getId());

            stmt.executeUpdate();

            System.out.println("Contrato atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM " + this.tableName + " WHERE id = ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Contrato deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTableName() {
        return tableName;
    }
}
