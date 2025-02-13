package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.connection.Conexao;
import com.example.dominio.Usuario;

public class UsuarioDAO {
    public static void inserirUsuario(Usuario usuario) throws SQLException{
        String sql = "INSERT INTO usuarios (nome, cpf, email) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conexaoBanco()){
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());

            stmt.executeUpdate();

        }
    } 


}
