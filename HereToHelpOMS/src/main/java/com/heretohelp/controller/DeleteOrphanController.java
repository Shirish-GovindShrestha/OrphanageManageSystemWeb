package com.heretohelp.controller;

import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.heretohelp.service.DashboardService;



@WebServlet(asyncSupported = true, urlPatterns = { "/deleteOrphan" })
public class DeleteOrphanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;

	public DeleteOrphanController() {
		super();
		this.dashboardService = new DashboardService(); // Assuming service is here
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orphanIdStr = request.getParameter("id");

		if (orphanIdStr != null) {
			try {
				int orphanId = Integer.parseInt(orphanIdStr);
				boolean isDeleted = dashboardService.deleteOrphanById(orphanId);

				if (isDeleted) {
					request.setAttribute("success", "Orphan record deleted successfully!");
				} else {
					request.setAttribute("error", "Failed to delete the orphan record.");
				}
			} catch (NumberFormatException e) {
				request.setAttribute("error", "Invalid Orphan ID.");
			}
		} else {
			request.setAttribute("error", "Orphan ID not provided.");
		}

		// Redirect back to the dashboard or orphan list page
		response.sendRedirect(request.getContextPath() + "/dashboard");
	}
}
