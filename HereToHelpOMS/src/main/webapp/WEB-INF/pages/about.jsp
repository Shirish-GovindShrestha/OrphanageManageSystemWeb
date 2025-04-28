
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>About Us - Orphanage Management System</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/about.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
	<div class="container">

		<jsp:include page="sidebar.jsp" />


		<div class="main">


			<!-- About Content -->
			<div class="about-content">
				<div class="card">
					<div class="card-header">
						<div class="card-title">Our Institution</div>
					</div>
					<div class="card-body">
						<img src="orphanage-image.jpg" alt="Orphanage Building"
							class="about-image">
						<h2>HereToHelp Orphanage</h2>
						<p>Founded in 2000, Hope Children's Home has been dedicated to
							providing shelter, education, and care for orphaned and abandoned
							children for over 25 years. Our mission is to provide care,
							shelter, education, and various support to children in need.</p>

						<h3>Our Vision</h3>
						<p>To be a leading institution that transforms the lives of
							underprivileged children by providing them with quality care,
							education, and opportunities for a better future.</p>

						<h3>Our Values</h3>
						<ul>
							<li><strong>Compassion:</strong> We approach our work with
								empathy and understanding.</li>
							<li><strong>Integrity:</strong> We maintain high ethical
								standards in all our operations.</li>
							<li><strong>Excellence:</strong> We strive for the highest
								quality in childcare and education.</li>
							<li><strong>Inclusion:</strong> We create an environment
								where every child feels welcome regardless of background.</li>
						</ul>
					</div>
				</div>

				<div class="card">
					<div class="card-header">
						<div class="card-title">About Our Management System</div>
					</div>
					<div class="card-body">
						<h3>Purpose of Our System</h3>
						<p>The Orphanage Management System (OMS) is designed to
							streamline and enhance the administrative processes involved in
							running our institution. This system helps us track and manage:</p>

						<div class="features">
							<div class="feature">
								<div class="feature-icon bg-green">👤</div>
								<div class="feature-text">
									<h4>Child Records</h4>
									<p>Maintain comprehensive records of each child including
										their background, health, education, and development.</p>
								</div>
							</div>

							<div class="feature">
								<div class="feature-icon bg-blue">👥</div>
								<div class="feature-text">
									<h4>Staff Management</h4>
									<p>Track staff information, roles, schedules, and
										performance to ensure quality care.</p>
								</div>
							</div>

							<div class="feature">
								<div class="feature-icon bg-orange">💰</div>
								<div class="feature-text">
									<h4>Donation Management</h4>
									<p>Record and manage donations, generate reports, and
										ensure transparent fund utilization.</p>
								</div>
							</div>

							<div class="feature">
								<div class="feature-icon bg-purple">📊</div>
								<div class="feature-text">
									<h4>Reporting and Analytics</h4>
									<p>Generate insightful reports to track progress and make
										informed decisions.</p>
								</div>
							</div>
						</div>

						<h3>Our Impact</h3>
						<p>Since implementing this system in 2022, we have improved
							our operational efficiency by 40%, reduced administrative costs,
							and enhanced our ability to provide personalized care to each
							child.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>