package org.example.dao;

import org.example.connection.Conexao;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class MatriculaDAO {


    // CREATE
    public void inserir(Matricula matricula) throws SQLException {

        String sql = "INSERT INTO matriculas (id_curso, id_aluno) VALUES (?, ?)";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula.getCurso().getId_curso());
            stmt.setInt(2, matricula.getAluno().getId_aluno());
            stmt.executeUpdate();
        }
    }

    //VERIFICAR SE A MATRICULA JÁ EXISTE
    public boolean existeMatricula(int idAluno,int idCurso) throws SQLException {

        String sql = "SELECT 1 FROM matriculas WHERE id_aluno = ? AND id_curso = ?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    //ROAD - Alunos Matriculados
    public void listarMatriculas() throws SQLException {

        String sql = """
                SELECT matriculas.id_matricula AS id_matricula,
                       alunos.nome AS aluno,
                       cursos.nome AS curso
                FROM matriculas
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso""";

        try (Connection conn = Conexao.conexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                System.out.println(rs.getString("id_matricula")
                        + " | " + rs.getString("aluno")
                        + " | " + rs.getString("curso"));
            }
        }
    }

    //Listar alunos de curso especifico
    public void listarAlunosCursos(int id) throws SQLException {

        String sql = """
                SELECT alunos.nome AS nome
                FROM matriculas
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso
                WHERE cursos.id_curso = ?""";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    System.out.println(" - " + rs.getString("nome"));
                }
            }
        }
    }

    // ROAD - Cursos de aluno expecifico
    public void listarCursosAluno(int id) throws SQLException {

        String sql = """
                SELECT cursos.nome AS curso
                FROM matriculas
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                WHERE alunos.id_aluno = ?""";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    System.out.println(" - " + rs.getString("curso"));
                }
            }
        }
    }

    // DELETAR Matricula
    public void deletarMatricula(int id) throws SQLException{

        String sql = "DELETE FROM matriculas WHERE id_matricula = ?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        }
    }


}
