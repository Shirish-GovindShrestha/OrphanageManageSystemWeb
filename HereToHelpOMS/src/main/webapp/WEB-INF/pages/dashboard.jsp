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
	href="${pageContext.request.contextPath}/css/dashboard.css?v=1" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/sidebar.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
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
									<li><h3>${orphan.firstName} ${orphan.lastName}</h3></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="card-col" id="content">
						<div class="col-title">Total Children</div>
					</div>
				</div>

				<div class="orphan-table">
					<table>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>DOB</th>
							<th>Gender</th>
							<th>Status</th>
							<th>Admission Date</th>
						</tr>
						<c:choose>
							<c:when test="${not empty orphanList}">
								<c:forEach var="orphan" items="${orphanList}">
									<tr>
										<td>${orphan.firstName}</td>
										<td>${orphan.lastName}</td>
										<td>${orphan.dob}</td>
										<td>${orphan.gender}</td>
										<td>${orphan.status}</td>
										<td>${orphan.admissionDate}</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="6" style="text-align: center; color: #888;">No
										data found</td>
								</tr>
							</c:otherwise>
						</c:choose>


					</table>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>