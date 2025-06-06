package com.student.informationsystem.dao;
import com.student.informationsystem.entity.Subject;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class SubjectDAO extends BaseDAO<Subject> {

    public SubjectDAO() {
        super(Subject.class);
    }

    public Subject findBySubjectCode(String subjectCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Subject> query = em.createQuery(
                    "SELECT s FROM Subject s WHERE s.subjectCode = :subjectCode", Subject.class);
            query.setParameter("subjectCode", subjectCode);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean existsBySubjectCode(String subjectCode) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(s) FROM Subject s WHERE s.subjectCode = :subjectCode", Long.class);
            query.setParameter("subjectCode", subjectCode);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public List<Subject> findAllOrderByName() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Subject> query = em.createQuery(
                    "SELECT s FROM Subject s ORDER BY s.subjectName", Subject.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Subject> searchByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Subject> query = em.createQuery(
                    "SELECT s FROM Subject s WHERE LOWER(s.subjectName) LIKE LOWER(:name) ORDER BY s.subjectName",
                    Subject.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
