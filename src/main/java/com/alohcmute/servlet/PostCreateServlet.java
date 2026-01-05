package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.util.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(urlPatterns = {"/post/create"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024)
public class PostCreateServlet extends HttpServlet {
    private final PostRepository postRepo = new PostRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        
        String content = req.getParameter("content");
        
        // Validate content - at least content or media must be provided
        if ((content == null || content.trim().isEmpty())) {
            Part media = req.getPart("media");
            if (media == null || media.getSize() == 0) {
                // Neither content nor media provided
                resp.sendRedirect(req.getContextPath() + "/user/home?error=empty");
                return;
            }
        }

        Part media = req.getPart("media");
        String mediaUrl = null;
        if (media != null && media.getSize() > 0) {
            try {
                mediaUrl = FileUploadUtil.saveFile(media, FileUploadUtil.POST_DIR, "post");
                if (mediaUrl != null) {
                    mediaUrl = req.getContextPath() + mediaUrl;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error uploading media: " + e.getMessage());
            }
        }

        Post p = new Post();
        p.setAuthor(user);
        p.setContent(content != null ? content.trim() : "");
        p.setMediaUrl(mediaUrl);
        
        try {
            postRepo.save(p);
            System.out.println("Post created successfully: " + p.getId());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error saving post: " + e.getMessage());
        }
        
        resp.sendRedirect(req.getContextPath() + "/user/home");
    }
}
