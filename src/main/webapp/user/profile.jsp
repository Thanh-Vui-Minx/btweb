<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="py-4">
  <c:choose>
    <c:when test="${not empty profileUser}">
      <c:set var="u" value="${profileUser}" />
      <div class="row">
        <div class="col-12 mb-3">
          <div class="profile-cover" style="background-image: url('${u.coverPhotoUrl != null ? u.coverPhotoUrl : ''}');">
            <div class="profile-cover-overlay"></div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="card card-enhanced p-4 text-center">
            <c:choose>
              <c:when test="${not empty u.avatarUrl}">
                <img src="${u.avatarUrl}" class="img-fluid rounded-circle mb-2" style="width:150px;height:150px;object-fit:cover" alt="avatar" />
              </c:when>
              <c:otherwise>
                <img src="https://via.placeholder.com/150" class="img-fluid rounded-circle mb-2" alt="avatar" />
              </c:otherwise>
            </c:choose>
            <h4 class="gradient-text-primary">${u.displayName}</h4>
            <p class="text-muted">@${u.username}</p>
            <p>Email: <small>${u.email}</small></p>
            <p>Role: <strong>${u.role}</strong></p>
            <p>Status: <strong><c:choose><c:when test="${u.active}">Active</c:when><c:otherwise>Banned</c:otherwise></c:choose></strong></p>
            <c:if test="${not empty linksList}">
              <div class="mt-2 profile-ctas">
                <c:forEach var="link" items="${linksList}">
                  <a class="btn btn-outline-primary btn-sm" href="${link.url}" target="_blank">${link.label}</a>
                </c:forEach>
              </div>
            </c:if>
          </div>
        </div>
        <div class="col-md-8">
          <div class="card card-enhanced p-4">
            <h5>About</h5>
            <p><c:out value="${u.bio}" default="Chưa có thông tin."/></p>
            <hr/>
            <c:if test="${not empty sessionScope.user and sessionScope.user.id == u.id}">
              <h5>Edit profile</h5>
              <form method="post" action="${pageContext.request.contextPath}/user/profile" enctype="multipart/form-data">
                <div class="mb-3">
                  <label class="form-label">Display name</label>
                  <input class="form-control" name="displayName" value="${u.displayName}" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Bio</label>
                  <textarea class="form-control" name="bio" rows="4">${u.bio}</textarea>
                </div>
                <div class="mb-3">
                  <label class="form-label">Cover photo</label>
                  <input class="form-control" type="file" name="cover" accept="image/*" />
                </div>
                <div class="mb-3">
                  <label class="form-label">Links (one per line, format: Label|https://...)</label>
                  <textarea class="form-control" name="links" rows="3">${u.links}</textarea>
                </div>
                <div class="mb-3">
                  <label class="form-label">Avatar</label>
                  <input class="form-control" type="file" name="avatar" accept="image/*" />
                </div>
                <button class="btn btn-gradient-primary" type="submit"><i class="bi bi-save"></i> Save</button>
              </form>
            </c:if>
          </div>
        </div>
      </div>
    </c:when>
    <c:otherwise>
      <div class="alert alert-warning">Người dùng không tồn tại hoặc bạn chưa đăng nhập.</div>
    </c:otherwise>
  </c:choose>
</div>
