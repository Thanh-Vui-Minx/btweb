package com.alohcmute.servlet;

import com.alohcmute.entity.Post;
import com.alohcmute.entity.Report;
import com.alohcmute.entity.User;
import com.alohcmute.repo.PostRepository;
import com.alohcmute.repo.ReportRepository;
import com.alohcmute.repo.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

public class ModeratorServlet extends HttpServlet {
    private final ReportRepository reportRepo = new ReportRepository();
    private final PostRepository postRepo = new PostRepository();
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        
        if (path.endsWith("/queue")) {
            try {
                req.setAttribute("pendingReports", reportRepo.findPendingReports());
                req.setAttribute("allReports", reportRepo.findAllReports());
                req.setAttribute("pendingCount", reportRepo.countPendingReports());
                req.getRequestDispatcher("/moderator/queue.jsp").forward(req, resp);
            } catch (Exception e) {
                // If JSP fails, show simple HTML response
                resp.setContentType("text/html; charset=UTF-8");
                resp.getWriter().println("<!DOCTYPE html>");
                resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Moderator Queue</title>");
                resp.getWriter().println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
                resp.getWriter().println("</head><body>");
                resp.getWriter().println("<div class='container mt-4'>");
                resp.getWriter().println("<h2>🛡️ Moderator Queue</h2>");
                resp.getWriter().println("<div class='alert alert-danger'>JSP Error: " + e.getMessage() + "</div>");
                resp.getWriter().println("<p><a href='" + req.getContextPath() + "/simple-moderator-queue'>Try Simple Version</a></p>");
                resp.getWriter().println("</div></body></html>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession(false);
        User moderator = (session != null) ? (User) session.getAttribute("user") : null;

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

            switch (action) {
                case "approve_delete":
                    // Xóa nội dung vi phạm
                    if ("POST".equals(report.getContentType())) {
                        postRepo.deleteById(report.getContentId());
                    }
                    report.setStatus("RESOLVED");
                    report.setReviewedBy(moderator);
                    report.setReviewedAt(LocalDateTime.now());
                    report.setReviewNote(reviewNote != null ? reviewNote : "Content deleted");
                    break;

                case "temp_ban":
                    // Ban user tạm thời (7 ngày)
                    if (report.getReportedUser() != null) {
                        User reportedUser = report.getReportedUser();
                        reportedUser.setActive(false);
                        userRepo.save(reportedUser);
                    }
                    report.setStatus("RESOLVED");
                    report.setReviewedBy(moderator);
                    report.setReviewedAt(LocalDateTime.now());
                    report.setReviewNote(reviewNote != null ? reviewNote : "User temp banned for 7 days");
                    break;

                case "reject":
                    report.setStatus("REJECTED");
                    report.setReviewedBy(moderator);
                    report.setReviewedAt(LocalDateTime.now());
                    report.setReviewNote(reviewNote != null ? reviewNote : "Report rejected");
                    break;

                case "mark_reviewed":
                    report.setStatus("REVIEWED");
                    report.setReviewedBy(moderator);
                    report.setReviewedAt(LocalDateTime.now());
                    report.setReviewNote(reviewNote);
                    break;

                default:
                    resp.sendRedirect(req.getContextPath() + "/moderator/queue?error=invalid_action");
                    return;
            }

            reportRepo.save(report);
            resp.sendRedirect(req.getContextPath() + "/moderator/queue?success=action_completed");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/moderator/queue?error=failed");
        }
    }
}
