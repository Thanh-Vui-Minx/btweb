package com.alohcmute.repo;

import com.alohcmute.entity.VideoReaction;
import com.alohcmute.entity.Video;
import com.alohcmute.entity.User;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class VideoReactionRepository {

    public VideoReaction save(VideoReaction reaction) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (reaction.getId() == null) {
                em.persist(reaction);
            } else {
                reaction = em.merge(reaction);
            }
            em.getTransaction().commit();
            return reaction;
        } finally {
            em.close();
        }
    }

    public VideoReaction findByVideoAndUser(Long videoId, Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<VideoReaction> query = em.createQuery(
                "SELECT vr FROM VideoReaction vr WHERE vr.video.id = :videoId AND vr.user.id = :userId", 
                VideoReaction.class
            );
            query.setParameter("videoId", videoId);
            query.setParameter("userId", userId);
            List<VideoReaction> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public Long countByVideoId(Long videoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(vr) FROM VideoReaction vr WHERE vr.video.id = :videoId", 
                Long.class
            );
            query.setParameter("videoId", videoId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public void delete(VideoReaction reaction) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            VideoReaction managed = em.merge(reaction);
            em.remove(managed);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<VideoReaction> findByVideoId(Long videoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<VideoReaction> query = em.createQuery(
                "SELECT vr FROM VideoReaction vr JOIN FETCH vr.user WHERE vr.video.id = :videoId ORDER BY vr.createdAt DESC", 
                VideoReaction.class
            );
            query.setParameter("videoId", videoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
