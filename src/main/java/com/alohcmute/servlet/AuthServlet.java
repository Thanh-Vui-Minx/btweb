package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.repo.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/auth/register", "/auth/login", "/auth/logout"})
public class AuthServlet extends HttpServlet {
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.endsWith("/register")) {
            handleRegister(req, resp);
        } else if (path.endsWith("/login")) {
            handleLogin(req, resp);
        } else if (path.endsWith("/logout")) {
            // support POST logout
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.endsWith("/logout")) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        // For GET /auth/login or /auth/register, forward to the JSPs
        if (path.endsWith("/login")) {
            req.getRequestDispatcher("/user/login.jsp").forward(req, resp);
            return;
        }
        if (path.endsWith("/register")) {
            req.getRequestDispatcher("/user/register.jsp").forward(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == null || password == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/register?error=missing");
            return;
        }
        if (userRepo.findByEmail(email) != null) {
            resp.sendRedirect(req.getContextPath() + "/auth/register?error=exists");
            return;
        }
        User u = new User();
        u.setEmail(email);
        u.setUsername(email.substring(0, email.indexOf('@')));
        u.setDisplayName(name == null ? u.getUsername() : name);
        u.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        u.setRole("USER");
        userRepo.save(u);
        req.getSession().setAttribute("user", u);
        resp.sendRedirect(req.getContextPath() + "/user/home");
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String emailOrUsername = req.getParameter("email");
        String password = req.getParameter("password");
        if (emailOrUsername == null || password == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login?error=missing");
            return;
        }
        
        // Thử tìm user bằng email trước
        User u = userRepo.findByEmail(emailOrUsername);
        
        // Nếu không tìm thấy bằng email, thử tìm bằng username
        if (u == null) {
            u = userRepo.findByUsername(emailOrUsername);
        }
        
        // Kiểm tra user tồn tại và password đúng
        if (u == null || !BCrypt.checkpw(password, u.getPasswordHash())) {
            resp.sendRedirect(req.getContextPath() + "/auth/login?error=invalid");
            return;
        }
        
        // Kiểm tra user có bị ban không
        if (!u.isActive()) {
            resp.sendRedirect(req.getContextPath() + "/auth/login?error=banned");
            return;
        }
        
        req.getSession().setAttribute("user", u);
        resp.sendRedirect(req.getContextPath() + "/user/home");
    }
}
