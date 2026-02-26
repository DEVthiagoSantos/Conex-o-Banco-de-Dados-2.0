package org.example;

import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;
import org.example.dao.MatriculaDAO;
import org.example.model.Aluno;
import org.example.model.Curso;
import org.example.model.Matricula;
import org.example.service.AlunoService;
import org.example.service.CursoService;
import org.example.service.MatriculaService;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static AlunoDAO alunoDAO = new AlunoDAO();
    static CursoDAO cursoDAO = new CursoDAO();
    static MatriculaDAO matriculaDAO = new MatriculaDAO();
    static MatriculaService matriculaService = new MatriculaService(alunoDAO,
            cursoDAO,
            matriculaDAO);
    static AlunoService alunoService = new AlunoService();
    static CursoService cursoService = new CursoService(cursoDAO);

    public static void menuMatricula() {

        String menuMatricula = """
                ================ MATRICULA > ===============
                [ 1 ] Inserir Matricula
                [ 2 ] Listar Matriculas
                [ 3 ] Contagem de Alunos
                [ 4 ] Atualizar Matricula
                [ 5 ] Deletar matricula
                [ 6 ] Alunos >
                [ 7 ] Cursos >
                [ 8 ] Sair
                ===========================================""";

        System.out.println(menuMatricula);
        System.out.print("Escolha: ");
    }

    public static void exibirMenuAluno() {

        String menuAluno = """
                ================ < Aluno > ===============
                [ 1 ] Inserir Aluno
                [ 2 ] Listar Alunos
                [ 3 ] Listar Cursos de Aluno
                [ 4 ] Atualizar Aluno
                [ 5 ] Deletar Aluno
                [ 6 ] < Matricula
                [ 7 ] Cursos >
                ===========================================""";

        System.out.println(menuAluno);
        System.out.print("Escolha: ");
    }

    public static void exibirMenuCurso() {

        String menuCurso = """
                ================ < Curso ===============
                [ 1 ] Inserir Curso
                [ 2 ] Listar Cursos
                [ 3 ] Listar Alunos em Curso
                [ 4 ] Atualizar Curso
                [ 5 ] Deletar Curso
                [ 6 ] < Matricula
                [ 7 ] < Alunos
                ===========================================""";
        System.out.println(menuCurso);
        System.out.print("Escolha: ");
    }

    public static boolean executarOpcaoMatricula(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                inserirMatricula();
                break;
            case 2 :
                listarMatriculas();
                break;
            case 3 :
                contagemDeAlunos();
                break;
            case 4 :
                atualizarMatricula();
                break;
            case 5 :
                deletarMatricula();
                break;
            case 6 :
                menuAluno();
                break;
            case 7 :
                menuCurso();
                break;
            case 8 :
                return false;

        }

        return true;
    }

    public static boolean executarOpcaoAluno(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                inserirAluno();
                break;
            case 2 :
                listarAlunos();
                break;
            case 3 :
                listarCursosDeAluno();
                break;
            case 4 :
                atualizarAluno();
                break;
            case 5 :
                deletarAluno();
                break;
            case 6 :
                return false;
            case 7 :
                menuCurso();
                break;
        }

        return true;
    }

    public static boolean executarOpcaoCurso(int opcao) throws SQLException {

        switch (opcao) {

            case 1 :
                inserirCurso();
                break;
            case 2 :
                listarCursos();
                break;
            case 3 :
                listarAlunosEmCurso();
                break;
            case 4 :
                atualizarCurso();
                break;
            case 5 :
                deletarCurso();
                break;
            case 6 :
                return false;
            case 7 :
                menuAluno();
                break;
        }

        return true;
    }

    public static void menuAluno() throws SQLException {
        boolean executando = true;
        while (executando) {
            exibirMenuAluno();
            try {
                int opcao = Integer.parseInt(input.nextLine());
                executando = executarOpcaoAluno(opcao);
            } catch (NumberFormatException e){
                System.out.println("Inserir um numero valido.");
            }
        }
    }

    public static void menuCurso() throws SQLException {
        boolean executando = true;
        while (executando) {
            exibirMenuCurso();
            try {
                int opcao = Integer.parseInt(input.nextLine());
                executando = executarOpcaoCurso(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Inserir um numero valido.");
            }
        }
    }

    // Opções Matricula
    public static void inserirMatricula() throws SQLException {
        System.out.print("Nome do Aluno: ");
        String aluno = input.nextLine();
        System.out.print("Nome do Curso: ");
        String curso = input.nextLine();

        try {
            matriculaService.matricular(aluno, curso);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void listarMatriculas() throws SQLException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Matricula m : matriculaService.listarMatriculas()) {
            System.out.println(m.getIdMatricula()
                    + " | " + m.getAluno().getNome()
                    + " | " + m.getCurso().getNome()
                    + " | " + m.getData().format(formatter));
        }
    }

    public static void listarCursosDeAluno() throws SQLException {

        try {
            System.out.print("ID do aluno: ");
            int idAluno = Integer.parseInt(input.nextLine());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            Aluno aluno = alunoDAO.buscarPorId(idAluno);
            System.out.println("Cursos de " + aluno.getNome() + ":");
            for (Matricula m : matriculaService.listarCursosDeAluno(idAluno)) {
                System.out.println("- "
                        + m.getCurso().getNome()
                        + " | " + m.getData().format(formatter));
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void contagemDeAlunos() throws SQLException {


        Map<String, Integer> mapa = matriculaDAO.contagemDeAlunos();
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
    }

    public static void listarAlunosEmCurso() throws SQLException {
        System.out.print("ID do curso: ");
        int idCurso = Integer.parseInt(input.nextLine());

        for (String a : matriculaService.listarAlunosEmCurso(idCurso)) {
            System.out.println("- " + a);
        }
    }

    public static void deletarMatricula() throws SQLException {

        try {
            System.out.print("Nome do Aluno: ");
            String aluno = input.nextLine();
            System.out.print("Nome do Curso: ");
            String curso = input.nextLine();
            matriculaService.deletar(aluno, curso);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void atualizarMatricula() throws SQLException {

        try {
            System.out.print("Curso Atual: ");
            String cursoAnt = input.nextLine();
            System.out.print("Nome do Aluno: ");
            String nomeAluno = input.nextLine();
            System.out.print("Curso que deseja ser inserido: ");
            String cursoNov = input.nextLine();

            matriculaService.atualizar(cursoNov, nomeAluno, cursoAnt);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // Opções Alunos

    public static void inserirAluno() throws SQLException {

        try {
            System.out.print("Nome aluno: ");
            String nome = input.nextLine();
            System.out.println("Email: ");
            String email = input.nextLine();

            alunoService.inserirAluno(nome, email);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarAlunos() throws SQLException {

        alunoService.listarAlunos();

    }

    public static void deletarAluno() throws SQLException {

        try {
            System.out.print("Inserir o ID do aluno: ");
            int idAluno = Integer.parseInt(input.nextLine());

            alunoService.deletarAluno(idAluno);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void atualizarAluno() throws SQLException {

        try {
            System.out.println("O id inserido deve ser o exato id do aluno.");
            System.out.println("Nome e Email novos.");
            System.out.print("ID do aluno: ");
            int idAluno = Integer.parseInt(input.nextLine());
            System.out.print("Nome: ");
            String nomeAluno = input.nextLine();
            System.out.print("Email: ");
            String emailAluno = input.nextLine();

            alunoService.atualizarAluno(idAluno, nomeAluno, emailAluno);
        } catch (NumberFormatException e) {
            System.out.println("Digite um numero valido.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    // Opções de Curso
    public static void inserirCurso() throws SQLException {

        try {
            System.out.print("Nome do curso: ");
            String curso = input.nextLine();
            cursoService.inserirCurso(curso);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarCursos() throws SQLException {
        for (Curso c : cursoService.listarCursos()) {
            System.out.println("- "
                    + c.getId_curso()
                    + " | " + c.getNome());
        }
    }

    public static void deletarCurso() throws SQLException {
        try {
            System.out.print("ID do curso: ");
            int idCurso = Integer.parseInt(input.nextLine());
            cursoService.deletarCurso(idCurso);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void atualizarCurso() throws SQLException {
        try {

            System.out.println("ID do curso que irá ser atualizado: ");
            int idCurso = Integer.parseInt(input.nextLine());
            System.out.println("Nome novo: ");
            String nomeCurso = input.nextLine();
            cursoService.atualizarCurso(nomeCurso, idCurso);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) throws SQLException {

        boolean executando = true;

        while (executando) {
            menuMatricula();
            try {
                int opcao = Integer.parseInt(input.nextLine());
                executando = executarOpcaoMatricula(opcao);
            } catch (Exception e) {
                System.out.println("Digite um numero valido.");
            }
        }

    }
}