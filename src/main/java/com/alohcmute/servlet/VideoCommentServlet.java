package com.alohcmute.servlet;

import com.alohcmute.entity.Video;
import com.alohcmute.entity.VideoComment;
import com.alohcmute.entity.User;
import com.alohcmute.repo.VideoRepository;
import com.alohcmute.repo.VideoCommentRepository;
import com.alohcmute.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/api/video/comment")
public class VideoCommentServlet extends HttpServlet {
    
    private VideoCommentRepository commentRepo = new VideoCommentRepository();
    private VideoRepository videoRepo = new VideoRepository();
    private NotificationService notificationService = new NotificationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String videoIdStr = request.getParameter("videoId");
        
        if (videoIdStr == null || videoIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Video ID không hợp lệ\"}");
            return;
        }
        
        try {
            Long videoId = Long.parseLong(videoIdStr);
            List<VideoComment> comments = commentRepo.findByVideoId(videoId);
            
            StringBuilder json = new StringBuilder("{\"success\": true, \"comments\": [");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            for (int i = 0; i < comments.size(); i++) {
                VideoComment c = comments.get(i);
                User author = c.getAuthor();
                
                if (i > 0) json.append(",");
                json.append("{");
                json.append("\"id\": ").append(c.getId()).append(",");
                json.append("\"text\": \"").append(escapeJson(c.getContent())).append("\",");
                json.append("\"author\": \"").append(escapeJson(author.getDisplayName())).append("\",");
                
                String avatarUrl = author.getAvatarUrl() != null && !author.getAvatarUrl().isEmpty() 
                    ? author.getAvatarUrl() 
                    : request.getContextPath() + "/assets/images/default-avatar.png";
                json.append("\"avatarUrl\": \"").append(avatarUrl).append("\",");
                json.append("\"timestamp\": \"").append(c.getCreatedAt().format(formatter)).append("\"");
                json.append("}");
            }
            
            json.append("]}");
            out.print(json.toString());
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Video ID không hợp lệ\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"Lỗi server: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("{\"success\": false, \"message\": \"Vui lòng đăng nhập\"}");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String videoIdStr = request.getParameter("videoId");
        String content = request.getParameter("content");
        
        System.out.println("VideoCommentServlet - Received videoId: " + videoIdStr + ", content: " + content);
        System.out.println("VideoCommentServlet - All parameters: " + request.getParameterMap().keySet());
        
        if (videoIdStr == null || videoIdStr.isEmpty() || content == null || content.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Dữ liệu không hợp lệ - videoId: " + videoIdStr + ", content: " + content + "\"}");
            return;
        }
        
        try {
            Long videoId = Long.parseLong(videoIdStr);
            Video video = videoRepo.findById(videoId);
            
            if (video == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"success\": false, \"message\": \"Không tìm thấy video\"}");
                return;
            }
            
            VideoComment comment = new VideoComment(video, user, content.trim());
            commentRepo.save(comment);
            
            // Tạo thông báo cho chủ video
            notificationService.createVideoCommentNotification(video.getAuthor(), user, video, content.trim());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String avatarUrl = user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty() 
                ? user.getAvatarUrl() 
                : request.getContextPath() + "/assets/images/default-avatar.png";
            
            out.print(String.format("{\"success\": true, \"comment\": {\"id\": %d, \"text\": \"%s\", \"author\": \"%s\", \"avatarUrl\": \"%s\", \"timestamp\": \"%s\"}}",
                      comment.getId(),
                      escapeJson(comment.getContent()),
                      escapeJson(user.getDisplayName()),
                      avatarUrl,
                      comment.getCreatedAt().format(formatter)
            ));
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Video ID không hợp lệ\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"Lỗi server: " + e.getMessage() + "\"}");
        }
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
