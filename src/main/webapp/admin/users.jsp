<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="py-4">
    <h1>Admin - Users</h1>
    <p>Danh sách người dùng (thực tế).</p>
  
  <!-- Success/Error Messages -->
  <c:if test="${param.success == 'passwordChanged'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      ✓ Mật khẩu đã được thay đổi thành công!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  
  <c:if test="${param.error == 'passwordTooShort'}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ❌ Mật khẩu phải có ít nhất 6 ký tự!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  
  <c:if test="${param.error == 'passwordFailed'}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ❌ Không thể thay đổi mật khẩu. Vui lòng thử lại!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  
  <c:if test="${param.error == 'missing'}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ❌ Thiếu thông tin cần thiết!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  
  <c:if test="${param.error == 'invalidId'}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ❌ ID người dùng không hợp lệ!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  
  <c:if test="${param.error == 'notfound'}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ❌ Không tìm thấy người dùng!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  
  <table class="table table-striped">
    <thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Status</th><th>Actions</th></tr></thead>
    <tbody>
      <c:forEach var="u" items="${users}">
        <tr>
          <td>${u.id}</td>
          <td>${u.displayName}</td>
          <td>${u.email}</td>
          <td>
            <c:choose>
              <c:when test="${u.role == 'ADMIN'}">
                <span class="badge bg-danger">ADMIN</span>
              </c:when>
              <c:when test="${u.role == 'MODERATOR'}">
                <span class="badge bg-warning">MODERATOR</span>
              </c:when>
              <c:otherwise>
                <span class="badge bg-secondary">USER</span>
              </c:otherwise>
            </c:choose>
          </td>
          <td>
            <c:choose>
              <c:when test="${u.active}">
                <span class="badge bg-success">Active</span>
              </c:when>
              <c:otherwise>
                <span class="badge bg-dark">Banned</span>
              </c:otherwise>
            </c:choose>
          </td>
          <td>
            <form method="post" action="${pageContext.request.contextPath}/admin/action" style="display:inline">
              <input type="hidden" name="userId" value="${u.id}" />
              <c:choose>
                <c:when test="${u.active}">
                  <button class="btn btn-sm btn-danger" name="action" value="ban">Ban</button>
                </c:when>
                <c:otherwise>
                  <button class="btn btn-sm btn-success" name="action" value="unban">Unban</button>
                </c:otherwise>
              </c:choose>
            </form>
            
            <form method="post" action="${pageContext.request.contextPath}/admin/action" style="display:inline; margin-left:6px;" id="roleForm${u.id}">
              <input type="hidden" name="userId" value="${u.id}" />
              <input type="hidden" name="action" value="changeRole" />
              <select name="newRole" class="form-select form-select-sm" style="display:inline-block; width:auto;" size="1" onchange="changeRole(this, '${u.role}')">
                <option value="">-- Change Role --</option>
                <option value="USER" ${u.role == 'USER' ? 'selected' : ''}>USER</option>
                <option value="MODERATOR" ${u.role == 'MODERATOR' ? 'selected' : ''}>MODERATOR</option>
                <option value="ADMIN" ${u.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
              </select>
            </form>
            
            <button class="btn btn-sm btn-warning" style="margin-left:6px;" 
                    data-bs-toggle="modal" 
                    data-bs-target="#passwordModal${u.id}">
              🔑 Change Password
            </button>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
</div>

<!-- Password Change Modals - Moved outside the table to avoid DOM issues -->
<c:forEach var="u" items="${users}">
  <!-- Password Change Modal -->
  <div class="modal fade" id="passwordModal${u.id}" tabindex="-1" aria-labelledby="passwordModalLabel${u.id}" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="passwordModalLabel${u.id}">🔑 Đổi Mật Khẩu cho ${u.displayName}</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <form method="post" action="${pageContext.request.contextPath}/admin/action" onsubmit="return validatePassword('${u.id}')">
          <div class="modal-body">
            <input type="hidden" name="userId" value="${u.id}" />
            <input type="hidden" name="action" value="changePassword" />
            
            <div class="mb-3">
              <label class="form-label">Username:</label>
              <input type="text" class="form-control" value="${u.username}" readonly />
            </div>
            
            <div class="mb-3">
              <label class="form-label">Email:</label>
              <input type="text" class="form-control" value="${u.email}" readonly />
            </div>
            
            <div class="mb-3">
              <label for="newPassword${u.id}" class="form-label">Mật khẩu mới: *</label>
              <input type="text" 
                     class="form-control" 
                     id="newPassword${u.id}" 
                     name="newPassword" 
                     placeholder="Nhập mật khẩu mới..."
                     required 
                     minlength="6" 
                     maxlength="100" />
              <div class="form-text">Mật khẩu tối thiểu 6 ký tự, tối đa 100 ký tự</div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            <button type="submit" class="btn btn-primary">💾 Lưu mật khẩu mới</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</c:forEach>

<script>
function changeRole(selectElement, currentRole) {
  const newRole = selectElement.value;
  
  // If empty option selected, reset to current
  if (!newRole) {
    selectElement.value = currentRole;
    return;
  }
  
  // If same as current, do nothing
  if (newRole === currentRole) {
    return;
  }
  
  // Confirm change
  if (confirm('Xác nhận thay đổi role từ ' + currentRole + ' sang ' + newRole + '?')) {
    selectElement.form.submit();
  } else {
    // Reset to current value
    selectElement.value = currentRole;
  }
}

function validatePassword(userId) {
  const passwordInput = document.getElementById('newPassword' + userId);
  const password = passwordInput.value.trim();
  
  if (password.length < 6) {
    alert('Mật khẩu phải có ít nhất 6 ký tự!');
    passwordInput.focus();
    return false;
  }
  
  if (password.length > 100) {
    alert('Mật khẩu không được vượt quá 100 ký tự!');
    passwordInput.focus();
    return false;
  }
  
  if (confirm('Xác nhận thay đổi mật khẩu cho người dùng này?')) {
    return true;
  }
  
  return false;
}

// Debug modal issues
document.addEventListener('DOMContentLoaded', function() {
  // Ensure Bootstrap is loaded
  if (typeof bootstrap === 'undefined') {
    console.error('Bootstrap is not loaded!');
    return;
  }
  
  // Add event listeners for modal debugging
  const modals = document.querySelectorAll('.modal');
  modals.forEach(modal => {
    modal.addEventListener('show.bs.modal', function (event) {
      console.log('Modal showing:', modal.id);
    });
    
    modal.addEventListener('shown.bs.modal', function (event) {
      console.log('Modal shown:', modal.id);
      // Focus on password input when modal is fully shown
      const passwordInput = modal.querySelector('input[name="newPassword"]');
      if (passwordInput) {
        setTimeout(() => passwordInput.focus(), 100);
      }
    });
    
    modal.addEventListener('hide.bs.modal', function (event) {
      console.log('Modal hiding:', modal.id);
    });
  });
});
</script>
