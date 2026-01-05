
package com.alohcmute.servlet;

import com.alohcmute.entity.Report;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.ReportRepository;
import com.alohcmute.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/moderator/action")
public class ModeratorActionServlet extends HttpServlet {
    private final ReportRepository reportRepo = new ReportRepository();
    private final PostRepository postRepo = new PostRepository();
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        String action = req.getParameter("action");
        String reportIdStr = req.getParameter("reportId");
        String reviewNote = req.getParameter("reviewNote");

        if (reportIdStr == null || action == null) {
            resp.sendRedirect(req.getContextPath() + "/moderator/queue?error=missing_data");
            return;
        }

        try {
            Long reportId = Long.parseLong(reportIdStr);
            Report report = reportRepo.findById(reportId);

            if (report == null) {
                resp.sendRedirect(req.getContextPath() + "/moderator/queue?error=not_found");
                return;
            }

            // For demo purposes, we'll simulate a moderator user
            User moderator = null; // Could get from session if logged in
            
            switch (action) {
                case "approve_delete":
                    // Delete content if it's a post
                    if ("POST".equals(report.getContentType())) {
                        postRepo.deleteById(report.getContentId());
                    }
                    report.setStatus("RESOLVED");
                    report.setReviewedBy(moderator);
                    report.setReviewedAt(LocalDateTime.now());
                    report.setReviewNote(reviewNote != null ? reviewNote : "Content deleted by moderator");
                    break;

                case "temp_ban":
                    // Ban user temporarily
                    if (report.getReportedUser() != null) {
                        User reportedUser = report.getReportedUser();
                        reportedUser.setActive(false);
                        userRepo.save(reportedUser);
                    }
                    report.setStatus("RESOLVED");
                    report.setReviewedBy(moderator);
                    report.setReviewedAt(LocalDateTime.now());
                    report.setReviewNote(reviewNote != null ? reviewNote : "User temporarily banned for 7 days");
                    break;

                case "reject":
                    report.setStatus("REJECTED");
                    report.setReviewedBy(moderator);
                    report.setReviewedAt(LocalDateTime.now());
                    report.setReviewNote(reviewNote != null ? reviewNote : "Report rejected - no violation found");
                    break;

                default:
                    resp.sendRedirect(req.getContextPath() + "/moderator/queue?error=invalid_action");
                    return;
            }

            reportRepo.save(report);
            resp.sendRedirect(req.getContextPath() + "/moderator/queue?success=action_completed");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/moderator/queue?error=" + e.getMessage());
        }
    }
}