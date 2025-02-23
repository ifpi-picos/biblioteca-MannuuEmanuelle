package com.example.dominio;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.example.dao.EmprestimoDAO;
import com.example.dao.LivroDAO;
import com.example.dao.UsuarioDAO;

public class Biblioteca {

    public Connection conexao;
    EmprestimoDAO emprestimoDAO;
    LivroDAO livroDAO;

    static Scanner scanner = new Scanner(System.in);

    List<Livro> listaLivros = livroDAO.listarLivros();
    List<Livro> listaLivrosEmprestados = livroDAO.listarLivrosEmprestados();
    List<Livro> listaLivrosDisponiveis = livroDAO.listarLivrosDisponiveis();

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

            Livro livro = new Livro(null,titulo, autor, genero, editora, anoPublicacao, isbn, status);

            livroDAO.inserirLivro(livro);
            listaLivros.add(livro);

            System.out.println("\nLivro adicionado com sucesso!\n");

        } catch (Exception ex) {
            System.out.println("Erro ao adicionar livro! " + ex.getMessage());
        }

    }

    public void pegarEmprestado() {


        try {

            System.out.println("ID do livro: ");
            int idLivro = scanner.nextInt();
            scanner.nextLine();

            System.out.println("ID do usuário: ");
            int idUsuario = scanner.nextInt();
            scanner.nextLine();

            
            

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

        livroDAO.atualizarStatusLivro(idLivro, "Disponível");
        if (idLivro < 0 || idUsuario < 0) {
            System.out.println("\nID do usuário ou livro não encontrado.\n");
        }

        
        try {

            // emprestimoDAO.devolverLivro(idLivro, idUsuario);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listarLivros() {

        try {

            
           
            System.out.println("\n--- LIVROS ---\n");

            for(Livro livro : listaLivros){
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
            System.err.println("Erro ao listar livros " + e.getMessage());
        }

    }

    public void listarLivrosEmprestados() {

        try {



            System.out.println("\n--- LIVROS EMPRESTADOS ---\n");

            for(Livro livro : listaLivrosEmprestados){
                System.out.println("ID: " + livro.getIdLivro());
                System.out.println("Título:" + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println("Gênero: " + livro.getGenero());
                System.out.println("Editora: " + livro.getEditora());
                System.out.println("Ano de publicação: " + livro.getAnoPublicacao());
                System.out.println("ISBN: " + livro.getIsbn());
            
            } 
        }   
        catch (Exception e) {
            System.err.println("Erro ao listar livros emprestados " + e.getMessage());
        }

    }

    public void listarLivrosDisponiveis() {

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
}
//     public void exibirHistorico() throws SQLException{

//         System.out.println("ID do usuário: ");
//         int idUsuario = scanner.nextInt();
//         scanner.nextLine();

//         if (idUsuario < 0){
//             System.out.println("ID do usuário não encontrado.");
//             return;
//         }

//         try {
//             // ResultSet usuarioAtual = emprestimoDAO.historicoEmprestimo(idUsuario);
//             boolean historicoEncontrado = false;

            

//             System.out.println("--- HISTÓRICO DE EMPRÉSTIMOS --- \n");
//             while(usuarioAtual.next()){
//                 historicoEncontrado = true;
//                 System.out.println("Nome do usuário: " + usuarioAtual.getString("nome_usuario"));
//                 System.out.println("Título do livro: " + usuarioAtual.getString("titulo_livro"));
//                 System.out.println("Data de empréstimo: " + usuarioAtual.getString("data_emprestimo"));
//                 System.out.println("Data de devolução: " + usuarioAtual.getString("data_devolucao"));
//                 System.out.println("Status: " + usuarioAtual.getString("status") + "\n");

//             }

//             if (!historicoEncontrado){
//                 System.out.println("Nenhum histórico de empréstimos para este usuário!\n");
//             }
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }

        
//     }

    

// }
