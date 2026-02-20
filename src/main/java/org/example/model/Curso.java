package org.example.model;

public class Curso {

    private int id_curso;
    private String nome;

    public Curso() {}

    public Curso(String nome) {
        this.nome = nome;
    }

    public Curso(String nome, int id) {
        this.nome = nome;
        this.id_curso = id;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
