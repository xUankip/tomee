package com.student.informationsystem.service;


import com.student.informationsystem.dao.StudentScoreDAO;
import com.student.informationsystem.entity.StudentScore;

import java.math.BigDecimal;
import java.util.List;

public class StudentScoreService {
    private StudentScoreDAO studentScoreDAO;

    public StudentScoreService() {
        this.studentScoreDAO = new StudentScoreDAO();
    }

    public void saveStudentScore(StudentScore studentScore) throws Exception {
        // Validate input
        if (studentScore == null) {
            throw new IllegalArgumentException("StudentScore cannot be null");
        }

        if (studentScore.getStudent() == null) {
            throw new IllegalArgumentException("Student is required");
        }

        if (studentScore.getSubject() == null) {
            throw new IllegalArgumentException("Subject is required");
        }

        if (studentScore.getScore1() == null || studentScore.getScore2() == null) {
            throw new IllegalArgumentException("Both scores are required");
        }

        // Validate score range (0-10)
        if (isScoreInvalid(studentScore.getScore1()) || isScoreInvalid(studentScore.getScore2())) {
            throw new IllegalArgumentException("Scores must be between 0 and 10");
        }

        // Check if score already exists for this student and subject
        if (studentScoreDAO.existsByStudentAndSubject(
                studentScore.getStudent().getStudentId(),
                studentScore.getSubject().getSubjectId())) {
            throw new Exception("Score already exists for this student and subject");
        }

        studentScoreDAO.save(studentScore);
    }

    public void updateStudentScore(StudentScore studentScore) throws Exception {
        if (studentScore == null) {
            throw new IllegalArgumentException("StudentScore cannot be null");
        }

        if (studentScore.getStudentScoreId() <= 0) {
            throw new IllegalArgumentException("StudentScore ID is required for update");
        }

        // Validate scores
        if (studentScore.getScore1() == null || studentScore.getScore2() == null) {
            throw new IllegalArgumentException("Both scores are required");
        }

        if (isScoreInvalid(studentScore.getScore1()) || isScoreInvalid(studentScore.getScore2())) {
            throw new IllegalArgumentException("Scores must be between 0 and 10");
        }

        studentScoreDAO.update(studentScore);
    }

    public void deleteStudentScore(StudentScore studentScore) throws Exception {
        if (studentScore == null) {
            throw new IllegalArgumentException("StudentScore cannot be null");
        }

        studentScoreDAO.delete(studentScore);
    }

    public StudentScore findStudentScoreById(int id) {
        return studentScoreDAO.findById(id);
    }

    public StudentScore findByStudentAndSubject(int studentId, int subjectId) {
        return studentScoreDAO.findByStudentAndSubject(studentId, subjectId);
    }

    public List<StudentScore> getScoresByStudent(int studentId) {
        return studentScoreDAO.findByStudent(studentId);
    }

    public List<StudentScore> getScoresBySubject(int subjectId) {
        return studentScoreDAO.findBySubject(subjectId);
    }

    public List<StudentScore> getAllStudentScores() {
        return studentScoreDAO.findAllWithDetails();
    }

    public boolean isScoreExists(int studentId, int subjectId) {
        return studentScoreDAO.existsByStudentAndSubject(studentId, subjectId);
    }

    private boolean isScoreInvalid(BigDecimal score) {
        return score.compareTo(BigDecimal.ZERO) < 0 || score.compareTo(new BigDecimal("10")) > 0;
    }

    public String calculateLetterGrade(BigDecimal score1, BigDecimal score2) {
        if (score1 == null || score2 == null) {
            return "N/A";
        }

        BigDecimal grade = score1.multiply(new BigDecimal("0.3"))
                .add(score2.multiply(new BigDecimal("0.7")));

        if (grade.compareTo(new BigDecimal("8.0")) >= 0) {
            return "A";
        } else if (grade.compareTo(new BigDecimal("6.0")) >= 0) {
            return "B";
        } else if (grade.compareTo(new BigDecimal("4.0")) >= 0) {
            return "D";
        } else {
            return "F";
        }
    }

    public BigDecimal calculateNumericGrade(BigDecimal score1, BigDecimal score2) {
        if (score1 == null || score2 == null) {
            return BigDecimal.ZERO;
        }

        return score1.multiply(new BigDecimal("0.3"))
                .add(score2.multiply(new BigDecimal("0.7")));
    }
}