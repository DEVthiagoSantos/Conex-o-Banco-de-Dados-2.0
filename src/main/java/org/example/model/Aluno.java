package org.example.model;

public class Aluno {

    private int id_aluno;
    private String nome;
    private String email;

    public Aluno() {}

    public Aluno(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Aluno(String nome, String email, int id_aluno) {
        this.nome = nome;
        this.email = email;
        this.id_aluno = id_aluno;
    }

    public int getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(int id_aluno) {
        this.id_aluno = id_aluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
