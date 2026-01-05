package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = {"/dev/migrate-add-active"})
public class MigrationServlet extends HttpServlet {
    // Use same DB settings as persistence.xml — adjust if your env differs
    private static final String URL = "jdbc:sqlserver://DESKTOP-IMSJ193:1433;databaseName=LTWEBTUXA1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "Ad12345678!";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ignored) {}
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             Statement s = c.createStatement()) {
            String sql = "ALTER TABLE users ADD active bit DEFAULT 1 NOT NULL";
            s.executeUpdate(sql);
            resp.getWriter().write("Migration applied: added 'active' column.");
        } catch (SQLException ex) {
            resp.getWriter().write("Migration failed or already applied: " + ex.getMessage());
        }
    }
}
