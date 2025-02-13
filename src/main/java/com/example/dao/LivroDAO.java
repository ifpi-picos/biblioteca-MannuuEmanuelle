package com.example.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.connection.Conexao;
import com.example.dominio.Livro;



public class LivroDAO {

    
    public static void inserirLivro(Livro livro){

        String sql = "INSERT INTO livros(titulo, autor, genero, editora, ano_publicacao, isbn, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conexaoBanco();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setString(4, livro.getEditora());
            stmt.setInt(5, livro.getAnoPublicacao());
            stmt.setString(6, livro.getIsbn());
            stmt.setString(7, livro.getStatus());

            stmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void atualizarStatusLivro(Connection conexao, int id, String status) throws SQLException {
        String sql = "UPDATE livros SET status = ? WHERE id = ? ";

       
        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, status);
        stmt.setInt(2, id);

        stmt.executeUpdate();

    }

    public static void listarLivros(){

        String sql = "SELECT * FROM livros";
        try ( Connection conexao = Conexao.conexaoBanco();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery()){

                while(resultado.next()){
                    int id = resultado.getInt("id");
                    System.out.println("ID: " + id);
                    
                    String titulo = resultado.getString("titulo");
                    System.out.println("Título: " + titulo);

                    String autor = resultado.getString("autor");
                    System.out.println("Autor: " + autor);

                    String genero = resultado.getString("genero");
                    System.out.println("Gênero: " + genero);

                    String editora = resultado.getString("editora");
                    System.out.println("Editora: " + editora);

                    int ano = resultado.getInt("ano_publicacao");
                    System.out.println("Ano de publicação: " + ano);

                    String isbn = resultado.getString("isbn");
                    System.out.println("ISBN: " + isbn);

                    String status = resultado.getString("status");
                    System.out.println("Status: " + status + "\n\n");
    
                }
                    } catch (SQLException e) {
                        e.printStackTrace();
        }
           
                
        } 



       
    }


    
