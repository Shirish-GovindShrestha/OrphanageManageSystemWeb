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
<title>Orphanage Management System</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/home.css?v=1" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
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
										<div id="orphanNumberDisplay" data-count="${fn:length(orphanList)}">0</div>
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
							<div class="col-title">Family Supported</div>
							<div class="card-content">300+</div>
						</div>
					</div>
				</div>
				<div>
					<div class=student-display>
						<c:if test="${not empty orphanList}">
							<c:forEach var="orphan" items="${orphanList}" end="3">
								<div class="student">
									<div class="photo">📍</div>
									Name: ${orphan.firstName} ${orphan.lastName}<br> Dob:
									${orphan.dob}<br> Gender: ${orphan.gender}<br>
									Status: ${orphan.status}<br> Admitted date:
									${orphan.admissionDate}<br> <a
										href="{pageContext.request.contextPath}/${orphan.firstName}">Click
										here for more!</a>
								</div>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div class="goal-section">
					<div class="col-title">Our Goal</div>
					<div class="card-row">
						<div class="card-col">
							<div class="card">
								<div class="col-title">Prevent Child Separation</div>
								<div class="card-content"></div>
							</div>
						</div>
						<div class="card-col">
							<div class="card">
								<div class="col-title">Prevent Child Separation</div>
								<div class="card-content"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>