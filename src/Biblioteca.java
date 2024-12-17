
import dominio.Livro;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Biblioteca {
    private List<Livro> livros;

    Scanner scanner = new Scanner(System.in);

    public Biblioteca() {
        this.livros = new ArrayList<>();
    }
    
    public List<Livro> getLivros() {
        return livros;
    }


    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    
    public void adicionarLivro(){
        
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

        System.out.println("Status: ");
        String status = scanner.nextLine();

        System.out.println("ISBN: ");
        int isbn = scanner.nextInt();

        System.out.println("Código: ");
        int codigo = scanner.nextInt();

        scanner.nextLine();
        
        Livro livro = new Livro(titulo, autor, genero, editora, anoPublicacao, status, isbn, codigo);
        livros.add(livro);

        System.out.println("Livro adicionado com sucesso! ");

        
        
    }



    public void listarLivros(){
        for (Livro livro : livros) {
            System.out.println("Nome: " + livro.getNome());
            System.out.println("Status: " + livro.getStatus());
        }
    }

    public void listarLivrosEmprestados(){
        for (Livro livro: livros){
            if ("emprestado".equals(livro.getStatus())){
                System.out.println("Título: " + livro.getNome());
            }
        }
    }

    public void listarLivrosDisponiveis(){
        for (Livro livro: livros){
            if ("disponivel".equals(livro.getStatus())){
                System.out.println("Título: " + livro.getNome());
            }
        }
    }


    
}



