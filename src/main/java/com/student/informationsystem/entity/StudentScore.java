package com.student.informationsystem.entity;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "student_score_t")
public class StudentScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_score_id")
    private int studentScoreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_student_id"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", foreignKey = @ForeignKey(name = "fk_subject_id"))
    private Subject subject;

    @Column(name = "score1", precision = 5, scale = 2)
    private BigDecimal score1;

    @Column(name = "score2", precision = 5, scale = 2)
    private BigDecimal score2;

    // Constructors
    public StudentScore() {}

    public StudentScore(Student student, Subject subject, BigDecimal score1, BigDecimal score2) {
        this.student = student;
        this.subject = subject;
        this.score1 = score1;
        this.score2 = score2;
    }

    // Business logic method to calculate grade
    public BigDecimal calculateGrade() {
        if (score1 == null || score2 == null) {
            return BigDecimal.ZERO;
        }
        return score1.multiply(new BigDecimal("0.3")).add(score2.multiply(new BigDecimal("0.7")));
    }

    public String getLetterGrade() {
        BigDecimal grade = calculateGrade();
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

    // Getters and Setters
    public int getStudentScoreId() {
        return studentScoreId;
    }

    public void setStudentScoreId(int studentScoreId) {
        this.studentScoreId = studentScoreId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public BigDecimal getScore1() {
        return score1;
    }

    public void setScore1(BigDecimal score1) {
        this.score1 = score1;
    }

    public BigDecimal getScore2() {
        return score2;
    }

    public void setScore2(BigDecimal score2) {
        this.score2 = score2;
    }

    @Override
    public String toString() {
        return "StudentScore{" +
                "studentScoreId=" + studentScoreId +
                ", score1=" + score1 +
                ", score2=" + score2 +
                ", grade=" + calculateGrade() +
                ", letterGrade='" + getLetterGrade() + '\'' +
                '}';
    }
}