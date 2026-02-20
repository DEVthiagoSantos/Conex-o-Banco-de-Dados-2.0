package org.example.dao;
import org.example.connection.Conexao;
import org.example.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO{

    //CREATE
    public void inserir(Curso curso) throws SQLException {

        String sql = "INSERT INTO cursos (nome) VALUES (?)";

        try (Connection conn = Conexao.conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.executeUpdate();
        }
    }

    //ROAD
    public List<Curso> listarCursos() throws SQLException {

        String sql = "SELECT * FROM cursos";
        List<Curso> lista = new ArrayList<>();

        try (Connection conn = Conexao.conexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId_curso(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome"));
                lista.add(curso);
            }
        }

        return lista;
    }

    //ROAD - Por Curso
    public Curso buscarPorCurso(String curso) throws SQLException {

        String sql = "SELECT * FROM cursos WHERE nome LIKE ?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + curso + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Curso curso2 = new Curso();

                    curso2.setId_curso(rs.getInt("id_curso"));
                    curso2.setNome(rs.getString("nome"));

                    return curso2;
                }
            }
        }

        return null;
    }
}
