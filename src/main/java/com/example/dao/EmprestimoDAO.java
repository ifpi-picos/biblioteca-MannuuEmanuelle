package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmprestimoDAO {

    String statusEncontrado;
    Connection conexao;

    public EmprestimoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public String buscandoStatusLivro(int id) throws SQLException {
        String sql = "SELECT status FROM livros WHERE id = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, id);

        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            statusEncontrado = resultado.getString("status");
        }

        return statusEncontrado;

    }

    public String usuarioEncontrado(int id) throws SQLException {
        String usuarioEncontrado = null;

        String sql = "SELECT nome FROM usuarios WHERE id = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, id);

        ResultSet resultado = stmt.executeQuery();
        if (resultado.next()) {
            usuarioEncontrado = resultado.getString("nome");
        }

        return usuarioEncontrado != null ? usuarioEncontrado : "Usuário não encontrado!";

    }

    public String livroEncontrado(int id) throws SQLException {
        String livroEncontrado = null;

        String sql = "SELECT titulo FROM livros WHERE id = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);

        stmt.setInt(1, id);

        ResultSet resultado = stmt.executeQuery();

        if (resultado.next()) {
            livroEncontrado = resultado.getString("titulo");
        }

        return livroEncontrado != null ? livroEncontrado : "Livro não encontrado!";
    }

    public void realizarEmprestimo(int idLivro, int idUsuario, LocalDate dataEmprestimo) {

        String usuario;
        String livro;
        String statusLivro;
        try {
            usuario = usuarioEncontrado(idUsuario);
            livro = livroEncontrado(idLivro);
            statusLivro = buscandoStatusLivro(idLivro);

            if ("Usuário não encontrado!".equals(usuario)) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            if ("Livro não encontrado!".equals(livro)) {
                System.out.println("Livro não encontrado!");
                return;
            }

            if (!"disponível".equalsIgnoreCase(statusLivro)) {
                System.out.println("Livro não disponível para empréstimo!");
                return;

            }

            
            LocalDate dataDevolucao = dataEmprestimo.plusDays(7);

            String sql = "INSERT INTO emprestimos(nome_usuario, titulo_Livro, data_emprestimo, data_devolucao, status) VALUES(?, ?, ?, ?, ?)";

            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, usuario);
            stmt.setString(2, livro);
            stmt.setDate(3, java.sql.Date.valueOf(dataEmprestimo));
            stmt.setDate(4, java.sql.Date.valueOf(dataDevolucao));
            stmt.setString(5, "Emprestado");

            conexao.setAutoCommit(false);
            stmt.executeUpdate();
            new LivroDAO().atualizarStatusLivro(conexao, idLivro, "Emprestado");
            conexao.commit();
            System.out.println("Empréstimo realizado com sucesso!");

        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void devolverLivro(int idLivro, int idUsuario) {
        String sql = "UPDATE emprestimos SET status = ? WHERE id_livro = ? AND id_usuario = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, "Devolvido");
            stmt.setInt(2, idLivro);
            stmt.setInt(3, idUsuario);
            conexao.setAutoCommit(false);
            stmt.executeUpdate();
            new LivroDAO().atualizarStatusLivro(conexao, idLivro, "Disponível");
            conexao.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            try {
                conexao.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        System.out.println("Livro devolvido!");

    }

    public void historicoEmprestimo() {

    }

}
