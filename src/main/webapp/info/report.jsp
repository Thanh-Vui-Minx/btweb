<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<div class="container container-main py-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <div>
      <h2>Report / Document</h2>
      <p class="muted">Embedded PDF viewer and upload.</p>
    </div>
    <div>
      <c:if test="${not empty sessionScope.user and sessionScope.user.role == 'ADMIN'}">
        <form method="post" action="${pageContext.request.contextPath}/admin/upload-doc" enctype="multipart/form-data" style="display:flex;gap:8px;align-items:center;">
          <input type="file" name="pdf" accept="application/pdf" />
          <button class="btn btn-primary" type="submit">Upload</button>
        </form>
      </c:if>
    </div>
  </div>

  <c:choose>
    <c:when test="${param.file != null}">
      <div class="mb-3">
        <iframe src="${pageContext.request.contextPath}/uploads/docs/${param.file}" width="100%" height="900px" style="border:1px solid #ddd"></iframe>
        <c:set var="txtPath" value="${pageContext.request.contextPath}/uploads/docs/${param.file}.txt" />
        <c:if test="${fn:length(param.file) > 0}">
          <div class="mt-3">
            <a class="btn btn-outline-secondary" href="${txtPath}">View extracted text</a>
          </div>
        </c:if>
      </div>
    </c:when>
    <c:otherwise>
      <div class="alert alert-info">Chưa có file nào. Nếu bạn là quản trị viên, hãy upload file PDF ở trên.</div>
    </c:otherwise>
  </c:choose>

</div>
<%@ include file="/WEB-INF/includes/footer.jsp" %>
