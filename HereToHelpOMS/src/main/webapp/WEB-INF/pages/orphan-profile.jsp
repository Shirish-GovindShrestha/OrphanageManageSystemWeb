<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.heretohelp.model.OrphanModel"%>
<%@ page import="com.heretohelp.model.OrphanEducationSchoolModel"%>
<%@ page import="com.heretohelp.model.EducationModel"%>
<%@ page import="com.heretohelp.model.SchoolModel"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Orphan Profile | Here to Help</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/orphan-profile.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
	<div class="container">
		<a href="${pageContext.request.contextPath}/orphans" class="nav-link">
			<i class="fas fa-arrow-left"></i> Back to Home
		</a>
		<c:choose>
			<c:when test="${not empty orphan}">
				<div class="profile-container">
					<div class="orphan-card">
						<div class="orphan-photo">
							<img
								src="${pageContext.request.contextPath}/resources/images/orphan/${orphan.imageUrl}"
								alt="${orphan.firstName}'s Photo">
						</div>
						<div class="orphan-info">
							<div class="orphan-name">${orphan.firstName}
								${orphan.lastName}</div>
							<div class="orphan-status ${orphan.status.toLowerCase()}">${orphan.status}</div>
						</div>
					</div>

					<div class="profile-details">
						<div class="profile-section personal-details">
							<h2>
								<i class="fas fa-user-circle"></i> Personal Information
							</h2>
							<div class="detail-grid">
								<div class="detail-item">
									<div class="detail-label">Gender</div>
									<div class="detail-value">${orphan.gender}</div>
								</div>
								<div class="detail-item">
									<div class="detail-label">Age</div>
									<div class="detail-value">${orphan.calculateAge()} years</div>
								</div>
								<div class="detail-item">
									<div class="detail-label">Date of Birth</div>
									<div class="detail-value">${orphan.dob}</div>
								</div>
							</div>
						</div>

						<div class="profile-section education-details">
							<h2>
								<i class="fas fa-graduation-cap"></i> Education Information
							</h2>
							<c:choose>
								<c:when test="${not empty orphan.educationSchoolRecords}">
									<div class="education-cards">
										<c:forEach items="${orphan.educationSchoolRecords}"
											var="eduSchool">
											<div class="education-card">
												<h3 class="school-name">${eduSchool.school.schoolName}</h3>
												<div class="detail-grid">
													<div class="detail-item">
														<div class="detail-label">Grade</div>
														<div class="detail-value">${eduSchool.education.grade}</div>
													</div>
													<div class="detail-item">
														<div class="detail-label">Performance</div>
														<div
															class="detail-value performance-${eduSchool.education.performance.toLowerCase()}">
															${eduSchool.education.performance}</div>
													</div>
													<div class="detail-item full-width">
														<div class="detail-label">Remarks</div>
														<div class="detail-value remarks">${eduSchool.education.remarks}</div>
													</div>
												</div>
											</div>
										</c:forEach>
									</div>
								</c:when>
								<c:otherwise>
									<div class="no-data">
										<i class="fas fa-info-circle"></i>
										<p>No education information available yet.</p>
									</div>
								</c:otherwise>
							</c:choose>
						</div>

					</div>
				</div>
			</c:when>

			<c:otherwise>
				<div class="empty-state">
					<i class="fas fa-exclamation-circle"></i>
					<h2>No profile found</h2>
					<p>The requested child profile could not be found in our
						system.</p>
					<a href="${pageContext.request.contextPath}/home"
						class="btn btn-primary"> <i class="fas fa-arrow-left"></i>
						Back to Home
					</a>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
