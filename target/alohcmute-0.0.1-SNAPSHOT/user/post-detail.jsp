<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container container-main mt-4">
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card mb-4 glass">
        <div class="card-body">
          <div class="d-flex align-items-center mb-3">
            <img src="${post.author.avatarUrl != null && !empty post.author.avatarUrl ? post.author.avatarUrl : pageContext.request.contextPath.concat('/assets/images/default-avatar.png')}" 
                 class="rounded-circle me-3" style="width: 50px; height: 50px; object-fit: cover;" alt="${post.author.displayName}">
            <div>
              <h6 class="mb-0">${post.author.displayName}</h6>
              <small class="text-muted">
                ${post.createdAt.toString().substring(0, 16).replace('T', ' ')}
              </small>
            </div>
          </div>
          
          <div class="mb-3">
            <p class="mb-2">${post.content}</p>
            <c:if test="${post.mediaUrl != null && !empty post.mediaUrl}">
              <img src="${post.mediaUrl}" class="img-fluid rounded" style="max-height: 400px; width: auto;" alt="Post Image">
            </c:if>
          </div>
          
          <div class="d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
              <button class="btn btn-link p-0 me-3 reaction-btn" data-post-id="${post.id}" data-reaction="thumb">
                <i class="bi bi-hand-thumbs-up"></i> Thích
              </button>
              <button class="btn btn-link p-0 me-3 reaction-btn" data-post-id="${post.id}" data-reaction="heart">
                <i class="bi bi-heart"></i> Yêu thích
              </button>
              <button class="btn btn-link p-0 me-3" onclick="toggleCommentForm(${post.id})">
                <i class="bi bi-chat"></i> Bình luận
              </button>
            </div>
          </div>
          
          <!-- Comment form -->
          <div id="comment-form-${post.id}" class="mt-3" style="display: none;">
            <form action="${pageContext.request.contextPath}/post/comment" method="post" class="comment-form">
              <input type="hidden" name="postId" value="${post.id}" />
              <div class="input-group">
                <textarea class="form-control" name="content" rows="2" placeholder="Viết bình luận..." required></textarea>
                <button type="submit" class="btn btn-primary">Gửi</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      
      <div class="text-center">
        <a href="${pageContext.request.contextPath}/user/home" class="btn btn-outline-primary">
          <i class="bi bi-arrow-left"></i> Quay lại trang chủ
        </a>
      </div>
    </div>
  </div>
</div>

<script>
function toggleCommentForm(postId) {
    const form = document.getElementById('comment-form-' + postId);
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

// Handle reaction buttons
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.reaction-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const postId = this.dataset.postId;
            const reactionType = this.dataset.reaction;
            
            fetch('${pageContext.request.contextPath}/reaction', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `postId=${postId}&reactionType=${reactionType}`
            }).then(response => {
                if (response.ok) {
                    // Toggle button appearance
                    this.classList.toggle('text-primary');
                }
            });
        });
    });
});
</script>