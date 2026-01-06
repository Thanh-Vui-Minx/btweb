<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card p-4">
        <h3 class="mb-3">Đăng nhập ALOHCMUTE</h3>
        <c:if test="${param.error == 'missing'}"><div class="alert alert-danger">Vui lòng nhập email/username và mật khẩu.</div></c:if>
        <c:if test="${param.error == 'invalid'}"><div class="alert alert-danger">Email/Username hoặc mật khẩu không đúng.</div></c:if>
        <c:if test="${param.error == 'banned'}"><div class="alert alert-danger">Tài khoản của bạn đã bị khóa. Vui lòng liên hệ admin.</div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/auth/login">
          <div class="mb-3">
            <label class="form-label">Email hoặc Username</label>
            <input class="form-control" name="email" type="text" placeholder="Nhập email hoặc username" required />
            <small class="form-text text-muted">Bạn có thể đăng nhập bằng email hoặc username</small>
          </div>
          <div class="mb-3">
            <label class="form-label">Mật khẩu</label>
            <input class="form-control" name="password" type="password" placeholder="Nhập mật khẩu" required />
          </div>
          <button class="btn btn-primary w-100" type="submit">Đăng nhập</button>
        </form>
        <div class="mt-3 text-center muted">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/auth/register">Đăng ký ngay</a></div>
      </div>
    </div>
  </div>
</div>
