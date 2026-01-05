package com.alohcmute.servlet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/admin/extract-doc"})
public class DocExtractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // admin-only
        Object user = req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        String file = req.getParameter("file");
        if (file == null) {
            resp.getWriter().write("missing file param");
            return;
        }
        String uploadsDir = getServletContext().getRealPath("/uploads/docs");
        File pdf = new File(uploadsDir, file);
        if (!pdf.exists()) {
            resp.getWriter().write("file not found");
            return;
        }
        File txt = new File(uploadsDir, file + ".txt");
        try (PDDocument doc = PDDocument.load(pdf)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);
            try (FileWriter writer = new FileWriter(txt)) {
                writer.write(text == null ? "" : text);
            }
            resp.getWriter().write("extracted");
        } catch (Exception ex) {
            resp.getWriter().write("error: " + ex.getMessage());
        }
    }
}
