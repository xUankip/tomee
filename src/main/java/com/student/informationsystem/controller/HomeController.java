package com.student.informationsystem.controller;


import com.student.informationsystem.entity.Student;
import com.student.informationsystem.entity.StudentScore;
import com.student.informationsystem.service.StudentScoreService;
import com.student.informationsystem.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeController", urlPatterns = {"/", "/home", "/index"})
public class HomeController extends HttpServlet {
    private StudentService studentService;
    private StudentScoreService scoreService;

    @Override
    public void init() throws ServletException {
        super.init();
        studentService = new StudentService();
        scoreService = new StudentScoreService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get students with their scores for display
            List<Student> studentsWithScores = studentService.getStudentsWithScores();
            List<StudentScore> allScores = scoreService.getAllStudentScores();

            request.setAttribute("students", studentsWithScores);
            request.setAttribute("allScores", allScores);

            // Statistics
            int totalStudents = studentService.getAllStudents().size();
            int totalScores = allScores.size();

            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("totalScores", totalScores);

            request.getRequestDispatcher("/views/home.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}