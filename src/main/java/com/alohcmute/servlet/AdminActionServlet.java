package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.repo.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/action"})
public class AdminActionServlet extends HttpServlet {
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check authentication
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }
        
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Admin role required.");
            return;
        }
        
        String action = req.getParameter("action");
        String userIdStr = req.getParameter("userId");
        if (action == null || userIdStr == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/users?error=missing");
            return;
        }
        Long userId;
        try { userId = Long.parseLong(userIdStr); } catch (NumberFormatException ex) {
            resp.sendRedirect(req.getContextPath() + "/admin/users?error=invalidId");
            return;
        }
        User u = userRepo.findById(userId);
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/users?error=notfound");
            return;
        }
        switch (action) {
            case "ban":
                u.setActive(false);
                userRepo.save(u);
                break;
            case "unban":
                u.setActive(true);
                userRepo.save(u);
                break;
            case "promote":
                u.setRole("ADMIN");
                userRepo.save(u);
                break;
            case "demote":
                u.setRole("USER");
                userRepo.save(u);
                break;
            case "changeRole":
                String newRole = req.getParameter("newRole");
                if (newRole != null && (newRole.equals("USER") || newRole.equals("MODERATOR") || newRole.equals("ADMIN"))) {
                    u.setRole(newRole);
                    userRepo.save(u);
                }
                break;
            case "changePassword":
                String newPassword = req.getParameter("newPassword");
                if (newPassword != null && !newPassword.trim().isEmpty() && newPassword.trim().length() >= 6) {
                    try {
                        String hashedPassword = BCrypt.hashpw(newPassword.trim(), BCrypt.gensalt());
                        u.setPasswordHash(hashedPassword);
                        userRepo.save(u);
                        resp.sendRedirect(req.getContextPath() + "/admin/users?success=passwordChanged");
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        resp.sendRedirect(req.getContextPath() + "/admin/users?error=passwordFailed");
                        return;
                    }
                } else {
                    resp.sendRedirect(req.getContextPath() + "/admin/users?error=passwordTooShort");
                    return;
                }
            default:
                // unknown action
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
