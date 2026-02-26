package org.example.dao;

import org.example.connection.Conexao;
import org.example.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    //CREATE
    public void inserir(Aluno aluno) throws SQLException {

        String sql = "INSERT INTO alunos (nome, email) VALUES (?, ?)";

        try (Connection conn = Conexao.conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.executeUpdate();
        }
    }

    //ROAD
    public List<Aluno> listarAlunos() throws SQLException {

        String sql = "SELECT * FROM alunos";
        List<Aluno> lista = new ArrayList<>();

        try (Connection conn = Conexao.conexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId_aluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                lista.add(aluno);
            }
        }

        return lista;
    }

    //UPDATE
    public void atualizar(Aluno aluno) throws SQLException {

        String sql = "UPDATE alunos SET nome =?, email =? WHERE id_aluno =?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setInt(3, aluno.getId_aluno());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deletar(int id) throws SQLException {

        String sql = "DELETE FROM alunos WHERE id_aluno = ?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    //CONSULTAS
    public Aluno buscarPorNomeAluno(String nome) throws SQLException {

        String sql = "SELECT * FROM alunos WHERE nome LIKE ?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return montagemAluno(rs);
                }
            }
        }

        return null;
    }

    public Aluno buscarPorId(int idAluno) throws SQLException {

        String sql = "SELECT * FROM alunos WHERE id_aluno = ?";

        try (Connection conn = Conexao.conexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    return montagemAluno(rs);

                }
            }
        }

        return null;
    }

    public Aluno montagemAluno(ResultSet rs) throws SQLException {

        Aluno aluno = new Aluno();
        aluno.setId_aluno(rs.getInt("id_aluno"));
        aluno.setNome(rs.getString("nome"));
        aluno.setEmail(rs.getString("email"));

        return aluno;
    }

}
