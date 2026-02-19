package org.example;

import org.example.dao.AlunoDAO;
import org.example.model.Aluno;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        Aluno aluno = new Aluno("Thalita Santos", "thalita21@gmail.com", 2);

        AlunoDAO dao = new AlunoDAO();

        for(Aluno alunos : dao.buscarPorNome("T")){
            System.out.println(alunos.getNome());
        }
    }
}