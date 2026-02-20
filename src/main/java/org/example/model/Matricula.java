package org.example.model;

public class Matricula {

    private int idMatricula;
    private Aluno aluno;
    private Curso curso;

    public Matricula () {}

    public Matricula (Aluno aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
    }

    public Matricula (Aluno aluno, Curso curso, int idMatricula) {
        this.aluno = aluno;
        this.curso = curso;
        this.idMatricula = idMatricula;
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
}
