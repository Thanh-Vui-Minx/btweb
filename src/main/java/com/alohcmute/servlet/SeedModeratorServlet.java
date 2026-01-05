package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.repo.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class SeedModeratorServlet extends HttpServlet {

    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Seed Moderators</title></head><body>");
        out.println("<h2>Creating Moderator Accounts...</h2>");
        
        try {
            // Moderator 1
            User mod1 = userRepo.findByUsername("moderator1");
            if (mod1 == null) {
                mod1 = new User();
                mod1.setUsername("moderator1");
                mod1.setEmail("mod1@alohcmute.edu.vn");
                mod1.setPasswordHash(BCrypt.hashpw("password123", BCrypt.gensalt()));
                mod1.setDisplayName("Moderator One");
                mod1.setRole("MODERATOR");
                mod1.setActive(true);
                mod1.setCreatedAt(LocalDateTime.now());
                userRepo.save(mod1);
                out.println("<p style='color: green;'>✓ Created moderator1 (password: password123)</p>");
            } else {
                out.println("<p style='color: orange;'>→ moderator1 already exists</p>");
            }
            
            // Moderator 2
            User mod2 = userRepo.findByUsername("moderator2");
            if (mod2 == null) {
                mod2 = new User();
                mod2.setUsername("moderator2");
                mod2.setEmail("mod2@alohcmute.edu.vn");
                mod2.setPasswordHash(BCrypt.hashpw("password123", BCrypt.gensalt()));
                mod2.setDisplayName("Moderator Two");
                mod2.setRole("MODERATOR");
                mod2.setActive(true);
                mod2.setCreatedAt(LocalDateTime.now());
                userRepo.save(mod2);
                out.println("<p style='color: green;'>✓ Created moderator2 (password: password123)</p>");
            } else {
                out.println("<p style='color: orange;'>→ moderator2 already exists</p>");
            }
            
            // Moderator 3 (tiếng Việt)
            User mod3 = userRepo.findByUsername("quantrivien");
            if (mod3 == null) {
                mod3 = new User();
                mod3.setUsername("quantrivien");
                mod3.setEmail("quantri@alohcmute.edu.vn");
                mod3.setPasswordHash(BCrypt.hashpw("admin123", BCrypt.gensalt()));
                mod3.setDisplayName("Quản Trị Viên");
                mod3.setRole("MODERATOR");
                mod3.setActive(true);
                mod3.setCreatedAt(LocalDateTime.now());
                userRepo.save(mod3);
                out.println("<p style='color: green;'>✓ Created quantrivien (password: admin123)</p>");
            } else {
                out.println("<p style='color: orange;'>→ quantrivien already exists</p>");
            }
            
            out.println("<hr>");
            out.println("<h3>Moderator Accounts:</h3>");
            out.println("<ul>");
            out.println("<li><strong>moderator1</strong> / password123</li>");
            out.println("<li><strong>moderator2</strong> / password123</li>");
            out.println("<li><strong>quantrivien</strong> / admin123</li>");
            out.println("</ul>");
            out.println("<p><a href='" + req.getContextPath() + "/admin/dashboard'>← Back to Admin Dashboard</a></p>");
            
        } catch (Exception e) {
            out.println("<p style='color: red;'>✗ Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
        
        out.println("</body></html>");
    }
}
