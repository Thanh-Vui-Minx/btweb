package com.alohcmute.servlet;

import com.alohcmute.entity.User;
import com.alohcmute.repo.FollowRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user/following"})
public class FollowingServlet extends HttpServlet {
    
    private final FollowRepository followRepo = new FollowRepository();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        
        // Lấy danh sách người đang theo dõi
        List<User> followingUsers = followRepo.getFollowing(currentUser.getId());
        req.setAttribute("followingUsers", followingUsers);
        
        // Lấy số liệu thống kê
        long followersCount = followRepo.getFollowersCount(currentUser.getId());
        long followingCount = followRepo.getFollowingCount(currentUser.getId());
        req.setAttribute("followersCount", followersCount);
        req.setAttribute("followingCount", followingCount);
        
        req.getRequestDispatcher("/user/following.jsp").forward(req, resp);
    }
}
