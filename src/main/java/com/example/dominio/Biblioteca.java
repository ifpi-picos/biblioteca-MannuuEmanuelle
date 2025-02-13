package com.example.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.connection.Conexao;
import com.example.dao.EmprestimoDAO;
import com.example.dao.LivroDAO;
import com.example.dao.UsuarioDAO;


public class Biblioteca {
    private List<Emprestimo> historicoEmprestimos;
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;
    private static LocalDate dataDevolucao;

    static Scanner scanner = new Scanner(System.in);
    
        public Biblioteca() {
            this.livros = new ArrayList<>();
            this.historicoEmprestimos = new ArrayList<>();
            this.usuarios = new ArrayList<>();
            this.scanner = new Scanner(System.in);
    
        }
    
        public List<Livro> getLivros() {
            return livros;
        }
    
        public void setLivros(List<Livro> livros) {
            this.livros = livros;
    
        }
    
        public void adicionarLivro() {
    
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
            livros.add(livro);
    
            System.out.println("Livro adicionado com sucesso! ");
    
            LivroDAO.inserirLivro(livro);
    
        }
    
        public void listarLivros() {
            LivroDAO.listarLivros();
        }
    
        public void listarLivrosEmprestados() {
            for (Livro livro : livros) {
                if ("Emprestado".equalsIgnoreCase(livro.getStatus())) {
                    System.out.println("Título: " + livro.getTitulo());
                    System.out.println("Autor: " + livro.getAutor() + "\n");
                }
            }
        }
    
        public void listarLivrosDisponiveis() {
            for (Livro livro : livros) {
                if ("Disponivel".equalsIgnoreCase(livro.getStatus())) {
                    System.out.println("Título: " + livro.getTitulo());
                    System.out.println("Autor: " + livro.getAutor() + "\n");
                }
            }
        }
    
        public void pegarEmprestado() {

            LocalDate dataEmprestimo = LocalDate.now();
    
            System.out.println("ID do usuário: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine();

            System.out.println("ID do livro: ");
            int idLivro = scanner.nextInt();
            scanner.nextLine();

            try {
                new EmprestimoDAO(Conexao.conexaoBanco()).realizarEmprestimo(idLivro, idUsuario, dataEmprestimo);
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
            new EmprestimoDAO(Conexao.conexaoBanco()).devolverLivro(idLivro, idUsuario);
           
            
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

            Usuario usuario = new Usuario(nome, cpf, email);
            usuarios.add(usuario);

            UsuarioDAO.inserirUsuario(usuario);

            System.out.println("Usuário cadastrado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}