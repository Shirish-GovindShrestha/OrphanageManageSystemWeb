package com.heretohelp.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.heretohelp.config.DbConfig;
import com.heretohelp.model.UserModel;


/**
 * RegisterService handles the registration of new users. It manages database
 * interactions for new user registration
 */
public class RegisterService {
	private int userRole;
	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public RegisterService() {
		try {
			this.userRole =1;
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	
	/**
	 * Registers a new user in the database.
	 *
	 * @param userModel the user details to be registered
	 * @return String "User added successfully." indicating the success of the operation else "Failed to add user."
	 */
	public Boolean addUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String insertQuery = "INSERT INTO user (first_name, last_name, username, dob, gender, number, email, password, role_id, image_url=?) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

		try {
			PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery);
			// Insert student details
			insertStmt.setString(1, userModel.getFirstName());
			insertStmt.setString(2, userModel.getLastName());
			insertStmt.setString(3, userModel.getUsername());
			insertStmt.setDate(4, Date.valueOf(userModel.getDob()));
			insertStmt.setString(5, userModel.getGender());
			insertStmt.setString(6, userModel.getNumber());
			insertStmt.setString(7, userModel.getEmail());
			insertStmt.setString(8, userModel.getPassword());
			insertStmt.setInt(9, userRole);
			insertStmt.setString(10, userModel.getImageUrl());
			
			return insertStmt.executeUpdate() > 0;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Checks for duplicate data in the database
	 *
	 * @param userModel the user details
	 * @return returns "NoDuplicatedDataFound" when values are unique else returns value that is found duplicate
	 */
	public String checkDuplicateData(UserModel userModel) {
		if (dbConn == null) {
			return "Database connection is not available.";
		}

		String selectQuery =  "SELECT username, email,number FROM user WHERE username = ? OR email = ? OR number = ?";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			// Insert student details
			ps.setString(1, userModel.getUsername());
			ps.setString(2, userModel.getEmail());
			ps.setString(3, userModel.getNumber());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String matchedUsername = rs.getString("username");
	            String matchedEmail = rs.getString("email");
	            String matchedPhone = rs.getString("number");
	            if (matchedUsername != null && matchedUsername.equals(userModel.getUsername())) {
	                return "Username already exists";
	            } else if (matchedEmail != null && matchedEmail.equals(userModel.getEmail())) {
	                return "Email already exists";
	            } else if (matchedPhone != null && matchedPhone.equals(userModel.getNumber())) {
	                return "Phone number already exists";
	            }
	           
			}
			return "NoDuplicatedDataFound" ;
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error while loggin ";	
		}
	}
}