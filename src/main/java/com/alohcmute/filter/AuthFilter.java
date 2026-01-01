package com.alohcmute.filter;

import com.alohcmute.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User u = null;
        if (session != null) u = (User) session.getAttribute("user");
        if (u == null || u.getRole() == null || !u.getRole().equals("ADMIN")) {
            resp.sendRedirect(req.getContextPath() + "/user/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
