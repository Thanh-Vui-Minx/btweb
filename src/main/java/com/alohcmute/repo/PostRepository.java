package com.alohcmute.repo;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PostRepository extends BaseRepository<Post> {

    public PostRepository() {
        super(Post.class);
    }
    
    @Override
    public Post findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Post> query = em.createQuery(
                "SELECT p FROM Post p JOIN FETCH p.author WHERE p.id = :id", 
                Post.class
            );
            query.setParameter("id", id);
            List<Post> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public List<Post> findRecent(int max) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // fetch join author to avoid LazyInitializationException when rendering in JSP
            TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p JOIN FETCH p.author ORDER BY p.createdAt DESC", Post.class);
            q.setMaxResults(max);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public long countAll() {
        return countByQuery("SELECT COUNT(p) FROM Post p");
    }
    
    public int countByUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("SELECT COUNT(p) FROM Post p WHERE p.author = ?1", Long.class);
            q.setParameter(1, user);
            return q.getSingleResult().intValue();
        } finally {
            em.close();
        }
    }
    
    public int countLikesByUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("SELECT COUNT(r) FROM Reaction r WHERE r.post.author = ?1 AND r.type = 'LIKE'", Long.class);
            q.setParameter(1, user);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            return 0; // Return 0 if Reaction table doesn't exist yet
        } finally {
            em.close();
        }
    }

    public void deleteById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // remove reactions and comments first
            em.createQuery("DELETE FROM com.alohcmute.entity.Reaction r WHERE r.post.id = :pid").setParameter("pid", id).executeUpdate();
            em.createQuery("DELETE FROM com.alohcmute.entity.Comment c WHERE c.post.id = :pid").setParameter("pid", id).executeUpdate();
            // then delete the post
            em.createQuery("DELETE FROM Post p WHERE p.id = :pid").setParameter("pid", id).executeUpdate();
            em.getTransaction().commit();
        } finally { em.close(); }
    }
}
