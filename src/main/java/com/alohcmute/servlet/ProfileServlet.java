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

@WebServlet(urlPatterns = {"/user/profile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024)
public class ProfileServlet extends HttpServlet {
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        User profileUser = null;
        if (idParam != null) {
            try { profileUser = userRepo.findById(Long.parseLong(idParam)); } catch (Exception ignored) {}
        }
        if (profileUser == null) {
            // default to current session user
            profileUser = (User) req.getSession().getAttribute("user");
        }
        req.setAttribute("profileUser", profileUser);
        // prepare links list to avoid EL string-escaping issues in JSP
        if (profileUser != null && profileUser.getLinks() != null) {
            String raw = profileUser.getLinks();
            String[] lines = raw.split("\\r?\\n");
            java.util.List<java.util.Map<String,String>> parsed = new java.util.ArrayList<>();
            for (String line : lines) {
                if (line == null) continue;
                String l = line.trim();
                if (l.isEmpty()) continue;
                java.util.Map<String,String> m = new java.util.HashMap<>();
                int idx = l.indexOf('|');
                if (idx > 0) {
                    m.put("label", l.substring(0, idx));
                    m.put("url", l.substring(idx+1));
                } else {
                    m.put("label", "Link");
                    m.put("url", l);
                }
                parsed.add(m);
            }
            req.setAttribute("linksList", parsed);
        }
        req.getRequestDispatcher("/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set character encoding to handle UTF-8 properly
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        // update profile (must be logged in)
        User current = (User) req.getSession().getAttribute("user");
        if (current == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        String displayName = req.getParameter("displayName");
        String bio = req.getParameter("bio");
        if (displayName != null) current.setDisplayName(displayName);
        if (bio != null) current.setBio(bio);

        Part avatarPart = req.getPart("avatar");
        if (avatarPart != null && avatarPart.getSize() > 0) {
            try {
                String avatarUrl = FileUploadUtil.saveUserFile(avatarPart, FileUploadUtil.AVATAR_DIR, current.getId());
                if (avatarUrl != null) {
                    current.setAvatarUrl(req.getContextPath() + avatarUrl);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        // cover photo upload
        Part coverPart = req.getPart("cover");
        if (coverPart != null && coverPart.getSize() > 0) {
            try {
                String coverUrl = FileUploadUtil.saveUserFile(coverPart, FileUploadUtil.COVER_DIR, current.getId());
                if (coverUrl != null) {
                    current.setCoverPhotoUrl(req.getContextPath() + coverUrl);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // profile links (textarea input; one per line as label|url)
        String links = req.getParameter("links");
        if (links != null) {
            current.setLinks(links.trim());
        }
        userRepo.save(current);
        // update session user reference
        req.getSession().setAttribute("user", userRepo.findById(current.getId()));
        resp.sendRedirect(req.getContextPath() + "/user/profile");
    }
}
