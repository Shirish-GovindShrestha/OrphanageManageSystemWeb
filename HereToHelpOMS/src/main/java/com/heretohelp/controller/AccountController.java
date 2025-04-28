package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;

import com.heretohelp.model.UserModel;
import com.heretohelp.service.ManageAccountService;
import com.heretohelp.util.ImageUtil;
import com.heretohelp.util.PasswordUtil;
import com.heretohelp.util.RedirectionUtil;
import com.heretohelp.util.SessionUtil;
import com.heretohelp.util.UserUtil;
import com.heretohelp.util.ValidationUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/account" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RedirectionUtil redirectionUtil;
	private ManageAccountService manageAccountService;
	private ImageUtil imageUtil;
	private UserUtil userUtil;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountController() {
		super();
		redirectionUtil = new RedirectionUtil();
		manageAccountService = new ManageAccountService();
		imageUtil = new ImageUtil();
		userUtil = new UserUtil();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			userUtil.setUserData(req);
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
			userUtil.setUserData(req);
			if (uploadImage(req) && result.equals("User updated successfully.")) {	
				redirectionUtil.setMsgAndRedirect(req, resp, "success", "Your account is successfully updated!",
						RedirectionUtil.accountUrl);
			} else {
				handleError(req, resp, "Could not upload the image. Please try again later!");
			}
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

	
	private UserModel extractUserModel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String username = req.getParameter("username");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String number = req.getParameter("phoneNumber");
		String password = req.getParameter("password");
		String retypePassword = req.getParameter("retypePassword");

		if (password == null || !ValidationUtil.doPasswordsMatch(password, retypePassword)) {
			userUtil.setUserData(req);
			redirectionUtil.setMsgAndRedirect(req, resp, "error", "Please correct your password & retype-password!",
					RedirectionUtil.accountUrl);
		}
		password = PasswordUtil.encrypt(username, password);
		Part image = req.getPart("image");
		String imageUrl = imageUtil.getImageNameFromPart(image);

		return new UserModel(firstName, lastName, username, dob, gender, number, email, password, imageUrl);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		userUtil.setUserData(req);
		req.getRequestDispatcher("/WEB-INF/pages/account.jsp").forward(req, resp);
	}

	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("image");
		return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "user");
	}

}
