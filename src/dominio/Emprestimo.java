
package dominio;


import java.time.LocalDate;
import java.util.List;


public class Emprestimo {

    private List<Livro> livros;

    private static LocalDate dataEmprestimo;
        private  String nomeUsuario;
        private String  nomeLivro;
    
        public static void main(String[] args) {
            
        }
        public static LocalDate getDataEmprestimo() {
            return dataEmprestimo;
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        Emprestimo.dataEmprestimo = dataEmprestimo;
    }
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    
}