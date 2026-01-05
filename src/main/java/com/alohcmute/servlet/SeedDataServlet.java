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
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/admin/seed")
public class SeedDataServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Kiểm tra quyền admin
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Admin role required.");
            return;
        }
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("alohcmutePU");
            em = emf.createEntityManager();
            
            // Kiểm tra xem đã có admin user chưa
            List<User> existingAdmins = em.createQuery(
                "SELECT u FROM User u WHERE u.role = 'ADMIN'", User.class)
                .getResultList();
                
            if (!existingAdmins.isEmpty()) {
                resp.getWriter().println("<h3>Đã tồn tại tài khoản admin, không cần seed dữ liệu</h3>");
                resp.getWriter().println("<p><a href='" + req.getContextPath() + "/admin/dashboard'>Quay về Dashboard</a></p>");
                return;
            }
            
            em.getTransaction().begin();
            
            // Tạo Admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@alohcmute.edu");
            admin.setPasswordHash(BCrypt.hashpw("admin123", BCrypt.gensalt()));
            admin.setDisplayName("Administrator");
            admin.setRole("ADMIN");
            admin.setActive(true);
            admin.setCreatedAt(LocalDateTime.now());
            em.persist(admin);
            
            // Tạo Moderator user
            User moderator = new User();
            moderator.setUsername("moderator");
            moderator.setEmail("moderator@alohcmute.edu");
            moderator.setPasswordHash(BCrypt.hashpw("mod123", BCrypt.gensalt()));
            moderator.setDisplayName("Moderator User");
            moderator.setRole("MODERATOR");
            moderator.setActive(true);
            moderator.setCreatedAt(LocalDateTime.now());
            em.persist(moderator);
            
            // Tạo User thường
            for (int i = 1; i <= 3; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setEmail("user" + i + "@alohcmute.edu");
                user.setPasswordHash(BCrypt.hashpw("user123", BCrypt.gensalt()));
                user.setDisplayName("Demo User " + i);
                user.setRole("USER");
                user.setActive(true);
                user.setCreatedAt(LocalDateTime.now());
                em.persist(user);
            }
            
            em.getTransaction().commit();
            
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<!DOCTYPE html>");
            resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Seed Data Complete</title></head><body>");
            resp.getWriter().println("<h2>✅ Tạo dữ liệu mẫu thành công!</h2>");
            resp.getWriter().println("<p>Đã tạo các tài khoản sau:</p>");
            resp.getWriter().println("<ul>");
            resp.getWriter().println("<li><strong>admin</strong> / admin123 (ADMIN)</li>");
            resp.getWriter().println("<li><strong>moderator</strong> / mod123 (MODERATOR)</li>");
            resp.getWriter().println("<li><strong>user1</strong> / user123 (USER)</li>");
            resp.getWriter().println("<li><strong>user2</strong> / user123 (USER)</li>");
            resp.getWriter().println("<li><strong>user3</strong> / user123 (USER)</li>");
            resp.getWriter().println("</ul>");
            resp.getWriter().println("<p><a href='" + req.getContextPath() + "/user/login'>Đăng nhập</a> | ");
            resp.getWriter().println("<a href='" + req.getContextPath() + "/admin/dashboard'>Admin Dashboard</a></p>");
            resp.getWriter().println("</body></html>");
            
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            resp.getWriter().println("<h3>❌ Lỗi khi tạo dữ liệu: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}