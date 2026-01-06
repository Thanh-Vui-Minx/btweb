<%@ page language="java" %>
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
            <span class="navbar-text text-light">🛡️ Moderator Panel</span>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="<%= request.getContextPath() %>/debug-data">
                    <i class="bi bi-bug"></i> Debug
                </a>
                <a class="nav-link" href="<%= request.getContextPath() %>/simple-moderator-queue">
                    <i class="bi bi-shield-check"></i> Simple
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1><i class="bi bi-shield-exclamation"></i> Hàng đợi kiểm duyệt</h1>
        <p class="text-muted">Quản lý nội dung bị báo cáo và vi phạm của người dùng</p>

        <!-- Quick Stats -->
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="card text-bg-warning">
                    <div class="card-body text-center">
                        <h2>
                            <%= request.getAttribute("pendingCount") != null ? request.getAttribute("pendingCount") : "0" %>
                        </h2>
                        <p class="mb-0"><i class="bi bi-clock"></i> Chờ xử lý</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-bg-info">
                    <div class="card-body text-center">
                        <h2>
                            <%
                                List<Report> allReports = (List<Report>) request.getAttribute("allReports");
                                int totalReports = allReports != null ? allReports.size() : 0;
                            %>
                            <%= totalReports %>
                        </h2>
                        <p class="mb-0"><i class="bi bi-list"></i> Tổng cộng</p>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5><i class="bi bi-info-circle"></i> Hành động nhanh</h5>
                        <a href="<%= request.getContextPath() %>/seed-reports" class="btn btn-sm btn-outline-primary">Seed Reports</a>
                        <a href="<%= request.getContextPath() %>/simple-moderator-queue" class="btn btn-sm btn-outline-secondary">Simple View</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Pending Reports -->
        <div class="card mb-4">
            <div class="card-header bg-warning text-dark">
                <h5 class="mb-0"><i class="bi bi-exclamation-triangle"></i> Pending Reports</h5>
            </div>
            <div class="card-body">
                <%
                    List<Report> pendingReports = (List<Report>) request.getAttribute("pendingReports");
                    if (pendingReports == null || pendingReports.isEmpty()) {
                %>
                    <div class="text-center py-4">
                        <i class="bi bi-check-circle-fill text-success" style="font-size: 3rem;"></i>
                        <h4 class="text-success mt-2">No Pending Reports!</h4>
                        <p class="text-muted">All reports have been reviewed.</p>
                        <a href="<%= request.getContextPath() %>/seed-reports" class="btn btn-primary">Create Sample Reports</a>
                    </div>
                <%
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
                                    <p><strong>Content ID:</strong> #<%= report.getContentId() %></p>
                                    <% if (report.getReporter() != null) { %>
                                        <p><strong>Reporter:</strong> <%= report.getReporter().getDisplayName() %> 
                                           (@<%= report.getReporter().getUsername() %>)</p>
                                    <% } else { %>
                                        <p><strong>Reporter:</strong> <em>Unknown user</em></p>
                                    <% } %>
                                    <% if (report.getReportedUser() != null) { %>
                                        <p><strong>Reported User:</strong> <%= report.getReportedUser().getDisplayName() %>
                                           (@<%= report.getReportedUser().getUsername() %>)</p>
                                    <% } %>
                                    <p><strong>Date:</strong> <%= report.getCreatedAt().format(formatter) %></p>
                                    <% if (report.getDescription() != null && !report.getDescription().isEmpty()) { %>
                                        <p><strong>Description:</strong> <%= report.getDescription() %></p>
                                    <% } %>
                                </div>
                                <div class="col-md-4">
                                    <div class="d-grid gap-2">
                                        <form method="post" action="<%= request.getContextPath() %>/moderator/action">
                                            <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                            <input type="hidden" name="action" value="approve_delete">
                                            <textarea name="reviewNote" class="form-control form-control-sm mb-2" 
                                                      placeholder="Review note..." rows="2"></textarea>
                                            <button type="submit" class="btn btn-danger btn-sm w-100 mb-1">
                                                <i class="bi bi-trash"></i> Delete Content
                                            </button>
                                        </form>
                                        
                                        <% if (report.getReportedUser() != null) { %>
                                        <form method="post" action="<%= request.getContextPath() %>/moderator/action">
                                            <input type="hidden" name="reportId" value="<%= report.getId() %>">
                                            <input type="hidden" name="action" value="temp_ban">
                                            <button type="submit" class="btn btn-warning btn-sm w-100 mb-1">
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

        <!-- All Reports Table -->
        <% if (allReports != null && !allReports.isEmpty()) { %>
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0"><i class="bi bi-list-ul"></i> All Reports History</h5>
            </div>
            <div class="card-body">
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
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                                for (Report report : allReports) {
                                    String badgeClass = "secondary";
                                    if ("RESOLVED".equals(report.getStatus())) badgeClass = "success";
                                    else if ("REJECTED".equals(report.getStatus())) badgeClass = "danger";
                                    else if ("PENDING".equals(report.getStatus())) badgeClass = "warning text-dark";
                            %>
                                <tr>
                                    <td><strong>#<%= report.getId() %></strong></td>
                                    <td><%= report.getContentType() %></td>
                                    <td><%= report.getReason() %></td>
                                    <td><span class="badge bg-<%= badgeClass %>"><%= report.getStatus() %></span></td>
                                    <td>@<%= report.getReporter().getUsername() %></td>
                                    <td><%= report.getCreatedAt().format(formatter) %></td>
                                </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <% } %>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>