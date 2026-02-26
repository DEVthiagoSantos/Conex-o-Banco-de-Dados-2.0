package org.example.service;

import org.example.dao.AlunoDAO;
import org.example.model.Aluno;

import java.sql.SQLException;

public class AlunoService {

    AlunoDAO alunoDAO = new AlunoDAO();

    public void inserirAluno(String nome, String email) throws SQLException {

        nome = nome.trim();
        email = email.trim();

        verificacao(nome, email);
        Aluno aluno = new Aluno(nome, email);
        alunoDAO.inserir(aluno);

    }

    public void atualizarAluno(int idAluno, String nome, String email) throws SQLException {

        nome = nome.trim();
        email = email.trim();

        verificacao(nome, email);
        Aluno aluno = new Aluno(nome, email, idAluno);
        alunoDAO.atualizar(aluno);
    }

    public void verificacao(String nome, String email) throws SQLException {

        if (!nome.matches("^[a-zA-ZÀ-ÿ ]+$")) {
            throw new RuntimeException("O nome de usuário não pode ser dessa forma,");
        }
        if (!email.matches("^[^@\\s]+@[a-zA-Z0-9]+\\.com$")) {
            throw new RuntimeException("O email de usuário não pode ter esse formato");
        }

    }

    public void listarAlunos() throws SQLException {

        for (Aluno alunos : alunoDAO.listarAlunos()) {
            System.out.println("- "
                    + alunos.getId_aluno()
                    + " | " + alunos.getNome()
                    + " | " + alunos.getEmail());
        }
    }

    public void deletarAluno(int idAluno) throws SQLException {

        if (alunoDAO.buscarPorId(idAluno) == null) {
            throw new RuntimeException("Aluno não encontrado");
        }

        alunoDAO.deletar(idAluno);
    }
}
