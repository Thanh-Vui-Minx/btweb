<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.alohcmute.entity.Report" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <title>Moderation Queue - ALOHCMUTE</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<%= request.getContextPath() %>/user/home">🎓 ALOHCMUTE</a>
            <span class="navbar-text text-light">
                Moderator Panel
            </span>
        </div>
    </nav>

    <div class="container mt-4">
        <h1>🛡️ Moderation Queue</h1>
        
        <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success alert-dismissible fade show">
                Action completed successfully!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>
        
        <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show">
                Error: <%= request.getParameter("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="card mb-4">
            <div class="card-header bg-warning">
                <h5 class="mb-0">⚠️ Pending Reports (<%= request.getAttribute("pendingCount") %>)</h5>
            </div>
            <div class="card-body">
                <%
                    List<Report> pendingReports = (List<Report>) request.getAttribute("pendingReports");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    
                    if (pendingReports == null || pendingReports.isEmpty()) {
                %>
                    <p class="text-muted">No pending reports. Great job! 🎉</p>
                <%
                    } else {
                        for (Report report : pendingReports) {
                %>
                    <div class="card mb-3 border-warning">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-8">
                                    <h6 class="card-title">
                                        <span class="badge bg-danger"><%= report.getReason() %></span>
                                        <span class="badge bg-secondary"><%= report.getContentType() %></span>
                                    </h6>
                                    <p class="card-text">
                                        <strong>Content ID:</strong> #<%= report.getContentId() %><br>
                                        <strong>Reporter:</strong> <%= report.getReporter().getDisplayName() %> 
                                        (@<%= report.getReporter().getUsername() %>)<br>
                                        <% if (report.getReportedUser() != null) { %>
                                            <strong>Reported User:</strong> <%= report.getReportedUser().getDisplayName() %>
                                            (@<%= report.getReportedUser().getUsername() %>)<br>
                                        <% } %>
                                        <strong>Submitted:</strong> <%= report.getCreatedAt().format(formatter) %><br>
                                        <% if (report.getDescription() != null && !report.getDescription().isEmpty()) { %>
                                            <strong>Description:</strong> <%= report.getDescription() %>
                                        <% } %>
                                    </p>
                                </div>
                                <div class="col-md-4">
                                    <form method="post" action="<%= request.getContextPath() %>/moderator/action" class="mb-2">
                                        <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                        <input type="hidden" name="action" value="approve_delete">
                                        <textarea name="reviewNote" class="form-control form-control-sm mb-2" 
                                                  placeholder="Review note..." rows="2"></textarea>
                                        <button type="submit" class="btn btn-danger btn-sm w-100 mb-1">
                                            🗑️ Delete Content
                                        </button>
                                    </form>
                                    
                                    <% if (report.getReportedUser() != null) { %>
                                    <form method="post" action="<%= request.getContextPath() %>/moderator/action" class="mb-2">
                                        <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                        <input type="hidden" name="action" value="temp_ban">
                                        <button type="submit" class="btn btn-warning btn-sm w-100 mb-1">
                                            ⏱️ Temp Ban User (7d)
                                        </button>
                                    </form>
                                    <% } %>
                                    
                                    <form method="post" action="<%= request.getContextPath() %>/moderator/action" class="mb-2">
                                        <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                        <input type="hidden" name="action" value="reject">
                                        <button type="submit" class="btn btn-secondary btn-sm w-100">
                                            ✖️ Reject Report
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                <%
                        }
                    }
                %>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">📋 All Reports History</h5>
            </div>
            <div class="card-body">
                <table class="table table-sm table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Type</th>
                            <th>Reason</th>
                            <th>Status</th>
                            <th>Reporter</th>
                            <th>Date</th>
                            <th>Reviewed By</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Report> allReports = (List<Report>) request.getAttribute("allReports");
                            if (allReports != null) {
                                for (Report report : allReports) {
                        %>
                            <tr>
                                <td>#<%= report.getId() %></td>
                                <td><%= report.getContentType() %></td>
                                <td><%= report.getReason() %></td>
                                <td>
                                    <% 
                                        String badgeClass = "secondary";
                                        if ("RESOLVED".equals(report.getStatus())) badgeClass = "success";
                                        else if ("REJECTED".equals(report.getStatus())) badgeClass = "danger";
                                        else if ("PENDING".equals(report.getStatus())) badgeClass = "warning";
                                    %>
                                    <span class="badge bg-<%= badgeClass %>"><%= report.getStatus() %></span>
                                </td>
                                <td>@<%= report.getReporter().getUsername() %></td>
                                <td><%= report.getCreatedAt().format(formatter) %></td>
                                <td>
                                    <% if (report.getReviewedBy() != null) { %>
                                        @<%= report.getReviewedBy().getUsername() %>
                                    <% } else { %>
                                        -
                                    <% } %>
                                </td>
                            </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
