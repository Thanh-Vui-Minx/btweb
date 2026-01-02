package com.alohcmute.repo;

import com.alohcmute.entity.Follow;
import com.alohcmute.entity.User;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Repository for Follow entity operations
 */
public class FollowRepository extends BaseRepository<Follow> {
    
    public FollowRepository() {
        super(Follow.class);
    }
    
    /**
     * Check if follower is following the followed user
     */
    public boolean isFollowing(Long followerId, Long followedId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Follow f WHERE f.follower.id = :followerId AND f.followed.id = :followedId";
            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("followerId", followerId)
                    .setParameter("followedId", followedId)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
    
    /**
     * Create a follow relationship
     */
    public Follow follow(User follower, User followed) {
        // Check if already following
        if (isFollowing(follower.getId(), followed.getId())) {
            return null; // Already following
        }
        
        Follow follow = new Follow(follower, followed);
        return save(follow);
    }
    
    /**
     * Remove a follow relationship
     */
    public boolean unfollow(Long followerId, Long followedId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            String jpql = "DELETE FROM Follow f WHERE f.follower.id = :followerId AND f.followed.id = :followedId";
            int deleted = em.createQuery(jpql)
                    .setParameter("followerId", followerId)
                    .setParameter("followedId", followedId)
                    .executeUpdate();
            em.getTransaction().commit();
            return deleted > 0;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    /**
     * Get list of users that the given user is following
     */
    public List<User> getFollowing(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT f.followed FROM Follow f WHERE f.follower.id = :userId ORDER BY f.createdAt DESC";
            return em.createQuery(jpql, User.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get list of users following the given user
     */
    public List<User> getFollowers(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT f.follower FROM Follow f WHERE f.followed.id = :userId ORDER BY f.createdAt DESC";
            return em.createQuery(jpql, User.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get count of users the given user is following
     */
    public long getFollowingCount(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Follow f WHERE f.follower.id = :userId";
            return em.createQuery(jpql, Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get count of followers for the given user
     */
    public long getFollowersCount(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(f) FROM Follow f WHERE f.followed.id = :userId";
            return em.createQuery(jpql, Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
