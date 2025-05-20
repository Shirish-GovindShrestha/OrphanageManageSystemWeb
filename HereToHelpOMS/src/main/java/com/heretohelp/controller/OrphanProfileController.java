package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.heretohelp.model.OrphanModel;
import com.heretohelp.service.OrphanService;

/**
 * Servlet implementation class ProfileController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/orphan-profile" })
public class OrphanProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrphanService orphanService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrphanProfileController() {
		super();
		this.orphanService = new OrphanService();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Handles HTTP GET requests to retrieve and display the profile of a specific
	 * orphan. Fetches the orphan's details by ID and forwards them to the profile
	 * JSP for rendering. If the orphan ID is invalid or the orphan is not found, an
	 * error message is displayed.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orphanId = request.getParameter("id");
		try {
			OrphanModel orphan = orphanService.getOrphanFromId(Integer.parseInt(orphanId));

			if (orphan != null) {
				request.setAttribute("orphan", orphan);
				request.getRequestDispatcher("/WEB-INF/pages/orphan-profile.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "Orphan Not Found");
				request.getRequestDispatcher("/WEB-INF/pages/orphans.jsp").forward(request, response);
			}

		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
