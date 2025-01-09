package construtora.model.dao;

import construtora.model.entity.Obra;
import construtora.model.entity.Usuario;
import construtora.model.entity.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO {
    private Connection conexao;
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    private final String notApproved = "Não aprovada";
    private final String approved = "Aprovada";
    private final String underReview = "Sob revisão";
    
    public ObraDAO () {
        this.conexao = Conexao.getConexao();
    }
    
    /**
     * Adiciona uma obra ao banco de dados, o cliente da obra é o que está logado
     * @param obra TAD da obra a ser adicionada
     */
    public void create (Obra obra) {
        String sql = "INSERT INTO obra (endereco, tipoObra, status, cliente_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, obra.getEndereco());
            stmt.setString(2, obra.getTipoObra());
            stmt.setString(3, obra.getStatus());
            stmt.setInt(4, obra.getCliente().getId());
            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obra.setId(rs.getInt(1));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Atualiza as informações da obra
     * @param obra TAD da obra com os dados já atualizados
     */
    public void update (Obra obra) {
        String sql = "UPDATE obra SET endereco = ?, tipoObra = ?, status = ?, cliente_id = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, obra.getEndereco());
            stmt.setString(2, obra.getTipoObra());
            stmt.setString(3, obra.getStatus());
            stmt.setInt(4, obra.getCliente().getId());
            stmt.setInt(5, obra.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Exclui uma obra do banco de dados
     * @param id ID da obra a ser excluída
     */
    public void delete (int id) {
        String sql = "DELETE FROM obra WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Encontra uma obra específica de acordo com o ID dela
     * @param id ID da obra a ser encontrada
     * @return TAD da obra encontrada ou NULL
     */
    public Obra find(int id) {
    String sql = "SELECT * FROM obra WHERE id = ?;";
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setInt(1, id);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = this.clienteDAO.find(clienteId);
                System.out.println(cliente.toString());
                
                Obra obra = new Obra(
                    rs.getInt("id"),
                    rs.getString("endereco"),
                    rs.getString("tipoObra"),
                    rs.getString("status"),
                    cliente
                );
                
                return obra;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Recomendado utilizar logging em vez de printStackTrace
    }
    
    return null;
}

    
    /**
     * Encontra todas as obras cadastradas no banco de dados
     * @return Lista das obras encontradas
     */
    public List<Obra> findAll () {
        String sql = "SELECT * FROM obra";
        List<Obra> obras = new ArrayList<>();
        
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                obras.add(
                        new Obra(
                            rs.getInt("id"),
                            rs.getString("endereco"),
                            rs.getString("tipoObra"),
                            rs.getString("status"),
                            clienteDAO.find(rs.getInt("cliente_id")
                        )
                    )
                );
            }
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        return obras;
    }
    
    /**
     * Encontra todas as obras de um cliente específico
     * @param cpf CPF do cliente
     * @return Lista de todas as obras do cliente especificado
     */
    public List<Obra> findByClient (String cpf) {
        String sql = "SELECT o.* FROM obra o " +
                     "INNER JOIN cliente c ON o.cliente_id = c.id " +
                     "INNER JOIN usuario u ON c.usuario_id = u.id " +
                     "WHERE u.cpf = ?";
        List<Obra> obras = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    obras.add(new Obra(
                        rs.getInt("id"),
                        rs.getString("endereco"),
                        rs.getString("tipoObra"),
                        rs.getString("status"),
                        clienteDAO.find(rs.getInt("cliente_id")
                    )));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return obras;
    }
    
    /**
     * Função genérica para recuperar as obras de acordo com os status delas
     * @param status Status das obras que deseja recuperar
     * @return Lista de todas as obras que atendam ao requisito
     */
    private List<Obra> findByStatus(String status) {
        String sql = "SELECT * FROM obra WHERE status = ?";
        List<Obra> obras = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    obras.add(new Obra(
                        rs.getInt("id"),
                        rs.getString("endereco"),
                        rs.getString("tipoObra"),
                        rs.getString("status"),
                        clienteDAO.find(rs.getInt("cliente_id"))
                    ));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return obras;
    }
    
    /**
     * Encontra todas as obras cujo status é "Não aprovada"
     * @return Lista de todas as obras de status "Não aprovada"
     */
    public List<Obra> findNotApproved () {
        return findByStatus(this.notApproved);
    }
    
    /**
     * Encontra todas as obras cujo status é "Aprovada"
     * @return Lista de todas as obras de status "Aprovada"
     */
    public List<Obra> findApproved () {
        return findByStatus(this.approved);
    }
    
    /**
     * Encontra todas as obras cujo status é "Sob revisão"
     * @return Lista de todas as obras de status "Sob revisão"
     */
    public List<Obra> findUnderReview () {
        return findByStatus(this.underReview);
    }
}
