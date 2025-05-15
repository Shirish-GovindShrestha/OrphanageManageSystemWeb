<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.heretohelp.model.OrphanModel"%>
<%@ page import="com.heretohelp.model.OrphanEducationSchoolModel"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Edit Child Profile | Here to Help</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/edit-orphan.css?v=4">
</head>
<body>
	<div class="container">
		<header>
			<h1>
				<i class="fas fa-edit"></i> Edit Child Information
			</h1>
		</header>

		<!-- Alert Messages -->
		<c:if test="${not empty success}">
			<div class="alert alert-success">
				<i class="fas fa-check-circle"></i> ${success}
			</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-error">
				<i class="fas fa-exclamation-circle"></i> ${error}
			</div>
		</c:if>
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
		

		<form action="${pageContext.request.contextPath}/editOrphan"
			method="post" enctype="multipart/form-data">
			<input type="hidden" name="orphanId" value="${orphan.orphanId}" />
			<!-- Personal Info -->
			<div class="form-section">
				<h2>
					<i class="fas fa-user-circle"></i> Personal Information
				</h2>
				<div class="form-row">
					<div class="form-group">
						<label for="firstName">First Name</label> <input type="text"
							id="firstName" name="firstName" value="${orphan.firstName}"
							required />
					</div>
					<div class="form-group">
						<label for="lastName">Last Name</label> <input type="text"
							id="lastName" name="lastName" value="${orphan.lastName}" required />
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="dob">Date of Birth</label> <input type="date" id="dob"
							name="dob" value="${orphan.dob}" required />
					</div>
					<div class="form-group">
						<label for="gender">Gender</label> <select id="gender"
							name="gender" required>
							<option value="Male" ${orphan.gender == 'Male' ? 'selected' : ''}>Male</option>
							<option value="Female"
								${orphan.gender == 'Female' ? 'selected' : ''}>Female</option>
							<option value="Other"
								${orphan.gender == 'Other' ? 'selected' : ''}>Other</option>
						</select>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="status">Status</label> <select id="status"
							name="status" required>
							<option value="Available"
								${orphan.status == 'Available' ? 'selected' : ''}>Available</option>
							<option value="Adopted"
								${orphan.status == 'Adopted' ? 'selected' : ''}>Adopted</option>
							<option value="Unavailable"
								${orphan.status == 'Unavailable' ? 'selected' : ''}>Unavailable</option>
						</select>
					</div>
					<div class="form-group">
						<label for="admissionDate">Admission Date</label> <input
							type="date" id="admissionDate" name="admissionDate"
							value="${orphan.admissionDate}" required />
					</div>
				</div>
			</div>

			<!-- Education Entries -->
			<div class="form-section">
				<h2>
					<i class="fas fa-graduation-cap"></i> Education History
				</h2>

				<c:forEach var="eduSchool" items="${orphan.educationSchoolRecords}"
					varStatus="loop">
					<div class="profile-section education-details">
						<input type="hidden" name="educationId"
							value="${eduSchool.education.educationId}" /> <input
							type="hidden" name="schoolId"
							value="${eduSchool.school.schoolId}" />

						<div class="detail-grid">
							<div class="detail-item">
								<label>School Name</label> <input type="text" name="schoolName"
									value="${eduSchool.school.schoolName}" required />
							</div>
							<div class="detail-item">
								<label>Grade</label> <input type="text" name="grade"
									value="${eduSchool.education.grade}" required />
							</div>
							<div class="detail-item">
								<label>Performance</label> <select name="performance" required>
									<option value="Excellent"
										${eduSchool.education.performance == 'Excellent' ? 'selected' : ''}>Excellent</option>
									<option value="Good"
										${eduSchool.education.performance == 'Good' ? 'selected' : ''}>Good</option>
									<option value="Average"
										${eduSchool.education.performance == 'Average' ? 'selected' : ''}>Average</option>
									<option value="Poor"
										${eduSchool.education.performance == 'Poor' ? 'selected' : ''}>Poor</option>
								</select>
							</div>
							<div class="detail-item full-width">
								<label>Remarks</label>
								<textarea name="remarks" rows="3">${eduSchool.education.remarks}</textarea>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<!-- Form Actions -->
			<div class="form-actions">
				<button type="submit" class="btn-submit">
					<i class="fas fa-save"></i> Save Changes
				</button>
				<a
					href="${pageContext.request.contextPath}/orphans/view/${orphan.orphanId}"
					class="btn-cancel"> <i class="fas fa-times"></i> Cancel
				</a>
			</div>
		</form>
	</div>
</body>
</html>
