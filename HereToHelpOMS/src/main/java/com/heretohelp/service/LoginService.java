package com.heretohelp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.heretohelp.config.DbConfig;
import com.heretohelp.model.UserModel;
import com.heretohelp.util.PasswordUtil;

/**
 * LoginService handles the login of old users
 * 
 */
public class LoginService {
	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public LoginService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Checks database for existing user
	 *
	 * @param userModel the user credentials
	 * @return Boolean indicating the success of the operation
	 */
	public Boolean checkUserCredentials(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT username, password From user WHERE username= ?";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			// Insert student details
			ps.setString(1, userModel.getUsername());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return validatePassword(rs, userModel);
			}
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return false;
	}

	/**
	 * Validates the password retrieved from the database.
	 *
	 * @param result    the ResultSet containing the password from the database
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");
		 return dbUsername.equals(userModel.getUsername()) && PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());

	}

	public Boolean retrieveUserRole(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}
		String selectQuery = "select r.role_name from role r join user u on u.role_id = r.role_id where username=?";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			ps.setString(1, userModel.getUsername());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return "admin".equals(rs.getString("role_name"));
			}
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
