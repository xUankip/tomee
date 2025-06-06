package com.student.informationsystem.service;

import com.student.informationsystem.dao.SubjectDAO;
import com.student.informationsystem.entity.Subject;

import java.util.List;

public class SubjectService {
    private SubjectDAO subjectDAO;

    public SubjectService() {
        this.subjectDAO = new SubjectDAO();
    }

    public void saveSubject(Subject subject) throws Exception {
        // Validate input
        if (subject == null) {
            throw new IllegalArgumentException("Subject cannot be null");
        }

        if (subject.getSubjectCode() == null || subject.getSubjectCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Subject code is required");
        }

        if (subject.getSubjectName() == null || subject.getSubjectName().trim().isEmpty()) {
            throw new IllegalArgumentException("Subject name is required");
        }

        if (subject.getCredit() <= 0) {
            throw new IllegalArgumentException("Credit must be greater than 0");
        }

        // Check if subject code already exists
        if (subjectDAO.existsBySubjectCode(subject.getSubjectCode())) {
            throw new Exception("Subject code already exists: " + subject.getSubjectCode());
        }

        subjectDAO.save(subject);
    }

    public void updateSubject(Subject subject) throws Exception {
        if (subject == null) {
            throw new IllegalArgumentException("Subject cannot be null");
        }

        if (subject.getSubjectId() <= 0) {
            throw new IllegalArgumentException("Subject ID is required for update");
        }

        subjectDAO.update(subject);
    }

    public void deleteSubject(Subject subject) throws Exception {
        if (subject == null) {
            throw new IllegalArgumentException("Subject cannot be null");
        }

        subjectDAO.delete(subject);
    }

    public Subject findSubjectById(int id) {
        return subjectDAO.findById(id);
    }

    public Subject findSubjectByCode(String subjectCode) {
        return subjectDAO.findBySubjectCode(subjectCode);
    }

    public List<Subject> getAllSubjects() {
        return subjectDAO.findAll();
    }

    public List<Subject> getAllSubjectsOrderByName() {
        return subjectDAO.findAllOrderByName();
    }

    public List<Subject> searchSubjectsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return getAllSubjects();
        }
        return subjectDAO.searchByName(name);
    }

    public boolean isSubjectCodeExists(String subjectCode) {
        return subjectDAO.existsBySubjectCode(subjectCode);
    }
}