package br.com.criptaliza.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Constantes que são informações para manter a conexão com o banco de dados:
    private static final String URL = System.getenv("DB_URL");
    private static final String USUARIO = System.getenv("DB_USER");
    private static final String SENHA = System.getenv("DB_PASS");

    // Método para obter uma conexão com o banco de dados:

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }


}
