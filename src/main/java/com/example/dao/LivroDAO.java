package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.connection.Conexao;
import com.example.dominio.Livro;

public class LivroDAO {

    String statusEncontrado;
    Connection conexao;

    public LivroDAO() {
        try {
            this.conexao = Conexao.conexaoBanco();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void inserirLivro(Livro livro) {
        String sql = "INSERT INTO livros(titulo, autor, genero, editora, ano_publicacao, isbn, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setString(4, livro.getEditora());
            stmt.setInt(5, livro.getAnoPublicacao());
            stmt.setString(6, livro.getIsbn());
            stmt.setString(7, livro.getStatus());

            stmt.executeUpdate();

        } catch(Exception e){
            System.err.println("Erro ao conectar com o banco de dados!");
        }

    }

    public void atualizarStatusLivro(int id, String status) {

        String sql = "UPDATE livros SET status = ? WHERE id = ? ";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com banco de dados");

        }

    }

    public List<Livro> listarLivros() {

        
        String sql = "SELECT * FROM livros";

        List<Livro> listaLivros;

        listaLivros = new ArrayList<>();

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

        String sql = "SELECT * FROM livros WHERE status = ?";

        List<Livro> listaLivrosEmprestados = new ArrayList<>();

        try(PreparedStatement stmt = conexao.prepareStatement(sql)) {
        ResultSet resultado = stmt.executeQuery();


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
            
        } catch (Exception ex) {
            System.err.println("Erro ao conectar com banco de dados!" + ex.getMessage());
            

        }

        return listaLivrosEmprestados;
        

    }

    public List<Livro> listarLivrosDisponiveis() {

        String sql = "SELECT * FROM livros WHERE status = ?";

        List<Livro> listaLivrosDisponiveis = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet resultado = stmt.executeQuery();
            
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
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados" + e.getMessage());
        }

        return listaLivrosDisponiveis;
        

    }

    // public String buscandoStatusLivro(int id) {
    //     String sql = "SELECT status FROM livros WHERE id = ?";

    //     try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
    //         stmt.setInt(1, id);
    //         try (ResultSet resultado = stmt.executeQuery()) {
    //             if (resultado.next()) {
    //                 statusEncontrado = resultado.getString("status");
    //             }
    //         }

    //         return statusEncontrado;

    //     }
    // }

}
