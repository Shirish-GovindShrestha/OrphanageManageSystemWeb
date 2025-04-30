<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.heretohelp.model.OrphanModel"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>HereToHelp Orphanage Management System</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/home.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
</head>
<script>
  function showNumbers(upTo, delay = 50) {
    let current = 1;
    const display = document.getElementById("orphanNumberDisplay");

    const interval = setInterval(() => {
      display.textContent = current;
      if (current >= upTo) {
        clearInterval(interval);
      }
      current++;
    }, delay);
  }

  window.onload = function () {
    const display = document.getElementById("orphanNumberDisplay");

    if (!display) return;

    const count = parseInt(display.dataset.count, 10);
    let hasAnimated = false;

    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting && !hasAnimated) {
          hasAnimated = true; // only run once
          showNumbers(count);
          observer.unobserve(display); // stop observing once triggered
        }
      });
    }, {
      threshold: 0.5 // Trigger when 50% of element is in view
    });

    observer.observe(display);
  };
</script>
<body>
	<div class="container">
		<!-- Import the sidebar -->
		<jsp:include page="sidebar.jsp" />

		<!-- Main Content -->
		<div class="main">
			<div class="card-background">
				<div class="overlay"></div>
				<img
					src="${pageContext.request.contextPath}/resources/images/system/homebg.jpg"
					alt="Children Banner" class="banner-image">
				<div class="image-text">
					<h1>Welcome to HereToHelp Orphanage</h1>
				</div>
			</div>

			<div class="dashboard">
				<div class="card-row">
					<div class="card-col">
						<div class="card">
							<div class="col-title">Total Children</div>
							<div class="card-content">
								<c:choose>
									<c:when test="${not empty orphanList}">
										<div id="orphanNumberDisplay"
											data-count="${fn:length(orphanList)}">0</div>
									</c:when>
									<c:otherwise>
										<div>Database error</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="card-col">
						<div class="card">
							<div class="col-title">Families Supported</div>
							<div class="card-content">300+</div>
						</div>
					</div>
				</div>

				<div class="goal-section">
					<div class="row-title">Our Goals</div>
					<div class="card-row">
						<div class="card-col">
							<div class="card">
								<div class="col-title">Prevent Child Separation</div>
								<div class="card-content">
									<i class="fas fa-home"></i>
								</div>
							</div>
						</div>
						<div class="card-col">
							<div class="card">
								<div class="col-title">Provide Quality Education</div>
								<div class="card-content">
									<i class="fas fa-graduation-cap"></i>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="about-section">
					<div class="about-card">
						<h2 class="about-title">About HereToHelp Orphanage</h2>
						<div class="about-content">
							<p>Founded in 2000, Hope Children's Home has been dedicated
								to providing shelter, education, and care for orphaned and
								abandoned children for over 25 years. Our mission is to provide
								care, shelter, education, and various support to children in
								need.</p>
							<a href="${pageContext.request.contextPath}/about"
								class="learn-more-btn"> Learn More About Us <i
								class="fas fa-arrow-right"></i>
							</a>
						</div>
					</div>
				</div>

				<h2 class="section-title">Student Activities</h2>
				<div class="activities-section">
					<div class="activity-grid">
						<div class="activity-card">
							<div class="activity-icon">
								<i class="fas fa-music"></i>
							</div>
							<h3>Music & Arts</h3>
							<p>Students develop creative expression through instruments,
								singing, drawing, and crafts.</p>
						</div>
					</div>
				</div>

				<h2 class="section-title">Featured Children</h2>
				<div class="orphan-display">
					<c:if test="${not empty orphanList}">
						<c:forEach var="orphan" items="${orphanList}" end="3">
							<div class="orphan">
								<div class="photo">
									<i class="fas fa-child"></i>
								</div>
								<div class="orphan-info">
									<p>
										<strong>Name:</strong> ${orphan.firstName} ${orphan.lastName}
									</p>
									<p>
										<strong>DOB:</strong> ${orphan.dob}
									</p>
									<p>
										<strong>Gender:</strong> ${orphan.gender}
									</p>
									<p>
										<strong>Status:</strong> ${orphan.status}
									</p>
									<p>
										<strong>Admitted:</strong> ${orphan.admissionDate}
									</p>
									<a
										href="${pageContext.request.contextPath}/profile/${orphan.firstName}"
										class="profile-link"> View Full Profile <i
										class="fas fa-arrow-right"></i>
									</a>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>