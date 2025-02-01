package construtora.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static final String URL = "jdbc:mysql://localhost:3307/construtora";
    public static final String USER = "root";
    public static final String PASSWORD = "1010";
    
    public static Connection getConexao () {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e) {
            System.out.println("=== ERRO AO CONECTAR AO BANCO DE DADOS ===");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}