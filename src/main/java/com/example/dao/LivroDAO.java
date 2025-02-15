package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dominio.Livro;

public class LivroDAO {

    Connection conexao;

    public LivroDAO(Connection conexao) {
        if (conexao == null) {
            System.out.println("Falha ao conectar!");
        }
        this.conexao = conexao;
    };

    public void inserirLivro(Livro livro) throws SQLException {

        String sql = "INSERT INTO livros(titulo, autor, genero, editora, ano_publicacao, isbn, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setString(1, livro.getTitulo());
        stmt.setString(2, livro.getAutor());
        stmt.setString(3, livro.getGenero());
        stmt.setString(4, livro.getEditora());
        stmt.setInt(5, livro.getAnoPublicacao());
        stmt.setString(6, livro.getIsbn());
        stmt.setString(7, livro.getStatus());

        stmt.executeUpdate();

    }

    public void atualizarStatusLivro(int id, String status) throws SQLException {
        String sql = "UPDATE livros SET status = ? WHERE id = ? ";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }

    }

    public ResultSet listarLivros() throws SQLException {

        String sql = "SELECT * FROM livros";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet resultado = stmt.executeQuery();
            return resultado;

        }

    }
}
