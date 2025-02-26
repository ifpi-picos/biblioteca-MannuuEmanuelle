package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.dominio.Livro;

public class LivroDAO {

   
    Connection conexao;

    public LivroDAO(Connection conexao) {

        if (conexao == null){
            System.err.println("Erro ao fazer conexão! ");

        }
        this.conexao = conexao;

      
    }

    public int inserirLivro(Livro livro) {
        String sql = "INSERT INTO livros(titulo, autor, genero, editora, ano_publicacao, isbn, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setString(4, livro.getEditora());
            stmt.setInt(5, livro.getAnoPublicacao());
            stmt.setString(6, livro.getIsbn());
            stmt.setString(7, livro.getStatus());

            stmt.executeUpdate();


            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                
            } catch (Exception e) {
                System.out.println("Erro ao buscar ID do livro: " + e.getMessage());
            }

            

        } catch(Exception e){
            System.err.println("Erro ao conectar com o banco de dados! " + e.getMessage());
        }
        return -1;
    }

    public void atualizarStatusLivro(int id, String status) {

        // System.out.println("Testando...");

        try {

            conexao.setAutoCommit(false);
       
        
            String sql = "UPDATE livros SET status = ? WHERE id = ? ";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, status);
                stmt.setInt(2, id);

                stmt.executeUpdate();



            } catch (SQLException e) {
                System.err.println("Erro ao conectar com banco de dados");

        }


        conexao.commit();

    } catch (SQLException e) {
        System.err.println("Erro ao atualizar status do livro\n" + e.getMessage());
    }

    }

    public List<Livro> listarLivros() {

        
        String sql = "SELECT * FROM livros"; 

        List<Livro> listaLivros = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql); 
            ResultSet resultado = stmt.executeQuery()) {
           
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String titulo = resultado.getString("titulo");
                String autor = resultado.getString("autor");
                String genero = resultado.getString("genero");
                String editora = resultado.getString("editora");
                int anoPublicacao = resultado.getInt("ano_publicacao");
                String isbn = resultado.getString("isbn");
                String status = resultado.getString("status");
                
                Livro livro = new Livro(id, titulo, autor, genero, editora, anoPublicacao, isbn, status);
                listaLivros.add(livro);
            }          
        } catch (SQLException e){
            System.err.println("Erro ao conectar com banco de dados!");
            
        }

        return listaLivros;

    }

    public List<Livro> listarLivrosEmprestados() {

        String sql = "SELECT * FROM livros WHERE status = ?"; // Fetch books based on status

        List<Livro> listaLivrosEmprestados = new ArrayList<>();

        try(PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "Emprestado");
        
            try (ResultSet resultado = stmt.executeQuery()) {
                while(resultado.next()){
                    int id = resultado.getInt("id");
                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    String genero = resultado.getString("genero");
                    String editora = resultado.getString("editora");
                    int anoPublicacao = resultado.getInt("ano_publicacao");
                    String isbn = resultado.getString("isbn");
                    String status = resultado.getString("status");
        
                    Livro livro = new Livro(id, titulo, autor, genero, editora, anoPublicacao, isbn, status);
                    listaLivrosEmprestados.add(livro);
                }

            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros emprestados! " + e.getMessage());
        }   
    

        return listaLivrosEmprestados;
        

    }

    public List<Livro> listarLivrosDisponiveis() {

        String sql = "SELECT * FROM livros WHERE status = ?"; // Fetch available books

        List<Livro> listaLivrosDisponiveis = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "Disponível");
            try (ResultSet resultado = stmt.executeQuery()) {

                while (resultado.next()){
                    int id = resultado.getInt("id");
                    String titulo = resultado.getString("titulo");
                    String autor = resultado.getString("autor");
                    String genero = resultado.getString("genero");
                    String editora = resultado.getString("editora");
                    int anoPublicacao = resultado.getInt("ano_publicacao");
                    String isbn = resultado.getString("isbn");
                    String status = resultado.getString("status");
    
                    Livro livro = new Livro(id, titulo, autor, genero, editora, anoPublicacao, isbn, status);
                    listaLivrosDisponiveis.add(livro);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados" + e.getMessage());
        }

        return listaLivrosDisponiveis;
        

    }
}
