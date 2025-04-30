<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contact Us - Orphanage Management System</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/contact.css?v=3" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<!-- Import the sidebar -->
		<jsp:include page="sidebar.jsp" />

		<!-- Main Content -->
		<div class="main">
			<!-- Contact Content -->
			<div class="contact-content">
				<div class="contact-card">
					<div class="contact-info-card card">
						<div class="card-header">
							<h2 class="card-title">Give Us A Call 📞</h2>
						</div>
						<div class="card-body">
							<div class="phone-number-details">
								<p>+977 9876543210</p>
							</div>
						</div>
					</div>
					<div class="contact-info-card card">
						<div class="card-header">
							<h2 class="card-title">How to Reach Us</h2>
						</div>
						<div class="card-body">
							<div class="contact-methods">
								<div class="contact-method">
									<div class="contact-icon bg-green">📍</div>
									<div class="contact-details">
										<h3>Address</h3>
										<p>
											123 Kaka Street<br>Runtime City, SC 12345<br>Nepal
										</p>
									</div>
								</div>
								<div class="contact-method">
									<div class="contact-icon bg-orange">✉️</div>
									<div class="contact-details">
										<h3>Email</h3>
										<p>
											info@heretohelp.org
										</p>
									</div>
								</div>
								<div class="contact-method">
									<div class="contact-icon bg-purple">🕒</div>
									<div class="contact-details">
										<h3>Office Hours</h3>
										<p>
											Monday - Friday: 9:00 AM - 5:00 PM<br>Weekends: By
											appointment only
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="contact-form-card card">
					<div class="card-header">
						<h2 class="card-title">Send Us a Message</h2>
					</div>
					<div class="card-body">
						<form action="processContact.jsp" method="post"
							class="contact-form">
							<div class="form-group">
								<label for="name">Full Name</label> <input type="text" id="name"
									name="name" required placeholder="Enter your full name">
							</div>
							<div class="form-group">
								<label for="email">Email Address</label> <input type="email"
									id="email" name="email" required
									placeholder="Enter your email address">
							</div>
							<div class="form-group">
								<label for="phone">Phone Number</label> <input type="tel"
									id="phone" name="phone"
									placeholder="Enter your phone number (optional)">
							</div>
							<div class="form-group">
								<label for="message">Message</label>
								<textarea id="message" name="message" rows="5" required
									placeholder="Type your message here..."></textarea>
							</div>
							<div class="form-group">
								<button type="submit" class="button button-primary">Send
									Message</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>