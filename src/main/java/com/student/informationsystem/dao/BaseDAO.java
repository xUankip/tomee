package com.student.informationsystem.dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

public abstract class BaseDAO<T> {
    private static EntityManagerFactory entityManagerFactory;
    private Class<T> entityClass;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("sis-pu");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to create EntityManagerFactory: " + e.getMessage());
        }
    }

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void save(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenceException("Error saving entity: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void update(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenceException("Error updating entity: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void delete(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T managedEntity = em.merge(entity);
            em.remove(managedEntity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenceException("Error deleting entity: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public T findById(Object id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
