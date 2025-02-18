package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.connection.Conexao;
import com.example.dominio.Livro;

public class LivroDAO {

    Connection conexao;

    public LivroDAO() {
        try {
            this.conexao = Conexao.conexaoBanco();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void inserirLivro(Livro livro) throws SQLException {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void atualizarStatusLivro(int id, String status) throws SQLException {

        String sql = "UPDATE livros SET status = ? WHERE id = ? ";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public ResultSet listarLivros() throws SQLException {

        String sql = "SELECT * FROM livros";

        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        return resultado;

    }

    public ResultSet listarLivrosEmprestados() throws SQLException {

        String sql = "SELECT * FROM livros WHERE status = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, "Emprestado");
        ResultSet resultado = stmt.executeQuery();
        return resultado;

    }

    public ResultSet listarLivrosDisponiveis() throws SQLException {

        String sql = "SELECT * FROM livros WHERE status = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, "Disponível");
        ResultSet resultado = stmt.executeQuery();
        return resultado;

    }

}
