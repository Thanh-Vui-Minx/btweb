package com.alohcmute.servlet;

import com.alohcmute.entity.ChatMessage;
import com.alohcmute.entity.User;
import com.alohcmute.repo.ChatRepository;

import javax.servlet.ServletException;
// servlet mapping is provided in WEB-INF/web.xml
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChatServlet extends HttpServlet {
    private final ChatRepository chatRepo = new ChatRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        List<ChatMessage> messages = chatRepo.findRecent(100);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/user/chat.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
        String content = req.getParameter("content");
        if (content != null && !content.trim().isEmpty()) {
            ChatMessage m = new ChatMessage();
            m.setAuthor(user);
            m.setContent(content.trim());
            chatRepo.save(m);
        }
        resp.sendRedirect(req.getContextPath() + "/user/chat");
    }
}
