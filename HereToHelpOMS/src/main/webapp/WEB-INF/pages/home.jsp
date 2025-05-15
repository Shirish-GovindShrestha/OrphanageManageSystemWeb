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
	href="${pageContext.request.contextPath}/css/home.css?v=5" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<jsp:include page="sidebar.jsp" />

		<div class="main">
			<!-- Hero Section -->
			<section class="hero">
				<div class="hero-content">
					<h1 class="hero-title">Empowering Young Lives</h1>
					<p class="hero-subtitle">Providing love, care, and
						opportunities for a brighter future</p>
					<a href="${pageContext.request.contextPath}/orphans"
						class="cta-button"> Meet Our Children <i
						class="fas fa-arrow-right"></i>
					</a>
				</div>
				<div class="hero-overlay"></div>
				<img
					src="${pageContext.request.contextPath}/resources/images/system/homebg.jpg"
					alt="Happy children" class="hero-image">
			</section>

			<!-- Stats Section -->
			<section class="stats-section">
				<div class="stat-card">
					<div class="stat-icon">
						<i class="fas fa-child"></i>
					</div>
					<div class="stat-content">
						<h3>Children Helped</h3>
						<div class="stat-number" id="orphanNumberDisplay"
							data-count="${not empty orphanList ? fn:length(orphanList) : 0}">
							0</div>
					</div>
				</div>

				<div class="stat-card">
					<div class="stat-icon">
						<i class="fas fa-hands-helping"></i>
					</div>
					<div class="stat-content">
						<h3>Families Supported</h3>
						<div class="stat-number">300+</div>
					</div>
				</div>
			</section>

			<!-- Mission Section -->
			<section class="mission-section">
				<h2 class="section-title">Our Commitment</h2>
				<div class="mission-grid">
					<article class="mission-card">
						<div class="mission-icon">
							<i class="fas fa-home"></i>
						</div>
						<h3>Family Preservation</h3>
						<p>Strengthening families through community support and
							preventive care</p>
					</article>

					<article class="mission-card">
						<div class="mission-icon">
							<i class="fas fa-graduation-cap"></i>
						</div>
						<h3>Quality Education</h3>
						<p>Personalized learning programs for holistic development</p>
					</article>

					<article class="mission-card">
						<div class="mission-icon">
							<i class="fas fa-heartbeat"></i>
						</div>
						<h3>Mental Wellness</h3>
						<p>Comprehensive emotional support and counseling services</p>
					</article>

					<article class="mission-card">
						<div class="mission-icon">
							<i class="fas fa-hands"></i>
						</div>
						<h3>Community Impact</h3>
						<p>Empowering local communities through sustainable
							initiatives</p>
					</article>
				</div>
			</section>

			<!-- Featured Children -->
			<section class="featured-section">
				<h2 class="section-title">Meet Our Stars</h2>
				<div class="children-grid">
					<c:forEach var="orphan" items="${orphanList}" end="3">
						<article class="child-card">
							<div class="child-photo">
								<img
									src="${pageContext.request.contextPath}/resources/images/orphan/${orphan.imageUrl}"
									alt="${orphan.firstName}"
									onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/resources/images/orphan/default.png'">
								<div
									class="child-status ${fn:toLowerCase(fn:replace(orphan.status, ' ', '-'))}">
									${orphan.status}</div>
							</div>
							<div class="child-info">
								<h3>${orphan.firstName}${orphan.lastName}</h3>
								<div class="child-meta">
									<span>${orphan.calculateAge()} years</span> <span>${orphan.gender}</span>
								</div>
								<a
									href="${pageContext.request.contextPath}/orphan-profile?id=${orphan.orphanId}"
									class="profile-link"> View Profile <i
									class="fas fa-arrow-right"></i>
								</a>
							</div>
						</article>
					</c:forEach>
				</div>
			</section>
		</div>
	</div>

	<jsp:include page="footer.jsp" />

	<script>
    function animateValue(obj, start, end, duration) {
        let startTimestamp = null;
        const step = (timestamp) => {
            if (!startTimestamp) startTimestamp = timestamp;
            const progress = Math.min((timestamp - startTimestamp) / duration, 1);
            obj.textContent = Math.floor(progress * (end - start) + start);
            if (progress < 1) {
                window.requestAnimationFrame(step);
            }
        };
        window.requestAnimationFrame(step);
    }

    document.addEventListener('DOMContentLoaded', () => {
        const counter = document.getElementById('orphanNumberDisplay');
        if (counter) {
            const target = parseInt(counter.dataset.count, 10);
            animateValue(counter, 0, target, 2000);
        }
    });
    </script>
</body>
</html>