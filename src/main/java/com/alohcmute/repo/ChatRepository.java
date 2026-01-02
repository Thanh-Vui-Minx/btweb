package com.alohcmute.repo;

import com.alohcmute.entity.ChatMessage;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ChatRepository implements com.alohcmute.dao.ChatDao {

    public ChatMessage save(ChatMessage m) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (m.getId() == null) em.persist(m); else m = em.merge(m);
            em.getTransaction().commit();
            return m;
        } finally { em.close(); }
    }

    public List<ChatMessage> findRecent(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // fetch author to avoid LazyInitializationException in JSP
            TypedQuery<ChatMessage> q = em.createQuery("SELECT c FROM ChatMessage c JOIN FETCH c.author ORDER BY c.createdAt DESC", ChatMessage.class);
            q.setMaxResults(limit);
            return q.getResultList();
        } finally { em.close(); }
    }
}
