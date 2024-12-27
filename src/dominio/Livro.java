package dominio;

public class Livro {

    private String titulo;
    private String autor;
    private String genero;
    private String editora;
    private int anoPublicacao;
    private String status;
    private int isbn;
    private int codigo;

    public Livro(String titulo, String autor, String genero, String editora, int anoPublicacao, String status, int isbn, int codigo) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.status = status;
        this.isbn = isbn;
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public static void main(String[] args) {
        System.out.println("Biblioteca");
    }

    public String getNome() {
        return titulo;
    }

    public void setTitulo(String nome) {
        this.titulo = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    
}