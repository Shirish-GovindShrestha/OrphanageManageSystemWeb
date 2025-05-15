package com.heretohelp.service;

import com.heretohelp.model.UserModel;
import com.heretohelp.util.CookieUtil;
import com.heretohelp.util.SessionUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class UserService {
	private ManageAccountService manageAccountService;
	
	public UserService() {
		super();
		manageAccountService = new ManageAccountService();

	

		// TODO Auto-generated constructor stub
	}
	
	public void setUserData(HttpServletRequest req) {
		Cookie Cookie = CookieUtil.getCookie(req, "username");
		String currentUser = Cookie != null ? Cookie.getValue() : null;
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
