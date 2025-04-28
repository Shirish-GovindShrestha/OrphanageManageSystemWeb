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
import com.heretohelp.service.HomeService;


/**
 * @author Shirish Govind Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home", "/"})
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HomeService homeService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
    	
        super();
        homeService = new HomeService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			List<OrphanModel> orphanList = homeService.getOrphanModels();
			if(orphanList==null) {
				
				request.setAttribute("orphanList", null);
			}	
			request.setAttribute("orphanList", orphanList);
			request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request,response);
			
		}catch (SQLException e) {
            // Log and handle the error
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
