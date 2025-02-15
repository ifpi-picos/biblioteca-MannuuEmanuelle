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


    static Scanner scanner = new Scanner(System.in);
    
        public Biblioteca(Connection conexao) {
            this.conexao = conexao;
        }
    
        
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO(this.conexao);
    
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
    
            LivroDAO livroDAO = new LivroDAO(conexao);
            livroDAO.inserirLivro(livro);
            
            System.out.println("Livro adicionado com sucesso! ");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
        }
    
        // public void listarLivros() throws SQLException {
        //     new LivroDAO().listarLivros();

            
        // }
    
        public void listarLivros() {
            

        try {

            LivroDAO livroDAO = new LivroDAO(conexao);
            ResultSet livroAtual = livroDAO.listarLivros(); 

            while(livroAtual.next()){
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
                System.out.println("Status: " + status + "\n\n");
        
            }
        } catch (SQLException ex) {
        }
                        
               
                    
            } 
        
            
        
        // public void listarLivrosEmprestados() {
        //     for (Livro livro : livros) {
        //         if ("Emprestado".equalsIgnoreCase(livro.getStatus())) {
        //             System.out.println("Título: " + livro.getTitulo());
        //             System.out.println("Autor: " + livro.getAutor() + "\n");
        //         }
        //     }
        // }
    
        // public void listarLivrosDisponiveis() {
        //     for (Livro livro : livros) {
        //         if ("Disponivel".equalsIgnoreCase(livro.getStatus())) {
        //             System.out.println("Título: " + livro.getTitulo());
        //             System.out.println("Autor: " + livro.getAutor() + "\n");
        //         }
        //     }
        // }
    
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
                // TODO Auto-generated catch block
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



        
        try {
            EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conexao);
            emprestimoDAO.devolverLivro(idLivro, idUsuario);
            
           
            
        } catch (Exception e) {
        }

    }

   
    // public void exibirHistorico() {
    //     if (historicoEmprestimos.isEmpty()) {
    //         System.out.println("Nenhum empréstimo registrado!");

    //     } else {

    //         System.out.println("Histórico de empréstimos: ");
    //         for (Emprestimo emprestimo : historicoEmprestimos) {
    //             System.out.println("Livro: " + emprestimo.getNomeLivro());
    //             System.out.println("Data de empréstimo: " + emprestimo.getDataEmprestimo());
    //             // System.out.println("Data de devolução: " + emprestimo.getDataDevolucao() +
    //             // "\n");

    //         }

    //     }
    // }

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

            
            
            

            System.out.println("Usuário cadastrado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}