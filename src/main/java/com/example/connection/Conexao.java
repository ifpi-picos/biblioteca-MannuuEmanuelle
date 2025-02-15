package com.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conexaoBanco() throws SQLException {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_biblioteca", "postgres", "postgresql");
            if (conexao == null || conexao.isClosed()) {
                throw new SQLException("Falha ao estabelecer conexão com o banco de dados");
            }
            return conexao;
        } catch (SQLException ex) {
            System.err.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
            throw ex;
        }
    }

}
