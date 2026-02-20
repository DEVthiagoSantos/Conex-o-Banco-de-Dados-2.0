package org.example.dao;

import org.example.connection.Conexao;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Matricula;

import java.sql.*;
import java.util.ArrayList;
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

    //ROAD
    public void listarMatriculas() throws SQLException {

        String sql = """
                SELECT alunos.nome AS aluno,
                       cursos.nome AS curso
                FROM matriculas
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso""";

        try (Connection conn = Conexao.conexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                System.out.println(rs.getString("aluno")
                        + " | " + rs.getString("curso"));
            }
        }
    }
}
