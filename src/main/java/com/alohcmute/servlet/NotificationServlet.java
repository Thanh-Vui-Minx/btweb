package com.alohcmute.servlet;

import com.alohcmute.entity.Notification;
import com.alohcmute.entity.User;
import com.alohcmute.repo.NotificationRepository;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/user/notifications"})
public class NotificationServlet extends HttpServlet {
    
    private final NotificationRepository notificationRepo = new NotificationRepository();
    private final UserRepository userRepo = new UserRepository();
    private final PostRepository postRepo = new PostRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        // Lấy notifications thực tế từ database
        List<Notification> notifications = notificationRepo.findByUserWithLimit(currentUser, 20);
        
        // Debug encoding của messages
        for (Notification notif : notifications) {
            System.out.println("[NotificationServlet] Notification message: " + notif.getMessage());
            System.out.println("[NotificationServlet] Message length: " + notif.getMessage().length());
        }
        
        req.setAttribute("notifications", notifications);
        
        // Tạo stats thực tế
        Map<String, Integer> stats = new HashMap<>();
        stats.put("postCount", postRepo.countByUser(currentUser));
        stats.put("followerCount", userRepo.countFollowers(currentUser.getId()));
        stats.put("followingCount", userRepo.countFollowing(currentUser.getId()));
        stats.put("likeCount", postRepo.countLikesByUser(currentUser));
        req.setAttribute("stats", stats);
        
        // Đánh dấu tất cả notifications là đã đọc
        String markAsRead = req.getParameter("markAsRead");
        if ("true".equals(markAsRead)) {
            notificationRepo.markAllAsRead(currentUser);
            resp.sendRedirect(req.getContextPath() + "/user/notifications");
            return;
        }
        
        req.getRequestDispatcher("/user/notifications.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        String action = req.getParameter("action");
        
        if ("markAsRead".equals(action)) {
            String notificationId = req.getParameter("notificationId");
            if (notificationId != null) {
                try {
                    notificationRepo.markAsRead(Long.parseLong(notificationId));
                } catch (NumberFormatException e) {
                    // Ignore invalid ID
                }
            }
        } else if ("markAllAsRead".equals(action)) {
            notificationRepo.markAllAsRead(currentUser);
        }
        
        resp.sendRedirect(req.getContextPath() + "/user/notifications");
    }
}
