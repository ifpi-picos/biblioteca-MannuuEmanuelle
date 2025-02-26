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


            }


            conexao.commit();
            System.out.println("Empréstimo realizado com sucesso!");

        } catch (SQLException e) {
            try {
                conexao.rollback();
                System.err.println("Erro ao realizar empréstimo " + e.getMessage());

            } catch (SQLException e1) {

                System.err.println("Erro ao realizar rollback" + e1.getMessage());
            }

        }

    }

    public Emprestimo procurarEmprestimo(int idUsuario, int idLivro){
        String sql = "SELECT * FROM emprestimos WHERE id_livro = ? AND id_usuario = ? ";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setInt(1, idLivro);
            stmt.setInt(2, idUsuario);

            ResultSet resultado = stmt.executeQuery();

            if(resultado.next()){
                Usuario usuario = new Usuario(resultado.getInt("id_usuario"), resultado.getString("nome_usuario"));
                Livro livro = new Livro(resultado.getInt("id_livro"), resultado.getString("titulo_livro"));
                LocalDate dataEmprestimo = resultado.getDate("data_emprestimo").toLocalDate();
                String status = resultado.getString("status");


                return new Emprestimo(usuario, livro, dataEmprestimo, status);

            }

            return null;

            
        } 
        catch (Exception e) {
            System.err.println("Erro ao buscar empréstimo!\n" + e.getMessage());
            return null;
        }
    
    }

    public void devolverLivro(Emprestimo emprestimo) {

        try {

            int idLivro = emprestimo.getLivro().getIdLivro();
            int idUsuario = emprestimo.getUsuario().getIdUsuario();

            if (!"Emprestado".equalsIgnoreCase(emprestimo.getStatus())) {
                System.out.println("Livro não encontrado!");
                return;
            }

            conexao.setAutoCommit(false);

            String sql = "UPDATE emprestimos SET status = ?, data_devolucao = ? WHERE id_livro = ? AND id_usuario = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {

                stmt.setString(1, "Devolvido");
                stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setInt(3, idLivro);
                stmt.setInt(4, idUsuario);

                stmt.executeUpdate();

            }

            
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

        List<Emprestimo> emprestimos = new ArrayList<>();

        int idUsuario = usuario.getIdUsuario();
        

        String sql = "SELECT * FROM emprestimos WHERE id_usuario = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setInt(1, idUsuario);
            ResultSet resultado = stmt.executeQuery();

            while(resultado.next()){
                int idLivro = resultado.getInt("id_livro");
                String tituloLivro = resultado.getString("titulo_livro");
                String status = resultado.getString("status");
                Date dataEmprestimo = resultado.getDate("data_emprestimo");


                LocalDate dateEmprestimo = dataEmprestimo.toLocalDate(); 
                Livro livro = new Livro(idLivro, tituloLivro);
                
                Emprestimo emprestimo = new Emprestimo(usuario, livro, dateEmprestimo, status);

                emprestimos.add(emprestimo);

            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados " + e.getMessage());

        }

        return emprestimos;
    }

}

