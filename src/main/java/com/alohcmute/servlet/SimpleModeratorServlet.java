package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/simple-moderator-queue")
public class SimpleModeratorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Simple Moderator Queue</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body>");
        out.println("<div class='container mt-4'>");
        out.println("<h2><i class='bi bi-shield-exclamation'></i> Simple Moderator Queue</h2>");
        
        try {
            // Test database connection
            out.println("<div class='alert alert-info'>Testing database connection...</div>");
            
            javax.persistence.EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("alohcmutePU");
            javax.persistence.EntityManager em = emf.createEntityManager();
            
            // Count reports
            Long reportCount = em.createQuery("SELECT COUNT(r) FROM Report r", Long.class).getSingleResult();
            Long pendingCount = em.createQuery("SELECT COUNT(r) FROM Report r WHERE r.status = 'PENDING'", Long.class).getSingleResult();
            
            out.println("<div class='alert alert-success'>");
            out.println("✅ Database connection successful!<br>");
            out.println("Total reports: " + reportCount + "<br>");
            out.println("Pending reports: " + pendingCount);
            out.println("</div>");
            
            // Simple list
            java.util.List<com.alohcmute.entity.Report> reports = em.createQuery(
                "SELECT r FROM Report r ORDER BY r.id DESC", com.alohcmute.entity.Report.class)
                .setMaxResults(10).getResultList();
                
            if (reports.isEmpty()) {
                out.println("<div class='alert alert-warning'>");
                out.println("⚠️ No reports found. <a href='" + req.getContextPath() + "/seed-reports'>Create sample reports</a>");
                out.println("</div>");
            } else {
                out.println("<h4>Latest Reports (Top 10)</h4>");
                out.println("<table class='table table-striped'>");
                out.println("<thead><tr><th>ID</th><th>Type</th><th>Reason</th><th>Status</th><th>Reporter</th></tr></thead><tbody>");
                
                for (com.alohcmute.entity.Report report : reports) {
                    out.println("<tr>");
                    out.println("<td>#" + report.getId() + "</td>");
                    out.println("<td>" + report.getContentType() + "</td>");
                    out.println("<td>" + report.getReason() + "</td>");
                    out.println("<td><span class='badge bg-" + 
                        ("PENDING".equals(report.getStatus()) ? "warning" : 
                         "RESOLVED".equals(report.getStatus()) ? "success" : "secondary") + "'>" + 
                        report.getStatus() + "</span></td>");
                    out.println("<td>" + report.getReporter().getUsername() + "</td>");
                    out.println("</tr>");
                }
                out.println("</tbody></table>");
            }
            
            em.close();
            emf.close();
            
        } catch (Exception e) {
            out.println("<div class='alert alert-danger'>");
            out.println("❌ Error: " + e.getMessage());
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
            out.println("</div>");
        }
        
        out.println("<div class='mt-4'>");
        out.println("<a href='" + req.getContextPath() + "/seed-reports' class='btn btn-primary'>Create Reports</a> ");
        out.println("<a href='" + req.getContextPath() + "/debug-data' class='btn btn-info'>Debug Data</a> ");
        out.println("<a href='" + req.getContextPath() + "/' class='btn btn-secondary'>Home</a>");
        out.println("</div>");
        
        out.println("</div></body></html>");
    }
}