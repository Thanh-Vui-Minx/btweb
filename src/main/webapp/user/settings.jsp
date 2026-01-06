<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="mb-4">
    <h2 style="color: #111827; font-weight: 700;">Cài đặt tài khoản</h2>
    <p style="color: #6B7280;">Quản lý thông tin và cài đặt tài khoản của bạn</p>
  </div>

  <c:if test="${not empty param.success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      Thông tin đã được cập nhật thành công!
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>

  <c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ${error}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>

  <div class="row">
    <!-- Thông tin cá nhân -->
    <div class="col-md-8">
      <div class="card mb-4">
        <div class="card-header">
          <h5 class="mb-0">Thông tin cá nhân</h5>
        </div>
        <div class="card-body">
          <form method="post" action="${pageContext.request.contextPath}/user/settings/update" enctype="multipart/form-data">
            <div class="mb-3">
              <label for="displayName" class="form-label">Tên hiển thị</label>
              <input type="text" class="form-control" id="displayName" name="displayName" 
                     value="${sessionScope.user.displayName}" required>
            </div>
            
            <div class="mb-3">
              <label for="email" class="form-label">Email</label>
              <input type="email" class="form-control" id="email" name="email" 
                     value="${sessionScope.user.email}" required>
            </div>
            
            <div class="mb-3">
              <label for="bio" class="form-label">Tiểu sử</label>
              <textarea class="form-control" id="bio" name="bio" rows="3" 
                        placeholder="Giới thiệu về bản thân...">${sessionScope.user.bio}</textarea>
            </div>
            
            <div class="mb-3">
              <label for="avatarUpload" class="form-label">Ảnh đại diện</label>
              <input type="file" class="form-control" id="avatarUpload" name="avatar" accept="image/*">
              <small class="text-muted">Chọn ảnh mới để thay đổi ảnh đại diện</small>
            </div>
            
            <button type="submit" class="btn btn-gradient-primary"><i class="bi bi-save"></i> Lưu thay đổi</button>
          </form>
        </div>
      </div>

      <!-- Đổi mật khẩu -->
      <div class="card card-enhanced mb-4">
        <div class="card-header">
          <h5 class="mb-0 gradient-text-pink">Đổi mật khẩu</h5>
        </div>
        <div class="card-body">
          <form method="post" action="${pageContext.request.contextPath}/user/settings/password">
            <div class="mb-3">
              <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
              <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
            </div>
            
            <div class="mb-3">
              <label for="newPassword" class="form-label">Mật khẩu mới</label>
              <input type="password" class="form-control" id="newPassword" name="newPassword" required>
            </div>
            
            <div class="mb-3">
              <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
              <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
            </div>
            
            <button type="submit" class="btn btn-gradient-pink"><i class="bi bi-shield-lock"></i> Đổi mật khẩu</button>
          </form>
        </div>
      </div>
    </div>

    <!-- Sidebar -->
    <div class="col-md-4">
      <div class="card mb-4">
        <div class="card-header">
          <h5 class="mb-0">Thông tin tài khoản</h5>
        </div>
        <div class="card-body">
          <div class="mb-3">
            <small class="text-muted">Tên đăng nhập</small>
            <p class="mb-1"><strong>@${sessionScope.user.username}</strong></p>
          </div>
          <div class="mb-3">
            <small class="text-muted">Vai trò</small>
            <p class="mb-1"><span class="badge bg-info">${sessionScope.user.role}</span></p>
          </div>
          <div class="mb-3">
            <small class="text-muted">Ngày tham gia</small>
            <p class="mb-1">${sessionScope.user.createdAt}</p>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <h5 class="mb-0">Cài đặt thông báo</h5>
        </div>
        <div class="card-body">
          <form method="post" action="${pageContext.request.contextPath}/user/settings/notifications">
            <div class="form-check form-switch mb-2">
              <input class="form-check-input" type="checkbox" id="emailNotif" name="emailNotif" checked>
              <label class="form-check-label" for="emailNotif">
                Thông báo qua email
              </label>
            </div>
            <div class="form-check form-switch mb-2">
              <input class="form-check-input" type="checkbox" id="commentNotif" name="commentNotif" checked>
              <label class="form-check-label" for="commentNotif">
                Thông báo bình luận
              </label>
            </div>
            <div class="form-check form-switch mb-3">
              <input class="form-check-input" type="checkbox" id="reactNotif" name="reactNotif" checked>
              <label class="form-check-label" for="reactNotif">
                Thông báo tương tác
              </label>
            </div>
            <button type="submit" class="btn btn-sm btn-outline-primary">Lưu cài đặt</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
