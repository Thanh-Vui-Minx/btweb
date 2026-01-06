<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="row">
    <div class="col-md-8">
      <div class="card glass mb-4">
        <div class="card-body">
          <h4 class="mb-4 gradient-text-primary">
            <i class="bi bi-fire"></i> Bài viết thịnh hành
          </h4>
          
          <c:choose>
            <c:when test="${not empty trendingPosts}">
              <c:forEach var="post" items="${trendingPosts}">
                <div class="card mb-3 card-feed">
                  <div class="card-body">
                    <div class="d-flex">
                      <img src="${post.author.avatarUrl}" class="rounded-circle me-3" style="width: 56px; height: 56px; object-fit: cover;" alt="${post.author.displayName}">
                      <div style="flex: 1;">
                        <div class="d-flex justify-content-between align-items-start">
                          <div>
                            <h6 class="mb-0">${post.author.displayName}</h6>
                            <small class="text-muted">@${post.author.username} • ${post.createdAt}</small>
                          </div>
                          <span class="trending-badge">${post.trendScore} 🔥</span>
                        </div>
                        <p class="mt-3 mb-2">${post.content}</p>
                        <c:if test="${not empty post.mediaUrl}">
                          <div class="img-zoom mb-3">
                            <img src="${post.mediaUrl}" class="img-fluid" style="max-height: 400px; border-radius: 12px; width: 100%; object-fit: cover;" alt="Post image">
                          </div>
                        </c:if>
                        <div class="d-flex gap-3 text-muted">
                          <span><i class="bi bi-heart-fill text-danger"></i> ${post.likeCount} lượt thích</span>
                          <span><i class="bi bi-chat-fill text-primary"></i> ${post.commentCount} bình luận</span>
                          <span><i class="bi bi-eye-fill text-info"></i> ${post.viewCount} lượt xem</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <div class="text-center py-5">
                <i class="bi bi-graph-up text-muted float-animation" style="font-size: 4rem;"></i>
                <p class="mt-3 text-muted">Chưa có bài viết thịnh hành</p>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
    
    <div class="col-md-4">
      <!-- Trending Topics -->
      <div class="card glass mb-4">
        <div class="card-body">
          <h6 class="mb-3">🔥 Chủ đề thịnh hành</h6>
          <div class="list-group list-group-flush">
            <a href="#" class="list-group-item list-group-item-action border-0 d-flex justify-content-between align-items-center" style="border-radius: 8px;">
              <div>
                <strong>#AlohCMUTE</strong>
                <br><small class="text-muted">1,234 bài viết</small>
              </div>
              <span class="badge bg-primary">1</span>
            </a>
            <a href="#" class="list-group-item list-group-item-action border-0 d-flex justify-content-between align-items-center" style="border-radius: 8px;">
              <div>
                <strong>#TechNews</strong>
                <br><small class="text-muted">892 bài viết</small>
              </div>
              <span class="badge bg-primary">2</span>
            </a>
            <a href="#" class="list-group-item list-group-item-action border-0 d-flex justify-content-between align-items-center" style="border-radius: 8px;">
              <div>
                <strong>#Programming</strong>
                <br><small class="text-muted">756 bài viết</small>
              </div>
              <span class="badge bg-primary">3</span>
            </a>
            <a href="#" class="list-group-item list-group-item-action border-0 d-flex justify-content-between align-items-center" style="border-radius: 8px;">
              <div>
                <strong>#Education</strong>
                <br><small class="text-muted">623 bài viết</small>
              </div>
              <span class="badge bg-primary">4</span>
            </a>
          </div>
        </div>
      </div>
      
      <!-- Top Contributors -->
      <div class="card gradient-card" style="color: white;">
        <div class="card-body">
          <h6 class="mb-3">⭐ Người đóng góp hàng đầu</h6>
          <c:forEach var="contributor" items="${topContributors}" varStatus="status">
            <div class="d-flex align-items-center mb-3">
              <span class="badge bg-light text-dark me-2" style="font-size: 1.2rem; width: 32px; height: 32px; display: flex; align-items: center; justify-content: center; border-radius: 50%;">
                ${status.index + 1}
              </span>
              <img src="${contributor.avatarUrl}" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover; border: 2px solid white;" alt="${contributor.displayName}">
              <div>
                <strong>${contributor.displayName}</strong>
                <br><small>${contributor.postCount} bài viết</small>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
</div>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
