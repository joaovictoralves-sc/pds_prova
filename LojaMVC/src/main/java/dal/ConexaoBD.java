package dal;

import java.sql.*;

public class ConexaoBD {
    // São criadas constantes pois as informações são estáticas
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/lojabd";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    // Método para estabelecer conexão com o BD
    public static Connection conectar() {
        try {
            // Carregar o driver JDBC
            Class.forName(DRIVER_CLASS);

            // Retornar a conexão
            return DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            // Registrar a exceção (poderia ser feito com Logger, mas aqui usamos printStackTrace)
            e.printStackTrace();
            return null;
        }
    }

    // Método para desconectar do banco de dados
    public static void desconectar(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();  // Fechar a conexão
            } catch (SQLException e) {
                // Registrar qualquer erro ao fechar a conexão
                e.printStackTrace();
            }
        }
    }
}
