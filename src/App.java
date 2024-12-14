import java.util.Scanner;


public class App {
    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        Scanner scanner = new Scanner(System.in);

        System.out.println("--- BIBLIOTECA ---");

        while(true) {
            System.out.println("1 - Adicionar Livro ");
            System.out.println("2 - Remover Livro ");
            System.out.println("3 - Listar Livros ");
            System.out.println("4 - Sair ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1){
                biblioteca.adicionarLivro();
            } else if (opcao == 2) {
                biblioteca.removerLivro();
            } else{
                break;
            }

            
        }



       

       



      
       

       
       
    }
}
