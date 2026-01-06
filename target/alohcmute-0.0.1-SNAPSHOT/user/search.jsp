<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="card glass mb-4">
    <div class="card-body">
      <h4 class="mb-4 gradient-text-primary">
        <i class="bi bi-search"></i> Tìm kiếm
      </h4>
      <form method="get" action="${pageContext.request.contextPath}/user/search" class="mb-4">
        <div class="input-group input-group-lg">
          <input type="text" name="q" class="form-control" placeholder="Tìm kiếm người dùng, bài viết..." value="${param.q}" autofocus>
          <button class="btn btn-gradient-primary" type="submit">
            <i class="bi bi-search"></i> Tìm kiếm
          </button>
        </div>
      </form>
      
      <ul class="nav nav-tabs nav-tabs-enhanced mb-3" id="searchTabs" role="tablist">
        <li class="nav-item" role="presentation">
          <button class="nav-link active" id="users-tab" data-bs-toggle="tab" data-bs-target="#users" type="button">
            Người dùng
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button class="nav-link" id="posts-tab" data-bs-toggle="tab" data-bs-target="#posts" type="button">
            Bài viết
          </button>
        </li>
      </ul>
      
      <div class="tab-content" id="searchTabContent">
        <!-- Users Tab -->
        <div class="tab-pane fade show active" id="users" role="tabpanel">
          <c:choose>
            <c:when test="${not empty users}">
              <div class="row row-cols-1 row-cols-md-2 g-4">
                <c:forEach var="user" items="${users}">
                  <div class="col">
                    <div class="card card-enhanced h-100">
                      <div class="card-body">
                        <div class="d-flex align-items-center">
                          <img src="${user.avatarUrl}" class="rounded-circle me-3 img-zoom" style="width: 64px; height: 64px; object-fit: cover;" alt="${user.displayName}">
                          <div style="flex: 1;">
                            <h5 class="mb-1">${user.displayName}</h5>
                            <p class="text-muted mb-2">@${user.username}</p>
                            <c:if test="${not empty user.bio}">
                              <small class="text-muted">${user.bio}</small>
                            </c:if>
                          </div>
                        </div>
                        <div class="mt-3">
                          <a href="${pageContext.request.contextPath}/user/profile?id=${user.id}" class="btn btn-sm btn-outline-primary">
                            Xem trang cá nhân
                          </a>
                          <c:if test="${not empty sessionScope.user and sessionScope.user.id ne user.id}">
                            <button class="btn btn-sm btn-gradient-blue">
                              <i class="bi bi-person-plus"></i> Theo dõi
                            </button>
                          </c:if>
                        </div>
                      </div>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:when>
            <c:otherwise>
              <div class="text-center py-5">
                <i class="bi bi-person-x text-muted" style="font-size: 4rem;"></i>
                <p class="mt-3 text-muted">Không tìm thấy người dùng nào</p>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
        
        <!-- Posts Tab -->
        <div class="tab-pane fade" id="posts" role="tabpanel">
          <c:choose>
            <c:when test="${not empty searchPosts}">
              <c:forEach var="post" items="${searchPosts}">
                <div class="card mb-3" style="border-radius: 16px;">
                  <div class="card-body">
                    <div class="d-flex">
                      <img src="${post.author.avatarUrl}" class="rounded-circle me-3" style="width: 48px; height: 48px; object-fit: cover;" alt="${post.author.displayName}">
                      <div style="flex: 1;">
                        <div class="d-flex justify-content-between">
                          <div>
                            <strong>${post.author.displayName}</strong>
                            <small class="text-muted ms-2">@${post.author.username} • ${post.createdAt}</small>
                          </div>
                        </div>
                        <p class="mt-2 mb-2">${post.content}</p>
                        <c:if test="${not empty post.mediaUrl}">
                          <div class="img-zoom">
                            <img src="${post.mediaUrl}" class="img-fluid" style="max-height: 300px; border-radius: 12px;" alt="Post image">
                          </div>
                        </c:if>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <div class="text-center py-5">
                <i class="bi bi-file-earmark-text text-muted" style="font-size: 4rem;"></i>
                <p class="mt-3 text-muted">Không tìm thấy bài viết nào</p>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</div>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
