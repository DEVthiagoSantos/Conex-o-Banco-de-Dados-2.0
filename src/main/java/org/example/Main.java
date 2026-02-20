package org.example;

import org.example.dao.AlunoDAO;
import org.example.dao.MatriculaDAO;
import org.example.model.Aluno;
import org.example.service.MatriculaService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    // Exibir Menu
    public static void exibirMenu() {
        System.out.println("============== MENU ==============");
        System.out.println("[ 1 ] Cadastrar aluno");
        System.out.println("[ 2 ] Listar alunos");
        System.out.println("[ 3 ] Matricular aluno em curso");
        System.out.println("[ 4 ] Listar alunos matriculados");
        System.out.println("[ 5 ] Sair");
        System.out.println("==================================");
        System.out.print("Escolha uma opção: ");

    }

    // Lê a opção do usuario
    public static int opcaoUsuario() {

        try {
            int opcao = input.nextInt();
            input.nextLine();
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("Opcão Invalida!");
            input.nextLine();
            return 5;
        }


    }

    //Executa a opção escolhida
    public static boolean executaOpcao(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                cadastrarAluno();
                break;
            case 2 :
                listarAlunos();
                break;
            case 3 :
                matricularAlunoCurso();
                break;
            case 4 :
                listarAlunosMatriculados();
                break;
            case 5 :
                return false;

            default:
                System.out.println("Opção invalida");
        }

        return true;
    }

    // Métodos do sistema
    public static void cadastrarAluno() throws SQLException {
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();

        Aluno aluno = new Aluno(nome, email);
        AlunoDAO dao = new AlunoDAO();
        dao.inserir(aluno);
    }

    public static void listarAlunos() throws SQLException {
        AlunoDAO dao = new AlunoDAO();

        for (Aluno alunos : dao.listarAlunos()) {
            System.out.println(alunos.getNome() + " | " + alunos.getEmail());
        }
    }

    public static void matricularAlunoCurso() throws SQLException {
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.println("Nome do Curso: ");
        String cursoNome = input.nextLine();

        System.out.println("Matriculando....");
        MatriculaService matricula = new MatriculaService();
        matricula.matricular(nome, cursoNome);

    }

    public static void listarAlunosMatriculados() throws SQLException {

        MatriculaDAO daoM = new MatriculaDAO();

        daoM.listarMatriculas();
    }

    public static void main(String[] args) throws SQLException {

        boolean executando = true;

        while (executando) {
            exibirMenu();
            int opcao = opcaoUsuario();
            executando = executaOpcao(opcao);
        }

        System.out.println("Sistema encerrado");
    }
}