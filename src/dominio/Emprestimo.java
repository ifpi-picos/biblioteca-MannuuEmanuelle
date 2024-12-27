package dominio;

import java.time.LocalDate;

public class Emprestimo {

    private String nomeLivro;
    private static LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
        
    // System.out.println("Teste");

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Emprestimo(String nomeLivro, LocalDate dataEmprestimo, LocalDate dataDevolucao){
        this.nomeLivro = nomeLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }
        
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        Emprestimo.dataEmprestimo = dataEmprestimo;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    
}
