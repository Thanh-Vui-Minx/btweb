package com.alohcmute.repo;

import com.alohcmute.entity.Reaction;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ReactionRepository implements com.alohcmute.dao.ReactionDao {

    public Reaction save(Reaction r) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (r.getId() == null) em.persist(r); else r = em.merge(r);
            em.getTransaction().commit();
            return r;
        } finally { em.close(); }
    }

    public List<Reaction> findByPostId(Long postId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reaction> q = em.createQuery("SELECT r FROM Reaction r WHERE r.post.id = :pid", Reaction.class);
            q.setParameter("pid", postId);
            return q.getResultList();
        } finally { em.close(); }
    }

    public Map<String, Long> countByEmojiForPost(Long postId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Object[]> rows = em.createQuery("SELECT r.emoji, COUNT(r) FROM Reaction r WHERE r.post.id=:pid GROUP BY r.emoji", Object[].class)
                    .setParameter("pid", postId)
                    .getResultList();
            Map<String, Long> map = new HashMap<>();
            for (Object[] row : rows) {
                map.put((String) row[0], (Long) row[1]);
            }
            return map;
        } finally { em.close(); }
    }

    public void deleteByPostId(Long postId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Reaction r WHERE r.post.id = :pid").setParameter("pid", postId).executeUpdate();
            em.getTransaction().commit();
        } finally { em.close(); }
    }
    
    public Reaction findByPostAndUser(Long postId, String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reaction> q = em.createQuery("SELECT r FROM Reaction r WHERE r.post.id = :pid AND r.username = :username", Reaction.class);
            q.setParameter("pid", postId);
            q.setParameter("username", username);
            List<Reaction> results = q.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally { em.close(); }
    }
    
    public void delete(Reaction reaction) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (reaction.getId() != null) {
                Reaction managed = em.find(Reaction.class, reaction.getId());
                if (managed != null) {
                    em.remove(managed);
                }
            }
            em.getTransaction().commit();
        } finally { em.close(); }
    }
}
