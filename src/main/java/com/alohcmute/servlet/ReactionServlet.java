package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.Reaction;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.ReactionRepository;
import com.alohcmute.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/post/react"})
public class ReactionServlet extends HttpServlet {
    private final ReactionRepository reactionRepo = new ReactionRepository();
    private final PostRepository postRepo = new PostRepository();
    private final NotificationService notificationService = new NotificationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String postId = req.getParameter("postId");
        String emoji = req.getParameter("emoji");
        System.out.println("[ReactionServlet] incoming - user=" + (user!=null?user.getUsername():"(null)") + ", postId=" + postId + ", emoji(raw)=" + emoji);
        if (postId == null || emoji == null) { resp.sendRedirect(req.getContextPath() + "/user/home"); return; }
        try {
            Post p = postRepo.findById(Long.parseLong(postId));
            if (p != null) {
                // Kiểm tra xem user đã react bài này chưa
                Reaction existingReaction = reactionRepo.findByPostAndUser(p.getId(), user.getUsername());
                
                if (existingReaction != null) {
                    // Nếu đã react, kiểm tra xem có phải cùng loại emoji không
                    if (emoji.equals(existingReaction.getEmoji())) {
                        // Cùng emoji -> Hủy reaction (toggle off)
                        reactionRepo.delete(existingReaction);
                        System.out.println("[ReactionServlet] Removed reaction: " + emoji + " from post " + postId);
                    } else {
                        // Khác emoji -> Cập nhật thành emoji mới
                        existingReaction.setEmoji(emoji);
                        reactionRepo.save(existingReaction);
                        System.out.println("[ReactionServlet] Updated reaction to: " + emoji + " for post " + postId);
                        
                        // Tạo thông báo cho chủ bài viết
                        if (p.getAuthor() != null && !p.getAuthor().getId().equals(user.getId())) {
                            notificationService.createPostReactionNotification(p.getAuthor(), user, p, emoji);
                        }
                    }
                } else {
                    // Chưa react -> Tạo mới
                    Reaction r = new Reaction();
                    r.setPost(p);
                    r.setEmoji(emoji);
                    r.setUsername(user.getUsername());
                    reactionRepo.save(r);
                    System.out.println("[ReactionServlet] Added new reaction: " + emoji + " to post " + postId);
                    
                    // Tạo thông báo cho chủ bài viết
                    if (p.getAuthor() != null && !p.getAuthor().getId().equals(user.getId())) {
                        notificationService.createPostReactionNotification(p.getAuthor(), user, p, emoji);
                    }
                }
            }
        } catch (NumberFormatException ignored) {}
        resp.sendRedirect(req.getContextPath() + "/user/home");
    }
}
