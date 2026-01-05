package com.alohcmute.servlet;

import com.alohcmute.entity.Report;
import com.alohcmute.repo.ReportRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test-moderator-queue")
public class TestModeratorServlet extends HttpServlet {
    private final ReportRepository reportRepo = new ReportRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("pendingReports", reportRepo.findPendingReports());
            req.setAttribute("allReports", reportRepo.findAllReports());
            req.setAttribute("pendingCount", reportRepo.countPendingReports());
            req.getRequestDispatcher("/moderator/queue.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading moderation queue: " + e.getMessage());
        }
    }
}