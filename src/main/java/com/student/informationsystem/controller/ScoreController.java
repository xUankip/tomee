package com.student.informationsystem.controller;

import com.student.informationsystem.entity.Student;
import com.student.informationsystem.entity.StudentScore;
import com.student.informationsystem.entity.Subject;
import com.student.informationsystem.service.StudentScoreService;
import com.student.informationsystem.service.StudentService;
import com.student.informationsystem.service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "ScoreController", urlPatterns = {"/scores", "/score"})
public class ScoreController extends HttpServlet {
    private StudentScoreService scoreService;
    private StudentService studentService;
    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        super.init();
        scoreService = new StudentScoreService();
        studentService = new StudentService();
        subjectService = new SubjectService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listScores(request, response);
            } else if (action.equals("add")) {
                showAddForm(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            } else if (action.equals("delete")) {
                deleteScore(request, response);
            } else {
                listScores(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action != null && action.equals("save")) {
                saveScore(request, response);
            } else if (action != null && action.equals("update")) {
                updateScore(request, response);
            } else {
                listScores(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void listScores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<StudentScore> scores = scoreService.getAllStudentScores();
        request.setAttribute("scores", scores);
        request.getRequestDispatcher("/views/score/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students = studentService.getAllStudents();
        List<Subject> subjects = subjectService.getAllSubjectsOrderByName();

        request.setAttribute("students", students);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/views/score/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        StudentScore score = scoreService.findStudentScoreById(id);

        if (score != null) {
            List<Student> students = studentService.getAllStudents();
            List<Subject> subjects = subjectService.getAllSubjectsOrderByName();

            request.setAttribute("score", score);
            request.setAttribute("students", students);
            request.setAttribute("subjects", subjects);
            request.getRequestDispatcher("/views/score/edit.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Score not found");
            listScores(request, response);
        }
    }

    private void saveScore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        BigDecimal score1 = new BigDecimal(request.getParameter("score1"));
        BigDecimal score2 = new BigDecimal(request.getParameter("score2"));

        Student student = studentService.findStudentById(studentId);
        Subject subject = subjectService.findSubjectById(subjectId);

        if (student == null || subject == null) {
            throw new Exception("Student or Subject not found");
        }

        StudentScore studentScore = new StudentScore(student, subject, score1, score2);

        try {
            scoreService.saveStudentScore(studentScore);
            request.setAttribute("successMessage", "Score added successfully!");
            listScores(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());

            // Reload form data
            List<Student> students = studentService.getAllStudents();
            List<Subject> subjects = subjectService.getAllSubjectsOrderByName();
            request.setAttribute("students", students);
            request.setAttribute("subjects", subjects);
            request.setAttribute("selectedStudentId", studentId);
            request.setAttribute("selectedSubjectId", subjectId);
            request.setAttribute("score1", score1);
            request.setAttribute("score2", score2);

            request.getRequestDispatcher("/views/score/add.jsp").forward(request, response);
        }
    }

    private void updateScore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        int id = Integer.parseInt(request.getParameter("id"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        BigDecimal score1 = new BigDecimal(request.getParameter("score1"));
        BigDecimal score2 = new BigDecimal(request.getParameter("score2"));

        Student student = studentService.findStudentById(studentId);
        Subject subject = subjectService.findSubjectById(subjectId);

        if (student == null || subject == null) {
            throw new Exception("Student or Subject not found");
        }

        StudentScore studentScore = new StudentScore(student, subject, score1, score2);
        studentScore.setStudentScoreId(id);

        try {
            scoreService.updateStudentScore(studentScore);
            request.setAttribute("successMessage", "Score updated successfully!");
            listScores(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());

            // Reload form data
            List<Student> students = studentService.getAllStudents();
            List<Subject> subjects = subjectService.getAllSubjectsOrderByName();
            request.setAttribute("score", studentScore);
            request.setAttribute("students", students);
            request.setAttribute("subjects", subjects);

            request.getRequestDispatcher("/views/score/edit.jsp").forward(request, response);
        }
    }

    private void deleteScore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        int id = Integer.parseInt(request.getParameter("id"));
        StudentScore score = scoreService.findStudentScoreById(id);

        if (score != null) {
            scoreService.deleteStudentScore(score);
            request.setAttribute("successMessage", "Score deleted successfully!");
        } else {
            request.setAttribute("errorMessage", "Score not found!");
        }

        listScores(request, response);
    }
}