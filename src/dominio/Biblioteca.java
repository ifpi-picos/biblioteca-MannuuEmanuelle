package dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Biblioteca {
    private List<Emprestimo> historicoEmprestimos;
    private List<Livro> livros;

    Scanner scanner = new Scanner(System.in);

    public Biblioteca(){
        this.livros = new ArrayList<>();
        this.historicoEmprestimos = new ArrayList<>();

        Livro livro1 = new Livro("Morte no Nilo", "Agatha Cristie", "Mistério", "HarperCollins", 2020, "Disponivel", 8, 678);

        Livro livro2 = new Livro("Sherlock Holmes: Um estudo em vermelho", "Arthur Conan Doyle", "Mistério", "Ática", 1887, "Disponivel", 10, 123);

        livros.add(livro1);
        livros.add(livro2);

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
            System.out.println("Autor: " + livro.getAutor());
            System.out.println("Status: " + livro.getStatus() + "\n");
        }
    }

    public void listarLivrosEmprestados(){
        for (Livro livro: livros){
            if ("Emprestado".equalsIgnoreCase(livro.getStatus())){
                System.out.println("Título: " + livro.getNome());
                System.out.println("Autor: " + livro.getAutor() + "\n");
            }
        }
    }

    public void listarLivrosDisponiveis(){
        for (Livro livro: livros){
            if ("Disponivel".equalsIgnoreCase(livro.getStatus())){
                System.out.println("Título: " + livro.getNome());
                System.out.println("Autor: " + livro.getAutor() + "\n");
            }
        }
    }

    public void pegarEmprestado(){

        boolean livroEncontrado = false;
        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        for (Livro livro: livros){
            if (titulo.equalsIgnoreCase(livro.getNome())){
                if ("disponivel".equalsIgnoreCase(livro.getStatus())){
                    LocalDate dataEmprestimo = LocalDate.now();
                    LocalDate dataDevolucao = dataEmprestimo.plusDays(7);

                    System.out.println("Empréstimo realizado com sucesso!");
                    System.out.println("Data de devolução: " + dataDevolucao);

                    livro.setStatus("Emprestado");
                    Emprestimo emprestimo = new Emprestimo(livro.getNome(), dataEmprestimo, dataDevolucao);
                    registrarEmprestimo(emprestimo);
                    livroEncontrado = true;
                    
                } else {
                    System.out.println("Livro indisponível para empréstimo!");
                }

            }
        }

        if (!livroEncontrado){
            System.out.println("Livro não encontrado!");
        }
    
    
}

    public void devolverLivro(){
        boolean livroEncontrado = false;

        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        for (Livro livro: livros){
            if (titulo.equalsIgnoreCase(livro.getNome())){  
                livro.setStatus("Disponivel");
                livroEncontrado = true;
                
                for (Emprestimo emprestimo: historicoEmprestimos){
                    if (emprestimo.getNomeLivro().equalsIgnoreCase(titulo)){
                        emprestimo.setDataDevolucao(LocalDate.now());
                        break;
                    }
                }
                System.out.println("Livro devolvido!");
            }
        }

        if (!livroEncontrado){
        System.out.println("Livro não encontrado!");
        } 
        
    }


    public void registrarEmprestimo(Emprestimo emprestimo){
        historicoEmprestimos.add(emprestimo);
    }

    public void exibirHistorico(){
        if (historicoEmprestimos.isEmpty()){
        System.out.println("Nenhum empréstimo registrado!");
        
        } else {

            System.out.println("Histórico de empréstimos: ");
            for (Emprestimo emprestimo: historicoEmprestimos){
                System.out.println("Livro: " + emprestimo.getNomeLivro());
                System.out.println("Data de empréstimo: " + emprestimo.getDataEmprestimo());
                System.out.println("Data de devolução: " + emprestimo.getDataDevolucao() + "\n");

            }

        }
    }
}