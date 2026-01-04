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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="<%= request.getContextPath() %>/user/home">🎓 ALOHCMUTE</a>
            <span class="navbar-text text-light">
                🛡️ Bảng điều khiển kiểm duyệt
            </span>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="<%= request.getContextPath() %>/debug-data">
                    <i class="bi bi-bug"></i> Gỡ lỗi
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h1><i class="bi bi-shield-exclamation"></i> Hàng đợi kiểm duyệt</h1>
                <p class="text-muted">Quản lý nội dung bị báo cáo và vi phạm của người dùng</p>
            </div>
        </div>
        
        <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success alert-dismissible fade show">
                <i class="bi bi-check-circle"></i> Action completed successfully!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>
        
        <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show">
                <i class="bi bi-exclamation-triangle"></i> Error: <%= request.getParameter("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <!-- Stats Cards -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card text-bg-warning">
                    <div class="card-body text-center">
                        <h2><%= request.getAttribute("pendingCount") != null ? request.getAttribute("pendingCount") : "0" %></h2>
                        <p class="mb-0"><i class="bi bi-clock"></i> Pending Reports</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-bg-success">
                    <div class="card-body text-center">
                        <h2>
                            <% 
                                List<Report> allReports = (List<Report>) request.getAttribute("allReports");
                                long resolvedCount = 0;
                                if (allReports != null) {
                                    resolvedCount = allReports.stream().filter(r -> "RESOLVED".equals(r.getStatus())).count();
                                }
                            %>
                            <%= resolvedCount %>
                        </h2>
                        <p class="mb-0"><i class="bi bi-check-circle"></i> Resolved</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-bg-danger">
                    <div class="card-body text-center">
                        <h2>
                            <% 
                                long rejectedCount = 0;
                                if (allReports != null) {
                                    rejectedCount = allReports.stream().filter(r -> "REJECTED".equals(r.getStatus())).count();
                                }
                            %>
                            <%= rejectedCount %>
                        </h2>
                        <p class="mb-0"><i class="bi bi-x-circle"></i> Rejected</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-bg-info">
                    <div class="card-body text-center">
                        <h2><%= allReports != null ? allReports.size() : "0" %></h2>
                        <p class="mb-0"><i class="bi bi-list"></i> Total Reports</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Pending Reports -->
        <div class="card mb-4">
            <div class="card-header bg-warning text-dark">
                <h5 class="mb-0">
                    <i class="bi bi-exclamation-triangle"></i> Pending Reports 
                    (<%= request.getAttribute("pendingCount") != null ? request.getAttribute("pendingCount") : "0" %>)
                </h5>
            </div>
            <div class="card-body">
                <%
                    List<Report> pendingReports = (List<Report>) request.getAttribute("pendingReports");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    
                    if (pendingReports == null || pendingReports.isEmpty()) {
                %>
                    <div class="text-center py-4">
                        <i class="bi bi-check-circle-fill text-success" style="font-size: 3rem;"></i>
                        <h4 class="text-success mt-2">No Pending Reports!</h4>
                        <p class="text-muted">Great job! All reports have been reviewed.</p>
                    </div>
                <%
                    } else {
                        for (Report report : pendingReports) {
                %>
                    <div class="card mb-3 border-warning">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="d-flex gap-2 mb-2">
                                        <span class="badge bg-danger"><%= report.getReason() %></span>
                                        <span class="badge bg-secondary"><%= report.getContentType() %></span>
                                        <span class="badge bg-warning text-dark">PENDING</span>
                                    </div>
                                    <h6 class="card-title">Report #<%= report.getId() %></h6>
                                    <div class="card-text">
                                        <p><strong><i class="bi bi-file-text"></i> Content ID:</strong> #<%= report.getContentId() %></p>
                                        <p><strong><i class="bi bi-person"></i> Reporter:</strong> 
                                           <%= report.getReporter().getDisplayName() %> 
                                           (<code>@<%= report.getReporter().getUsername() %></code>)</p>
                                        <% if (report.getReportedUser() != null) { %>
                                            <p><strong><i class="bi bi-person-exclamation"></i> Reported User:</strong> 
                                               <%= report.getReportedUser().getDisplayName() %>
                                               (<code>@<%= report.getReportedUser().getUsername() %></code>)</p>
                                        <% } %>
                                        <p><strong><i class="bi bi-calendar"></i> Submitted:</strong> 
                                           <%= report.getCreatedAt().format(formatter) %></p>
                                        <% if (report.getDescription() != null && !report.getDescription().isEmpty()) { %>
                                            <p><strong><i class="bi bi-chat-quote"></i> Description:</strong></p>
                                            <blockquote class="blockquote-footer">
                                                "<%= report.getDescription() %>"
                                            </blockquote>
                                        <% } %>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="d-grid gap-2">
                                        <form method="post" action="<%= request.getContextPath() %>/moderator/action">
                                            <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                            <input type="hidden" name="action" value="approve_delete">
                                            <div class="mb-2">
                                                <textarea name="reviewNote" class="form-control form-control-sm" 
                                                          placeholder="Review note (optional)..." rows="2"></textarea>
                                            </div>
                                            <button type="submit" class="btn btn-danger btn-sm w-100">
                                                <i class="bi bi-trash"></i> Delete Content
                                            </button>
                                        </form>
                                        
                                        <% if (report.getReportedUser() != null) { %>
                                        <form method="post" action="<%= request.getContextPath() %>/moderator/action">
                                            <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                            <input type="hidden" name="action" value="temp_ban">
                                            <button type="submit" class="btn btn-warning btn-sm w-100">
                                                <i class="bi bi-person-slash"></i> Temp Ban (7d)
                                            </button>
                                        </form>
                                        <% } %>
                                        
                                        <form method="post" action="<%= request.getContextPath() %>/moderator/action">
                                            <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                            <input type="hidden" name="action" value="reject">
                                            <button type="submit" class="btn btn-secondary btn-sm w-100">
                                                <i class="bi bi-x-lg"></i> Reject Report
                                            </button>
                                        </form>
                                    </div>
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

        <!-- All Reports History -->
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0"><i class="bi bi-list-ul"></i> Reports History</h5>
            </div>
            <div class="card-body">
                <% if (allReports == null || allReports.isEmpty()) { %>
                    <p class="text-muted text-center">No reports found. 
                       <a href="<%= request.getContextPath() %>/seed-reports">Create sample reports</a>
                    </p>
                <% } else { %>
                    <div class="table-responsive">
                        <table class="table table-sm table-hover">
                            <thead class="table-dark">
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
                                    for (Report report : allReports) {
                                %>
                                    <tr>
                                        <td><strong>#<%= report.getId() %></strong></td>
                                        <td><%= report.getContentType() %></td>
                                        <td><%= report.getReason() %></td>
                                        <td>
                                            <% 
                                                String badgeClass = "secondary";
                                                if ("RESOLVED".equals(report.getStatus())) badgeClass = "success";
                                                else if ("REJECTED".equals(report.getStatus())) badgeClass = "danger";
                                                else if ("PENDING".equals(report.getStatus())) badgeClass = "warning text-dark";
                                            %>
                                            <span class="badge bg-<%= badgeClass %>"><%= report.getStatus() %></span>
                                        </td>
                                        <td><code>@<%= report.getReporter().getUsername() %></code></td>
                                        <td><%= report.getCreatedAt().format(formatter) %></td>
                                        <td>
                                            <% if (report.getReviewedBy() != null) { %>
                                                <code>@<%= report.getReviewedBy().getUsername() %></code>
                                            <% } else { %>
                                                <span class="text-muted">-</span>
                                            <% } %>
                                        </td>
                                    </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                <% } %>
            </div>
        </div>

        <!-- Quick Actions -->
        <div class="mt-4 text-center">
            <a href="<%= request.getContextPath() %>/seed-reports" class="btn btn-outline-primary">
                <i class="bi bi-plus-circle"></i> Seed Sample Reports
            </a>
            <a href="<%= request.getContextPath() %>/debug-data" class="btn btn-outline-info">
                <i class="bi bi-bug"></i> Debug Data
            </a>
            <a href="<%= request.getContextPath() %>/user/home" class="btn btn-outline-success">
                <i class="bi bi-house"></i> Home
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>