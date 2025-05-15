package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.heretohelp.model.OrphanModel;
import com.heretohelp.service.DashboardService;
import com.heretohelp.service.HomeService;
import com.heretohelp.service.OrphanService;

/**
 * Servlet implementation class OrphanController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/orphans" })
public class OrphanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HomeService homeService;
	private OrphanService orphanService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrphanController() {
		super();
		homeService = new HomeService();
		orphanService = new OrphanService();
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
			List<OrphanModel> orphanList = homeService.getOrphanModels();
			request.setAttribute("orphanList", orphanList);
			String searchItem = request.getParameter("search-item");
			if (searchItem != null) {
				request.setAttribute("searchActive", true);
				orphanList = orphanService.getOrphanData(searchItem);
				if (orphanList != null && !orphanList.isEmpty()) {
					request.setAttribute("orphanList", orphanList);
					request.setAttribute("success", "Found in the database");

				} else {
					request.setAttribute("error", "No orphan data found in the database.");
				}
			} else {
				request.setAttribute("orphanList", orphanList);
			}
			request.getRequestDispatcher("/WEB-INF/pages/orphans.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
