package org.example.model;

import java.time.LocalDateTime;

public class Matricula {

    private int idMatricula;
    private Aluno aluno;
    private Curso curso;
    private LocalDateTime data;

    public Matricula () {}

    public Matricula (Aluno aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
    }

    public Matricula (int idMatricula, Aluno aluno, Curso curso, LocalDateTime data) {

        this.idMatricula = idMatricula;
        this.aluno = aluno;
        this.curso = curso;
        this.data = data;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
