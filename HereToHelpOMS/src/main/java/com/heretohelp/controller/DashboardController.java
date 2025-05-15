package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.heretohelp.model.EducationModel;
import com.heretohelp.model.OrphanEducationSchoolModel;
import com.heretohelp.model.OrphanModel;
import com.heretohelp.model.SchoolModel;
import com.heretohelp.model.UserModel;
import com.heretohelp.service.DashboardService;
import com.heretohelp.util.ImageUtil;

/**
 * @author Shirish Govind Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private ImageUtil imageUtil;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardController() {
		super();
		dashboardService = new DashboardService();
		this.imageUtil = new ImageUtil();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			List<UserModel> userList = dashboardService.getLatestUserData();
			request.setAttribute("userList", userList);
			List<OrphanModel> orphanList = dashboardService.getOrphanModels();
			int totalOrphan = dashboardService.getTotalOrphan();
			request.setAttribute("totalOrphanCount", totalOrphan);
			String searchItem = request.getParameter("search-item");
			if (searchItem != null) {
				request.setAttribute("searchActive", true);
				orphanList = dashboardService.getOrphanData(searchItem);
				if (orphanList != null && !orphanList.isEmpty()) {
					request.setAttribute("orphanList", orphanList);
					request.setAttribute("success", "Found in the database");

				} else {
					request.setAttribute("error", "No orphan data found in the database.");
				}
			} else {
				request.setAttribute("orphanList", orphanList);
			}
			List<OrphanModel> orphanBirthdateList = dashboardService.getNearestBirthdayOrphan();
			request.setAttribute("orphanBirthdateList", orphanBirthdateList);
			request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);

		} catch (SQLException e) {
			// Log and handle the error
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			OrphanModel orphanModel = extractOrphanModel(req, resp);
			Boolean isAdded = dashboardService.addOrphanWithEducation(orphanModel); // NEW method

			if (isAdded == null) {
				req.setAttribute("error", "Our server is under maintenance. Please try again later!");
			} else if (isAdded) {
				try {
					if (uploadImage(req)) {
						req.setAttribute("success", "Orphan details added successfully.");
					} else {
						req.setAttribute("error", "Could not upload the image. Please try again later!");
					}
				} catch (Exception e) {
					req.setAttribute("error", "Could not upload the image. Please try again later!");
				}
			} else {
				req.setAttribute("error", "Could not register orphan data. Please try again later!");
			}
		} catch (Exception e) {
			req.setAttribute("error", "Could not register orphan data. Please try again later!");
		}

		doGet(req, resp);
	}

	private OrphanModel extractOrphanModel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		String gender = req.getParameter("gender");
		String status = req.getParameter("status");
		LocalDate admissionDate = LocalDate.now();

		// Image handling
		Part image = req.getPart("photo"); // Fix: match form input name
		String imageUrl = image != null ? imageUtil.getImageNameFromPart(image) : "default.png";

		// Education attributes from form
		String schoolName = req.getParameter("schoolName");
		String grade = req.getParameter("grade");
		String performance = req.getParameter("performance");
		String remarks = req.getParameter("remarks");

		EducationModel education = null;
		if (grade != null && !grade.isEmpty() && performance != null && !performance.isEmpty()) {
			education = new EducationModel();
			education.setGrade(grade);
			education.setPerformance(performance);
			education.setRemarks(remarks);
		}

		// Create school model only if the school name is provided
		SchoolModel school = null;
		if (schoolName != null && !schoolName.isEmpty()) {
			school = new SchoolModel();
			school.setSchoolName(schoolName);
		}

		// Create orphan model
		OrphanModel orphan = new OrphanModel(firstName, lastName, dob, gender, status, admissionDate, imageUrl);

		// Attach education record only if education and school are available
		List<OrphanEducationSchoolModel> educationList = new ArrayList<>();
		if (education != null && school != null) {
			OrphanEducationSchoolModel eduSchoolModel = new OrphanEducationSchoolModel();
			eduSchoolModel.setEducation(education);
			eduSchoolModel.setSchool(school);
			educationList.add(eduSchoolModel);
		}

		// Set the education records to the orphan model, even if it's empty
		orphan.setEducationSchoolRecords(educationList);

		// Return the orphan model
		return orphan;
	}

	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("photo");
		return imageUtil.uploadImage(image, "orphan");
	}
}
