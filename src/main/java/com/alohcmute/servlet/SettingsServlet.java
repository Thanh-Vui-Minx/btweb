package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.repo.UserRepository;
import com.alohcmute.util.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user/settings", "/user/settings/update", "/user/settings/password", "/user/settings/notifications"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024)
public class SettingsServlet extends HttpServlet {
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        req.getRequestDispatcher("/user/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String path = req.getServletPath();
        
        if (path.endsWith("/update")) {
            handleProfileUpdate(req, resp, currentUser);
        } else if (path.endsWith("/password")) {
            handlePasswordChange(req, resp, currentUser);
        } else if (path.endsWith("/notifications")) {
            handleNotificationSettings(req, resp, currentUser);
        }
    }

    private void handleProfileUpdate(HttpServletRequest req, HttpServletResponse resp, User currentUser) throws ServletException, IOException {
        // Set character encoding to handle UTF-8 properly
        req.setCharacterEncoding("UTF-8");
        
        String displayName = req.getParameter("displayName");
        String email = req.getParameter("email");
        String bio = req.getParameter("bio");

        if (displayName != null && !displayName.trim().isEmpty()) {
            currentUser.setDisplayName(displayName.trim());
        }
        if (email != null && !email.trim().isEmpty()) {
            currentUser.setEmail(email.trim());
        }
        if (bio != null) {
            currentUser.setBio(bio.trim());
        }

        // Handle avatar upload
        Part avatarPart = req.getPart("avatar");
        if (avatarPart != null && avatarPart.getSize() > 0) {
            try {
                String avatarUrl = FileUploadUtil.saveUserFile(avatarPart, FileUploadUtil.AVATAR_DIR, currentUser.getId());
                if (avatarUrl != null) {
                    currentUser.setAvatarUrl(req.getContextPath() + avatarUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        userRepo.save(currentUser);
        req.getSession().setAttribute("user", currentUser);
        
        resp.sendRedirect(req.getContextPath() + "/user/settings?success=true");
    }

    private void handlePasswordChange(HttpServletRequest req, HttpServletResponse resp, User currentUser) throws ServletException, IOException {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (currentPassword == null || newPassword == null || confirmPassword == null) {
            req.setAttribute("error", "Vui lòng điền đầy đủ thông tin");
            req.getRequestDispatcher("/user/settings.jsp").forward(req, resp);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp");
            req.getRequestDispatcher("/user/settings.jsp").forward(req, resp);
            return;
        }

        // Verify current password (simplified - in production use proper password hashing)
        if (!currentPassword.equals(currentUser.getPasswordHash())) {
            req.setAttribute("error", "Mật khẩu hiện tại không đúng");
            req.getRequestDispatcher("/user/settings.jsp").forward(req, resp);
            return;
        }

        currentUser.setPasswordHash(newPassword);
        userRepo.save(currentUser);
        
        resp.sendRedirect(req.getContextPath() + "/user/settings?success=true");
    }

    private void handleNotificationSettings(HttpServletRequest req, HttpServletResponse resp, User currentUser) throws ServletException, IOException {
        // Store notification preferences (would need to add these fields to User entity in production)
        // For now, just redirect back with success
        resp.sendRedirect(req.getContextPath() + "/user/settings?success=true");
    }
}
