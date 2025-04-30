package com.heretohelp.service;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.heretohelp.config.DbConfig;
import com.heretohelp.model.UserModel;
import com.heretohelp.util.ImageUtil;
import com.heretohelp.util.SessionUtil;

/**
 * Servlet implementation class ManageAccountService1
 */
public class ManageAccountService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection dbConn;
	private ImageUtil imageUtil;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageAccountService() {
		super();
		imageUtil = new ImageUtil();
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	/**
	 * update existing user profile image data
	 *
	 * @param userModel the student details to be updated
	 * @return String "User updated successfully." indicating the success of the
	 *         operation else "Failed to update user."
	 */
	public Boolean updateProfileImage(UserModel userModel, HttpServletRequest req) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}		
		String currentUser = (String) SessionUtil.getAttribute(req, "username");
		String updateQuery = "UPDATE user set image_url=? WHERE username = ?";

		try {
			PreparedStatement updateStmt = dbConn.prepareStatement(updateQuery);
			// Insert student details
			updateStmt.setString(1, userModel.getImageUrl());
			updateStmt.setString(2, currentUser);

			return updateStmt.executeUpdate() > 0 ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * update existing user data
	 *
	 * @param userModel the student details to be updated
	 * @return String "User updated successfully." indicating the success of the
	 *         operation else "Failed to update user."
	 */
	public String updateUser(UserModel userModel, HttpServletRequest req) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}
		String result = checkDuplicateData(userModel);
		if (!result.equals("NoDuplicatedDataFound")|| result==null) {
			return result;
		}
		
		String currentUser = (String) SessionUtil.getAttribute(req, "username");
		String updateQuery = "UPDATE user SET first_name = ?, last_name = ?, username = ?, dob = ?, gender = ?, number = ?, email = ?, password = ? WHERE username = ?";

		try {
			PreparedStatement updateStmt = dbConn.prepareStatement(updateQuery);
			// Insert student details
			updateStmt.setString(1, userModel.getFirstName());
			updateStmt.setString(2, userModel.getLastName());
			updateStmt.setString(3, userModel.getUsername());
			updateStmt.setDate(4, Date.valueOf(userModel.getDob()));
			updateStmt.setString(5, userModel.getGender());
			updateStmt.setString(6, userModel.getNumber());
			updateStmt.setString(7, userModel.getEmail());
			updateStmt.setString(8, userModel.getPassword());
			updateStmt.setString(9, currentUser);

			return updateStmt.executeUpdate() > 0 ? "User updated successfully." : "Failed to update user.";
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Checks for duplicate data in the database
	 *
	 * @param userModel the user details
	 * @return returns "NoDuplicatedDataFound" when values are unique else returns
	 *         value that is found duplicate
	 */
	public String checkDuplicateData(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT username, email, number FROM user WHERE  email = ? OR number = ?";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			ps.setString(1, userModel.getEmail());
			ps.setString(2, userModel.getNumber());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String username = rs.getString("username");
				String matchedEmail = rs.getString("email");
				String matchedPhone = rs.getString("number");
				if (matchedEmail != null && matchedEmail.equals(userModel.getEmail())
						&& !username.equals(userModel.getUsername())) {
					return "Email already exists";
				} else if (matchedPhone != null && matchedPhone.equals(userModel.getNumber())
						&& !username.equals(userModel.getUsername())) {
					return "Phone number already exists";
				}

			}
			return "NoDuplicatedDataFound";
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * retrieve existing user data
	 *
	 * @param userModel contains the username
	 * @return userModel with existing data of user else null
	 */
	public UserModel retrieveUserData(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT first_name, last_name, dob, gender, number, email, password, image_url From user WHERE username= ?";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			// Insert student details
			ps.setString(1, userModel.getUsername());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userModel.setFirstName(rs.getString("first_name"));
				userModel.setLastName(rs.getString("last_name"));
				userModel.setDob(LocalDate.parse(rs.getString("dob")));
				userModel.setGender(rs.getString("gender"));
				userModel.setNumber(rs.getString("number"));
				userModel.setEmail(rs.getString("email"));
				userModel.setPassword(rs.getString("password"));
				userModel.setImageUrl(rs.getString("image_url"));
			}
			return userModel;
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
