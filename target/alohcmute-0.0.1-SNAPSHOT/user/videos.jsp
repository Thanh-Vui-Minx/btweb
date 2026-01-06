<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <c:if test="${param.upload == 'success'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      <i class="bi bi-check-circle-fill"></i> Video đã được đăng thành công!
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>
  
  <c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      <i class="bi bi-exclamation-triangle-fill"></i> ${error}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>
  
  <!-- Video Header Card -->
  <div class="card mb-4" style="border-radius: 16px; border: 1px solid rgba(99,102,241,0.15); box-shadow: 0 4px 8px rgba(0,0,0,.12);">
    <div class="card-body" style="padding: 24px;">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2 style="color: #111827; font-weight: 700; margin-bottom: 8px;">Video ngắn</h2>
          <p style="color: #4B5563; font-size: 15px; margin: 0;">Khám phá các video thú vị từ cộng đồng</p>
        </div>
        <c:if test="${not empty sessionScope.user}">
          <button class="btn btn-gradient-primary" data-bs-toggle="modal" data-bs-target="#uploadVideoModal">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16">
              <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
              <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
            </svg>
            Đăng video
          </button>
        </c:if>
      </div>

      <!-- Filter tabs -->
      <ul class="nav nav-tabs nav-tabs-enhanced" id="videoTabs" role="tablist" style="margin: 0; border: none;">
        <li class="nav-item" role="presentation">
          <button class="nav-link active" id="trending-tab" data-bs-toggle="tab" data-bs-target="#trending" type="button" role="tab">
            🔥 Thịnh hành
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button class="nav-link" id="following-tab" data-bs-toggle="tab" data-bs-target="#following" type="button" role="tab">
            👥 Đang theo dõi
          </button>
        </li>
        <li class="nav-item" role="presentation">
          <button class="nav-link" id="recent-tab" data-bs-toggle="tab" data-bs-target="#recent" type="button" role="tab">
            🕒 Gần đây
          </button>
        </li>
      </ul>
    </div>
  </div>

  <!-- Video grid -->
  <div class="tab-content" id="videoTabContent">
    <div class="tab-pane fade show active" id="trending" role="tabpanel">
      <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <c:choose>
          <c:when test="${not empty videos}">
            <c:forEach var="video" items="${videos}">
              <div class="col">
                <a href="${pageContext.request.contextPath}/user/video/${video.id}" class="text-decoration-none">
                  <div class="card h-100 video-card">
                    <div class="video-thumbnail position-relative">
                      <c:choose>
                        <c:when test="${not empty video.thumbnailUrl}">
                          <img src="${pageContext.request.contextPath}${video.thumbnailUrl}" class="card-img-top" alt="${video.title}">
                        </c:when>
                        <c:otherwise>
                          <div class="bg-gradient-primary d-flex align-items-center justify-content-center" style="height: 250px;">
                            <i class="bi bi-play-circle-fill text-white" style="font-size: 4rem; opacity: 0.8;"></i>
                          </div>
                        </c:otherwise>
                      </c:choose>
                      <span class="badge bg-dark position-absolute bottom-0 end-0 m-2">
                        <i class="bi bi-eye"></i> ${video.views}
                      </span>
                      <div class="play-overlay position-absolute top-50 start-50 translate-middle">
                        <i class="bi bi-play-circle-fill text-white" style="font-size: 3rem; opacity: 0.9;"></i>
                      </div>
                    </div>
                    <div class="card-body">
                      <div class="d-flex align-items-start">
                        <c:choose>
                          <c:when test="${not empty video.author.avatarUrl}">
                            <c:choose>
                              <c:when test="${fn:startsWith(video.author.avatarUrl, 'http')}">
                                <img src="${video.author.avatarUrl}" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;" alt="${video.author.displayName}">
                              </c:when>
                              <c:otherwise>
                                <img src="${pageContext.request.contextPath}${video.author.avatarUrl}" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;" alt="${video.author.displayName}">
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
                          <h6 class="card-title mb-1 text-dark">${video.title}</h6>
                          <small class="text-muted d-block">${video.author.displayName}</small>
                          <small class="text-muted">${video.views} lượt xem</small>
                        </div>
                      </div>
                      <c:if test="${not empty video.description}">
                        <p class="card-text mt-2 text-muted small text-truncate">${video.description}</p>
                      </c:if>
                    </div>
                  </div>
                </a>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <div class="col-12">
              <div class="alert alert-light text-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-camera-video mb-3" viewBox="0 0 16 16">
                  <path fill-rule="evenodd" d="M0 5a2 2 0 0 1 2-2h7.5a2 2 0 0 1 1.983 1.738l3.11-1.382A1 1 0 0 1 16 4.269v7.462a1 1 0 0 1-1.406.913l-3.111-1.382A2 2 0 0 1 9.5 13H2a2 2 0 0 1-2-2V5zm11.5 5.175 3.5 1.556V4.269l-3.5 1.556v4.35zM2 4a1 1 0 0 0-1 1v6a1 1 0 0 0 1 1h7.5a1 1 0 0 0 1-1V5a1 1 0 0 0-1-1H2z"/>
                </svg>
                <p class="mb-0">Chưa có video nào. Hãy là người đầu tiên đăng video!</p>
              </div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    
    <div class="tab-pane fade" id="following" role="tabpanel">
      <c:choose>
        <c:when test="${empty sessionScope.user}">
          <div class="alert alert-warning text-center">
            <i class="bi bi-exclamation-triangle-fill"></i>
            Vui lòng <a href="${pageContext.request.contextPath}/auth/login" class="alert-link">đăng nhập</a> để xem video từ người bạn theo dõi
          </div>
        </c:when>
        <c:when test="${not empty followingVideos}">
          <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <c:forEach var="video" items="${followingVideos}">
              <div class="col">
                <a href="${pageContext.request.contextPath}/user/video/${video.id}" class="text-decoration-none">
                  <div class="card h-100 video-card">
                    <div class="video-thumbnail position-relative">
                      <c:choose>
                        <c:when test="${not empty video.thumbnailUrl}">
                          <img src="${pageContext.request.contextPath}${video.thumbnailUrl}" class="card-img-top" alt="${video.title}">
                        </c:when>
                        <c:otherwise>
                          <div class="bg-gradient-primary d-flex align-items-center justify-content-center" style="height: 250px;">
                            <i class="bi bi-play-circle-fill text-white" style="font-size: 4rem; opacity: 0.8;"></i>
                          </div>
                        </c:otherwise>
                      </c:choose>
                      <span class="badge bg-dark position-absolute bottom-0 end-0 m-2">
                        <i class="bi bi-eye"></i> ${video.views}
                      </span>
                      <div class="play-overlay position-absolute top-50 start-50 translate-middle">
                        <i class="bi bi-play-circle-fill text-white" style="font-size: 3rem; opacity: 0.9;"></i>
                      </div>
                    </div>
                    <div class="card-body">
                      <div class="d-flex align-items-start">
                        <c:choose>
                          <c:when test="${not empty video.author.avatarUrl}">
                            <c:choose>
                              <c:when test="${fn:startsWith(video.author.avatarUrl, 'http')}">
                                <img src="${video.author.avatarUrl}" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;" alt="${video.author.displayName}">
                              </c:when>
                              <c:otherwise>
                                <img src="${pageContext.request.contextPath}${video.author.avatarUrl}" class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;" alt="${video.author.displayName}">
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
                          <h6 class="card-title mb-1 text-dark">${video.title}</h6>
                          <small class="text-muted d-block">${video.author.displayName}</small>
                          <small class="text-muted">${video.views} lượt xem</small>
                        </div>
                      </div>
                      <c:if test="${not empty video.description}">
                        <p class="card-text mt-2 text-muted small text-truncate">${video.description}</p>
                      </c:if>
                    </div>
                  </div>
                </a>
              </div>
            </c:forEach>
          </div>
        </c:when>
        <c:otherwise>
          <div class="alert alert-light text-center">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" class="bi bi-people mb-3" viewBox="0 0 16 16">
              <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8Zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022ZM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4Zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0ZM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816ZM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0Zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4Z"/>
            </svg>
            <h5 class="mb-3">Chưa có video từ người bạn theo dõi</h5>
            <p class="text-muted mb-3">Hãy theo dõi người dùng để xem video của họ tại đây!</p>
            <a href="${pageContext.request.contextPath}/user/following" class="btn btn-primary" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none;">
              <i class="bi bi-people"></i> Xem danh sách đang theo dõi
            </a>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
    
    <div class="tab-pane fade" id="recent" role="tabpanel">
      <div class="alert alert-info">Hiển thị video mới nhất</div>
    </div>
  </div>
</div>

<!-- Upload Video Modal -->
<c:if test="${not empty sessionScope.user}">
  <div class="modal fade" id="uploadVideoModal" tabindex="-1" aria-labelledby="uploadVideoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="uploadVideoModalLabel">Đăng video mới</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <form method="post" action="${pageContext.request.contextPath}/video/upload" enctype="multipart/form-data">
          <div class="modal-body">
            <div class="mb-3">
              <label for="videoTitle" class="form-label">Tiêu đề video</label>
              <input type="text" class="form-control" id="videoTitle" name="title" required placeholder="Nhập tiêu đề...">
            </div>
            
            <div class="mb-3">
              <label for="videoDescription" class="form-label">Mô tả</label>
              <textarea class="form-control" id="videoDescription" name="description" rows="3" placeholder="Mô tả video của bạn..."></textarea>
            </div>
            
            <div class="mb-3">
              <label for="videoFile" class="form-label">Chọn video</label>
              <input type="file" class="form-control" id="videoFile" name="video" accept="video/*" required>
              <small class="text-muted">Định dạng: MP4, MOV, AVI (tối đa 100MB)</small>
            </div>
            
            <div class="mb-3">
              <label for="thumbnailFile" class="form-label">Ảnh thu nhỏ (tùy chọn)</label>
              <input type="file" class="form-control" id="thumbnailFile" name="thumbnail" accept="image/*">
            </div>
            
            <div class="form-check">
              <input class="form-check-input" type="checkbox" id="isPublic" name="isPublic" checked>
              <label class="form-check-label" for="isPublic">
                Công khai video
              </label>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            <button type="submit" class="btn btn-primary">Đăng video</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</c:if>

<style>
  .video-card {
    transition: transform 0.2s, box-shadow 0.2s;
    cursor: pointer;
  }
  .video-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 24px rgba(0,0,0,0.15);
  }
  .video-card:hover .play-overlay {
    opacity: 1 !important;
  }
  .video-thumbnail {
    overflow: hidden;
    height: 250px;
    position: relative;
  }
  .video-thumbnail img,
  .video-thumbnail video {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  .play-overlay {
    opacity: 0;
    transition: opacity 0.3s;
    pointer-events: none;
  }
  .video-card:hover .play-overlay i {
    animation: pulse 1s ease-in-out infinite;
  }
  @keyframes pulse {
    0%, 100% { transform: scale(1); }
    50% { transform: scale(1.1); }
  }
</style>
