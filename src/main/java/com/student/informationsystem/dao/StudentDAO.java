package com.student.informationsystem.dao;


import com.student.informationsystem.entity.Student;
import com.student.informationsystem.entity.StudentScore;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentDAO extends BaseDAO<Student> {

    public StudentDAO() {
        super(Student.class);
    }

    public Student findByStudentCode(String studentCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.studentCode = :studentCode", Student.class);
            query.setParameter("studentCode", studentCode);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Student> findStudentsWithScores() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.scores sc LEFT JOIN FETCH sc.subject ORDER BY s.studentCode",
                    Student.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<StudentScore> getStudentScores(int studentId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<StudentScore> query = em.createQuery(
                    "SELECT sc FROM StudentScore sc JOIN FETCH sc.subject WHERE sc.student.studentId = :studentId ORDER BY sc.subject.subjectName",
                    StudentScore.class);
            query.setParameter("studentId", studentId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByStudentCode(String studentCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(s) FROM Student s WHERE s.studentCode = :studentCode", Long.class);
            query.setParameter("studentCode", studentCode);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public List<Student> searchByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE LOWER(s.fullName) LIKE LOWER(:name) ORDER BY s.fullName",
                    Student.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}