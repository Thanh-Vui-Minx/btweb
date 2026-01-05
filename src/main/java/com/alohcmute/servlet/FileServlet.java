package com.alohcmute.servlet;

import com.alohcmute.util.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Servlet to serve uploaded files from the persistent upload directory
 */
@WebServlet("/uploads/*")
public class FileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedFile = req.getPathInfo();
        
        if (requestedFile == null || requestedFile.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Remove leading slash
        if (requestedFile.startsWith("/")) {
            requestedFile = requestedFile.substring(1);
        }
        
        File file = new File(FileUploadUtil.getBaseUploadDir(), requestedFile);
        
        if (!file.exists() || !file.isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // Set content type
        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = Files.probeContentType(file.toPath());
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        resp.setContentType(contentType);
        
        // Set content length
        resp.setContentLengthLong(file.length());
        
        // Set cache headers for better performance
        resp.setHeader("Cache-Control", "public, max-age=31536000"); // 1 year
        
        // Stream the file
        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
