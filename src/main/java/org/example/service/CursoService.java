package org.example.service;

import org.example.dao.CursoDAO;
import org.example.model.Curso;

import java.sql.SQLException;
import java.util.List;

public record CursoService(CursoDAO cursoDAO) {

    public void inserirCurso(String nomeCurso) throws SQLException {

        if (nomeCurso == null || nomeCurso.isBlank()) {
            throw new RuntimeException("Nome do curso não pode ser vazio");
        }
        if (cursoDAO.buscarPorCurso(nomeCurso) != null) {
            throw new RuntimeException("Curso já cadastrado");
        }

        Curso curso = new Curso(nomeCurso);
        cursoDAO.inserir(curso);

    }

    public List<Curso> listarCursos() throws SQLException {

        return cursoDAO.listarCursos();
    }

    public Curso buscarCurso(String curso) throws SQLException {

        Curso curso1 = cursoDAO.buscarPorCurso(curso);

        if (curso1 == null) {
            throw new RuntimeException("Curso não encontrado");
        }

        return curso1;
    }

    public void deletarCurso(int idCurso) throws SQLException {

        if (cursoDAO.buscarPorId(idCurso) == null) {
            throw new RuntimeException("Curso não foi encontrado");
        }

        cursoDAO.deletarCurso(idCurso);
    }

    public void atualizarCurso(String curso, int idCurso) throws SQLException {

        if (cursoDAO.buscarPorId(idCurso)== null) {
            throw new RuntimeException("Curso não foi encontrado");
        }

        cursoDAO.atualizarCurso(curso, idCurso);
    }
}
