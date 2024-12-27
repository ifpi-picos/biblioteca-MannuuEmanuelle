import dominio.Biblioteca;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("--- BIBLIOTECA ---");
            
            OUTER:
            while (true) {
                System.out.println("1 - Cadastrar Livro ");
                System.out.println("2 - Pegar livro emprestado");
                System.out.println("3 - Devolver livro ");
                System.out.println("4 - Listar todos os livros ");
                System.out.println("5 - Listar livros emprestados ");
                System.out.println("6 - Listar livros disponíveis ");
                System.out.println("7 - Listar histórico do usuário ");
                System.out.println("8 - Sair ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 1 -> biblioteca.adicionarLivro();
                    case 2 -> biblioteca.pegarEmprestado();
                    case 3 -> biblioteca.devolverLivro();
                    case 4 -> biblioteca.listarLivros();
                    case 5 -> biblioteca.listarLivrosEmprestados();
                    case 6 -> biblioteca.listarLivrosDisponiveis();
                    case 7 -> biblioteca.exibirHistorico();
                    default -> {
                        break OUTER;
                    }
                }
            }

        }
    }
}
        // TODO: Detalhar usuários e conectar à biblioteca.