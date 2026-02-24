package org.example;

import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;
import org.example.dao.MatriculaDAO;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Matricula;
import org.example.service.MatriculaService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    // Exibir Menu
    public static void exibirMenu() {

        String menu = """
                ============== MENU ==============
                [ 1 ] Cadastrar aluno
                [ 2 ] Listar cursos
                [ 3 ] Listar alunos
                [ 4 ] Matricular aluno em curso
                [ 5 ] Listar alunos matriculados
                [ 6 ] Alunos em curso especifico (ID)
                [ 7 ] Cursos de aluno especifico (ID)
                [ 8 ] Next =>
                [ 9 ] Sair
                =================================""";
        System.out.println(menu);
        System.out.print("Escolha Opção: ");

    }

    public static void exibirMenu2() {

        String menu2 = """
                ============== MENU 2 ==============
                [ 1 ] Atualizar Aluno
                [ 2 ] Atualizar Matricula
                [ 3 ] Deletar Aluno
                [ 4 ] Deletar Matricula
                [ 5 ] Contagem Alunos
                [ 6 ] <= Voltar
                ====================================""";

        System.out.println(menu2);
        System.out.print("Escolha Opção: ");
    }

    public static void menu2() throws SQLException {

        boolean executanto2 = true;
        while (executanto2) {
            exibirMenu2();
            int opcao = input.nextInt();
            executanto2 = executaOpcao2(opcao);
        }
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
                listarCursos();
                break;
            case 3 :
                listarAlunos();
                break;
            case 4 :
                matricularAlunoCurso();
                break;
            case 5 :
                listarAlunosMatriculados();
                break;
            case 6 :
                listarAlunoEmCursos();
                break;
            case 7 :
                listarCursosDeAluno();
                break;
            case 8 :
                menu2();
                break;
            case 9 :
                return false;

            default:
                System.out.println("Opção invalida");
        }

        return true;
    }

    public static boolean executaOpcao2(int opcao) throws SQLException {

        switch (opcao) {

            case 4 :
                deletarMatricula();
                break;
            case 5 :
                contagemDeAlunos();
                break;
            case 6 :
                return false;
        }

        return true;
    }

    // Métodos do sistema - ESCOLHAS
    public static void cadastrarAluno() throws SQLException {
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.print("Email: ");
        String email = input.nextLine();

        Aluno aluno = new Aluno(nome, email);
        AlunoDAO dao = new AlunoDAO();
        dao.inserir(aluno);
    }

    public static void contagemDeAlunos() throws SQLException {

        MatriculaDAO daoM = new MatriculaDAO();
        daoM.contagemDeAlunos();
    }

    public static void listarAlunos() throws SQLException {
        AlunoDAO dao = new AlunoDAO();

        for (Aluno alunos : dao.listarAlunos()) {
            System.out.println(alunos.getId_aluno()
                    + " | " + alunos.getNome()
                    + " | " + alunos.getEmail());
        }
    }

    public static void listarCursos() throws SQLException {
        CursoDAO daoC = new CursoDAO();
        for (Curso cursos : daoC.listarCursos()) {
            System.out.println(cursos.getId_curso()
                    + " | " +cursos.getNome());
        }
    }

    public static void matricularAlunoCurso() throws SQLException {
        System.out.print("Nome: ");
        String nome = input.nextLine();
        System.out.println("Nome do Curso: ");
        String cursoNome = input.nextLine();

        System.out.println("Matriculando....");
        MatriculaService matricula = new MatriculaService();
        try {
            matricula.matricular(nome, cursoNome);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void listarAlunosMatriculados() throws SQLException {

        MatriculaDAO daoM = new MatriculaDAO();

        // Formatar alunos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Matricula m : daoM.listarAunosMatriculados()) {
            System.out.println(m.getIdMatricula()
                    + " | " + m.getAluno().getNome()
                    + " | " + m.getCurso().getNome()
                    + " | "+ m.getData().format(formatter));
        }
    }

    public static void listarAlunoEmCursos() throws SQLException {

        System.out.print("ID do curso: ");

        int id = input.nextInt();
        MatriculaDAO daoM = new MatriculaDAO();
        daoM.listarAlunosCursos(id);
    }

    public static void listarCursosDeAluno() throws SQLException {

        System.out.print("ID do aluno: ");
        int id = input.nextInt();
        MatriculaDAO daoM = new MatriculaDAO();
        daoM.listarCursosAluno(id);
    }

    public static void deletarMatricula() throws SQLException {

        System.out.print("ID Matricula: ");
        int idMatricula = input.nextInt();

        MatriculaDAO daoM = new MatriculaDAO();
        daoM.deletarMatricula(idMatricula);
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