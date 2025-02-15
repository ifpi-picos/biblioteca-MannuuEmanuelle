package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    Connection conexao;

    public UsuarioDAO(Connection conexao){
        if (conexao == null){
            System.out.println("A conexão não pode ser nula!");
        }
        this.conexao = conexao;

    }
    
    public void inserirUsuario(String nome, String cpf, String email) throws SQLException{
        String sql = "INSERT INTO usuarios (nome, cpf, email) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            

            stmt.setString(1, nome );
            stmt.setString(2, cpf);
            stmt.setString(3, email);

            stmt.executeUpdate();

        }
        catch (Exception e) {
            // TODO: handle exception
        }
    } 


}
