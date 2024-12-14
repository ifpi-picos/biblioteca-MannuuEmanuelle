
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

        Livro livro = new Livro(titulo, autor, genero, editora, anoPublicacao, status, isbn, codigo);
        livros.add(livro);

        System.out.println("Livro adicionado com sucesso! ");

        
        
    }

    public void removerLivro(){

        System.out.println("Remover por: \n1 - Título \n2 - Código\n");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        // TODO: Mudar forma de remover livro;

        if (opcao == 1){
            System.out.println("Digite o titulo: ");
            String titulo = scanner.nextLine();

            livros.remove(titulo);

        }
        else{
            System.out.println("Digite o código: ");
            int codigo = scanner.nextInt();
            scanner.nextLine();

            livros.remove(codigo);
        }
        
    }

    
}
