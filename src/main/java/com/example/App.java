package com.example;

import java.util.Scanner;

import com.example.connection.Conexao;
import com.example.dominio.Biblioteca;

public class App {
    public static void main(String[] args) {

        Conexao conexao = new Conexao();
        conexao.conexaoBanco();

        Biblioteca biblioteca = new Biblioteca();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- BIBLIOTECA ---");
            
            OUTER:
            while (true) {
                System.out.println("1 - Cadastrar Usuário ");
                System.out.println("2 - Cadastrar Livro ");
                System.out.println("3 - Pegar livro emprestado");
                System.out.println("4 - Devolver livro ");
                System.out.println("5 - Listar todos os livros ");
                System.out.println("6 - Listar livros emprestados ");
                System.out.println("7 - Listar livros disponíveis ");
                System.out.println("8 - Listar histórico do usuário ");
                System.out.println("9 - Sair ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 1 -> biblioteca.adicionarUsuario();
                    case 2 -> biblioteca.adicionarLivro();
                    case 3 -> biblioteca.pegarEmprestado();
                    case 4 -> biblioteca.devolverLivro();
                    case 5 -> biblioteca.listarLivros();    
                    case 6 -> biblioteca.listarLivrosEmprestados();
                    case 7 -> biblioteca.listarLivrosDisponiveis();
                    // case 8 -> biblioteca.exibirHistorico();
                    default -> {
                        break OUTER;
                    }
                }
            }

        }
    }

}
        // TODO: Detalhar usuários e conectar à biblioteca.