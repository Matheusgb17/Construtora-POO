package construtora.model.dao;

import construtora.model.entity.Transferencia;
import construtora.model.entity.Administrador;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaDAO {

    public int create(Transferencia transferencia) {
        String sql = "INSERT INTO transferencia (valor, data, administrador_id) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setFloat(1, transferencia.getValor());
            stmt.setDate(2, Date.valueOf(transferencia.getData()));
            stmt.setInt(3, transferencia.getAdministrador().getId());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                   transferencia.setId(generatedKeys.getInt(1));
                   return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void update(Transferencia transferencia) {
        String sql = "UPDATE transferencia SET valor = ?, data = ?, administrador_id = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, transferencia.getValor());
            stmt.setDate(2, Date.valueOf(transferencia.getData()));
            stmt.setInt(3, transferencia.getAdministrador().getId());
            stmt.setInt(4, transferencia.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transferencia findById(int id) {
        String sql = "SELECT * FROM transferencia WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Administrador administrador = new AdministradorDAO().find(rs.getInt("administrador_id"));
                    return new Transferencia(
                            rs.getInt("id"),
                            rs.getFloat("valor"),
                            rs.getDate("data").toLocalDate(),
                            administrador
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Transferencia> findAll() {
        String sql = "SELECT * FROM transferencia";
        List<Transferencia> transferencias = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrador administrador = new AdministradorDAO().find(rs.getInt("administrador_id"));
                transferencias.add(new Transferencia(
                        rs.getInt("id"),
                        rs.getFloat("valor"),
                        rs.getDate("data").toLocalDate(),
                        administrador
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transferencias;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM transferencia WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transferencia> findByAdministradorId(int administradorId) {
        String sql = "SELECT * FROM transferencia WHERE administrador_id = ?";
        List<Transferencia> transferencias = new ArrayList<>();

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, administradorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Administrador administrador = new AdministradorDAO().find(administradorId);
                    transferencias.add(new Transferencia(
                            rs.getInt("id"),
                            rs.getFloat("valor"),
                            rs.getDate("data").toLocalDate(),
                            administrador
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transferencias;
    }
}
