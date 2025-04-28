package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.heretohelp.model.UserModel;
import com.heretohelp.service.LoginService;
import com.heretohelp.util.CookieUtil;
import com.heretohelp.util.RedirectionUtil;
import com.heretohelp.util.SessionUtil;

/**
 * @author Shirish Govind Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		new RedirectionUtil();
		this.loginService = new LoginService();
		;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		UserModel loginCredential = new UserModel(username, password);
		Boolean loginStatus = loginService.checkUserCredentials(loginCredential);

		if (loginStatus != null && loginStatus) {
			String Role = loginService.retrieveUserRole(loginCredential) ? "admin" : "adopter";
			SessionUtil.setAttribute(req, "username", username);
			if (Role.equals("admin")) {
				CookieUtil.addCookie(resp, "role", Role, 5*60);
				resp.sendRedirect(req.getContextPath() + "/dashboard");
			} else {
				CookieUtil.addCookie(resp, "role", Role, 5 * 60);
				resp.sendRedirect(req.getContextPath() + "/home"); // Redirect to /home
			}
		} else {
			handleLoginFailure(req,resp,loginStatus);
			
		}
	}

	/**
	 * Handles login failures by setting attributes and forwarding to the login
	 * page.
	 *
	 * @param req         HttpServletRequest object
	 * @param resp        HttpServletResponse object
	 * @param loginStatus Boolean indicating the login status
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
	}

}
