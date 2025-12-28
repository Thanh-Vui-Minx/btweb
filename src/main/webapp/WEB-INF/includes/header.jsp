<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>ALOHCMUTE</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
  <link href="${pageContext.request.contextPath}/assets/css/site.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/assets/css/enhanced.css" rel="stylesheet" />
  <style>
    body { padding-top: 70px; }
  </style>
</head>
<body>
  <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top site-header">
    <div class="container-fluid">
      <a class="navbar-brand nav-brand" href="${pageContext.request.contextPath}/user/home">ALOHCMUTE</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMain" aria-controls="navMain" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navMain">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/home"><i class="bi bi-house-fill"></i> Feed</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/trending"><i class="bi bi-fire"></i> Thịnh hành</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/videos"><i class="bi bi-play-circle-fill"></i> Videos</a></li>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/search"><i class="bi bi-search"></i> Tìm kiếm</a></li>
        </ul>
        <ul class="navbar-nav ms-auto">
          <c:choose>
            <c:when test="${not empty sessionScope.user}">
              <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/notifications"><i class="bi bi-bell-fill"></i> <span class="badge bg-danger pulse">3</span></a></li>
              <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/chat"><i class="bi bi-chat-dots-fill"></i> Chat</a></li>
              <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/messages"><i class="bi bi-envelope-fill"></i> Messages</a></li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="userMenu" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <img src="${sessionScope.user.avatarUrl}" class="rounded-circle" style="width: 32px; height: 32px; object-fit: cover; border: 2px solid white;" alt="${sessionScope.user.displayName}">
                  ${sessionScope.user.displayName}
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenu" style="border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); max-height: 400px; overflow-y: auto;">
                  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile"><i class="bi bi-person-circle"></i> Hồ sơ</a></li>
                  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/settings"><i class="bi bi-gear-fill"></i> Cài đặt</a></li>
                  <c:if test="${sessionScope.user.role == 'ADMIN'}">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-shield-fill-check"></i> Quản trị</a></li>
                  </c:if>
                  <c:if test="${sessionScope.user.role == 'MODERATOR' or sessionScope.user.role == 'ADMIN'}">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/moderator/queue"><i class="bi bi-shield-exclamation"></i> Kiểm duyệt</a></li>
                  </c:if>
                  <li><hr class="dropdown-divider"/></li>
                  <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/auth/logout"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
                </ul>
              </li>
            </c:when>
            <c:otherwise>
              <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/auth/login"><i class="bi bi-box-arrow-in-right"></i> Login</a></li>
              <li class="nav-item"><a class="nav-link btn btn-primary text-white" href="${pageContext.request.contextPath}/auth/register"><i class="bi bi-person-plus-fill"></i> Register</a></li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container">
