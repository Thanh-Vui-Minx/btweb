package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.entity.Video;
import com.alohcmute.repo.VideoRepository;
import com.alohcmute.repo.FollowRepository;
import com.alohcmute.util.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/user/videos", "/video/upload"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 100,       // 100MB
    maxRequestSize = 1024 * 1024 * 120     // 120MB
)
public class VideoServlet extends HttpServlet {
    private final VideoRepository videoRepo = new VideoRepository();
    private final FollowRepository followRepo = new FollowRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        
        // Load videos from database
        try {
            List<Video> videos = videoRepo.findRecent(20);
            req.setAttribute("videos", videos);
            
            // Load videos from following users
            if (currentUser != null) {
                List<User> followingUsers = followRepo.getFollowing(currentUser.getId());
                List<Video> followingVideos = new ArrayList<>();
                
                for (User user : followingUsers) {
                    List<Video> userVideos = videoRepo.findByAuthor(user.getId());
                    followingVideos.addAll(userVideos);
                }
                
                // Sort by created date
                followingVideos.sort((v1, v2) -> v2.getCreatedAt().compareTo(v1.getCreatedAt()));
                
                req.setAttribute("followingVideos", followingVideos);
            }
        } catch (Exception e) {
            System.err.println("Error loading videos: " + e.getMessage());
            e.printStackTrace();
        }
        req.getRequestDispatcher("/user/videos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String isPublicStr = req.getParameter("isPublic");
        boolean isPublic = isPublicStr != null && isPublicStr.equals("on");

        Part videoPart = req.getPart("video");
        Part thumbnailPart = req.getPart("thumbnail");

        if (videoPart == null || videoPart.getSize() == 0) {
            req.setAttribute("error", "Vui lòng chọn file video");
            doGet(req, resp);
            return;
        }

        if (title == null || title.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập tiêu đề video");
            doGet(req, resp);
            return;
        }

        try {
            // Save video file
            String videoUrl = FileUploadUtil.saveFile(videoPart, FileUploadUtil.VIDEO_DIR, "video");
            if (videoUrl == null) {
                req.setAttribute("error", "Không thể lưu video");
                doGet(req, resp);
                return;
            }

            // Save thumbnail if provided
            String thumbnailUrl = null;
            if (thumbnailPart != null && thumbnailPart.getSize() > 0) {
                thumbnailUrl = FileUploadUtil.saveFile(thumbnailPart, FileUploadUtil.THUMBNAIL_DIR, "thumb");
            }

            // Create video entity and save to database
            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setVideoUrl(videoUrl);
            video.setThumbnailUrl(thumbnailUrl);
            video.setIsPublic(isPublic);
            video.setAuthor(currentUser);
            video.setViews(0L);

            videoRepo.save(video);
            
            System.out.println("✓ Video uploaded successfully: " + title);
            resp.sendRedirect(req.getContextPath() + "/user/videos?upload=success");
            
        } catch (Exception e) {
            System.err.println("✗ Error uploading video: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("error", "Có lỗi xảy ra khi tải video: " + e.getMessage());
            doGet(req, resp);
        }
    }
}
