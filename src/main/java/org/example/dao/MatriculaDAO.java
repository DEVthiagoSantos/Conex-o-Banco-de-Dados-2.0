package org.example.dao;

import org.example.connection.Conexao;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Matricula;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

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

    // ROAD - Alunos Matriculados
    public List<Matricula> listarAlunosMatriculados() throws SQLException {

        String sql = """
                SELECT matriculas.id_matricula AS id_matricula,
                       matriculas.data_matricula AS data_matricula,
                       alunos.id_aluno AS id_aluno,
                       alunos.nome AS aluno,
                       cursos.id_curso AS id_curso,
                       cursos.nome AS curso
                FROM matriculas
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso""";

        List<Matricula> lista = new ArrayList<>();
        try (Connection con = Conexao.conexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    lista.add(montagemMatricula(rs));
                }

            }
        }

        return lista;
    }

    //Listar alunos de curso especifico
    public List<String> listarAlunosCursos(int id) throws SQLException {

        String sql = """
                SELECT alunos.nome AS nome
                FROM matriculas
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso
                WHERE cursos.id_curso = ?""";
        List<String> nomes = new ArrayList<>();

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    nomes.add(rs.getString("nome"));
                }
            }
        }

        return nomes;
    }

    // ROAD - Cursos de aluno especifico
    public List<Matricula> listarCursosDeAluno (int id) throws SQLException {

        String sql = """
                SELECT matriculas.id_matricula AS id_matricula,
                       matriculas.data_matricula AS data_matricula,
                       alunos.id_aluno AS id_aluno,
                       alunos.nome AS aluno,
                       cursos.id_curso AS id_curso,
                       cursos.nome AS curso
                FROM matriculas
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                WHERE alunos.id_aluno = ?""";

        List<Matricula> lista = new ArrayList<>();

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    lista.add(montagemMatricula(rs));
                }
            }
        }

        return lista;
    }

    //Contagem de Alunos com Map
    public Map<String, Integer> contagemDeAlunos() throws SQLException {

        String sql = """
                SELECT cursos.nome AS curso,
                       COUNT(matriculas.id_aluno) AS aluno
                FROM matriculas
                INNER JOIN cursos ON matriculas.id_curso = cursos.id_curso
                INNER JOIN alunos ON matriculas.id_aluno = alunos.id_aluno
                GROUP BY cursos.nome""";

        Map<String, Integer> mapa = new LinkedHashMap<>();

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    mapa.put(
                         rs.getString("curso"),
                         rs.getInt("aluno")
                    );
                }
            }
        }

        return mapa;
    }

    //ATUALIZAR MATRICULA
    public void atualizarMatricula(int idCursoNovo,
                                   int idAluno,
                                   int idCursoAntigo) throws SQLException {

        String sql = "UPDATE matriculas SET id_curso = ? WHERE id_aluno = ? AND id_curso = ?";

        try (Connection conn = Conexao.conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCursoNovo);
            stmt.setInt(2, idAluno);
            stmt.setInt(3, idCursoAntigo);

            stmt.executeUpdate();
        }
    }

    // DELETAR Matricula
    public void deletarMatricula(int idAluno, int idCurso) throws SQLException{

        String sql = "DELETE FROM matriculas WHERE id_aluno = ? AND id_curso = ?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);
            stmt.executeUpdate();

        }
    }

    // Montagem matricula
    private Matricula montagemMatricula(ResultSet rs) throws SQLException {

        //Aluno
        Aluno aluno = new Aluno();
        aluno.setId_aluno(rs.getInt("id_aluno"));
        aluno.setNome(rs.getString("aluno"));

        //Curso
        Curso curso = new Curso();
        curso.setId_curso(rs.getInt("id_curso"));
        curso.setNome(rs.getString("curso"));

        //DATA
        LocalDateTime data = rs.getTimestamp("data_matricula")
                .toLocalDateTime();

        // Matricula
        Matricula matricula = new Matricula(
                rs.getInt("id_matricula"),
                aluno,
                curso,
                data
        );

        return matricula;
    }
}
