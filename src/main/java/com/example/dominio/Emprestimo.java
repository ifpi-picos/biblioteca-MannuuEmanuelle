package com.example.dominio;

import java.time.LocalDate;

public class Emprestimo {

    private int idLivro;
    private static LocalDate dataEmprestimo;
    private int idUsuario;

    public Emprestimo(int idLivro, LocalDate dataEmprestimo, int idUsuario) {
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.idUsuario = idUsuario;

    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        Emprestimo.dataEmprestimo = dataEmprestimo;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
