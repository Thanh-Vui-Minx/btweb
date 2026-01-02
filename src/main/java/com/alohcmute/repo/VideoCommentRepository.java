package com.alohcmute.repo;

import com.alohcmute.entity.VideoComment;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class VideoCommentRepository {

    public VideoComment save(VideoComment comment) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (comment.getId() == null) {
                em.persist(comment);
            } else {
                comment = em.merge(comment);
            }
            em.getTransaction().commit();
            return comment;
        } finally {
            em.close();
        }
    }

    public List<VideoComment> findByVideoId(Long videoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<VideoComment> query = em.createQuery(
                "SELECT vc FROM VideoComment vc JOIN FETCH vc.author WHERE vc.video.id = :videoId ORDER BY vc.createdAt ASC", 
                VideoComment.class
            );
            query.setParameter("videoId", videoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Long countByVideoId(Long videoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(vc) FROM VideoComment vc WHERE vc.video.id = :videoId", 
                Long.class
            );
            query.setParameter("videoId", videoId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public void deleteByVideoId(Long videoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM VideoComment vc WHERE vc.video.id = :videoId")
                .setParameter("videoId", videoId)
                .executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public VideoComment findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(VideoComment.class, id);
        } finally {
            em.close();
        }
    }
}
