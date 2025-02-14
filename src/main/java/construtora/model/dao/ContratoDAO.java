package construtora.model.dao;

/* Imports do projeto. */
import construtora.model.entity.Contrato;
import construtora.model.entity.Engenheiro;
import construtora.model.entity.Construtor;
import construtora.model.entity.Funcionario;
import construtora.model.entity.Obra;

/* Imports do Java */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ContratoDAO {
    private final String tableName = "contrato";

    private final EngenheiroDAO engenheiroDAO = new EngenheiroDAO();
    private final ConstrutorDAO construtorDAO = new ConstrutorDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ObraDAO obraDAO = new ObraDAO();

    private final Connection conexao;

    public ContratoDAO() {
        this.conexao = Conexao.getConexao();
    }

    public void create(Contrato contrato) {
        String sql = "INSERT INTO " + this.tableName + " (dataInicio, dataFim, valor, engenheiro_id, construtor_id, obra_id) VALUES (?, ?, ?, ?, ?, ?);";

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
                        rs.getDate("dataInicio").toLocalDate(),
                        rs.getDate("dataFim").toLocalDate(),
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
                    rs.getDate("dataInicio").toLocalDate(),
                    rs.getDate("dataFim").toLocalDate(),
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
        String sql = "UPDATE " + this.tableName + " SET dataInicio = ?, dataFim = ?, valor = ?, engenheiro_id = ?, construtor_id = ?, obra_id = ? WHERE id = ?;";

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
    
    public List<Contrato> getContratosFinalizados () {
        List<Contrato> contratosFinalizados = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE dataFim <= ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosFinalizados.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosFinalizados;
    }
    
    public List<Contrato> getContratosEmAndamento () {
        List<Contrato> contratosFinalizados = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE dataInicio <= ? AND dataFim > ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosFinalizados.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosFinalizados;
    }
    
    public List<Contrato> getContratosFuturos () {
        List<Contrato> contratosFinalizados = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE dataInicio > ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosFinalizados.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosFinalizados;
    }
    
    /**
     * Retorna uma lista de contratos finalizados de um engenheiro.
     * @param engenheiro O engenheiro cujos contratos finalizados serão retornados.
     * @return Lista de contratos finalizados.
     */
    public List<Contrato> getContratosFinalizados(Engenheiro engenheiro) {
        List<Contrato> contratosFinalizados = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE engenheiro_id = ? AND dataFim <= ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, engenheiro.getId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosFinalizados.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosFinalizados;
    }

    /**
     * Retorna uma lista de contratos em andamento de um engenheiro.
     * @param engenheiro O engenheiro cujos contratos em andamento serão retornados.
     * @return Lista de contratos em andamento.
     */
    public List<Contrato> getContratosEmAndamento(Engenheiro engenheiro) {
        List<Contrato> contratosEmAndamento = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE engenheiro_id = ? AND dataInicio <= ? AND dataFim > ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, engenheiro.getId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosEmAndamento.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosEmAndamento;
    }

    /**
     * Retorna uma lista de contratos futuros de um engenheiro.
     * @param engenheiro O engenheiro cujos contratos futuros serão retornados.
     * @return Lista de contratos futuros.
     */
    public List<Contrato> getContratosFuturos(Engenheiro engenheiro) {
        List<Contrato> contratosFuturos = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE engenheiro_id = ? AND dataInicio > ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, engenheiro.getId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosFuturos.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosFuturos;
    }
    
    /**
     * Lista todos os contratos de determinado engenheiro
     * @param engenheiro O engenheiro cujos contratos serçao retornados
     * @return Lista de todos os contratos.
     */
    public List<Contrato> getTodosContratosPorEngenheiro(Engenheiro engenheiro) {
        List<Contrato> todosContratos = new ArrayList<>();
        todosContratos.addAll(getContratosFinalizados(engenheiro));
        todosContratos.addAll(getContratosEmAndamento(engenheiro));
        todosContratos.addAll(getContratosFuturos(engenheiro));
        return todosContratos;
    }
    
    /**
     * Retorna uma lista de contratos finalizados de um construtor.
     *
     * @param construtor O construtor cujos contratos finalizados serão
     * retornados.
     * @return Lista de contratos finalizados.
     */
    public List<Contrato> getContratosFinalizados(Construtor construtor) {
        List<Contrato> contratosFinalizados = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE construtor_id = ? AND dataFim <= ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, construtor.getId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosFinalizados.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosFinalizados;
    }

    /**
     * Retorna uma lista de contratos em andamento de um construtor.
     *
     * @param construtor O construtor cujos contratos em andamento serão
     * retornados.
     * @return Lista de contratos em andamento.
     */
    public List<Contrato> getContratosEmAndamento(Construtor construtor) {
        List<Contrato> contratosEmAndamento = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE construtor_id = ? AND dataInicio <= ? AND dataFim > ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, construtor.getId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosEmAndamento.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosEmAndamento;
    }

    /**
     * Retorna uma lista de contratos futuros de um construtor.
     *
     * @param construtor O construtor cujos contratos futuros serão retornados.
     * @return Lista de contratos futuros.
     */
    public List<Contrato> getContratosFuturos(Construtor construtor) {
        List<Contrato> contratosFuturos = new ArrayList<>();
        String sql = "SELECT * FROM " + this.tableName + " WHERE construtor_id = ? AND dataInicio > ?;";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, construtor.getId());
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = buildContratoFromResultSet(rs);
                    contratosFuturos.add(contrato);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contratosFuturos;
    }

    /**
     * Lista todos os contratos de determinado construtor.
     *
     * @param construtor O construtor cujos contratos serão retornados.
     * @return Lista de todos os contratos.
     */
    public List<Contrato> getTodosContratosPorConstrutor(Construtor construtor) {
        List<Contrato> todosContratos = new ArrayList<>();
        todosContratos.addAll(getContratosFinalizados(construtor));
        todosContratos.addAll(getContratosEmAndamento(construtor));
        todosContratos.addAll(getContratosFuturos(construtor));
        return todosContratos;
    }
    
    public List<Contrato> getContratosFinalizados (Funcionario funcionario) {
        List<Contrato> contratos = new ArrayList<>();
        
        Construtor construtor = construtorDAO.find(funcionario.getConstrutor().getCpf());
        
        contratos = getContratosFinalizados(construtor);
        
        return contratos;
    }
    
    public List<Contrato> getContratosEmAndamento (Funcionario funcionario) {
        List<Contrato> contratos = new ArrayList<>();
        
        Construtor construtor = construtorDAO.find(funcionario.getConstrutor().getCpf());
        
        contratos = getContratosEmAndamento(construtor);
        
        return contratos;
    }
    
    public List<Contrato> getContratosFuturos (Funcionario funcionario) {
        List<Contrato> contratos = new ArrayList<>();
        
        Construtor construtor = construtorDAO.find(funcionario.getConstrutor().getCpf());
        
        contratos = getContratosFuturos(construtor);
        
        return contratos;
    }
    
    public List<Contrato> getTodosContratosPorFuncionario (Funcionario funcionario) {
        List<Contrato> todosContratos = new ArrayList<>();
        todosContratos.addAll(getContratosFinalizados(funcionario));
        todosContratos.addAll(getContratosEmAndamento(funcionario));
        todosContratos.addAll(getContratosFuturos(funcionario));
        return todosContratos;
    }

    /**
     * Constrói um objeto Contrato a partir de um ResultSet.
     * @param rs O ResultSet contendo os dados do contrato.
     * @return Um objeto Contrato.
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet.
     */
    private Contrato buildContratoFromResultSet(ResultSet rs) throws SQLException {
        Engenheiro engenheiro = engenheiroDAO.find(rs.getInt("engenheiro_id"));
        Construtor construtor = construtorDAO.find(rs.getInt("construtor_id"));
        Obra obra = obraDAO.find(rs.getInt("obra_id"));

        return new Contrato(
            rs.getInt("id"),
            rs.getDate("dataInicio").toLocalDate(),
            rs.getDate("dataFim").toLocalDate(),
            rs.getFloat("valor"),
            engenheiro,
            construtor,
            obra
        );
    }

    public String getTableName() {
        return tableName;
    }
    
    public void close() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
