package com.alohcmute.repo;

import com.alohcmute.entity.Notification;
import com.alohcmute.entity.User;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class NotificationRepository extends BaseRepository<Notification> {

    public NotificationRepository() {
        super(Notification.class);
    }

    public List<Notification> findByUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Notification> query = em.createQuery(
                "SELECT n FROM Notification n LEFT JOIN FETCH n.fromUser WHERE n.user = :user ORDER BY n.createdAt DESC", 
                Notification.class
            );
            query.setParameter("user", user);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Notification> findByUserWithLimit(User user, int maxResults) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Notification> query = em.createQuery(
                "SELECT n FROM Notification n LEFT JOIN FETCH n.fromUser WHERE n.user = :user ORDER BY n.createdAt DESC", 
                Notification.class
            );
            query.setParameter("user", user);
            query.setMaxResults(maxResults);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long countUnreadByUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(n) FROM Notification n WHERE n.user = :user AND n.read = false", 
                Long.class
            );
            query.setParameter("user", user);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public void markAllAsRead(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE Notification n SET n.read = true WHERE n.user = :user AND n.read = false")
              .setParameter("user", user)
              .executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void markAsRead(Long notificationId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE Notification n SET n.read = true WHERE n.id = :id")
              .setParameter("id", notificationId)
              .executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
