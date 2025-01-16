package ufrn.tads.agendamentodeconsultas.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Dados para conexão com o banco
    private static final String URL = "jdbc:postgresql://localhost:5432/crud";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "admin";

    // Metodo para conectar ao banco de dados
    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão bem-sucedida!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
        return conexao;
    }
}
