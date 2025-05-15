<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register Page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/register.css?v=2" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
</head>

<body>
	<div class="container">
		<h1>Registration Form</h1>
		<form action="${pageContext.request.contextPath}/register"
			method="post" enctype="multipart/form-data">
			<div class="row">
				<div class="col">
					<label for="firstName">First Name:</label> <input type="text"
						id="firstName" name="firstName" value="${firstName}" required>
				</div>
				<div class="col">
					<label for="lastName">Last Name:</label> <input type="text"
						id="lastName" name="lastName" value="${lastName}" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="username">Username:</label> <input type="text"
						id="username" name="username" value="${username}" required>
				</div>
				<div class="col">
					<label for="birthday">Date of Birth:</label> <input type="date"
						id="birthday" name="dob" value="${dob}" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="gender">Gender:</label> <select id="gender"
						name="gender" required>
						<option value="male" ${gender == 'male' ? 'selected' : ''}>Male</option>
						<option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
						<option value="other" ${gender == 'other' ? 'selected' : ''}>Other</option>
					</select>
				</div>
				<div class="col">
					<label for="phoneNumber">Phone Number:</label> <input type="tel"
						id="phoneNumber" name="phoneNumber" value="${phoneNumber}"
						required>
				</div>

			</div>
			<div class="row">

				<div class="col">
					<label for="email">Email:</label> <input type="email" id="email"
						name="email" value="${email}" required
						placeholder="name@gmail.com">
				</div>

			</div>
			<div class="row">
				<div class="col">
					<label for="password">Password:</label> <input type="password"
						id="password" name="password" required>
				</div>
				<div class="col">
					<label for="retypePassword">Retype Password:</label> <input
						type="password" id="retypePassword" name="retypePassword" required>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<label for="image">Profile Picture:</label> <input type="file"
						id="image" name="image">
				</div>
			</div>
			<jsp:include page="validation.jsp" />
			<button type="submit">Register A New Account</button>
		</form>
		<a href="${pageContext.request.contextPath}/login" class="login-link">Login
			If You Have Already An Account </a>
	</div>
</body>

</html>