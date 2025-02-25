package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.dominio.Emprestimo;
import com.example.dominio.Livro;
import com.example.dominio.Usuario;

public class EmprestimoDAO {

    Connection conexao;

    public EmprestimoDAO(Connection conexao) {

        if (conexao == null) {
            System.out.println("Conexão nula!");
        }
        this.conexao = conexao;

    }

    List<Emprestimo> emprestimos = new ArrayList<>();
    

    public void realizarEmprestimo(Livro livro, Usuario usuario, LocalDate dataEmprestimo) {

        try {

            int idLivro = livro.getIdLivro();
            int idUsuario = usuario.getIdUsuario();

            if (!"Disponível".equalsIgnoreCase(livro.getStatus())) {
                System.out.println("Livro não disponível para empréstimo!");
                return;

            }

            conexao.setAutoCommit(false);

            String sql = "INSERT INTO emprestimos(id_usuario, id_livro, nome_usuario, titulo_livro, data_emprestimo, data_devolucao, status) VALUES(?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, idUsuario);
                stmt.setInt(2, idLivro);
                stmt.setString(3, usuario.getNome());
                stmt.setString(4, livro.getTitulo());
                stmt.setDate(5, java.sql.Date.valueOf(dataEmprestimo));
                stmt.setDate(6, java.sql.Date.valueOf(dataEmprestimo.plusDays(7)));
                stmt.setString(7, "Emprestado");

                stmt.executeUpdate();

                conexao.commit();
                System.out.println("Empréstimo realizado com sucesso!");

            }

        } catch (SQLException e) {
            try {
                conexao.rollback();
                System.err.println("Erro ao realizar empréstimo " + e.getMessage());

            } catch (SQLException e1) {

                System.err.println("Erro ao realizar rollback" + e1.getMessage());
            }

        }

    }

    public void devolverLivro(Livro livro, Usuario usuario, LivroDAO livroDAO) {

        try {

            int idLivro = livro.getIdLivro();
            int idUsuario = usuario.getIdUsuario();

            if (!"Emprestado".equalsIgnoreCase(livro.getStatus())) {
                System.out.println("Livro não encontrado!");
                return;
            }

            conexao.setAutoCommit(false);

            String sql = "UPDATE emprestimos SET status = ? WHERE id_livro = ? AND id_usuario = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

                stmt.setString(1, "Devolvido");
                stmt.setInt(2, idLivro);
                stmt.setInt(3, idUsuario);

                stmt.executeUpdate();

            }

            livroDAO.atualizarStatusLivro(idLivro, "Disponível");
            conexao.commit();

        } catch (SQLException e) {

            try {
                conexao.rollback();
                System.err.println("Erro ao devolver livro! " + e.getMessage());

            } catch (SQLException e1) {
                System.err.println("Erro ao realizar rollback " + e1.getMessage());

            }

        }

    }

    public List<Emprestimo> historicoEmprestimo(Usuario usuario) {

        List<Emprestimo> historicoEmprestimo = new ArrayList<>();

        int idUsuario = usuario.getIdUsuario();
        

        String sql = "SELECT * FROM emprestimos WHERE id_usuario = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, idUsuario);
            ResultSet resultado = stmt.executeQuery();

            while(resultado.next()){

                String tituloLivro = resultado.getString("titulo_livro");
                String autorLivro = resultado.getString("autor_livro");
                String genero = resultado.getString("genero");
                String editora = resultado.getString("editora");
                int anoPublicacao = resultado.getInt("ano_publicacao");
                String isbn = resultado.getString("isbn");
                String status = resultado.getString("status");
                Date dataEmprestimo = resultado.getDate("data_emprestimo");


                LocalDate dateEmprestimo = dataEmprestimo.toLocalDate(); 
                Livro livro = new Livro(tituloLivro, autorLivro, genero, editora, anoPublicacao, isbn, status);

                Emprestimo emprestimo = new Emprestimo(usuario, livro, dateEmprestimo);

                emprestimos.add(emprestimo);

                



            }

            // stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            
            // Emprestimo emprestimo = new Emprestimo(usuario);
            // emprestimos.add(emprestimo);

            // return emprestimos;

        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados " + e.getMessage());

        }

        return emprestimos;
    }

}

// public List<Emprestimo> historicoEmprestimo(Usuario usuario) {

// List<Emprestimo> listaHistoricoEmprestimos = new ArrayList<>();

// int idUsuario = usuario.getIdUsuario();

// String sql = "SELECT * FROM emprestimos WHERE id_usuario = ?";

// try {

// PreparedStatement stmt = conexao.prepareStatement(sql);
// stmt.setInt(1, idUsuario);

// ResultSet resultado = stmt.executeQuery();

// while (resultado.next()){
// String nomeUsuario = resultado.getString("nome_usuario");
// String tituloLivro = resultado.getString("titulo_livro");
// Date dataEmprestimo = resultado.getDate("data_emprestimo");
// Date dataDevolucao = resultado.getDate("data_devolucao");
// String status = resultado.getString("status");

// }
// return listaHistoricoEmprestimos;

// } catch (Exception e) {
// System.err.println("Erro ao listar livros " + e.getMessage());
// }

// }
