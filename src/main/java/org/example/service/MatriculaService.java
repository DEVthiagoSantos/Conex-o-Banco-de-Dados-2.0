package org.example.service;

import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;
import org.example.dao.MatriculaDAO;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Matricula;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record MatriculaService(AlunoDAO alunoDAO, CursoDAO cursoDAO, MatriculaDAO matriculaDAO) {

    public void matricular(String nomeAluno, String nomeCurso) throws SQLException {

        Aluno aluno = alunoDAO.buscarPorNomeAluno(nomeAluno);
        Curso curso = cursoDAO.buscarPorCurso(nomeCurso);

        if (aluno == null) {
            throw new RuntimeException("Aluno não encontrado");
        }
        if (curso == null) {
            throw new RuntimeException("Curso não encontrado");
        }

        if (matriculaDAO.existeMatricula(aluno.getId_aluno(), curso.getId_curso())) {
            throw new RuntimeException("Aluno já está matriculado neste curso");
        }

        Matricula matricula = new Matricula(aluno, curso);
        matriculaDAO.inserir(matricula);


    }

    public void deletar(String nomeAluno, String nomeCurso) throws SQLException {


        Aluno aluno = alunoDAO.buscarPorNomeAluno(nomeAluno);
        Curso curso = cursoDAO.buscarPorCurso(nomeCurso);

        if (aluno == null) {
            throw new RuntimeException("Esse aluno não foi encontrado");
        }
        if (curso == null) {
            throw new RuntimeException("Esse curso não está listado");
        }

        if (!matriculaDAO.existeMatricula(aluno.getId_aluno(), curso.getId_curso())) {
            throw new RuntimeException("Matricula não encontrada");
        }

        matriculaDAO.deletarMatricula(aluno.getId_aluno(), curso.getId_curso());

    }

    public void atualizar(String cursoNovo,
                          String nomeAluno,
                          String cursoAntigo) throws SQLException {

        Curso novCurso = cursoDAO.buscarPorCurso(cursoNovo);
        Aluno aluno = alunoDAO.buscarPorNomeAluno(nomeAluno);
        Curso antCurso = cursoDAO.buscarPorCurso(cursoAntigo);

        if (novCurso == null) {
            throw new RuntimeException("Esse curso não está listado");
        }
        if (aluno == null) {
            throw new RuntimeException("Aluno não encontrado");
        }
        if (antCurso == null) {
            throw new RuntimeException("Curso não encontrado");
        }

        if (!matriculaDAO.existeMatricula(aluno.getId_aluno(), antCurso.getId_curso())) {
            throw new RuntimeException("Matricula não encontrada");
        }

        matriculaDAO.atualizarMatricula(novCurso.getId_curso(), aluno.getId_aluno(), antCurso.getId_curso());

    }

    public List<String> listarAlunosEmCurso(int idCurso) throws SQLException {

        return matriculaDAO.listarAlunosCursos(idCurso);
    }

    public List<Matricula> listarCursosDeAluno(int idAluno) throws SQLException {

        Aluno aluno = alunoDAO.buscarPorId(idAluno);
        if (aluno == null) {
            throw new RuntimeException("Aluno não foi encontrado");
        }

        return matriculaDAO.listarCursosDeAluno(idAluno);
    }

    public List<Matricula> listarMatriculas() throws SQLException {

        return matriculaDAO.listarAlunosMatriculados();
    }
}
