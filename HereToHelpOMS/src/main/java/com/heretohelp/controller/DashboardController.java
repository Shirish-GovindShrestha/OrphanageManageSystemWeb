package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.heretohelp.model.OrphanModel;
import com.heretohelp.model.UserModel;
import com.heretohelp.service.DashboardService;



/**
 * @author Shirish Govind Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardController() {
		super();
		dashboardService = new DashboardService();
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
				orphanList = dashboardService.getOrphanData(searchItem);
				if(orphanList!=null && !orphanList.isEmpty()) {
					request.setAttribute("orphanList", orphanList);
					request.setAttribute("success", "Found in the database");
				}else {
					request.setAttribute("error", "No orphan data found in the database.");
				}
			}else {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
