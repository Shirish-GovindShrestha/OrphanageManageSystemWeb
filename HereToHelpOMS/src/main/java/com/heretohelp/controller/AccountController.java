package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

import com.heretohelp.model.UserModel;
import com.heretohelp.service.ManageAccountService;
import com.heretohelp.service.UserService;
import com.heretohelp.util.PasswordUtil;
import com.heretohelp.util.RedirectionUtil;
import com.heretohelp.util.ValidationUtil;

/**
 * @author Shirish Govind Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/account" })
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RedirectionUtil redirectionUtil;
	private ManageAccountService manageAccountService;
	private UserService userService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountController() {
		super();
		redirectionUtil = new RedirectionUtil();
		manageAccountService = new ManageAccountService();
		userService = new UserService();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			userService.setUserData(req);
			req.getRequestDispatcher("/WEB-INF/pages/account.jsp").forward(req, resp);
		} catch (Exception e) {
			redirectionUtil.setMsgAndRedirect(req, resp, "error", "Error retrieving User Data",
					RedirectionUtil.accountUrl);
			e.printStackTrace(); // Log the exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			UserModel userModel = extractUserModel(req, resp);
			String result = manageAccountService.updateUser(userModel, req);
			userService.setUserData(req);
			switch (result) {
			case "User updated successfully.":
				redirectionUtil.setMsgAndRedirect(req, resp, "success", "Your account is successfully updated!",
						RedirectionUtil.accountUrl);
				break;
			case "Username already exists":
			case "Email already exists":
			case "Phone number already exists":
				handleError(req, resp, result);
				break;
			default:
				handleError(req, resp,
						result != null ? result : "An unexpected error occurred. Please try again later!");
			}

		} catch (

		Exception e) {
			handleError(req, resp, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); // Log the exception
		}

	}
	
	/**
	 * Extracts user data from the HttpServletRequest and processes it to create a UserModel object.
	 * Handles validation and password management (including updates) for the user.
	 *
	 * @param req  HttpServletRequest object containing user input
	 * @param resp HttpServletResponse object for sending responses
	 * @return UserModel object containing extracted and processed user information, or null if errors occur
	 * @throws Exception if any validation, parsing, or service-related error occurs
	 */
	private UserModel extractUserModel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String username = req.getParameter("username");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String number = req.getParameter("phoneNumber");

		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("password");
		String retypePassword = req.getParameter("retypePassword");

		String finalPassword;

		// If user is trying to change password
		if (newPassword != null && !newPassword.isBlank()) {

			// Check old password is correct
			String storedPassword = manageAccountService.getCurrentPassword(username);
			System.out.print(storedPassword);
			System.out.print(oldPassword);
			if (!oldPassword.equals(storedPassword)) {
				userService.setUserData(req);
				redirectionUtil.setMsgAndRedirect(req, resp, "error", "Old password is incorrect!",
						RedirectionUtil.accountUrl);
				return null;
			}

			// Check new and retype password match
			if (!ValidationUtil.doPasswordsMatch(newPassword, retypePassword)) {
				userService.setUserData(req);
				redirectionUtil.setMsgAndRedirect(req, resp, "error", "Passwords do not match!",
						RedirectionUtil.accountUrl);
				return null;
			}

			// Encrypt new password
			finalPassword = PasswordUtil.encrypt(username, newPassword);

		} else {
			// Keep the existing password if not changing
			finalPassword = PasswordUtil.encrypt(manageAccountService.getCurrentPassword(username),username);
		}

		return new UserModel(firstName, lastName, username, dob, gender, number, email, finalPassword);
	}

	
	/**
	 * Handles errors by setting an error message in the request attributes
	 * and forwarding to the account page.
	 *
	 * @param req     HttpServletRequest object
	 * @param resp    HttpServletResponse object
	 * @param message Error message to display to the user
	 * @throws ServletException if forwarding fails
	 * @throws IOException      if an input/output error occurs
	 */
	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.getRequestDispatcher("/WEB-INF/pages/account.jsp").forward(req, resp);
	}

}
