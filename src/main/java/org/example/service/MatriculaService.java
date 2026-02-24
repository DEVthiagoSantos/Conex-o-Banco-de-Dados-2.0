package org.example.service;

import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;
import org.example.dao.MatriculaDAO;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Matricula;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class MatriculaService {

    public void matricular(String nomeAluno, String nomeCurso) throws SQLException {

        AlunoDAO daoAluno = new AlunoDAO();
        CursoDAO daoCurso = new CursoDAO();
        MatriculaDAO daoMatricula = new MatriculaDAO();

        Aluno aluno = daoAluno.buscarPorAluno(nomeAluno);
        Curso curso = daoCurso.buscarPorCurso(nomeCurso);

        if (aluno == null) {
            throw new RuntimeException("Aluno não encontrado");
        }
        if (curso == null) {
            throw new RuntimeException("Curso não encontrado");
        }

        if (daoMatricula.existeMatricula(aluno.getId_aluno(), curso.getId_curso())) {
            throw new RuntimeException("Aluno já está matriculado neste curso");
        }

        Matricula matricula = new Matricula(aluno, curso);
        daoMatricula.inserir(matricula);


    }
}
