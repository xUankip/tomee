package com.student.informationsystem.dao;



import com.student.informationsystem.entity.StudentScore;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class StudentScoreDAO extends BaseDAO<StudentScore> {

    public StudentScoreDAO() {
        super(StudentScore.class);
    }

    public StudentScore findByStudentAndSubject(int studentId, int subjectId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<StudentScore> query = em.createQuery(
                    "SELECT sc FROM StudentScore sc WHERE sc.student.studentId = :studentId AND sc.subject.subjectId = :subjectId",
                    StudentScore.class);
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<StudentScore> findByStudent(int studentId) {
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

    public List<StudentScore> findBySubject(int subjectId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<StudentScore> query = em.createQuery(
                    "SELECT sc FROM StudentScore sc JOIN FETCH sc.student WHERE sc.subject.subjectId = :subjectId ORDER BY sc.student.fullName",
                    StudentScore.class);
            query.setParameter("subjectId", subjectId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<StudentScore> findAllWithDetails() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<StudentScore> query = em.createQuery(
                    "SELECT sc FROM StudentScore sc JOIN FETCH sc.student JOIN FETCH sc.subject ORDER BY sc.student.fullName, sc.subject.subjectName",
                    StudentScore.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existsByStudentAndSubject(int studentId, int subjectId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(sc) FROM StudentScore sc WHERE sc.student.studentId = :studentId AND sc.subject.subjectId = :subjectId",
                    Long.class);
            query.setParameter("studentId", studentId);
            query.setParameter("subjectId", subjectId);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}