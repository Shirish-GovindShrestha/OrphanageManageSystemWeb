<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.heretohelp.model.OrphanModel"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>HereToHelp Orphanage Management System</title>

<link rel="stylesheet" type="text/css"
    href="${pageContext.request.contextPath}/css/orphan.css?v=3" />
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
            <h2 class="section-title">Meet Our Children</h2>

            <!-- Search Bar -->
            <div class="search-bar">
                <form action="${pageContext.request.contextPath}/orphans"
                    method="get">
                    <input type="text" id="search-item" name="search-item"
                        placeholder="Search by name, age, or gender">
                    <button type="submit">
                        <i class="fas fa-search"></i>
                    </button>
                </form>
            </div>

            <!-- Children Grid -->
            <div class="orphan-grid">
                <c:if test="${not empty orphanList}">
                    <c:forEach var="orphan" items="${orphanList}">
                        <div class="orphan-card">
                            <div class="orphan-photo">
                                <img
                                    src="${pageContext.request.contextPath}/resources/images/orphan/${orphan.imageUrl}"
                                    alt="${orphan.firstName}" 
                                    onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/resources/images/orphan/default.png'"/>
                            </div>
                            <div class="orphan-details">
                                <h3 class="orphan-name">${orphan.firstName} ${orphan.lastName}</h3>
                                <p class="meta-info">
                                    <span>${orphan.calculateAge()} yrs</span> • 
                                    <span>${orphan.gender}</span>
                                </p>
                                <p class="status">
                                    <strong>Status:</strong> ${orphan.status}
                                </p>
                                <div class="additional-info">
                                    <p><strong>Admitted:</strong> ${orphan.admissionDate}</p>
                                    <p><strong>DOB:</strong> ${orphan.dob}</p>
                                </div>
                                <a
                                    href="${pageContext.request.contextPath}/orphan-profile?id=${orphan.orphanId}"
                                    class="profile-link">
                                    View Full Profile
                                    <i class="fas fa-arrow-right"></i>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>