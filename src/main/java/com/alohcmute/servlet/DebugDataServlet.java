package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.alohcmute.entity.User;
import com.alohcmute.entity.Report;
import com.alohcmute.entity.Post;
import java.io.IOException;
import java.util.List;

@WebServlet("/debug-data")
public class DebugDataServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("alohcmutePU");
            em = emf.createEntityManager();
            
            // Lấy stats
            List<User> users = em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class).getResultList();
            List<Post> posts = em.createQuery("SELECT p FROM Post p ORDER BY p.id DESC", Post.class).setMaxResults(5).getResultList();
            List<Report> reports = em.createQuery("SELECT r FROM Report r ORDER BY r.id DESC", Report.class).getResultList();
            
            Long userCount = em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
            Long postCount = em.createQuery("SELECT COUNT(p) FROM Post p", Long.class).getSingleResult();
            Long reportCount = em.createQuery("SELECT COUNT(r) FROM Report r", Long.class).getSingleResult();
            Long pendingReports = em.createQuery("SELECT COUNT(r) FROM Report r WHERE r.status = 'PENDING'", Long.class).getSingleResult();
            
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<!DOCTYPE html>");
            resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Debug Data</title>");
            resp.getWriter().println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
            resp.getWriter().println("</head><body>");
            resp.getWriter().println("<div class='container mt-4'>");
            resp.getWriter().println("<h2>📊 Database Debug Info</h2>");
            
            // Stats tổng quan
            resp.getWriter().println("<div class='row mb-4'>");
            resp.getWriter().println("<div class='col-md-3'>");
            resp.getWriter().println("<div class='card text-center'>");
            resp.getWriter().println("<div class='card-body'>");
            resp.getWriter().println("<h3 class='text-primary'>" + userCount + "</h3>");
            resp.getWriter().println("<p>👥 Users</p>");
            resp.getWriter().println("</div></div></div>");
            
            resp.getWriter().println("<div class='col-md-3'>");
            resp.getWriter().println("<div class='card text-center'>");
            resp.getWriter().println("<div class='card-body'>");
            resp.getWriter().println("<h3 class='text-success'>" + postCount + "</h3>");
            resp.getWriter().println("<p>📝 Posts</p>");
            resp.getWriter().println("</div></div></div>");
            
            resp.getWriter().println("<div class='col-md-3'>");
            resp.getWriter().println("<div class='card text-center'>");
            resp.getWriter().println("<div class='card-body'>");
            resp.getWriter().println("<h3 class='text-warning'>" + reportCount + "</h3>");
            resp.getWriter().println("<p>🚨 Reports</p>");
            resp.getWriter().println("</div></div></div>");
            
            resp.getWriter().println("<div class='col-md-3'>");
            resp.getWriter().println("<div class='card text-center'>");
            resp.getWriter().println("<div class='card-body'>");
            resp.getWriter().println("<h3 class='text-danger'>" + pendingReports + "</h3>");
            resp.getWriter().println("<p>⏳ Pending</p>");
            resp.getWriter().println("</div></div></div>");
            resp.getWriter().println("</div>");
            
            // Users table
            resp.getWriter().println("<div class='card mb-4'>");
            resp.getWriter().println("<div class='card-header'><h5>👥 Users</h5></div>");
            resp.getWriter().println("<div class='card-body'>");
            resp.getWriter().println("<table class='table table-sm'>");
            resp.getWriter().println("<thead><tr><th>ID</th><th>Username</th><th>Email</th><th>Role</th><th>Active</th><th>Quick Login</th></tr></thead><tbody>");
            
            for (User user : users) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td>" + user.getId() + "</td>");
                resp.getWriter().println("<td>" + user.getUsername() + "</td>");
                resp.getWriter().println("<td>" + user.getEmail() + "</td>");
                resp.getWriter().println("<td><span class='badge bg-" + 
                    ("ADMIN".equals(user.getRole()) ? "danger" : 
                     "MODERATOR".equals(user.getRole()) ? "warning" : "primary") + "'>" 
                    + user.getRole() + "</span></td>");
                resp.getWriter().println("<td>" + (user.isActive() ? "✅" : "❌") + "</td>");
                resp.getWriter().println("<td>");
                resp.getWriter().println("<form method='post' action='" + req.getContextPath() + "/debug-login' style='display:inline'>");
                resp.getWriter().println("<input type='hidden' name='userId' value='" + user.getId() + "'>");
                resp.getWriter().println("<button type='submit' class='btn btn-sm btn-outline-primary'>Login as " + user.getUsername() + "</button>");
                resp.getWriter().println("</form>");
                resp.getWriter().println("</td>");
                resp.getWriter().println("</tr>");
            }
            resp.getWriter().println("</tbody></table>");
            resp.getWriter().println("</div></div>");
            
            // Recent posts
            if (!posts.isEmpty()) {
                resp.getWriter().println("<div class='card mb-4'>");
                resp.getWriter().println("<div class='card-header'><h5>📝 Recent Posts (Top 5)</h5></div>");
                resp.getWriter().println("<div class='card-body'>");
                for (Post post : posts) {
                    resp.getWriter().println("<div class='border-bottom pb-2 mb-2'>");
                    resp.getWriter().println("<strong>#" + post.getId() + "</strong> by " + post.getAuthor().getUsername() + "<br>");
                    resp.getWriter().println("<small class='text-muted'>" + post.getCreatedAt() + "</small><br>");
                    String content = post.getContent();
                    if (content.length() > 100) content = content.substring(0, 100) + "...";
                    resp.getWriter().println("<p>" + content + "</p>");
                    resp.getWriter().println("</div>");
                }
                resp.getWriter().println("</div></div>");
            }
            
            // Reports summary
            if (!reports.isEmpty()) {
                resp.getWriter().println("<div class='card mb-4'>");
                resp.getWriter().println("<div class='card-header'><h5>🚨 Reports Summary</h5></div>");
                resp.getWriter().println("<div class='card-body'>");
                resp.getWriter().println("<table class='table table-sm'>");
                resp.getWriter().println("<thead><tr><th>ID</th><th>Type</th><th>Reason</th><th>Status</th><th>Reporter</th><th>Created</th></tr></thead><tbody>");
                for (Report report : reports) {
                    resp.getWriter().println("<tr>");
                    resp.getWriter().println("<td>#" + report.getId() + "</td>");
                    resp.getWriter().println("<td>" + report.getContentType() + "</td>");
                    resp.getWriter().println("<td>" + report.getReason() + "</td>");
                    String badgeClass = "PENDING".equals(report.getStatus()) ? "warning" : 
                                       "RESOLVED".equals(report.getStatus()) ? "success" : "secondary";
                    resp.getWriter().println("<td><span class='badge bg-" + badgeClass + "'>" + report.getStatus() + "</span></td>");
                    resp.getWriter().println("<td>" + report.getReporter().getUsername() + "</td>");
                    resp.getWriter().println("<td>" + report.getCreatedAt().toLocalDate() + "</td>");
                    resp.getWriter().println("</tr>");
                }
                resp.getWriter().println("</tbody></table>");
                resp.getWriter().println("</div></div>");
            }
            
            // Quick links
            resp.getWriter().println("<div class='card'>");
            resp.getWriter().println("<div class='card-header'><h5>🔗 Quick Links</h5></div>");
            resp.getWriter().println("<div class='card-body'>");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/user/login' class='btn btn-primary me-2'>🔑 Login Page</a>");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/moderator/queue' class='btn btn-warning me-2'>🛡️ Moderator Queue</a>");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/user/home' class='btn btn-success me-2'>🏠 Home Page</a>");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/admin/dashboard' class='btn btn-danger'>👑 Admin Panel</a>");
            resp.getWriter().println("</div></div>");
            
            resp.getWriter().println("</div></body></html>");
            
        } catch (Exception e) {
            resp.getWriter().println("<h3>❌ Lỗi: " + e.getMessage() + "</h3>");
            e.printStackTrace(resp.getWriter());
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String userIdStr = req.getParameter("userId");
        if (userIdStr != null) {
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("alohcmutePU");
                EntityManager em = emf.createEntityManager();
                
                Long userId = Long.parseLong(userIdStr);
                User user = em.find(User.class, userId);
                
                if (user != null) {
                    req.getSession().setAttribute("user", user);
                    resp.sendRedirect(req.getContextPath() + "/user/home");
                    return;
                }
                
                em.close();
                emf.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        resp.sendRedirect(req.getContextPath() + "/debug-data");
    }
}