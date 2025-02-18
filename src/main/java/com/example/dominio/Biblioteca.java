package com.example.dominio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.example.dao.EmprestimoDAO;
import com.example.dao.LivroDAO;
import com.example.dao.UsuarioDAO;

public class Biblioteca {

    public Connection conexao;
    EmprestimoDAO emprestimoDAO;
    LivroDAO livroDAO;

    static Scanner scanner = new Scanner(System.in);

    public Biblioteca(Connection conexao) {
        this.conexao = conexao;
        this.emprestimoDAO = new EmprestimoDAO(this.conexao);
        this.livroDAO = new LivroDAO();
    }

    public void adicionarUsuario() {

        try {
            System.out.println("Nome:");
            String nome = scanner.nextLine();

            System.out.println("CPF: ");
            String cpf = scanner.nextLine();

            System.out.println("Email: ");
            String email = scanner.nextLine();

            UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
            usuarioDAO.inserirUsuario(nome, cpf, email);

            System.out.println("\nUsuário cadastrado com sucesso!\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    public void adicionarLivro() {

        try {
            System.out.println("Título: ");
            String titulo = scanner.nextLine();

            System.out.println("Autor: ");
            String autor = scanner.nextLine();

            System.out.println("Gênero: ");
            String genero = scanner.nextLine();

            System.out.println("Editora: ");
            String editora = scanner.nextLine();

            System.out.println("Ano de publicação: ");
            int anoPublicacao = scanner.nextInt();
            scanner.nextLine();

            System.out.println("ISBN: ");
            String isbn = scanner.nextLine();

            System.out.println("Status: ");
            String status = scanner.nextLine();

            Livro livro = new Livro(titulo, autor, genero, editora, anoPublicacao, isbn, status);

            livroDAO.inserirLivro(livro);

            System.out.println("\nLivro adicionado com sucesso!\n");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void pegarEmprestado() {

        System.out.println("ID do usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        System.out.println("ID do livro: ");
        int idLivro = scanner.nextInt();
        scanner.nextLine();

        try {

            emprestimoDAO.realizarEmprestimo(idLivro, idUsuario, LocalDate.now());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void devolverLivro() {

        System.out.println("ID do usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        System.out.println("ID do livro: ");
        int idLivro = scanner.nextInt();
        scanner.nextLine();

        if (idLivro < 0 || idUsuario < 0) {
            System.out.println("\nID do usuário ou livro não encontrado.\n");
        }

        try {

            emprestimoDAO.devolverLivro(idLivro, idUsuario);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listarLivros() {

        try {

            ResultSet livroAtual = livroDAO.listarLivros();

            System.out.println("\n--- LIVROS ---\n");
            while (livroAtual.next()) {
                int id = livroAtual.getInt("id");
                System.out.println("ID: " + id);

                String titulo = livroAtual.getString("titulo");
                System.out.println("Título: " + titulo);

                String autor = livroAtual.getString("autor");
                System.out.println("Autor: " + autor);

                String genero = livroAtual.getString("genero");
                System.out.println("Gênero: " + genero);

                String editora = livroAtual.getString("editora");
                System.out.println("Editora: " + editora);

                int ano = livroAtual.getInt("ano_publicacao");
                System.out.println("Ano de publicação: " + ano);

                String isbn = livroAtual.getString("isbn");
                System.out.println("ISBN: " + isbn);

                String status = livroAtual.getString("status");
                System.out.println("Status: " + status + "\n");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void listarLivrosEmprestados() {

        try {

            ResultSet livroAtual = livroDAO.listarLivrosEmprestados();
            System.out.println("\n--- LIVROS EMPRESTADOS ---\n");

            while (livroAtual.next()) {
                int id = livroAtual.getInt("id");
                System.out.println("ID: " + id);

                String titulo = livroAtual.getString("titulo");
                System.out.println("Título: " + titulo);

                String autor = livroAtual.getString("autor");
                System.out.println("Autor: " + autor);

                String genero = livroAtual.getString("genero");
                System.out.println("Gênero: " + genero);

                String editora = livroAtual.getString("editora");
                System.out.println("Editora: " + editora);

                int ano = livroAtual.getInt("ano_publicacao");
                System.out.println("Ano de publicação: " + ano);

                String isbn = livroAtual.getString("isbn");
                System.out.println("ISBN: " + isbn + "\n");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void listarLivrosDisponiveis() {

        try {

            ResultSet livroAtual = livroDAO.listarLivrosDisponiveis();

            System.out.println("\n--- LIVROS DISPONÍVEIS ---\n");

            while (livroAtual.next()) {
                int id = livroAtual.getInt("id");
                System.out.println("ID: " + id);

                String titulo = livroAtual.getString("titulo");
                System.out.println("Título: " + titulo);

                String autor = livroAtual.getString("autor");
                System.out.println("Autor: " + autor);

                String genero = livroAtual.getString("genero");
                System.out.println("Gênero: " + genero);

                String editora = livroAtual.getString("editora");
                System.out.println("Editora: " + editora);

                int ano = livroAtual.getInt("ano_publicacao");
                System.out.println("Ano de publicação: " + ano);

                String isbn = livroAtual.getString("isbn");
                System.out.println("ISBN: " + isbn + "\n");

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    public void exibirHistorico() throws SQLException{

        System.out.println("ID do usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        if (idUsuario < 0){
            System.out.println("ID do usuário não encontrado.");
            return;
        }

        try {
            ResultSet usuarioAtual = emprestimoDAO.historicoEmprestimo(idUsuario);
            boolean historicoEncontrado = false;

            

            System.out.println("--- HISTÓRICO DE EMPRÉSTIMOS --- \n");
            while(usuarioAtual.next()){
                historicoEncontrado = true;
                System.out.println("Nome do usuário: " + usuarioAtual.getString("nome_usuario"));
                System.out.println("Título do livro: " + usuarioAtual.getString("titulo_livro"));
                System.out.println("Data de empréstimo: " + usuarioAtual.getString("data_emprestimo"));
                System.out.println("Data de devolução: " + usuarioAtual.getString("data_devolucao"));
                System.out.println("Status: " + usuarioAtual.getString("status") + "\n");

            }

            if (!historicoEncontrado){
                System.out.println("Nenhum histórico de empréstimos para este usuário!\n");
            }
            } catch (Exception e) {
                e.printStackTrace();
            }

        
    }

    

}
