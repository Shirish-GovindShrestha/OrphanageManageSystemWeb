package com.heretohelp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

import com.heretohelp.model.UserModel;
import com.heretohelp.service.ManageAccountService;
import com.heretohelp.util.ImageUtil;
import com.heretohelp.util.RedirectionUtil;
import com.heretohelp.util.UserUtil;


@WebServlet(asyncSupported = true, urlPatterns = { "/changePfp" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ChangePfpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RedirectionUtil redirectionUtil;
	private ManageAccountService manageAccountService;
	private ImageUtil imageUtil;
	private UserUtil userUtil;

	
	public ChangePfpController() {
		super();
		redirectionUtil = new RedirectionUtil();
		manageAccountService = new ManageAccountService();
		imageUtil = new ImageUtil();
		userUtil = new UserUtil();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
            UserModel userModel = extractProfile(req);
            boolean result = manageAccountService.updateProfileImage(userModel, req);

            if (uploadImage(req) && result) {
            	  userUtil.setUserData(req);  // This will populate the user data
                redirectionUtil.setMsgAndRedirect(req, resp, "success", "Your account is successfully updated!", RedirectionUtil.accountUrl);
            } else {
            	 userUtil.setUserData(req);  // Make sure to call this even if the image upload fails
                redirectionUtil.setMsgAndRedirect(req, resp, "error", "There was an error updating your profile.", RedirectionUtil.accountUrl);
               
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            redirectionUtil.setMsgAndRedirect(req, resp, "error", "An error occurred while updating your account.", RedirectionUtil.accountUrl);
        }
	}
	
	
	private UserModel extractProfile(HttpServletRequest req) throws IOException, ServletException {
		
		Part image = req.getPart("image");
		String imageUrl = imageUtil.getImageNameFromPart(image);
		return new UserModel(imageUrl);
	}
	
	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("image");
		return imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "user");
	}

}
