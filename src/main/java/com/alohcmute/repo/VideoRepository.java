package com.alohcmute.repo;

import com.alohcmute.entity.Video;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class VideoRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("alohcmutePU");

    public void save(Video video) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (video.getId() == null) {
                em.persist(video);
            } else {
                em.merge(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Video findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Video.class, id);
        } finally {
            em.close();
        }
    }

    public List<Video> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery(
                "SELECT v FROM Video v WHERE v.isPublic = true ORDER BY v.createdAt DESC", Video.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Video> findRecent(int limit) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery(
                "SELECT v FROM Video v WHERE v.isPublic = true ORDER BY v.createdAt DESC", Video.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Video> findByAuthor(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery(
                "SELECT v FROM Video v WHERE v.author.id = :userId ORDER BY v.createdAt DESC", Video.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void incrementViews(Long videoId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.find(Video.class, videoId);
            if (video != null) {
                video.setViews(video.getViews() + 1);
                em.merge(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.find(Video.class, id);
            if (video != null) {
                em.remove(video);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
