package com.alohcmute.servlet;

import com.alohcmute.entity.Comment;
import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.repo.CommentRepository;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/post/comment"})
public class CommentServlet extends HttpServlet {
    private final CommentRepository commentRepo = new CommentRepository();
    private final PostRepository postRepo = new PostRepository();
    private final NotificationService notificationService = new NotificationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String postId = req.getParameter("postId");
        String content = req.getParameter("content");
        if (postId == null || content == null || content.trim().isEmpty()) { resp.sendRedirect(req.getContextPath() + "/user/home"); return; }
        try {
            Post p = postRepo.findById(Long.parseLong(postId));
            if (p != null) {
                Comment c = new Comment();
                c.setPost(p);
                c.setAuthor(user);
                c.setContent(content.trim());
                commentRepo.save(c);
                
                // Create notification for post owner if commenter is not the owner
                if (p.getAuthor() != null && !p.getAuthor().getId().equals(user.getId())) {
                    notificationService.createPostCommentNotification(p.getAuthor(), user, p, content.trim());
                }
            }
        } catch (NumberFormatException ignored) {}
        resp.sendRedirect(req.getContextPath() + "/user/home");
    }
}
