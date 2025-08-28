<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
  <h2>Đăng nhập</h2>
  <c:if test="${alert != null}">
      <h3 class="alert alert-danger">${alert}</h3>
  </c:if>
  <section>
    <label>
      <input type="text" name="username" placeholder="Tài khoản" class="form-control"/>
    </label>
  </section>
  <section>
    <label>
      <input type="password" name="password" placeholder="Mật khẩu" class="form-control"/>
    </label>
  </section>
  <button type="submit">Đăng nhập</button>
</form>
</body>
</html>