package com.alohcmute.servlet;

import com.alohcmute.entity.DirectMessage;
import com.alohcmute.entity.User;
import com.alohcmute.repo.DirectMessageRepository;
import com.alohcmute.repo.UserRepository;

import javax.servlet.ServletException;
// servlet mapping is provided in WEB-INF/web.xml
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DirectMessageServlet extends HttpServlet {
    private final DirectMessageRepository dmRepo = new DirectMessageRepository();
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        User me = (User) req.getSession().getAttribute("user");
        if (me == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String with = req.getParameter("with");
        if (with != null && !with.trim().isEmpty()) {
            User other = userRepo.findByUsername(with.trim());
            if (other != null) {
                List<DirectMessage> conv = dmRepo.findConversation(me.getId(), other.getId(), 200);
                req.setAttribute("withUser", other);
                req.setAttribute("messages", conv);
            } else {
                req.setAttribute("error", "User not found: " + with);
            }
        }
        req.getRequestDispatcher("/user/messages.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        User me = (User) req.getSession().getAttribute("user");
        if (me == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String with = req.getParameter("with");
        String content = req.getParameter("content");
        if (with == null || with.trim().isEmpty()) { resp.sendRedirect(req.getContextPath() + "/user/messages"); return; }
        User other = userRepo.findByUsername(with.trim());
        if (other == null) { req.setAttribute("error", "User not found: " + with); req.getRequestDispatcher("/user/messages.jsp").forward(req, resp); return; }
        if (content != null && !content.trim().isEmpty()) {
            DirectMessage dm = new DirectMessage();
            dm.setSender(me);
            dm.setRecipient(other);
            dm.setContent(content.trim());
            dmRepo.save(dm);
        }
        resp.sendRedirect(req.getContextPath() + "/user/messages?with=" + other.getUsername());
    }
}
