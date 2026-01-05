package com.alohcmute.servlet;

import com.alohcmute.entity.Report;
import com.alohcmute.entity.User;
import com.alohcmute.repo.ReportRepository;
import com.alohcmute.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ReportServlet extends HttpServlet {
    private final ReportRepository reportRepo = new ReportRepository();
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;
        
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login.jsp");
            return;
        }

        String contentType = req.getParameter("contentType");
        String contentIdStr = req.getParameter("contentId");
        String reportedUserIdStr = req.getParameter("reportedUserId");
        String reason = req.getParameter("reason");
        String description = req.getParameter("description");
        String redirectUrl = req.getParameter("redirectUrl");

        if (contentType == null || contentIdStr == null || reason == null) {
            resp.sendRedirect(req.getContextPath() + "/user/home?error=missing_data");
            return;
        }

        try {
            Report report = new Report();
            report.setReporter(currentUser);
            report.setContentType(contentType);
            report.setContentId(Long.parseLong(contentIdStr));
            report.setReason(reason);
            report.setDescription(description);

            if (reportedUserIdStr != null && !reportedUserIdStr.isEmpty()) {
                User reportedUser = userRepo.findById(Long.parseLong(reportedUserIdStr));
                report.setReportedUser(reportedUser);
            }

            reportRepo.save(report);

            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                resp.sendRedirect(redirectUrl + "?success=reported");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/home?success=reported");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/user/home?error=report_failed");
        }
    }
}
