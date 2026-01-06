<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <div>
      <h2>Messages</h2>
      <p class="muted">Tin nhắn riêng tư giữa bạn và người khác.</p>
    </div>
    <div>
      <a href="${pageContext.request.contextPath}/user/home" class="btn btn-outline-primary">Back to Feed</a>
    </div>
  </div>

  <c:if test="${not empty error}">
    <div class="alert alert-danger"><c:out value="${error}"/></div>
  </c:if>

  <div class="card mb-3 p-3">
    <form method="get" action="${pageContext.request.contextPath}/user/messages">
      <div class="input-group">
        <input name="with" class="form-control" placeholder="Enter username to message" value="${withUser != null ? withUser.username : ''}" />
        <button class="btn btn-outline-secondary" type="submit">Open</button>
      </div>
    </form>
  </div>

  <c:if test="${not empty withUser}">
    <h4>Conversation with <c:out value="${withUser.displayName}"/> (@<c:out value="${withUser.username}"/>)</h4>
    <div class="card mb-3 p-3">
      <form method="post" action="${pageContext.request.contextPath}/user/messages">
        <input type="hidden" name="with" value="${withUser.username}" />
        <div class="mb-2">
          <input name="content" class="form-control" placeholder="Write a private message..." />
        </div>
        <div class="mb-2 text-end">
          <button class="btn btn-gradient-primary" type="submit"><i class="bi bi-send-fill"></i> Send</button>
        </div>
      </form>
    </div>

    <div class="card p-3">
      <c:forEach var="m" items="${messages}">
        <div class="mb-2">
          <strong><c:out value="${m.sender.displayName}"/></strong>
          <small class="muted"> <c:out value="${m.createdAt}"/> </small>
          <div><c:out value="${m.content}"/></div>
        </div>
        <hr/>
      </c:forEach>
    </div>
  </c:if>

</div>
