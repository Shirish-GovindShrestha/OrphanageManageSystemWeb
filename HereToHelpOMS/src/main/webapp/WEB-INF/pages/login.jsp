<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login to your account</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
</head>

<body>
	<div class="login-box">
		<h1>Login</h1>
		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="row">
				<div class="col">
					<input type="text" id="username" name="username" required
						placeholder="Username">
				</div>
			</div>
			<div class="row">
				<div class="col">
					<input type="password" id="password" name="password" required
						placeholder="Password">
				</div>
			</div>

			<jsp:include page="validation.jsp" />
			<div class="row">
				<div class="col">
					<button type="submit" class="login-button">Login</button>
				</div>
			</div>
			<div class="register">
				Don't have an Account? <a
					href="${pageContext.request.contextPath}/register"> Register
					Now</a>
			</div>
		</form>
	</div>
</body>
</html>