package com.student.informationsystem.controller;


import com.student.informationsystem.entity.Student;
import com.student.informationsystem.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentController", urlPatterns = {"/students", "/student"})
public class StudentController extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        super.init();
        studentService = new StudentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listStudents(request, response);
            } else if (action.equals("add")) {
                showAddForm(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            } else if (action.equals("delete")) {
                deleteStudent(request, response);
            } else if (action.equals("view")) {
                viewStudent(request, response);
            } else {
                listStudents(request, response);
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
                saveStudent(request, response);
            } else if (action != null && action.equals("update")) {
                updateStudent(request, response);
            } else {
                listStudents(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchName = request.getParameter("searchName");
        List<Student> students;

        if (searchName != null && !searchName.trim().isEmpty()) {
            students = studentService.searchStudentsByName(searchName);
            request.setAttribute("searchName", searchName);
        } else {
            students = studentService.getAllStudents();
        }

        request.setAttribute("students", students);
        request.getRequestDispatcher("/views/student/list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/student/add.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentService.findStudentById(id);

        if (student != null) {
            request.setAttribute("student", student);
            request.getRequestDispatcher("/views/student/edit.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Student not found");
            listStudents(request, response);
        }
    }

    private void saveStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        String studentCode = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");

        Student student = new Student(studentCode, fullName, address);

        try {
            studentService.saveStudent(student);
            request.setAttribute("successMessage", "Student added successfully!");
            listStudents(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("student", student);
            request.getRequestDispatcher("/views/student/add.jsp").forward(request, response);
        }
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        int id = Integer.parseInt(request.getParameter("id"));
        String studentCode = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");

        Student student = new Student(studentCode, fullName, address);
        student.setStudentId(id);

        try {
            studentService.updateStudent(student);
            request.setAttribute("successMessage", "Student updated successfully!");
            listStudents(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("student", student);
            request.getRequestDispatcher("/views/student/edit.jsp").forward(request, response);
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentService.findStudentById(id);

        if (student != null) {
            studentService.deleteStudent(student);
            request.setAttribute("successMessage", "Student deleted successfully!");
        } else {
            request.setAttribute("errorMessage", "Student not found!");
        }

        listStudents(request, response);
    }

    private void viewStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentService.findStudentById(id);

        if (student != null) {
            request.setAttribute("student", student);
            request.setAttribute("studentScores", studentService.getStudentScores(id));
            request.getRequestDispatcher("/views/student/view.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Student not found");
            listStudents(request, response);
        }
    }
}