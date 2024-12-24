package dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Biblioteca {
    private List<Emprestimo> historicoEmprestimos;
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


    Livro livro1 = new Livro("O mistério no mar mediterrâneo", "Agatha Cristie", "Mistério", "Oi" , 2016, "Disponível", 8, 6);

    livros.add(livro1);


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

    // TODO: Terminar o método devolver livro

    public void devolverLivro(){
        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        System.out.println("Livro devolvido!");
        
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
                System.out.println(emprestimo);
            }
        }
    }
}



