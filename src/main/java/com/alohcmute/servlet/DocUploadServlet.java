package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/admin/upload-doc"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 20 * 1024 * 1024)
public class DocUploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // only allow admin in session
        Object user = req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        Part p = req.getPart("pdf");
        if (p == null || p.getSize() == 0) {
            resp.sendRedirect(req.getContextPath() + "/info/report.jsp?error=nofile");
            return;
        }
        String uploadsDir = getServletContext().getRealPath("/uploads/docs");
        File dir = new File(uploadsDir);
        if (!dir.exists()) dir.mkdirs();
        String submitted = p.getSubmittedFileName();
        String filename = submitted == null ? "report.pdf" : submitted.replaceAll("[^a-zA-Z0-9.\\-_]", "_");
        File out = new File(dir, filename);
        Files.copy(p.getInputStream(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
        // try to extract text using PDFBox and save alongside with .txt suffix
        try (PDDocument doc = PDDocument.load(out)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(doc);
            File txt = new File(dir, filename + ".txt");
            try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(txt.toPath()))) {
                pw.write(text == null ? "" : text);
            }
        } catch (Exception ex) {
            // ignore extraction errors
        }
        resp.sendRedirect(req.getContextPath() + "/info/report.jsp?file=" + filename);
    }
}
