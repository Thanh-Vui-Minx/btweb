package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.repo.UserRepository;
import com.alohcmute.repo.PostRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/user/search"})
public class SearchServlet extends HttpServlet {
    private final UserRepository userRepo = new UserRepository();
    private final PostRepository postRepo = new PostRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        
        if (query != null && !query.trim().isEmpty()) {
            // Search users
            List<User> users = searchUsers(query);
            req.setAttribute("users", users);
            
            // Search posts would go here
            // List<Post> posts = searchPosts(query);
            // req.setAttribute("searchPosts", posts);
        }
        
        req.getRequestDispatcher("/user/search.jsp").forward(req, resp);
    }

    private List<User> searchUsers(String query) {
        // In production, this would be a database query with LIKE or full-text search
        // For now, return sample data
        List<User> results = new ArrayList<>();
        
        // Get all users and filter (simple implementation)
        List<User> allUsers = userRepo.listAll();
        for (User user : allUsers) {
            if (user.getUsername().toLowerCase().contains(query.toLowerCase()) ||
                user.getDisplayName().toLowerCase().contains(query.toLowerCase())) {
                results.add(user);
            }
        }
        
        return results;
    }
}
