package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/post/*"})
public class PostViewServlet extends HttpServlet {
    
    private final PostRepository postRepo = new PostRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }
        
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendRedirect(req.getContextPath() + "/user/home");
            return;
        }
        
        try {
            Long postId = Long.parseLong(pathInfo.substring(1));
            Post post = postRepo.findById(postId);
            
            if (post == null) {
                req.setAttribute("error", "Bài viết không tồn tại hoặc đã bị xóa");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
                return;
            }
            
            // Force load user để tránh LazyInitializationException
            if (post.getAuthor() != null) {
                post.getAuthor().getDisplayName(); // Force load
            }
            
            req.setAttribute("post", post);
            req.getRequestDispatcher("/user/post-detail.jsp").forward(req, resp);
            
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/user/home");
        }
    }
}