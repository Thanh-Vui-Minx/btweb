<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.following-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
  border-radius: 16px;
  padding: 30px;
  margin-bottom: 30px;
  text-align: center;
}

.page-title {
  font-size: 32px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  margin-bottom: 16px;
}

.stats-row {
  display: flex;
  gap: 24px;
  justify-content: center;
  margin-top: 20px;
}

.stat-box {
  background: white;
  padding: 16px 32px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.stat-label {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.following-list {
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.12);
  padding: 24px;
}

.following-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px;
  border-bottom: 1px solid #f0f0f0;
  transition: all 0.3s ease;
}

.following-item:last-child {
  border-bottom: none;
}

.following-item:hover {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
  transform: translateX(4px);
}

.user-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  transition: all 0.3s ease;
}

.following-item:hover .user-avatar {
  transform: scale(1.1);
  border-color: #667eea;
}

.user-info {
  flex: 1;
}

.user-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 4px;
}

.user-username {
  font-size: 14px;
  color: #666;
}

.btn-unfollow {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 10px 24px;
  border-radius: 10px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.btn-unfollow:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
  color: white;
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
  color: #999;
}

.empty-icon {
  font-size: 64px;
  margin-bottom: 20px;
}

.back-link {
  display: inline-block;
  margin-bottom: 20px;
  color: #667eea;
  text-decoration: none;
  font-weight: 600;
  transition: all 0.3s ease;
}

.back-link:hover {
  transform: translateX(-4px);
  color: #764ba2;
}
</style>

<div class="following-container">
  <a href="${pageContext.request.contextPath}/user/home" class="back-link">
    <i class="bi bi-arrow-left"></i> Quay lại trang chủ
  </a>
  
  <div class="page-header">
    <h1 class="page-title">Đang Theo Dõi</h1>
    <p style="color: #666; font-size: 16px;">Quản lý danh sách người bạn đang theo dõi</p>
    
    <div class="stats-row">
      <div class="stat-box">
        <div class="stat-label">👥 Đang theo dõi</div>
        <div class="stat-value">${followingCount}</div>
      </div>
      <div class="stat-box">
        <div class="stat-label">💜 Người theo dõi</div>
        <div class="stat-value">${followersCount}</div>
      </div>
    </div>
  </div>
  
  <div class="following-list">
    <c:choose>
      <c:when test="${not empty followingUsers}">
        <c:forEach var="user" items="${followingUsers}">
          <div class="following-item">
            <c:choose>
              <c:when test="${not empty user.avatarUrl}">
                <img src="${user.avatarUrl}" class="user-avatar" alt="${user.displayName}" />
              </c:when>
              <c:otherwise>
                <img src="https://via.placeholder.com/60" class="user-avatar" alt="${user.displayName}" />
              </c:otherwise>
            </c:choose>
            
            <div class="user-info">
              <div class="user-name">${user.displayName}</div>
              <div class="user-username">@${user.username}</div>
            </div>
            
            <button class="btn btn-unfollow" 
                    data-user-id="${user.id}" 
                    data-user-name="${user.displayName}"
                    onclick="unfollowUser(this)">
              Bỏ theo dõi
            </button>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="empty-state">
          <div class="empty-icon">👥</div>
          <h3 style="color: #666; margin-bottom: 12px;">Chưa theo dõi ai</h3>
          <p style="color: #999;">Hãy khám phá và kết nối với mọi người!</p>
          <a href="${pageContext.request.contextPath}/user/home" class="btn btn-primary" style="margin-top: 20px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none;">
            Khám phá ngay
          </a>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>

<script>
function unfollowUser(button) {
  const userId = button.getAttribute('data-user-id');
  const userName = button.getAttribute('data-user-name');
  
  if (!confirm('Bạn có chắc muốn bỏ theo dõi ' + userName + '?')) {
    return;
  }
  
  button.disabled = true;
  button.textContent = 'Đang xử lý...';
  
  fetch('${pageContext.request.contextPath}/user/unfollow', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: 'userId=' + encodeURIComponent(userId)
  })
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      // Remove the item from list with animation
      const item = button.closest('.following-item');
      item.style.animation = 'slideOut 0.3s ease-out';
      setTimeout(() => {
        item.remove();
        
        // Check if list is empty
        const list = document.querySelector('.following-list');
        if (list.querySelectorAll('.following-item').length === 0) {
          location.reload(); // Reload to show empty state
        }
      }, 300);
      
      showToast(data.message, 'success');
    } else {
      showToast(data.message, 'error');
      button.disabled = false;
      button.textContent = 'Bỏ theo dõi';
    }
  })
  .catch(error => {
    console.error('Error:', error);
    showToast('Đã xảy ra lỗi khi bỏ theo dõi', 'error');
    button.disabled = false;
    button.textContent = 'Bỏ theo dõi';
  });
}

function showToast(message, type) {
  const toast = document.createElement('div');
  toast.className = 'toast-notification ' + type;
  toast.textContent = message;
  
  toast.style.position = 'fixed';
  toast.style.top = '20px';
  toast.style.right = '20px';
  toast.style.padding = '16px 24px';
  toast.style.background = (type === 'success') ? 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' : '#e74c3c';
  toast.style.color = 'white';
  toast.style.borderRadius = '12px';
  toast.style.boxShadow = '0 4px 12px rgba(0,0,0,0.2)';
  toast.style.zIndex = '9999';
  toast.style.fontWeight = '600';
  toast.style.animation = 'slideIn 0.3s ease-out';
  
  document.body.appendChild(toast);
  
  setTimeout(() => {
    toast.style.animation = 'slideOut 0.3s ease-out';
    setTimeout(() => {
      document.body.removeChild(toast);
    }, 300);
  }, 3000);
}

const style = document.createElement('style');
style.textContent = `
  @keyframes slideIn {
    from {
      transform: translateX(400px);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }
  
  @keyframes slideOut {
    from {
      transform: translateX(0);
      opacity: 1;
    }
    to {
      transform: translateX(400px);
      opacity: 0;
    }
  }
`;
document.head.appendChild(style);
</script>
