package com.heretohelp.controller;

import com.heretohelp.model.EducationModel;
import com.heretohelp.model.OrphanEducationSchoolModel;
import com.heretohelp.model.OrphanModel;
import com.heretohelp.model.SchoolModel;
import com.heretohelp.service.OrphanService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = { "/editOrphan" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class EditOrphanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrphanService orphanProfileService;
	

	public EditOrphanController() {
		super();
		this.orphanProfileService = new OrphanService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orphanIdStr = request.getParameter("id");
		try {
			int orphanId = Integer.parseInt(orphanIdStr);
			OrphanModel orphan = orphanProfileService.getOrphanFromId(orphanId);
			if (orphan != null) {
				request.setAttribute("orphan", orphan);
				request.setAttribute("schoolList", orphanProfileService.getAllSchools());
				request.setAttribute("educationList", orphanProfileService.getAllEducationLevels());
				request.getRequestDispatcher("/WEB-INF/pages/edit-orphan.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "Orphan Not Found");
				request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
			}
		} catch (NumberFormatException | SQLException e) {
			request.setAttribute("error", "Error fetching orphan data");
			request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int orphanId = Integer.parseInt(request.getParameter("orphanId"));
			
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String dob = request.getParameter("dob");
			String gender = request.getParameter("gender");
			String status = request.getParameter("status");
			String admissionDate = request.getParameter("admissionDate");

			// Get the updated education and school data from the request
			String grade = request.getParameter("grade");
			String performance = request.getParameter("performance");
			String remarks = request.getParameter("remarks");
			String schoolName = request.getParameter("schoolName");

			// Get IDs for existing education and school records
			String schoolIdParam = request.getParameter("schoolId");
			String educationIdParam = request.getParameter("educationId");

			int schoolId = schoolIdParam != null && !schoolIdParam.isEmpty() ? Integer.parseInt(schoolIdParam) : -1;
			int educationId = educationIdParam != null && !educationIdParam.isEmpty()
					? Integer.parseInt(educationIdParam)
					: -1;

			// Create the OrphanModel to hold the updated orphan data
			OrphanModel updatedOrphan = new OrphanModel();
			updatedOrphan.setOrphanId(orphanId);
			updatedOrphan.setFirstName(firstName);
			updatedOrphan.setLastName(lastName);
			updatedOrphan.setDob(LocalDate.parse(dob));
			updatedOrphan.setGender(gender);
			updatedOrphan.setStatus(status);
			updatedOrphan.setAdmissionDate(LocalDate.parse(admissionDate));

			// Create the EducationModel for the orphan
			EducationModel education = new EducationModel();
			education.setGrade(grade);
			education.setPerformance(performance);
			education.setRemarks(remarks);
			education.setEducationId(educationId); // If the education record exists, update it

			// Create the SchoolModel
			SchoolModel school = new SchoolModel();
			school.setSchoolId(schoolId); // If the school exists, update it
			school.setSchoolName(schoolName);

			// Create the OrphanEducationSchoolModel to link education and school
			OrphanEducationSchoolModel eduSchool = new OrphanEducationSchoolModel(education, school);
			List<OrphanEducationSchoolModel> educationSchoolList = new ArrayList<>();
			educationSchoolList.add(eduSchool);
			updatedOrphan.setEducationSchoolRecords(educationSchoolList);

			// Call the update service to update the orphan details
			boolean success = orphanProfileService.updateOrphan(updatedOrphan);
			if (success) {
				request.setAttribute("orphan", updatedOrphan);
				request.setAttribute("success", "Orphan details updated successfully.");
			} else {
				request.setAttribute("error", "Failed to update orphan.");
			}
			request.getRequestDispatcher("/WEB-INF/pages/edit-orphan.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Error updating orphan ");
			request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);
		}
	}

}
