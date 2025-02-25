package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.dominio.Usuario;

public class UsuarioDAO {

    Connection conexao;

    public UsuarioDAO(Connection conexao){
        if (conexao == null){
            System.out.println("A conexão não pode ser nula!");
        }
        this.conexao = conexao;

    }
    
    public int inserirUsuario(Usuario usuario) throws SQLException{

        

        String sql = "INSERT INTO usuarios (nome, cpf, email) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());

            stmt.executeUpdate();


            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                
            } catch (Exception e) {
                System.out.println("Erro ao buscar ID do livro: " + e.getMessage());
            }

        }
        catch (Exception e) {
            System.err.println("Erro ao conectar!\n" + e.getMessage());
        }
        return -1;
    } 


    public List<Usuario> exibirUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                int idUsuario = resultado.getInt("id");
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");


                Usuario usuario = new Usuario(idUsuario, nome, cpf, email);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao exibir usuários\n" + e.getMessage());
        }

        return  usuarios;
    }

   

}


