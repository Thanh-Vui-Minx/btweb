<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.util.Date" %>
<div class="container container-main">
  <div class="row">
    <div class="col-md-8">
      <div class="card mb-4 glass">
        <div class="card-body">
          <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class="mb-0 gradient-text-primary">
              <i class="bi bi-bell-fill"></i> Thông báo
            </h4>
            <c:if test="${not empty notifications}">
              <form method="post" style="display: inline;">
                <input type="hidden" name="action" value="markAllAsRead" />
                <button type="submit" class="btn btn-sm btn-outline-primary">
                  <i class="bi bi-check-all"></i> Đánh dấu đã đọc
                </button>
              </form>
            </c:if>
          </div>
          
          <c:choose>
            <c:when test="${not empty notifications}">
              <div class="list-group">
                <c:forEach var="notif" items="${notifications}">
                  <div class="list-group-item list-group-item-action ${notif.read ? '' : 'bg-light'}" style="border-radius: 12px; margin-bottom: 8px; border: none;">
                    <div class="d-flex w-100 align-items-center">
                      <img src="${notif.fromUser.avatarUrl != null && !empty notif.fromUser.avatarUrl ? notif.fromUser.avatarUrl : pageContext.request.contextPath.concat('/assets/images/default-avatar.png')}" 
                           class="rounded-circle me-3" style="width: 48px; height: 48px; object-fit: cover;" alt="${notif.fromUser.displayName}">
                      <div style="flex: 1;">
                        <div class="d-flex w-100 justify-content-between">
                          <h6 class="mb-1">${notif.fromUser.displayName}</h6>
                          <small class="text-muted">
                            ${notif.createdAt.toString().substring(0, 16).replace('T', ' ')}
                          </small>
                        </div>
                        <p class="mb-1">${notif.message}</p>
                        <c:if test="${not notif.read}">
                          <span class="badge bg-primary pulse">Mới</span>
                        </c:if>
                        
                        <!-- Action buttons for specific notification types -->
                        <c:if test="${notif.contentType != null}">
                          <div class="mt-2">
                            <c:choose>
                              <c:when test="${notif.contentType == 'POST'}">
                                <a href="${pageContext.request.contextPath}/post/${notif.contentId}" class="btn btn-sm btn-outline-secondary">
                                  <i class="bi bi-arrow-right"></i> Xem bài viết
                                </a>
                              </c:when>
                              <c:when test="${notif.contentType == 'VIDEO'}">
                                <a href="${pageContext.request.contextPath}/video/${notif.contentId}" class="btn btn-sm btn-outline-secondary">
                                  <i class="bi bi-play-circle"></i> Xem video
                                </a>
                              </c:when>
                            </c:choose>
                          </div>
                        </c:if>
                        
                      </div>
                      
                      <c:if test="${not notif.read}">
                        <form method="post" style="display: inline;">
                          <input type="hidden" name="action" value="markAsRead" />
                          <input type="hidden" name="notificationId" value="${notif.id}" />
                          <button type="submit" class="btn btn-sm btn-link text-muted p-0 ms-2" title="Đánh dấu đã đọc">
                            <i class="bi bi-check"></i>
                          </button>
                        </form>
                      </c:if>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:when>
            <c:otherwise>
              <div class="text-center py-5">
                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-bell-slash text-muted float-animation" viewBox="0 0 16 16">
                  <path d="M5.164 14H15c-.299-.199-.557-.553-.78-1-.9-1.8-1.22-5.12-1.22-6 0-.264-.02-.523-.06-.776l-.938.938c.02.708.157 2.154.457 3.58.161.767.377 1.566.663 2.258H6.164l-1 1zm5.581-9.91a3.986 3.986 0 0 0-1.948-1.01L8 2.917l-.797.161A4.002 4.002 0 0 0 4 7c0 .628-.134 2.197-.459 3.742-.05.238-.105.479-.166.718l-1.653 1.653c.02-.037.04-.074.059-.113.162-.316.369-.753.639-1.3.827-1.667 1.58-4.2 1.58-5.7 0-.966.784-1.75 1.75-1.75.966 0 1.75.784 1.75 1.75 0 .316-.031.632-.095.945l1.328-1.328zM8.5 13a1.5 1.5 0 1 1-3 0h3z"/>
                  <path d="m13.646.854-13 13 .708.707 13-13-.707-.707z"/>
                </svg>
                <p class="mt-3 text-muted">Chưa có thông báo nào</p>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
    
    <div class="col-md-4">
      <div class="card gradient-card mb-4" style="color: white;">
        <div class="card-body text-center">
          <h5 class="mb-3">📊 Thống kê của bạn</h5>
          <div class="row">
            <div class="col-6 mb-3">
              <h3 class="mb-0">${stats.postCount}</h3>
              <small>Bài viết</small>
            </div>
            <div class="col-6 mb-3">
              <h3 class="mb-0">${stats.followerCount}</h3>
              <small>Người theo dõi</small>
            </div>
            <div class="col-6">
              <h3 class="mb-0">${stats.followingCount}</h3>
              <small>Đang theo dõi</small>
            </div>
            <div class="col-6">
              <h3 class="mb-0">${stats.likeCount}</h3>
              <small>Lượt thích</small>
            </div>
          </div>
        </div>
      </div>
      
      <div class="card glass">
        <div class="card-body">
          <h6 class="mb-3">🔔 Cài đặt thông báo</h6>
          <div class="form-check form-switch mb-2">
            <input class="form-check-input" type="checkbox" id="notifAll" checked>
            <label class="form-check-label" for="notifAll">
              Tất cả thông báo
            </label>
          </div>
          <div class="form-check form-switch mb-2">
            <input class="form-check-input" type="checkbox" id="notifComments" checked>
            <label class="form-check-label" for="notifComments">
              Bình luận mới
            </label>
          </div>
          <div class="form-check form-switch">
            <input class="form-check-input" type="checkbox" id="notifLikes" checked>
            <label class="form-check-label" for="notifLikes">
              Lượt thích mới
            </label>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
