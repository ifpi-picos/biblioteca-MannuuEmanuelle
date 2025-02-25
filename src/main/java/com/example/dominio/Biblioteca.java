package com.example.dominio;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.dao.EmprestimoDAO;
import com.example.dao.LivroDAO;
import com.example.dao.UsuarioDAO;

public class Biblioteca {

    public Connection conexao;
    EmprestimoDAO emprestimoDAO;
    UsuarioDAO usuarioDAO;
    LivroDAO livroDAO;

    List<Livro> listaLivros = new ArrayList<>();
    List<Livro> listaLivrosEmprestados = new ArrayList<>();
    List<Livro> listaLivrosDisponiveis = new ArrayList<>();

    List<Usuario> usuarios = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    public Biblioteca(Connection conexao) {
        this.conexao = conexao;
        this.emprestimoDAO = new EmprestimoDAO(conexao);
        this.livroDAO = new LivroDAO(conexao);
        this.usuarioDAO = new UsuarioDAO(conexao);

        atualizarListas();
    }

    private void atualizarListas() {
        listaLivros = livroDAO.listarLivros();
        listaLivrosDisponiveis = livroDAO.listarLivrosDisponiveis();
        listaLivrosEmprestados = livroDAO.listarLivrosEmprestados();
        
    }

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
            int idUsuario = usuarioDAO.inserirUsuario(usuario);

            if (idUsuario > 0) {
                usuario.setId(idUsuario);
                usuarios.add(usuario);

            } else {
                System.out.println("Erro ao adicionar livro! ID com valor negativo!");
            }

            System.out.println("\nUsuário cadastrado com sucesso!\n" + "ID do usuário cadastrado: " + idUsuario);

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar usuário! " + e.getMessage());
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
            int idLivro = livroDAO.inserirLivro(livro);

            if (idLivro > 0) {
                livro.setIdLivro(idLivro);
                listaLivros.add(livro);
                listaLivrosDisponiveis.add(livro);
                // atualizarListas(); // Removed to prevent overwriting the updated borrowed
                // list

            } else {
                System.out.println("Erro ao adicionar livro! ID com valor negativo!");
            }

            System.out.println("\nLivro adicionado com sucesso!\n" + "ID do livro cadastrado: " + idLivro);

        } catch (Exception ex) {
            System.out.println("Erro ao adicionar livro! " + ex.getMessage());
        }

    }

    public void pegarEmprestado() {

        try {

            usuarios = usuarioDAO.exibirUsuarios();

            System.out.println("--- USUÁRIOS CADASTRADOS ---");
            for (Usuario usuario : usuarios) {
                System.out.println("ID:" + usuario.getIdUsuario() + "\nNome: " + usuario.getNome() + "\n");
            }

            System.out.println("--- LIVROS DISPONÍVEIS --- ");
            for (Livro livro : listaLivrosDisponiveis) {
                System.out.println("ID: " + livro.getIdLivro() + "\nTítulo: " + livro.getTitulo() + "\n");
            }

            System.out.println("ID do usuário: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine();

            System.out.println("ID do livro: ");
            int idLivro = scanner.nextInt();
            scanner.nextLine();

            Usuario usuarioEncontrado = null;

            for (Usuario usuario : usuarios) {
                if (usuario.getIdUsuario() == idUsuario) {
                    usuarioEncontrado = usuario;
                    break;
                }
            }
            if (usuarioEncontrado == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            Livro livroEncontrado = null;

            for (Livro livro : listaLivrosDisponiveis) {
                if (livro.getIdLivro() == idLivro) {
                    livroEncontrado = livro;
                    break;
                }
            }

            if (livroEncontrado == null) {
                System.out.println("Livro não encontrado!");
                return;
            }

            LocalDate dataEmprestimo = LocalDate.now();

            if (conexao == null) {
                System.err.println("Erro: conexão com o banco de dados não estabelecida.");
                return;
            }
            emprestimoDAO.realizarEmprestimo(livroEncontrado, usuarioEncontrado, dataEmprestimo);
            livroDAO.atualizarStatusLivro(livroEncontrado.getIdLivro(), "Emprestado");
            listaLivrosEmprestados.add(livroEncontrado);
            listaLivrosDisponiveis.remove(livroEncontrado);

        } catch (Exception e) {
            System.err.println("Erro ao realizar empréstimo! " + e.getMessage());

        }
    }

    public void devolverLivro() {
        try {

            usuarios = usuarioDAO.exibirUsuarios();
            listaLivrosEmprestados = livroDAO.listarLivrosEmprestados();

            System.out.println("ID do usuário: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine();

            System.out.println("ID do livro: ");
            int idLivro = scanner.nextInt();
            scanner.nextLine();

            Usuario usuarioEncontrado = null;

            for (Usuario usuario : usuarios) {
                if (usuario.getIdUsuario() == idUsuario) {
                    usuarioEncontrado = usuario;
                    break;
                }
            }
            if (usuarioEncontrado == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            Livro livroEncontrado = null;

            for (Livro livro : listaLivrosEmprestados) {
                if (livro.getIdLivro() == idLivro) {
                    livroEncontrado = livro;
                    break;
                }
            }

            if (livroEncontrado == null) {
                System.out.println("Livro não encontrado!");
                return;
            }

            if (conexao == null) {
                System.err.println("Erro: conexão com o banco de dados não estabelecida.");
                return;
            }
            emprestimoDAO.devolverLivro(livroEncontrado, usuarioEncontrado, livroDAO);
            // livroDAO.atualizarStatusLivro(livroEncontrado.getIdLivro(), "Disponível");

            listaLivrosDisponiveis.add(livroEncontrado);
            listaLivrosEmprestados.remove(livroEncontrado);

        } catch (Exception e) {
            System.err.println("Erro ao devolver livro! " + e.getMessage());

        }
    }

    public void listarLivros() {

        listaLivros = livroDAO.listarLivros();

        if (listaLivros.isEmpty()) {
            listaLivros = livroDAO.listarLivros();
        }

        try {

            System.out.println("\n--- LIVROS ---\n");

            for (Livro livro : listaLivros) {

                System.out.println("ID: " + livro.getIdLivro());
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println("Gênero: " + livro.getGenero());
                System.out.println("Editora: " + livro.getEditora());
                System.out.println("Ano de publicação: " + livro.getAnoPublicacao());
                System.out.println("ISBN: " + livro.getIsbn());
                System.out.println("Status: " + livro.getStatus());
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar livros\n" + e.getMessage());
        }

    }

    public void listarLivrosEmprestados() {

        listaLivrosEmprestados = livroDAO.listarLivrosEmprestados();

        try {

            System.out.println("\n--- LIVROS EMPRESTADOS ---\n");

            for (Livro livro : listaLivrosEmprestados) {
                System.out.println("ID: " + livro.getIdLivro());
                System.out.println("Título:" + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println("Gênero: " + livro.getGenero());
                System.out.println("Editora: " + livro.getEditora());
                System.out.println("Ano de publicação: " + livro.getAnoPublicacao());
                System.out.println("ISBN: " + livro.getIsbn());
                System.out.println();

            }
        } catch (Exception e) {
            System.err.println("Erro ao listar livros emprestados " + e.getMessage());
        }

    }

    public void listarLivrosDisponiveis() {

        listaLivrosDisponiveis = livroDAO.listarLivrosDisponiveis();

        try {

            System.out.println("\n--- LIVROS DISPONÍVEIS ---\n");

            for (Livro livro : listaLivrosDisponiveis) {
                System.out.println("ID: " + livro.getIdLivro());
                System.out.println("Título:  " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println("Gênero: " + livro.getGenero());
                System.out.println("Editora: " + livro.getEditora());
                System.out.println("Ano de publicação: " + livro.getAnoPublicacao());
                System.out.println("ISBN: " + livro.getIsbn());
                System.out.println();

            }

        } catch (Exception e) {
            System.err.println("Erro ao listar livros disponíveis" + e.getMessage());

        }
    }

    public void exibirHistorico() {

        

        System.out.println("ID do usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        for (Usuario usuario: usuarios){
            if (idUsuario != usuario.getIdUsuario()){
                System.out.println("ID do usuário não encontrado! ");
            }
        }

        

        // try {
            
            

        //     System.out.println("--- HISTÓRICO DE EMPRÉSTIMOS --- \n");
        //     while (usuarioAtual.next()) {
        //         historicoEncontrado = true;
        //         System.out.println("Nome do usuário: " +
        //                 usuarioAtual.getString("nome_usuario"));
        //         System.out.println("Título do livro: " +
        //                 usuarioAtual.getString("titulo_livro"));
        //         System.out.println("Data de empréstimo: " +
        //                 usuarioAtual.getString("data_emprestimo"));
        //         System.out.println("Data de devolução: " +
        //                 usuarioAtual.getString("data_devolucao"));
        //         System.out.println("Status: " + usuarioAtual.getString("status") + "\n");

        //     }

        //     if (!historicoEncontrado) {
        //         System.out.println("Nenhum histórico de empréstimos para este usuário!\n");
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

    }

}
