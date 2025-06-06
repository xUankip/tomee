package com.student.informationsystem.service;

import com.student.informationsystem.dao.StudentDAO;
import com.student.informationsystem.entity.Student;
import com.student.informationsystem.entity.StudentScore;

import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public void saveStudent(Student student) throws Exception {
        // Validate input
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Student code is required");
        }

        if (student.getFullName() == null || student.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required");
        }

        // Check if student code already exists
        if (studentDAO.existsByStudentCode(student.getStudentCode())) {
            throw new Exception("Student code already exists: " + student.getStudentCode());
        }

        studentDAO.save(student);
    }

    public void updateStudent(Student student) throws Exception {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (student.getStudentId() <= 0) {
            throw new IllegalArgumentException("Student ID is required for update");
        }

        studentDAO.update(student);
    }

    public void deleteStudent(Student student) throws Exception {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        studentDAO.delete(student);
    }

    public Student findStudentById(int id) {
        return studentDAO.findById(id);
    }

    public Student findStudentByCode(String studentCode) {
        return studentDAO.findByStudentCode(studentCode);
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public List<Student> getStudentsWithScores() {
        return studentDAO.findStudentsWithScores();
    }

    public List<StudentScore> getStudentScores(int studentId) {
        return studentDAO.getStudentScores(studentId);
    }

    public List<Student> searchStudentsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllStudents();
        }
        return studentDAO.searchByName(name);
    }

    public boolean isStudentCodeExists(String studentCode) {
        return studentDAO.existsByStudentCode(studentCode);
    }
}