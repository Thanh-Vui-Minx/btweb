package com.alohcmute.servlet;

import com.alohcmute.entity.Follow;
import com.alohcmute.entity.User;
import com.alohcmute.repo.FollowRepository;
import com.alohcmute.repo.UserRepository;
import com.alohcmute.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/user/follow", "/user/unfollow", "/user/follow-status"})
public class FollowServlet extends HttpServlet {
    
    private final FollowRepository followRepo = new FollowRepository();
    private final UserRepository userRepo = new UserRepository();
    private final NotificationService notificationService = new NotificationService();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            sendJsonResponse(resp, false, "Bạn cần đăng nhập để thực hiện hành động này", 0, 0);
            return;
        }
        
        String targetUserIdStr = req.getParameter("userId");
        if (targetUserIdStr == null || targetUserIdStr.isEmpty()) {
            sendJsonResponse(resp, false, "ID người dùng không hợp lệ", 0, 0);
            return;
        }
        
        try {
            Long targetUserId = Long.parseLong(targetUserIdStr);
            
            // Prevent self-follow
            if (currentUser.getId().equals(targetUserId)) {
                sendJsonResponse(resp, false, "Bạn không thể theo dõi chính mình", 0, 0);
                return;
            }
            
            User targetUser = userRepo.findById(targetUserId);
            if (targetUser == null) {
                sendJsonResponse(resp, false, "Không tìm thấy người dùng", 0, 0);
                return;
            }
            
            String path = req.getServletPath();
            
            if ("/user/follow".equals(path)) {
                // Follow action
                Follow follow = followRepo.follow(currentUser, targetUser);
                if (follow != null) {
                    // Create notification for the followed user
                    notificationService.createFollowNotification(targetUser, currentUser);
                    
                    long followersCount = followRepo.getFollowersCount(targetUserId);
                    long followingCount = followRepo.getFollowingCount(currentUser.getId());
                    
                    sendJsonResponse(resp, true, "Đã theo dõi " + targetUser.getDisplayName(), followersCount, followingCount);
                } else {
                    sendJsonResponse(resp, false, "Bạn đã theo dõi người dùng này rồi", 0, 0);
                }
            } else if ("/user/unfollow".equals(path)) {
                // Unfollow action
                boolean success = followRepo.unfollow(currentUser.getId(), targetUserId);
                if (success) {
                    long followersCount = followRepo.getFollowersCount(targetUserId);
                    long followingCount = followRepo.getFollowingCount(currentUser.getId());
                    
                    sendJsonResponse(resp, true, "Đã bỏ theo dõi " + targetUser.getDisplayName(), followersCount, followingCount);
                } else {
                    sendJsonResponse(resp, false, "Không thể bỏ theo dõi", 0, 0);
                }
            }
            
        } catch (NumberFormatException e) {
            sendJsonResponse(resp, false, "ID người dùng không hợp lệ", 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(resp, false, "Đã xảy ra lỗi: " + e.getMessage(), 0, 0);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        
        String path = req.getServletPath();
        
        if ("/user/follow-status".equals(path)) {
            User currentUser = (User) req.getSession().getAttribute("user");
            if (currentUser == null) {
                sendJsonResponse(resp, false, "Chưa đăng nhập", 0, 0);
                return;
            }
            
            String targetUserIdStr = req.getParameter("userId");
            if (targetUserIdStr == null || targetUserIdStr.isEmpty()) {
                sendJsonResponse(resp, false, "ID người dùng không hợp lệ", 0, 0);
                return;
            }
            
            try {
                Long targetUserId = Long.parseLong(targetUserIdStr);
                boolean isFollowing = followRepo.isFollowing(currentUser.getId(), targetUserId);
                
                PrintWriter out = resp.getWriter();
                out.print("{\"success\":true,\"message\":\"Success\",\"data\":{\"isFollowing\":" + isFollowing + "}}");
                out.flush();
            } catch (NumberFormatException e) {
                sendJsonResponse(resp, false, "ID người dùng không hợp lệ", 0, 0);
            }
        }
    }
    
    private void sendJsonResponse(HttpServletResponse resp, boolean success, String message, long followersCount, long followingCount) throws IOException {
        PrintWriter out = resp.getWriter();
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"success\":").append(success).append(",");
        json.append("\"message\":\"").append(escapeJson(message)).append("\"");
        if (followersCount > 0 || followingCount > 0) {
            json.append(",\"data\":{");
            json.append("\"followersCount\":").append(followersCount).append(",");
            json.append("\"followingCount\":").append(followingCount);
            json.append("}");
        }
        json.append("}");
        out.print(json.toString());
        out.flush();
    }
    
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
