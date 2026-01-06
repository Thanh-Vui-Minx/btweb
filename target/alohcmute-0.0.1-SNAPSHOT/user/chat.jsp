<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container container-main">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <div>
      <h2>Group Chat</h2>
      <p class="muted">Nhắn tin với mọi người.</p>
    </div>
    <div>
      <a href="${pageContext.request.contextPath}/user/home" class="btn btn-outline-primary">Back to Feed</a>
    </div>
  </div>

  <c:if test="${not empty sessionScope.user}">
    <div class="card card-enhanced mb-3 p-4">
      <form method="post" action="${pageContext.request.contextPath}/user/chat">
        <div class="mb-2">
          <input name="content" class="form-control" placeholder="Write a message..." />
        </div>
        <div class="mb-2 text-end">
          <button class="btn btn-gradient-blue" type="submit"><i class="bi bi-send-fill"></i> Send</button>
        </div>
      </form>
    </div>
  </c:if>

  <div class="card p-3">
    <c:forEach var="m" items="${messages}">
      <div class="mb-2">
        <strong><c:out value="${m.author.displayName}"/></strong>
        <small class="muted"> <c:out value="${m.createdAt}"/> </small>
        <div><c:out value="${m.content}"/></div>
      </div>
      <hr/>
    </c:forEach>
  </div>

</div>
