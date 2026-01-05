package com.alohcmute.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = {"/dev/inspect-users"})
public class InspectUsersColumnsServlet extends HttpServlet {
    private static final String URL = "jdbc:sqlserver://DESKTOP-IMSJ193:1433;databaseName=LTWEBTUXA1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "Ad12345678!";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ignored) {}
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='users'");
            while (rs.next()) {
                resp.getWriter().println(rs.getString("COLUMN_NAME") + " : " + rs.getString("DATA_TYPE"));
            }
        } catch (SQLException ex) {
            resp.getWriter().println("Error: " + ex.getMessage());
        }
    }
}
