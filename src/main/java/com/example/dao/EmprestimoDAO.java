package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmprestimoDAO {

    String statusEncontrado;
    Connection conexao;
    LivroDAO livroDAO = new LivroDAO();

    public EmprestimoDAO(Connection conexao) {

        if (conexao == null) {
            System.out.println("Conexão nula!");
        }
        this.conexao = conexao;
    }

    public String buscandoStatusLivro(int id) throws SQLException {
        String sql = "SELECT status FROM livros WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    statusEncontrado = resultado.getString("status");
                }
            }

            return statusEncontrado;

        }
    }

    public String usuarioEncontrado(int id) throws SQLException {
        String usuarioEncontrado = null;

        String sql = "SELECT nome FROM usuarios WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    usuarioEncontrado = resultado.getString("nome");
                }

            }

        }
        return usuarioEncontrado != null ? usuarioEncontrado : "Usuário não encontrado!";

    }

    public String livroEncontrado(int id) throws SQLException {
        String livroEncontrado = null;

        String sql = "SELECT titulo FROM livros WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);) {
            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    livroEncontrado = resultado.getString("titulo");
                }

            }
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

            conexao.setAutoCommit(false);

            String sql = "INSERT INTO emprestimos(nome_usuario, titulo_livro, data_emprestimo, data_devolucao, status) VALUES(?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, usuario);
                stmt.setString(2, livro);
                stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(7)));
                stmt.setString(5, "Emprestado");
                

                
                
                stmt.executeUpdate();

                livroDAO.atualizarStatusLivro(idLivro, "Emprestado");

                conexao.commit();
                System.out.println("Empréstimo realizado com sucesso!");

            }

        } catch (SQLException e) {
            try {
                conexao.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }

            e.printStackTrace();
        }

    }

    public void devolverLivro(int idLivro, int idUsuario) throws SQLException {
        String nomeUsuario = usuarioEncontrado(idUsuario);
        String tituloLivro = livroEncontrado(idLivro);

        if (nomeUsuario == null || tituloLivro == null) {
            System.out.println("Livro ou usuário não encontrado!");
            return;
        }

        conexao.setAutoCommit(false);

        String sql = "UPDATE emprestimos SET status = ? WHERE titulo_livro = ? AND nome_usuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {


            stmt.setString(1, "Devolvido");
            stmt.setString(2, tituloLivro);
            stmt.setString(3, nomeUsuario);
            
            stmt.executeUpdate();

            livroDAO.atualizarStatusLivro(idLivro, "Disponível");

            conexao.commit();
            System.out.println("Livro devolvido!");

        } catch (SQLException e) {
            
            try {
                conexao.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
              
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        

    }

    public ResultSet historicoEmprestimo(int idUsuario) throws SQLException {
        String nomeUsuario = usuarioEncontrado(idUsuario);

        
        
        String sql = "SELECT nome_usuario, titulo_livro, data_emprestimo, data_devolucao, status FROM emprestimos WHERE nome_usuario = ?";

        PreparedStatement stmt = conexao.prepareStatement(sql);
        
            stmt.setString(1, nomeUsuario);
            ResultSet resultado = stmt.executeQuery();
            return resultado;

            
    

        





    }

}
