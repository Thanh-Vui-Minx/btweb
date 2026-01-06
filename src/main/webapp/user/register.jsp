<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container container-main">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card p-4">
        <h3 class="mb-3">Create your account</h3>
        <c:if test="${param.error == 'missing'}"><div class="alert alert-danger">Vui lòng nhập email và mật khẩu.</div></c:if>
        <c:if test="${param.error == 'exists'}"><div class="alert alert-warning">Email đã tồn tại, vui lòng đăng nhập hoặc dùng email khác.</div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/auth/register">
          <div class="mb-3">
            <label class="form-label">Name</label>
            <input class="form-control" name="name" type="text" />
          </div>
          <div class="mb-3">
            <label class="form-label">Email</label>
            <input class="form-control" name="email" type="email" />
          </div>
          <div class="mb-3">
            <label class="form-label">Password</label>
            <input class="form-control" name="password" type="password" />
          </div>
          <button class="btn btn-success w-100" type="submit">Create account</button>
        </form>
        <div class="mt-3 text-center muted">Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login</a></div>
      </div>
    </div>
  </div>
</div>
