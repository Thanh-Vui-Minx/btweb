<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container container-main">
  <div class="row">
    <!-- Main Video Player -->
    <div class="col-lg-8">
      <div class="card card-enhanced mb-4">
        <div class="video-player-container">
          <video id="videoPlayer" class="w-100" controls autoplay style="max-height: 600px; background: #000; border-radius: 16px 16px 0 0;">
            <source src="${pageContext.request.contextPath}${video.videoUrl}" type="video/mp4">
            Trình duyệt của bạn không hỗ trợ video tag.
          </video>
        </div>
        <div class="card-body">
          <h3 class="mb-3">${video.title}</h3>
          
          <div class="d-flex align-items-center justify-content-between mb-3">
            <div class="d-flex align-items-center">
              <c:choose>
                <c:when test="${not empty video.author.avatarUrl}">
                  <c:choose>
                    <c:when test="${fn:startsWith(video.author.avatarUrl, 'http')}">
                      <img src="${video.author.avatarUrl}" class="rounded-circle me-3" style="width: 48px; height: 48px; object-fit: cover;" alt="${video.author.displayName}">
                    </c:when>
                    <c:otherwise>
                      <img src="${pageContext.request.contextPath}${video.author.avatarUrl}" class="rounded-circle me-3" style="width: 48px; height: 48px; object-fit: cover;" alt="${video.author.displayName}">
                    </c:otherwise>
                  </c:choose>
                </c:when>
                <c:otherwise>
                  <div class="rounded-circle me-3 bg-secondary d-flex align-items-center justify-content-center" style="width: 48px; height: 48px;">
                    <i class="bi bi-person-fill text-white"></i>
                  </div>
                </c:otherwise>
              </c:choose>
              <div>
                <h6 class="mb-0">${video.author.displayName}</h6>
                <small class="text-muted">${video.author.username}</small>
              </div>
            </div>
            
            <div class="d-flex gap-2">
              <button class="btn btn-outline-primary btn-sm" id="likeBtn" onclick="handleLike()">
                <i class="bi bi-hand-thumbs-up" id="likeIcon"></i> <span id="likeText">Thích</span>
              </button>
              <button class="btn btn-outline-secondary btn-sm" onclick="handleShare()">
                <i class="bi bi-share"></i> Chia sẻ
              </button>
            </div>
          </div>
          
          <div class="video-stats mb-3">
            <span class="badge bg-light text-dark me-2">
              <i class="bi bi-eye-fill"></i> ${video.views} lượt xem
            </span>
            <span class="badge bg-light text-dark">
              <i class="bi bi-calendar3"></i> ${video.createdAt}
            </span>
          </div>
          
          <c:if test="${not empty video.description}">
            <div class="card bg-light">
              <div class="card-body">
                <h6 class="mb-2">Mô tả</h6>
                <p class="mb-0" style="white-space: pre-wrap;">${video.description}</p>
              </div>
            </div>
          </c:if>
        </div>
      </div>
      
      <!-- Comments Section -->
      <div class="card card-enhanced">
        <div class="card-body">
          <h5 class="mb-4">
            <i class="bi bi-chat-dots-fill"></i> Bình luận
          </h5>
          
          <c:if test="${not empty sessionScope.user}">
            <div class="mb-4">
              <div class="d-flex align-items-start">
                <c:choose>
                  <c:when test="${not empty sessionScope.user.avatarUrl}">
                    <c:choose>
                      <c:when test="${fn:startsWith(sessionScope.user.avatarUrl, 'http')}">
                        <img src="${sessionScope.user.avatarUrl}" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;" alt="${sessionScope.user.displayName}">
                      </c:when>
                      <c:otherwise>
                        <img src="${pageContext.request.contextPath}${sessionScope.user.avatarUrl}" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;" alt="${sessionScope.user.displayName}">
                      </c:otherwise>
                    </c:choose>
                  </c:when>
                  <c:otherwise>
                    <div class="rounded-circle me-2 bg-secondary d-flex align-items-center justify-content-center" style="width: 40px; height: 40px;">
                      <i class="bi bi-person-fill text-white"></i>
                    </div>
                  </c:otherwise>
                </c:choose>
                <div style="flex: 1;">
                  <textarea id="commentInput" class="form-control" rows="2" placeholder="Viết bình luận..."></textarea>
                  <div class="mt-2 text-end">
                    <button class="btn btn-primary btn-sm" onclick="handleComment()">Bình luận</button>
                  </div>
                </div>
              </div>
            </div>
          </c:if>
          
          <div class="alert alert-light text-center" id="noComments">
            <i class="bi bi-chat-dots"></i> Chưa có bình luận nào
          </div>
          
          <!-- Comments List -->
          <div id="commentsList"></div>
        </div>
      </div>
    </div>
    
    <!-- Sidebar - Related Videos -->
    <div class="col-lg-4">
      <div class="card card-enhanced">
        <div class="card-body">
          <h5 class="mb-3">
            <i class="bi bi-collection-play-fill"></i> Video liên quan
          </h5>
          
          <div class="alert alert-light text-center">
            <small class="text-muted">Sẽ có thêm video liên quan ở đây</small>
          </div>
        </div>
      </div>
      
      <div class="mt-3">
        <a href="${pageContext.request.contextPath}/user/videos" class="btn btn-outline-primary w-100">
          <i class="bi bi-arrow-left"></i> Quay lại danh sách
        </a>
      </div>
    </div>
  </div>
</div>

<style>
  .video-player-container {
    position: relative;
    background: #000;
  }
  
  .video-stats .badge {
    font-weight: 500;
    padding: 8px 12px;
  }
  
  .comment-item {
    padding: 12px;
    border-bottom: 1px solid #E5E7EB;
    animation: slideIn 0.3s ease-out;
  }
  
  .comment-item:last-child {
    border-bottom: none;
  }
  
  @keyframes slideIn {
    from {
      opacity: 0;
      transform: translateY(-10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
  
  .btn-liked {
    background: var(--gradient-primary) !important;
    color: white !important;
    border-color: transparent !important;
  }
</style>

<script>
  const videoId = ${video.id};
  const contextPath = '${pageContext.request.contextPath}';
  let isLiked = false;
  let likeCount = 0;
  let comments = [];
  
  console.log('Video ID:', videoId);
  console.log('Context Path:', contextPath);
  
  // Load initial data
  document.addEventListener('DOMContentLoaded', function() {
    loadLikeStatus();
    loadComments();
    
    const commentInput = document.getElementById('commentInput');
    if (commentInput) {
      commentInput.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && e.ctrlKey) {
          handleComment();
        }
      });
    }
  });
  
  // Load like status
  function loadLikeStatus() {
    fetch(contextPath + '/api/video/like?videoId=' + videoId)
      .then(response => response.json())
      .then(data => {
        if (data.success) {
          isLiked = data.liked || false;
          likeCount = data.likeCount || 0;
          updateLikeButton();
        }
      })
      .catch(error => console.error('Error loading like status:', error));
  }
  
  // Load comments
  function loadComments() {
    fetch(contextPath + '/api/video/comment?videoId=' + videoId)
      .then(response => response.json())
      .then(data => {
        if (data.success) {
          comments = data.comments || [];
          renderComments();
          
          if (comments.length > 0) {
            document.getElementById('noComments').style.display = 'none';
          }
        }
      })
      .catch(error => console.error('Error loading comments:', error));
  }
  
  // Like button handler
  function handleLike() {
    console.log('Sending like request for video:', videoId);
    
    fetch(contextPath + '/api/video/like?videoId=' + videoId, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    .then(response => {
      console.log('Like response status:', response.status);
      return response.json();
    })
    .then(data => {
      console.log('Like response data:', data);
      if (data.success) {
        isLiked = data.liked;
        likeCount = data.likeCount;
        updateLikeButton();
        showNotification(isLiked ? 'Đã thích video!' : 'Đã bỏ thích', 'success');
      } else {
        showNotification(data.message || 'Có lỗi xảy ra', 'error');
      }
    })
    .catch(error => {
      console.error('Error:', error);
      showNotification('Không thể kết nối đến server', 'error');
    });
  }
  
  // Update like button UI
  function updateLikeButton() {
    const likeBtn = document.getElementById('likeBtn');
    const likeIcon = document.getElementById('likeIcon');
    const likeText = document.getElementById('likeText');
    
    if (isLiked) {
      likeBtn.classList.add('btn-liked');
      likeIcon.classList.remove('bi-hand-thumbs-up');
      likeIcon.classList.add('bi-hand-thumbs-up-fill');
      likeText.textContent = 'Đã thích (' + likeCount + ')';
      
      // Animation
      likeBtn.style.transform = 'scale(1.1)';
      setTimeout(() => {
        likeBtn.style.transform = 'scale(1)';
      }, 200);
    } else {
      likeBtn.classList.remove('btn-liked');
      likeIcon.classList.remove('bi-hand-thumbs-up-fill');
      likeIcon.classList.add('bi-hand-thumbs-up');
      likeText.textContent = likeCount > 0 ? 'Thích (' + likeCount + ')' : 'Thích';
    }
  }
  
  // Share button handler
  function handleShare() {
    // Show share modal
    const modal = document.createElement('div');
    modal.innerHTML = `
      <div class="modal fade" id="shareModal" tabindex="-1">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Chia sẻ video</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <div class="mb-3">
                <label class="form-label">Nội dung chia sẻ (tùy chọn)</label>
                <textarea class="form-control" id="shareMessage" rows="3" placeholder="Nhập nội dung..."></textarea>
              </div>
              <div class="d-flex gap-2">
                <button class="btn btn-primary flex-fill" onclick="shareToProfile()">
                  <i class="bi bi-person"></i> Chia sẻ lên trang cá nhân
                </button>
                <button class="btn btn-outline-secondary flex-fill" onclick="copyVideoLink()">
                  <i class="bi bi-link-45deg"></i> Sao chép link
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    `;
    document.body.appendChild(modal);
    const modalInstance = new bootstrap.Modal(document.getElementById('shareModal'));
    modalInstance.show();
    
    // Clean up modal on close
    document.getElementById('shareModal').addEventListener('hidden.bs.modal', function() {
      modal.remove();
    });
  }
  
  // Share to profile
  function shareToProfile() {
    const shareMessage = document.getElementById('shareMessage').value.trim();
    
    const params = new URLSearchParams();
    params.append('videoId', videoId);
    params.append('message', shareMessage);
    
    fetch(contextPath + '/api/video/share', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: params.toString()
    })
    .then(response => response.json())
    .then(data => {
      if (data.success) {
        showNotification('Đã chia sẻ video lên trang cá nhân!', 'success');
        bootstrap.Modal.getInstance(document.getElementById('shareModal')).hide();
      } else {
        showNotification(data.message || 'Có lỗi xảy ra', 'error');
      }
    })
    .catch(error => {
      console.error('Error:', error);
      showNotification('Không thể kết nối đến server', 'error');
    });
  }
  
  // Copy video link
  function copyVideoLink() {
    const videoUrl = window.location.href;
    navigator.clipboard.writeText(videoUrl).then(() => {
      showNotification('Đã sao chép link vào clipboard!', 'success');
      bootstrap.Modal.getInstance(document.getElementById('shareModal')).hide();
    }).catch(() => {
      showNotification('Không thể sao chép link', 'error');
    });
  }
  
  // Comment handler
  function handleComment() {
    const commentInput = document.getElementById('commentInput');
    const commentText = commentInput.value.trim();
    
    if (!commentText) {
      showNotification('Vui lòng nhập nội dung bình luận', 'warning');
      return;
    }
    
    console.log('Sending comment for video:', videoId, 'content:', commentText);
    
    const params = new URLSearchParams();
    params.append('videoId', videoId);
    params.append('content', commentText);
    
    fetch(contextPath + '/api/video/comment', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: params.toString()
    })
    .then(response => {
      console.log('Comment response status:', response.status);
      return response.json();
    })
    .then(data => {
      console.log('Comment response data:', data);
      if (data.success) {
        comments.unshift(data.comment);
        renderComments();
        commentInput.value = '';
        document.getElementById('noComments').style.display = 'none';
        showNotification('Đã thêm bình luận!', 'success');
      } else {
        showNotification(data.message || 'Có lỗi xảy ra', 'error');
      }
    })
    .catch(error => {
      console.error('Error:', error);
      showNotification('Không thể kết nối đến server', 'error');
    });
  }
  
  // Render comments
  function renderComments() {
    const commentsList = document.getElementById('commentsList');
    commentsList.innerHTML = comments.map(comment => {
      let avatarHtml;
      if (comment.avatarUrl) {
        const avatarSrc = comment.avatarUrl.startsWith('http') ? comment.avatarUrl : contextPath + comment.avatarUrl;
        avatarHtml = '<img src="' + avatarSrc + '" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;">';
      } else {
        avatarHtml = '<div class="rounded-circle me-2 bg-secondary d-flex align-items-center justify-content-center" style="width: 40px; height: 40px;"><i class="bi bi-person-fill text-white"></i></div>';
      }
      
      return '<div class="comment-item d-flex align-items-start">' +
        avatarHtml +
        '<div style="flex: 1;">' +
          '<div class="d-flex justify-content-between align-items-start">' +
            '<strong>' + escapeHtml(comment.author) + '</strong>' +
            '<small class="text-muted">' + comment.timestamp + '</small>' +
          '</div>' +
          '<p class="mb-0 mt-1">' + escapeHtml(comment.text) + '</p>' +
        '</div>' +
      '</div>';
    }).join('');
  }
  
  // Escape HTML
  function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
  }
  
  // Show notification
  function showNotification(message, type) {
    const alertClass = type === 'success' ? 'alert-success' : 
                      type === 'error' ? 'alert-danger' : 'alert-warning';
    
    const notification = document.createElement('div');
    notification.className = 'alert ' + alertClass + ' alert-dismissible fade show position-fixed';
    notification.style.cssText = 'top: 100px; right: 20px; z-index: 9999; min-width: 300px;';
    notification.innerHTML = 
      escapeHtml(message) +
      '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
      notification.remove();
    }, 3000);
  }
</script>
