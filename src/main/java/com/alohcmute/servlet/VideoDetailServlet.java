package com.alohcmute.servlet;

import com.alohcmute.entity.Video;
import com.alohcmute.repo.VideoRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user/video/*"})
public class VideoDetailServlet extends HttpServlet {
    private final VideoRepository videoRepo = new VideoRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendRedirect(req.getContextPath() + "/user/videos");
            return;
        }

        try {
            Long videoId = Long.parseLong(pathInfo.substring(1));
            Video video = videoRepo.findById(videoId);
            
            if (video == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Video không tồn tại");
                return;
            }

            // Increment view count
            videoRepo.incrementViews(videoId);
            
            // Reload video to get updated view count
            video = videoRepo.findById(videoId);
            
            req.setAttribute("video", video);
            req.getRequestDispatcher("/user/video-detail.jsp").forward(req, resp);
            
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID video không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra");
        }
    }
}
