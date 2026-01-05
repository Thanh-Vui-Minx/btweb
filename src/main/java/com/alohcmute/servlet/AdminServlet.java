package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin/dashboard", "/admin/users"})
public class AdminServlet extends HttpServlet {
    private final UserRepository userRepo = new UserRepository();
    private final PostRepository postRepo = new PostRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check authentication
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied. Admin role required.");
            return;
        }
        
        String path = req.getServletPath();
        if (path.endsWith("/dashboard")) {
            req.setAttribute("userCount", userRepo.listAll().size());
            req.setAttribute("postCount", postRepo.countAll());
            req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
            return;
        }
        if (path.endsWith("/users")) {
            req.setAttribute("users", userRepo.listAll());
            req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
