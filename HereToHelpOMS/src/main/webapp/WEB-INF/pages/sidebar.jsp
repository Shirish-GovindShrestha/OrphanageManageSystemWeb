<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.Cookie"%>
<%@ page import="com.heretohelp.util.CookieUtil"%>
<title>Insert title here</title>

</head>
<%
// Retrieve the role from the cookie
Cookie roleCookie = CookieUtil.getCookie(request, "role");
String currentUserRole = (roleCookie != null) ? roleCookie.getValue() : null;
pageContext.setAttribute("currentUserRole", currentUserRole);
%>
<body>
	<div class="sidebar">

		<img
			src="${pageContext.request.contextPath}/resources/images/system/logo.png">
		<c:if
			test="${not empty currentUserRole and currentUserRole == 'admin'}">
			<a href="${pageContext.request.contextPath}/dashboard"
				class="nav-item active">Dashboard</a>
			<a href="${pageContext.request.contextPath}/login" class="nav-item">Manage
				Children</a>
		</c:if>
		<a href="${pageContext.request.contextPath}/home" class="nav-item">Home</a>
		<a href="${pageContext.request.contextPath}/about" class="nav-item">About</a>
		<a href="${pageContext.request.contextPath}/contact" class="nav-item">Contact
			us</a>

		<c:choose>
			<c:when test="${not empty currentUserRole}">
				<a href="${pageContext.request.contextPath}/account"
					class="nav-item">Manage Account</a>
				<form action="${pageContext.request.contextPath}/logout"
					method="post">
					<input type="submit" value="Logout" class="nav-item" />
				</form>
			</c:when>
			<c:otherwise>
				<form action="${pageContext.request.contextPath}/login" method="get">
					<input type="submit" value="Login" class="nav-item" />
				</form>
			</c:otherwise>
		</c:choose>
	</div>


</body>

</html>