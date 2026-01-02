package com.alohcmute.repo;

import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Base repository with common CRUD operations to reduce code duplication
 */
public abstract class BaseRepository<T> {
    
    private final Class<T> entityClass;
    
    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public T save(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T result = em.merge(entity);
            em.getTransaction().commit();
            return result;
        } finally {
            em.close();
        }
    }
    
    public T findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }
    
    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, entityClass).getResultList();
        } finally {
            em.close();
        }
    }
    
    public void deleteById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    protected T findSingleByQuery(String jpql, Object... params) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<T> q = em.createQuery(jpql, entityClass);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }
    
    protected List<T> findListByQuery(String jpql, Object... params) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<T> q = em.createQuery(jpql, entityClass);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    protected long countByQuery(String jpql, Object... params) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(jpql, Long.class);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            Long result = q.getSingleResult();
            return result == null ? 0L : result;
        } finally {
            em.close();
        }
    }
}
