package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/post/delete"})
public class PostDeleteServlet extends HttpServlet {
    private final PostRepository postRepo = new PostRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String idStr = req.getParameter("postId");
        if (idStr == null) { resp.sendRedirect(req.getContextPath() + "/user/home"); return; }
        try {
            Long id = Long.parseLong(idStr);
            Post p = postRepo.findById(id);
            if (p == null) { resp.sendRedirect(req.getContextPath() + "/user/home"); return; }
            boolean isAuthor = p.getAuthor() != null && p.getAuthor().getId() != null && p.getAuthor().getId().equals(user.getId());
            boolean isAdmin = "ADMIN".equalsIgnoreCase(user.getRole());
            if (isAuthor || isAdmin) {
                postRepo.deleteById(id);
            }
        } catch (NumberFormatException ignored) {}
        resp.sendRedirect(req.getContextPath() + "/user/home");
    }
}
