package com.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    public static Connection conexaoBanco(){

        try {
            Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_biblioteca", "postgres", "postgresql");
            return conexao;
            
        }
         catch (SQLException ex) {
            System.err.println("Falha na conexão com o banco");
            return null;
        }                     

    }
   
   
   


    static void consultaDados(Statement stm) {
        String sql = "select id, titulo, autor from livros";

        try {
            ResultSet result = stm.executeQuery(sql);

            while (result.next()) {
                System.out.println("id: " + result.getInt("id") + ", Título: " + result.getString("titulo") + ", Autor: " + result.getString("autor"));
            }
            

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   
    }


    


  



