package com.alohcmute.repo;

import com.alohcmute.entity.Comment;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CommentRepository implements com.alohcmute.dao.CommentDao {

    public Comment save(Comment c) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (c.getId() == null) em.persist(c); else c = em.merge(c);
            em.getTransaction().commit();
            return c;
        } finally { em.close(); }
    }

    public List<Comment> findByPostId(Long postId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Comment> q = em.createQuery("SELECT c FROM Comment c JOIN FETCH c.author WHERE c.post.id = :pid ORDER BY c.createdAt ASC", Comment.class);
            q.setParameter("pid", postId);
            return q.getResultList();
        } finally { em.close(); }
    }

    public void deleteByPostId(Long postId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Comment c WHERE c.post.id = :pid").setParameter("pid", postId).executeUpdate();
            em.getTransaction().commit();
        } finally { em.close(); }
    }
}
