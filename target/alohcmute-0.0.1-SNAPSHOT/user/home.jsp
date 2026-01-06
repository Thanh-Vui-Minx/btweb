<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
.home-layout {
  display: grid;
  grid-template-columns: 280px 1fr 320px;
  gap: 24px;
  max-width: 1400px;
  margin: 0 auto;
  padding: 20px;
}

.sidebar-left, .sidebar-right {
  position: sticky;
  top: 80px;
  height: fit-content;
  animation: slideInUp 0.6s ease-out;
}

@keyframes slideInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.05); }
}

.widget-card {
  background: linear-gradient(135deg, rgba(255, 255, 255, 0.98) 0%, rgba(249, 250, 255, 0.98) 100%);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.12);
  border: 1px solid rgba(102, 126, 234, 0.15);
  transition: all 0.3s ease;
}

.widget-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.2);
  border-color: rgba(102, 126, 234, 0.3);
}

.widget-title {
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  position: relative;
  padding-bottom: 8px;
}

.widget-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 40px;
  height: 3px;
  background: linear-gradient(90deg, #6366F1 0%, #8B5CF6 100%);
  border-radius: 2px;
}

.create-post-box {
  background: rgba(255, 255, 255, 1);
  border-radius: 20px;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.15);
  border: 2px solid rgba(99, 102, 241, 0.2);
  animation: fadeIn 0.5s ease-out;
  position: relative;
  overflow: hidden;
}

.create-post-box::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(99, 102, 241, 0.05) 0%, transparent 70%);
  animation: pulse 3s ease-in-out infinite;
  pointer-events: none;
  z-index: 0;
}

.create-post-box > * {
  position: relative;
  z-index: 1;
}

.create-post-textarea {
  border: 2px solid #E5E7EB;
  border-radius: 12px;
  padding: 16px;
  font-size: 15px;
  transition: all 0.3s ease;
  background: white !important;
  position: relative;
  z-index: 2;
  pointer-events: auto !important;
  user-select: text !important;
  -webkit-user-select: text !important;
  color: #111827;
}

.create-post-textarea:focus {
  border-color: #6366F1;
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.15);
}

.post-actions {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 16px;
}

.file-upload-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: white;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.file-upload-btn:hover {
  border-color: #667eea;
  background: #f8f9ff;
}

.quick-link {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 12px;
  text-decoration: none;
  color: #333;
  transition: all 0.3s ease;
  margin-bottom: 8px;
  position: relative;
}

.quick-link::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 3px;
  height: 100%;
  background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
  border-radius: 0 2px 2px 0;
  opacity: 0;
.trending-item {
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
  border-radius: 8px;
  transition: all 0.3s ease;
  cursor: pointer;
}

.trending-item:last-child {
  border-bottom: none;
}

.trending-item:hover {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.08) 0%, rgba(118, 75, 162, 0.08) 100%);
  transform: translateX(4px);
}

.trending-tag {
  font-weight: 600;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 15px;
  transition: all 0.3s ease;
}

.trending-item:hover .trending-tag {
  transform: scale(1.05);
}

.trending-count {
  font-size: 13px;
  color: #666;
  margin-top: 4px;
}
.quick-link:hover i {
  transform: scale(1.2);
  color: #667eea;
}

.trending-item {
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.trending-item:last-child {
.suggestion-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.suggestion-item:last-child {
  border-bottom: none;
}

.suggestion-item:hover {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
}

.suggestion-avatar {
  width: 40px;
  height: 40px;
  max-width: 40px;
  max-height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid transparent;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.suggestion-item:hover .suggestion-avatar {
  border-color: #667eea;
  transform: scale(1.1);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
} align-items: center;
  gap: 12px;
  padding: 12px 0;
.feed-main {
  min-height: 100vh;
  animation: fadeIn 0.5s ease-out;
}

.stat-item {
  padding: 10px;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.stat-item:hover {
  background: rgba(102, 126, 234, 0.05);
  transform: scale(1.05);
}

.btn-gradient {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  color: white;
  transition: all 0.3s ease;
}

.btn-gradient:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
  color: white;
}

@media (max-width: 1200px) {
  .home-layout {
    grid-template-columns: 1fr;
  }
  
  .sidebar-left, .sidebar-right {
    display: none;
  }
}

.suggestion-info {
  flex: 1;
}

.suggestion-name {
  font-weight: 600;
  font-size: 14px;
  color: #333;
}

.suggestion-username {
  font-size: 12px;
  color: #666;
}

.feed-main {
  min-height: 100vh;
}

@media (max-width: 1200px) {
  .home-layout {
    grid-template-columns: 1fr;
  }
  
  .sidebar-left, .sidebar-right {
    display: none;
  }
}
</style>

<div class="home-layout">
  <!-- Left Sidebar -->
  <aside class="sidebar-left">
    <div class="widget-card">
      <h3 class="widget-title">Liên Kết Nhanh</h3>
      <a href="${pageContext.request.contextPath}/user/profile" class="quick-link">
        <i class="bi bi-person-circle"></i>
        <span>Hồ Sơ Của Tôi</span>
      </a>
      <a href="${pageContext.request.contextPath}/user/messages" class="quick-link">
        <i class="bi bi-chat-dots"></i>
        <span>Tin Nhắn</span>
      </a>
      <a href="${pageContext.request.contextPath}/user/notifications" class="quick-link">
        <i class="bi bi-bell"></i>
        <span>Thông Báo</span>
      </a>
      <a href="${pageContext.request.contextPath}/user/videos" class="quick-link">
        <i class="bi bi-play-circle"></i>
        <span>Video</span>
      </a>
      <a href="${pageContext.request.contextPath}/user/settings" class="quick-link">
        <i class="bi bi-gear"></i>
        <span>Cài Đặt</span>
      </a>
    </div>
    
    <c:if test="${not empty sessionScope.user}">
      <div class="widget-card">
        <h3 class="widget-title">Thống Kê Của Bạn</h3>
        <div style="display: grid; gap: 12px;">
          <div class="stat-item" style="display: flex; justify-content: space-between;">
            <span style="color: #666;">📝 Bài viết</span>
            <strong style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent; font-size: 18px;">24</strong>
          </div>
          <div class="stat-item" style="display: flex; justify-content: space-between;">
            <span style="color: #666;">💜 Người theo dõi</span>
            <strong style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent; font-size: 18px;">${followersCount != null ? followersCount : 0}</strong>
          </div>
          <a href="${pageContext.request.contextPath}/user/following" style="text-decoration: none; color: inherit;">
            <div class="stat-item" style="display: flex; justify-content: space-between; cursor: pointer;">
              <span style="color: #666;">👥 Đang theo dõi</span>
              <strong style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent; font-size: 18px;">${followingCount != null ? followingCount : 0}</strong>
            </div>
          </a>
        </div>
      </div>
    </c:if>
  </aside>

  <!-- Main Feed -->
  <main class="feed-main">
    <c:if test="${not empty sessionScope.user}">
      <div class="create-post-box">
        <div class="d-flex align-items-center mb-3">
          <c:choose>
            <c:when test="${not empty sessionScope.user.avatarUrl}">
              <img src="${sessionScope.user.avatarUrl}" style="width: 48px; height: 48px; border-radius: 50%; object-fit: cover; margin-right: 12px;" />
            </c:when>
            <c:otherwise>
              <img src="https://via.placeholder.com/48" style="width: 48px; height: 48px; border-radius: 50%; margin-right: 12px;" />
            </c:otherwise>
          </c:choose>
          <h4 style="margin: 0; font-weight: 700; color: #111827;">Tạo Bài Viết</h4>
        </div>
        
        <form id="createPostForm" method="post" action="${pageContext.request.contextPath}/post/create" enctype="multipart/form-data" onsubmit="return validatePostForm()">
          <textarea id="postContent" name="content" class="form-control create-post-textarea" rows="4" placeholder="Bạn đang nghĩ gì, ${sessionScope.user.displayName}?"></textarea>
          
          <div class="post-actions">
            <label class="file-upload-btn">
              <i class="bi bi-image"></i>
              <span id="fileLabel">Thêm Ảnh</span>
              <input type="file" id="mediaFile" name="media" accept="image/*" style="display: none;" onchange="updateFileLabel()" />
            </label>
            
            <button class="btn btn-primary btn-gradient ms-auto" type="submit" style="padding: 10px 32px; border-radius: 10px; font-weight: 600;">
              <i class="bi bi-send"></i> Đăng
            </button>
          </div>
          <div id="postError" class="text-danger mt-2" style="display: none; font-size: 14px;"></div>
        </form>
        
        <script>
        function validatePostForm() {
          const content = document.getElementById('postContent').value.trim();
          const media = document.getElementById('mediaFile').files.length;
          const errorDiv = document.getElementById('postError');
          
          if (!content && media === 0) {
            errorDiv.textContent = 'Vui lòng nhập nội dung hoặc chọn ảnh!';
            errorDiv.style.display = 'block';
            return false;
          }
          
          errorDiv.style.display = 'none';
          return true;
        }
        
        function updateFileLabel() {
          const fileInput = document.getElementById('mediaFile');
          const label = document.getElementById('fileLabel');
          if (fileInput.files.length > 0) {
            label.textContent = fileInput.files[0].name;
          } else {
            label.textContent = 'Thêm Ảnh';
          }
        }
        </script>
      </div>
    </c:if>
    
    <c:if test="${empty sessionScope.user}">
      <div class="widget-card text-center" style="margin-bottom: 24px; background: linear-gradient(135deg, rgba(102, 126, 234, 0.08) 0%, rgba(118, 75, 162, 0.08) 100%);">
        <div style="font-size: 48px; margin-bottom: 16px;">👋</div>
        <h4 style="margin-bottom: 12px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">Chào mừng đến ALOHCMUTE!</h4>
        <p style="color: #666; margin-bottom: 16px;">Tham gia cộng đồng để chia sẻ và kết nối với mọi người.</p>
        <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-primary btn-gradient" style="padding: 10px 32px; border-radius: 10px;">Đăng Nhập</a>
      </div>
    </c:if>

    <c:choose>
      <c:when test="${not empty posts}">
        <c:forEach var="p" items="${posts}">
          <div class="card mb-3 card-feed p-4">
            <div class="d-flex">
              <div class="me-3">
                <c:choose>
                  <c:when test="${not empty p.author.avatarUrl}">
                    <img src="${p.author.avatarUrl}" class="profile-avatar" style="width: 48px; height: 48px;" />
                  </c:when>
                  <c:otherwise>
                    <img src="https://via.placeholder.com/48" class="profile-avatar" style="width: 48px; height: 48px;" />
                  </c:otherwise>
                </c:choose>
              </div>
              <div style="flex:1">
                <div class="d-flex justify-content-between align-items-start">
                  <div>
                    <strong style="font-size: 16px;">${p.author.displayName}</strong>
                    <div><small class="muted">@${p.author.username} • ${p.createdAt}</small></div>
                  </div>
                </div>
                
                <div class="mt-3" style="font-size: 15px; line-height: 1.6;"><c:out value="${p.content}"/></div>
                
                <c:if test="${not empty p.mediaUrl}">
                  <div class="mt-3">
                    <img src="${p.mediaUrl}" class="img-fluid" style="max-height:500px; width: 100%; object-fit: cover; border-radius: 12px;"/>
                  </div>
                </c:if>
                
                <div class="mt-3 pt-3" style="border-top: 1px solid #f0f0f0;">
                  <c:set var="rmap" value="${reactionsMap[p.id]}" />
                  <div class="d-flex gap-2 align-items-center flex-wrap">
                    <c:choose>
                      <c:when test="${not empty sessionScope.user}">
                        <form method="post" action="${pageContext.request.contextPath}/post/react" style="display:inline-flex; gap: 8px;">
                          <input type="hidden" name="postId" value="${p.id}" />
                          <button class="btn btn-light btn-sm" name="emoji" value="heart" style="border-radius: 10px; padding: 6px 14px;" title="Yêu thích - Chỉ được nhấn 1 lần, nhấn lại để hủy">
                            <i class="bi bi-heart-fill" style="color: #e74c3c;"></i> Yêu thích <c:out value="${rmap['heart']}" default="0"/>
                          </button>
                          <button class="btn btn-light btn-sm" name="emoji" value="thumb" style="border-radius: 10px; padding: 6px 14px;" title="Thích - Chỉ được nhấn 1 lần, nhấn lại để hủy">
                            <i class="bi bi-hand-thumbs-up-fill" style="color: #3498db;"></i> Thích <c:out value="${rmap['thumb']}" default="0"/>
                          </button>
                          <button class="btn btn-light btn-sm" name="emoji" value="laugh" style="border-radius: 10px; padding: 6px 14px;" title="Cười - Chỉ được nhấn 1 lần, nhấn lại để hủy">
                            😂 Cười <c:out value="${rmap['laugh']}" default="0"/>
                          </button>
                        </form>
                        
                        <button class="btn btn-light btn-sm" style="border-radius: 10px; padding: 6px 14px;" onclick="toggleCommentForm(${p.id})" title="Bình luận - Có thể bình luận nhiều lần">
                          <i class="bi bi-chat"></i> Bình luận
                        </button>
                      </c:when>
                      <c:otherwise>
                        <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/auth/login" style="border-radius: 10px;">
                          <i class="bi bi-heart"></i> Thả tim
                        </a>
                        <a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/auth/login" style="border-radius: 10px;">
                          <i class="bi bi-chat"></i> Bình luận
                        </a>
                      </c:otherwise>
                    </c:choose>
                    
                    <c:if test="${not empty sessionScope.user and (sessionScope.user.role == 'ADMIN' or sessionScope.user.id == p.author.id)}">
                      <form method="post" action="${pageContext.request.contextPath}/post/delete" style="display:inline-block; margin-left: auto;">
                        <input type="hidden" name="postId" value="${p.id}" />
                        <button class="btn btn-outline-danger btn-sm" type="submit" onclick="return confirm('Xóa bài viết này?');" style="border-radius: 10px;">
                          <i class="bi bi-trash"></i> Xóa
                        </button>
                      </form>
                    </c:if>
                    
                    <c:if test="${not empty sessionScope.user and sessionScope.user.id != p.author.id}">
                      <button class="btn btn-outline-warning btn-sm" style="border-radius: 10px; margin-left: auto;" 
                              onclick="showReportModal(${p.id}, ${p.author.id}, 'POST')">
                        <i class="bi bi-flag"></i> Báo cáo
                      </button>
                    </c:if>
                  </div>
                </div>

                <div class="mt-3">
                  <c:set var="clist" value="${commentsMap[p.id]}" />
                  <c:if test="${not empty clist}">
                    <div style="background: #f8f9fa; border-radius: 12px; padding: 16px; margin-top: 16px;">
                      <h6 style="margin-bottom: 12px; font-weight: 600;">Bình luận</h6>
                      <c:forEach var="c" items="${clist}">
                        <div class="mb-3 pb-3" style="border-bottom: 1px solid #e0e0e0;">
                          <div class="d-flex gap-2 align-items-start">
                            <div style="flex: 1;">
                              <div class="d-flex gap-2">
                                <strong style="font-size: 14px;">${c.author.displayName}</strong>
                                <small class="muted">${c.createdAt}</small>
                              </div>
                              <div style="margin-top: 4px; font-size: 14px;">${c.content}</div>
                            </div>
                            <c:if test="${not empty sessionScope.user and sessionScope.user.id != c.author.id}">
                              <button class="btn btn-outline-warning btn-sm" style="border-radius: 8px; padding: 4px 8px;" 
                                      onclick="showReportModal(${c.id}, ${c.author.id}, 'COMMENT')" title="Report comment">
                                <i class="bi bi-flag" style="font-size: 12px;"></i>
                              </button>
                            </c:if>
                          </div>
                        </div>
                      </c:forEach>
                    </div>
                  </c:if>
                  
                  <c:if test="${not empty sessionScope.user}">
                    <div id="comment-form-${p.id}" style="margin-top: 16px; display: none;">
                      <form method="post" action="${pageContext.request.contextPath}/post/comment">
                        <input type="hidden" name="postId" value="${p.id}" />
                        <div class="input-group">
                          <input name="content" class="form-control" placeholder="Viết bình luận..." style="border-radius: 10px 0 0 10px; border: 2px solid #e0e0e0;" required />
                          <button class="btn btn-primary" type="submit" style="border-radius: 0 10px 10px 0;">
                            <i class="bi bi-send"></i> Gửi
                          </button>
                        </div>
                      </form>
                    </div>
                  </c:if>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="widget-card text-center">
          <i class="bi bi-inbox" style="font-size: 48px; color: #ccc;"></i>
          <h5 style="margin-top: 16px;">No posts yet</h5>
          <p style="color: #666;">Be the first to share something!</p>
        </div>
      </c:otherwise>
    </c:choose>
  </main>

  <!-- Right Sidebar -->
  <aside class="sidebar-right">
    <div class="widget-card">
      <h3 class="widget-title">Trending Topics</h3>
      <div class="trending-item">
        <div class="trending-tag">#TechNews</div>
        <div class="trending-count">1.2K posts</div>
      </div>
      <div class="trending-item">
        <div class="trending-tag">#HCMUTE</div>
        <div class="trending-count">856 posts</div>
      </div>
      <div class="trending-item">
        <div class="trending-tag">#StudentLife</div>
        <div class="trending-count">642 posts</div>
      </div>
      <div class="trending-item">
        <div class="trending-tag">#Programming</div>
        <div class="trending-count">523 posts</div>
      </div>
      <a href="${pageContext.request.contextPath}/user/trending" style="color: #667eea; text-decoration: none; font-size: 14px; margin-top: 12px; display: inline-block;">
        View all trends <i class="bi bi-arrow-right"></i>
      </a>
    </div>

    <div class="widget-card">
      <h3 class="widget-title">Gợi Ý Kết Bạn</h3>
      <c:choose>
        <c:when test="${not empty suggestedUsers}">
          <c:forEach var="sugUser" items="${suggestedUsers}">
            <div class="suggestion-item">
              <c:choose>
                <c:when test="${not empty sugUser.avatarUrl}">
                  <img src="${sugUser.avatarUrl}" class="suggestion-avatar" alt="${sugUser.displayName}" style="width: 40px !important; height: 40px !important; max-width: 40px !important; max-height: 40px !important; min-width: 40px !important; min-height: 40px !important; object-fit: cover !important;" />
                </c:when>
                <c:otherwise>
                  <img src="https://via.placeholder.com/40" class="suggestion-avatar" alt="${sugUser.displayName}" style="width: 40px !important; height: 40px !important; max-width: 40px !important; max-height: 40px !important; min-width: 40px !important; min-height: 40px !important; object-fit: cover !important;" />
                </c:otherwise>
              </c:choose>
              <div class="suggestion-info">
                <div class="suggestion-name">${sugUser.displayName}</div>
                <div class="suggestion-username">@${sugUser.username}</div>
              </div>
              <c:set var="isFollowing" value="${followStatusMap[sugUser.id]}" />
              <button class="btn btn-sm ${isFollowing ? 'btn-success following' : 'btn-outline-primary'} btn-follow" 
                      data-user-id="${sugUser.id}" 
                      data-user-name="${sugUser.displayName}"
                      style="border-radius: 8px; border-width: 2px; transition: all 0.3s ease; ${isFollowing ? 'background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border-color: transparent;' : ''}" 
                      onclick="toggleFollow(this)"
                      onmouseover="this.style.background='linear-gradient(135deg, #667eea 0%, #764ba2 100%)'; this.style.color='white'; this.style.borderColor='transparent';" 
                      onmouseout="if(!this.classList.contains('following')) { this.style.background=''; this.style.color=''; this.style.borderColor=''; }">
                ${isFollowing ? 'Đang theo dõi' : 'Theo dõi'}
              </button>
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="text-center py-3 text-muted">
            <small>Chưa có gợi ý nào</small>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

    <div class="widget-card" style="font-size: 12px; color: #666;">
      <div style="display: flex; flex-wrap: wrap; gap: 12px; margin-bottom: 12px;">
        <a href="${pageContext.request.contextPath}/info/about" style="color: #666; text-decoration: none; transition: color 0.2s;" onmouseover="this.style.color='#667eea'" onmouseout="this.style.color='#666'">Giới thiệu</a>
        <a href="${pageContext.request.contextPath}/info/help" style="color: #666; text-decoration: none; transition: color 0.2s;" onmouseover="this.style.color='#667eea'" onmouseout="this.style.color='#666'">Trợ giúp</a>
        <a href="${pageContext.request.contextPath}/info/privacy" style="color: #666; text-decoration: none; transition: color 0.2s;" onmouseover="this.style.color='#667eea'" onmouseout="this.style.color='#666'">Quyền riêng tư</a>
        <a href="${pageContext.request.contextPath}/info/terms" style="color: #666; text-decoration: none; transition: color 0.2s;" onmouseover="this.style.color='#667eea'" onmouseout="this.style.color='#666'">Điều khoản</a>
      </div>
      <div style="color: #999; font-size: 11px;">
        © 2025 ALOHCMUTE - Mạng xã hội sinh viên
        <br>
        <span style="font-size: 10px;">Phiên bản 1.0.0</span>
      </div>
    </div>
  </aside>
</div>

<!-- Report Modal -->
<div class="modal fade" id="reportModal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">🚩 Report Content</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <form method="post" action="${pageContext.request.contextPath}/report/submit">
        <div class="modal-body">
          <input type="hidden" name="contentType" id="reportContentType" />
          <input type="hidden" name="contentId" id="reportContentId" />
          <input type="hidden" name="reportedUserId" id="reportedUserId" />
          <input type="hidden" name="redirectUrl" value="${pageContext.request.requestURL}" />
          
          <div class="mb-3">
            <label class="form-label">Reason for reporting:</label>
            <select name="reason" class="form-select" required>
              <option value="">Select a reason...</option>
              <option value="SPAM">Spam</option>
              <option value="HARASSMENT">Harassment or bullying</option>
              <option value="VIOLENCE">Violence or dangerous content</option>
              <option value="SENSITIVE_CONTENT">Sensitive or disturbing content</option>
              <option value="HATE_SPEECH">Hate speech</option>
              <option value="OTHER">Other</option>
            </select>
          </div>
          
          <div class="mb-3">
            <label class="form-label">Additional details (optional):</label>
            <textarea name="description" class="form-control" rows="3" 
                      placeholder="Please provide more context..."></textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-danger">Submit Report</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
function showReportModal(contentId, reportedUserId, contentType) {
  document.getElementById('reportContentId').value = contentId;
  document.getElementById('reportedUserId').value = reportedUserId;
  document.getElementById('reportContentType').value = contentType;
  new bootstrap.Modal(document.getElementById('reportModal')).show();
}

function toggleFollow(button) {
  const userId = button.getAttribute('data-user-id');
  const userName = button.getAttribute('data-user-name');
  const isFollowing = button.classList.contains('following');
  
  // Disable button during request
  button.disabled = true;
  const originalText = button.textContent;
  button.textContent = 'Đang xử lý...';
  
  const url = isFollowing ? '${pageContext.request.contextPath}/user/unfollow' : '${pageContext.request.contextPath}/user/follow';
  
  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: 'userId=' + encodeURIComponent(userId)
  })
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      // Toggle button state
      if (isFollowing) {
        // Was following, now unfollowed
        button.classList.remove('following');
        button.classList.remove('btn-success');
        button.classList.add('btn-outline-primary');
        button.textContent = 'Theo dõi';
        button.style.background = '';
        button.style.color = '';
        button.style.borderColor = '';
      } else {
        // Was not following, now following
        button.classList.add('following');
        button.classList.remove('btn-outline-primary');
        button.classList.add('btn-success');
        button.textContent = 'Đang theo dõi';
        button.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
        button.style.color = 'white';
        button.style.borderColor = 'transparent';
      }
      
      // Show success message
      showToast(data.message, 'success');
    } else {
      // Show error message
      showToast(data.message, 'error');
      button.textContent = originalText;
    }
  })
  .catch(error => {
    console.error('Error:', error);
    showToast('Đã xảy ra lỗi khi thực hiện hành động', 'error');
    button.textContent = originalText;
  })
  .finally(() => {
    button.disabled = false;
  });
}

function showToast(message, type) {
  // Create toast element
  const toast = document.createElement('div');
  toast.className = 'toast-notification ' + type;
  toast.textContent = message;
  
  // Set styles
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
  
  // Remove after 3 seconds
  setTimeout(() => {
    toast.style.animation = 'slideOut 0.3s ease-out';
    setTimeout(() => {
      document.body.removeChild(toast);
    }, 300);
  }, 3000);
}

// Toggle comment form
function toggleCommentForm(postId) {
    const form = document.getElementById('comment-form-' + postId);
    if (form) {
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }
}

// Add CSS animations
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