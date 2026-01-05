package com.alohcmute.servlet;

import com.alohcmute.entity.Video;
import com.alohcmute.entity.VideoReaction;
import com.alohcmute.entity.User;
import com.alohcmute.repo.VideoRepository;
import com.alohcmute.repo.VideoReactionRepository;
import com.alohcmute.service.NotificationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/video/like")
public class VideoLikeServlet extends HttpServlet {
    
    private VideoReactionRepository reactionRepo = new VideoReactionRepository();
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
            Long likeCount = reactionRepo.countByVideoId(videoId);
            
            // Check if current user liked
            boolean liked = false;
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");
                VideoReaction existing = reactionRepo.findByVideoAndUser(videoId, user.getId());
                liked = (existing != null);
            }
            
            out.print(String.format("{\"success\": true, \"liked\": %b, \"likeCount\": %d}", liked, likeCount));
            
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
        
        System.out.println("VideoLikeServlet - Received videoId parameter: " + videoIdStr);
        System.out.println("VideoLikeServlet - All parameters: " + request.getParameterMap().keySet());
        
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
            
            // Check if user already liked
            VideoReaction existing = reactionRepo.findByVideoAndUser(videoId, user.getId());
            
            if (existing != null) {
                // Unlike
                reactionRepo.delete(existing);
                Long likeCount = reactionRepo.countByVideoId(videoId);
                out.print(String.format("{\"success\": true, \"liked\": false, \"likeCount\": %d}", likeCount));
            } else {
                // Like
                VideoReaction reaction = new VideoReaction(video, user, "LIKE");
                reactionRepo.save(reaction);
                
                // Tạo thông báo cho chủ video
                notificationService.createVideoLikeNotification(video.getAuthor(), user, video);
                
                Long likeCount = reactionRepo.countByVideoId(videoId);
                out.print(String.format("{\"success\": true, \"liked\": true, \"likeCount\": %d}", likeCount));
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\": false, \"message\": \"Video ID không hợp lệ\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\": false, \"message\": \"Lỗi server: " + e.getMessage() + "\"}");
        }
    }
}
