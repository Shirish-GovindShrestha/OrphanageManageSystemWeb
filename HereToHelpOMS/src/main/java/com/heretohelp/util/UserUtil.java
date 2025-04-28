package com.heretohelp.util;

import com.heretohelp.model.UserModel;
import com.heretohelp.service.ManageAccountService;

import jakarta.servlet.http.HttpServletRequest;

public class UserUtil {
	private ManageAccountService manageAccountService;
	
	public UserUtil() {
		super();
		manageAccountService = new ManageAccountService();

		// TODO Auto-generated constructor stub
	}
	
	public void setUserData(HttpServletRequest req) {
		String currentUser = (String) SessionUtil.getAttribute(req, "username");
		UserModel userModel = new UserModel();
		userModel.setUsername(currentUser);
		userModel = manageAccountService.retrieveUserData(userModel);
		if (userModel != null) {
			req.setAttribute("firstName", userModel.getFirstName());
			req.setAttribute("lastName", userModel.getLastName());
			req.setAttribute("username", userModel.getUsername());
			req.setAttribute("dob", userModel.getDob());
			req.setAttribute("gender", userModel.getGender());
			req.setAttribute("email", userModel.getEmail());
			req.setAttribute("phoneNumber", userModel.getNumber());
			String imageUrl = userModel.getImageUrl();
			if (imageUrl != null && !imageUrl.trim().isEmpty()) {
				req.setAttribute("imageUrl", "/resources/images/user/" + imageUrl);
			} else {
				req.setAttribute("imageUrl", "/resources/images/user/default.png");
			}
		}

	}

}
