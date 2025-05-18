<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.heretohelp.model.OrphanModel"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Orphanage Management System</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/dashboard.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap"
	rel="stylesheet">
</head>
<style>
</style>
<body>
	<div class="container">
		<!-- Import the sidebar -->
		<jsp:include page="sidebar.jsp" />

		<!-- Main Content -->
		<div class="main">
			<div class="header">
				<div class="search-bar">
					<form action="${pageContext.request.contextPath}/dashboard"
						method="get">
						<input type="text" id="search-item" name="search-item"
							placeholder="Search Orphan">
						<button type="submit" class="search-button">⌕</button>
					</form>
				</div>
				<!-- adding a orphan -->
				<!-- Trigger the Modal -->
				<a href="#popup" class="button">Add Orphan</a>

				<!-- Modal Popup -->
				<div id="popup" class="modal">
					<div class="modal-content">
						<a href="#" class="close">&times;</a>
						<h2>Add Orphan Details</h2>

						<form method="post"
							action="${pageContext.request.contextPath}/dashboard"
							enctype="multipart/form-data">
							<!-- Personal Information Section -->
							<div class="form-group">
								<input type="text" id="firstName" name="firstName"
									value="${firstName}" placeholder="First Name" required>
							</div>

							<div class="form-group">
								<input type="text" id="lastName" name="lastName"
									value="${lastName}" placeholder="Last Name" required>
							</div>

							<div class="form-group">
								<label for="dob">Date of Birth</label> <input type="date"
									id="dob" name="dob" value="${dob}" placeholder="Date of Birth"
									required>
							</div>

							<div class="form-group">
								<label for="gender">Gender</label> <select id="gender"
									name="gender" required>
									<option value="Male" ${gender == 'Male' ? 'selected' : ''}>Male</option>
									<option value="Female" ${gender == 'Female' ? 'selected' : ''}>Female</option>
									<option value="Other" ${gender == 'Other' ? 'selected' : ''}>Other</option>
								</select>
							</div>

							<div class="form-group">
								<label for="status">Status</label> <select id="status"
									name="status" required>
									<option value="Active" ${status == 'Active' ? 'selected' : ''}>Active</option>
									<option value="Inactive"
										${status == 'Inactive' ? 'selected' : ''}>Inactive</option>
									<option value="Adopted"
										${status == 'Adopted' ? 'selected' : ''}>Adopted</option>
								</select>
							</div>

							<div class="form-group">
								<label for="photo">Photo</label> <input type="file" id="photo"
									name="photo" accept="image/*">
							</div>

							<!-- Education Section -->
							<div class="form-section">
								<h3>Education Information</h3>

								<div class="form-group">
									<input type="text" id="schoolName" name="schoolName"
										value="${schoolName}" placeholder="School Name">
								</div>

								<div class="form-group">
									<input type="text" id="grade" name="grade" value="${grade}"
										placeholder="Grade">
								</div>

								<div class="form-group">
									<label for="performance">Performance</label> <select
										id="performance" name="performance">
										<option value="Excellent"
											${performance == 'Excellent' ? 'selected' : ''}>Excellent</option>
										<option value="Good"
											${performance == 'Good' ? 'selected' : ''}>Good</option>
										<option value="Average"
											${performance == 'Average' ? 'selected' : ''}>Average</option>
										<option value="Poor"
											${performance == 'Poor' ? 'selected' : ''}>Poor</option>
									</select>
								</div>

								<div class="form-group">
									<textarea id="remarks" name="remarks" placeholder="Remarks"
										rows="4">${remarks}</textarea>
								</div>
							</div>

							<!-- Submit Button -->
							<div class="form-actions">
								<button type="submit" class="submit-button">Submit</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="dashboard">
				<div class="card-row">
					<div class="card-col" id="total-children-card">
						<div class="col-title">Total Children</div>
						<div class="col-content">${totalOrphanCount}</div>
					</div>
					<div class="card-col" id="upcoming-birthday-card">
						<div class="col-title">Upcoming birthday</div>
						<div class="col-content">
							<ul>
								<c:forEach var="orphan" items="${orphanBirthdateList}">
									<li><h3>${orphan.firstName}${orphan.lastName}</h3></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="card-col" id="latest-user">
						<div class="col-title">Latest User</div>
						<div class="col-content">
							<ul>
								<c:forEach var="user" items="${userList}">
									<li><h3>${user.username}</h3></li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>



				<!-- orphan table -->
				<div class="card-col" id="orphan-table">
					<div class="orphan-table">
						<div class="table-title">
							<c:choose>
								<c:when test="${searchActive}">Search Results</c:when>
								<c:otherwise>Recently added Orphan</c:otherwise>
							</c:choose>
						</div>

						<jsp:include page="validation.jsp" />
						<table>
							<tr>
								<th>Id</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>DOB</th>
								<th>Gender</th>
								<th>Status</th>
								<th>Admission Date</th>
								<th>Education Grade</th>
								<th>School Name</th>
								<th>Edit & Delete</th>
							</tr>
							<c:choose>
								<c:when test="${not empty orphanList}">
									<c:forEach var="orphan" items="${orphanList}">
										<tr>
											<td>${orphan.orphanId}</td>
											<td>${orphan.firstName}</td>
											<td>${orphan.lastName}</td>
											<td>${orphan.dob}</td>
											<td>${orphan.gender}</td>
											<td>${orphan.status}</td>
											<td>${orphan.admissionDate}</td>

											<!-- Check for Education and School data -->
											<c:choose>
												<c:when test="${not empty orphan.educationSchoolRecords}">
													<c:forEach var="eduSchool"
														items="${orphan.educationSchoolRecords}">
														<td>${eduSchool.education.grade != null && eduSchool.education.grade != '' ? eduSchool.education.grade : 'No Grade'}</td>
														<td>${eduSchool.school.schoolName != null && eduSchool.school.schoolName != '' ? eduSchool.school.schoolName : 'No School'}</td>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<td colspan="2" style="text-align: center; color: #888;">No
														Education or School Data Available</td>
												</c:otherwise>
											</c:choose>

											<td><a
												href="${pageContext.request.contextPath}/editOrphan?id=${orphan.orphanId}"
												class="edit-button">✏️</a> <a
												href="deleteOrphan?id=${orphan.orphanId}"
												class="delete-button"
												onclick="return confirm('Are you sure you want to delete this orphan?');">🗑</a></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="10" style="text-align: center; color: #888;">No
											Orphans Found</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>