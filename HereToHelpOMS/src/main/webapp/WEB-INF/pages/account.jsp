
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>User Account Management</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/account.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<jsp:include page="sidebar.jsp" />
		<div class="main">
			<div class="account-dashboard">
				<div class="content-area">
					<form action="${pageContext.request.contextPath}/changePfp"
						method="post" enctype="multipart/form-data">
						<div class="profile-row">
							<div class="profile-box">
								<div class="profile-photo">
									<img id="image-preview"
										src="${pageContext.request.contextPath}${imageUrl}" />
								</div>${username}</div>
						</div>
						<div class="change-pic">
							<input type="file" id="image" name="image"> <label
								for="image" class="custom-file-input">Update your</label>
							<button type="submit">Update Picture</button>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/account"
						method="post">

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
								<label for="username">Username(Cannot Be Changed):</label> <input
									type="text" id="username" name="username" value="${username}"
									readonly required>
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
							<div class="col">
								<label for="oldPassword">Old Password:</label> <input
									type="password" id="oldPassword" name="oldPassword" required>
							</div>

						</div>
						<div class="row">
							<div class="col">
								<label for="password">Password:</label> <input type="password"
									id="password" name="password" required>
							</div>
							<div class="col">
								<label for="retypePassword">Retype Password:</label> <input
									type="password" id="retypePassword" name="retypePassword"
									required>
							</div>
						</div>
						<jsp:include page="validation.jsp" />
						<div class="button-row">
							<button type="submit">Update Account</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	// Get the file input and the image preview elements
	var fileInput = document.getElementById('image');
	var imagePreview = document.getElementById('image-preview');

	// Add an event listener that runs when a file is selected
	fileInput.onchange = function() {
		// Check if a file was selected
		if (fileInput.files && fileInput.files[0]) {
			// Create a temporary URL for the selected image
			var imageURL = URL.createObjectURL(fileInput.files[0]);

			// Update the image preview with the selected image
			imagePreview.src = imageURL;
		}
	};
</script>
</html>