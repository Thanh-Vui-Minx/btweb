package com.alohcmute.repo;

import com.alohcmute.entity.Report;
import com.alohcmute.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReportRepository {
    
    public Report save(Report report) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (report.getId() == null) {
                em.persist(report);
            } else {
                report = em.merge(report);
            }
            em.getTransaction().commit();
            return report;
        } finally {
            em.close();
        }
    }

    public Report findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Report> query = em.createQuery(
                "SELECT r FROM Report r " +
                "LEFT JOIN FETCH r.reporter " +
                "LEFT JOIN FETCH r.reportedUser " +
                "WHERE r.id = :id",
                Report.class
            );
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Report> findPendingReports() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Report> query = em.createQuery(
                "SELECT r FROM Report r " +
                "LEFT JOIN FETCH r.reporter " +
                "LEFT JOIN FETCH r.reportedUser " +
                "WHERE r.status = 'PENDING' ORDER BY r.createdAt ASC",
                Report.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Report> findAllReports() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Report> query = em.createQuery(
                "SELECT r FROM Report r " +
                "LEFT JOIN FETCH r.reporter " +
                "LEFT JOIN FETCH r.reportedUser " +
                "ORDER BY r.createdAt DESC",
                Report.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Report> findByStatus(String status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Report> query = em.createQuery(
                "SELECT r FROM Report r " +
                "LEFT JOIN FETCH r.reporter " +
                "LEFT JOIN FETCH r.reportedUser " +
                "WHERE r.status = :status ORDER BY r.createdAt DESC",
                Report.class
            );
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long countPendingReports() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Report r WHERE r.status = 'PENDING'",
                Long.class
            );
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
