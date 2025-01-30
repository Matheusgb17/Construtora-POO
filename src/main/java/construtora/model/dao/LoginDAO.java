package construtora.model.dao;

import construtora.model.entity.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    /**
     * Realiza a autenticação de um usuário com CPF e senha
     * @param cpf CPF do usuário
     * @param senha Senha do usuário
     * @return O usuário autenticado ou NULL se falhar
     */
    public Usuario autenticar(String cpf, String senha) {
        String sql = "SELECT * FROM usuario WHERE cpf = ? AND senha = ?";

        try (
                Connection conexao = Conexao.getConexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("papel")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retorna NULL se não encontrar o usuário
    }
}
