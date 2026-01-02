package com.alohcmute.repo;

import com.alohcmute.entity.DirectMessage;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DirectMessageRepository {

    public DirectMessage save(DirectMessage m) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (m.getId() == null) em.persist(m); else m = em.merge(m);
            em.getTransaction().commit();
            return m;
        } finally { em.close(); }
    }

    public List<DirectMessage> findConversation(Long aId, Long bId, int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<DirectMessage> q = em.createQuery(
                    "SELECT d FROM DirectMessage d JOIN FETCH d.sender JOIN FETCH d.recipient WHERE (d.sender.id = :a AND d.recipient.id = :b) OR (d.sender.id = :b AND d.recipient.id = :a) ORDER BY d.createdAt DESC",
                    DirectMessage.class);
            q.setParameter("a", aId);
            q.setParameter("b", bId);
            q.setMaxResults(limit);
            return q.getResultList();
        } finally { em.close(); }
    }
}
