package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.entity.Video;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.VideoRepository;
import com.alohcmute.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/video/share")
public class VideoShareServlet extends HttpServlet {
    
    private VideoRepository videoRepo = new VideoRepository();
    private PostRepository postRepo = new PostRepository();
    private NotificationService notificationService = new NotificationService();

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
        String shareMessage = request.getParameter("message");
        
        System.out.println("VideoShareServlet - Received videoId: " + videoIdStr + ", message: " + shareMessage);
        System.out.println("VideoShareServlet - All parameters: " + request.getParameterMap().keySet());
        
        if (videoIdStr == null || videoIdStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Video ID không hợp lệ - videoId: " + videoIdStr + "\"}");
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
            
            // Create a new post with video link
            Post post = new Post();
            post.setAuthor(user);
            
            String videoLink = request.getContextPath() + "/user/video-detail?id=" + videoId;
            String content = shareMessage != null && !shareMessage.trim().isEmpty() 
                ? shareMessage.trim() + "\n\n" 
                : "";
            content += "📹 " + video.getTitle() + "\n";
            content += "🔗 Xem video: " + request.getScheme() + "://" + request.getServerName() + 
                       (request.getServerPort() != 80 && request.getServerPort() != 443 ? ":" + request.getServerPort() : "") + 
                       videoLink;
            
            post.setContent(content);
            
            // If video has thumbnail, use it as media
            if (video.getThumbnailUrl() != null && !video.getThumbnailUrl().isEmpty()) {
                post.setMediaUrl(video.getThumbnailUrl());
            }
            
            postRepo.save(post);
            
            // Create notification for video owner if sharer is not the owner
            if (video.getAuthor() != null && !video.getAuthor().equals(user)) {
                notificationService.createVideoShareNotification(video.getAuthor(), user, video);
            }
            
            out.print(String.format("{\"success\": true, \"message\": \"Đã chia sẻ video lên trang cá nhân\", \"postId\": %d}",
                      post.getId()
            ));
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Video ID không hợp lệ\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"Lỗi server: " + e.getMessage() + "\"}");
        }
    }
}
