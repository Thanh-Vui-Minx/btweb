package com.alohcmute.repo;

import com.alohcmute.entity.User;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserRepository extends BaseRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    public User findByEmail(String email) {
        return findSingleByQuery("SELECT u FROM User u WHERE u.email = ?1", email);
    }

    public User findByUsername(String username) {
        return findSingleByQuery("SELECT u FROM User u WHERE u.username = ?1", username);
    }

    public List<User> listAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> q = em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public int countFollowers(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("SELECT COUNT(f) FROM Follow f WHERE f.followedUserId = ?1", Long.class);
            q.setParameter(1, userId);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            return 0; // Return 0 if Follow table doesn't exist yet
        } finally {
            em.close();
        }
    }
    
    public int countFollowing(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("SELECT COUNT(f) FROM Follow f WHERE f.followerUserId = ?1", Long.class);
            q.setParameter(1, userId);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            return 0; // Return 0 if Follow table doesn't exist yet
        } finally {
            em.close();
        }
    }
}
