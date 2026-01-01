package com.alohcmute.filter;

import com.alohcmute.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/moderator/queue", "/moderator/action"})
public class ModeratorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        
        // Chỉ cho phép MODERATOR hoặc ADMIN truy cập
        if (user == null || user.getRole() == null || 
            (!user.getRole().equals("MODERATOR") && !user.getRole().equals("ADMIN"))) {
            resp.sendRedirect(req.getContextPath() + "/user/login.jsp?error=access_denied");
            return;
        }
        
        chain.doFilter(request, response);
    }
}
