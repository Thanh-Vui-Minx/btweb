package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet xử lý các trang thông tin: Giới thiệu, Trợ giúp, Quyền riêng tư, Điều khoản
 */
@WebServlet(urlPatterns = {"/info/about", "/info/help", "/info/privacy", "/info/terms"})
public class InfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String jspPage = null;
        
        switch (path) {
            case "/info/about":
                jspPage = "/info/about.jsp";
                break;
            case "/info/help":
                jspPage = "/info/help.jsp";
                break;
            case "/info/privacy":
                jspPage = "/info/privacy.jsp";
                break;
            case "/info/terms":
                jspPage = "/info/terms.jsp";
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }
        
        req.getRequestDispatcher(jspPage).forward(req, resp);
    }
}
