package com.student.informationsystem.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subject_t")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private int subjectId;

    @Column(name = "subject_code", nullable = false, length = 20)
    private String subjectCode;

    @Column(name = "subject_name", nullable = false, length = 100)
    private String subjectName;

    @Column(name = "credit", nullable = false)
    private int credit;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentScore> scores;

    // Constructors
    public Subject() {}

    public Subject(String subjectCode, String subjectName, int credit) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    // Getters and Setters
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public List<StudentScore> getScores() {
        return scores;
    }

    public void setScores(List<StudentScore> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectCode='" + subjectCode + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", credit=" + credit +
                '}';
    }
}