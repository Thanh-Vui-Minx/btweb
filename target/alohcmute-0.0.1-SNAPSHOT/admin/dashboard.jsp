<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="py-4">
    <div class="d-flex justify-content-between align-items-center">
      <div>
        <h2>Admin Dashboard</h2>
        <p class="muted">Overview metrics.</p>
      </div>
      <div>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-md-4"><div class="card p-3">Users: <strong>${userCount}</strong></div></div>
      <div class="col-md-4"><div class="card p-3">Posts: <strong>${postCount}</strong></div></div>
      <div class="col-md-4"><div class="card p-3">Reports: <strong>--</strong></div></div>
    </div>
  </div>
</div>
