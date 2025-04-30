package com.heretohelp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import com.heretohelp.config.DbConfig;
import com.heretohelp.model.OrphanModel;
import com.heretohelp.model.UserModel;

/**
 * DashboardService handles the retrieving insights about the data
 * 
 */
public class DashboardService {
	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public DashboardService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Retrieves the latest 4 orphan added to the system
	 *
	 * @return list of orphanModel order by admission date in descending order
	 * @throws SQLException
	 */
	public List<OrphanModel> getOrphanModels() throws SQLException {
		List<OrphanModel> orphanList = new ArrayList<OrphanModel>();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT * from orphan order by admission_date desc limit 4";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrphanModel orphanModel = new OrphanModel();
				orphanModel.setOrphanId(rs.getInt("orphan_id"));
				orphanModel.setFirstName(rs.getString("first_name"));
				orphanModel.setLastName(rs.getString("last_name"));
				orphanModel.setDob(LocalDate.parse(rs.getString("dob")));
				orphanModel.setGender(rs.getString("gender"));
				orphanModel.setStatus(rs.getString("status"));
				orphanModel.setAdmissionDate(LocalDate.parse(rs.getString("admission_date")));
				orphanList.add(orphanModel);

			}

			return orphanList;
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves orphan data from database
	 *
	 * @return int Total Number of orphan in the database
	 * @throws SQLException
	 */
	public int getTotalOrphan() throws SQLException {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return 0;
		}

		String selectQuery = "SELECT Count(*) from orphan";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}else {
				
				return 0;
			}
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Retrieves orphan data from database whose have the birthday nearing
	 *
	 * @return list of orphanModel
	 * @throws SQLException
	 */
	public List<OrphanModel> getNearestBirthdayOrphan() throws SQLException {
		List<OrphanModel> orphanList = new ArrayList<OrphanModel>();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT first_name, last_name, dob FROM orphan ORDER BY DATE_FORMAT(dob, '%m-%d') >= DATE_FORMAT(CURRENT_DATE, '%m-%d') DESC, DATE_FORMAT(dob, '%m-%d') LIMIT 3";

		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrphanModel orphanModel = new OrphanModel();
				orphanModel.setFirstName(rs.getString("first_name"));
				orphanModel.setLastName(rs.getString("last_name"));
				orphanModel.setDob(LocalDate.parse(rs.getString("dob")));
				orphanList.add(orphanModel);
			}

			return orphanList;
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves orphan data from database with input firstname or last name
	 *
	 * @return list of orphanModel
	 * @throws SQLException
	 */
	public List<OrphanModel> getOrphanData(String searchItem) throws SQLException {
		List<OrphanModel> orphanList = new ArrayList<OrphanModel>();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT * FROM orphan WHERE first_name LIKE ? OR last_name LIKE ? ";

		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			String searchTerm = "%" + searchItem + "%";
			ps.setString(1, searchTerm);
			ps.setString(2, searchTerm);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrphanModel orphanModel = new OrphanModel();
				orphanModel.setOrphanId(rs.getInt("orphan_id"));
				orphanModel.setFirstName(rs.getString("first_name"));
				orphanModel.setLastName(rs.getString("last_name"));
				orphanModel.setDob(LocalDate.parse(rs.getString("dob")));
				orphanModel.setGender(rs.getString("gender"));
				orphanModel.setStatus(rs.getString("status"));
				orphanModel.setAdmissionDate(LocalDate.parse(rs.getString("admission_date")));
				orphanList.add(orphanModel);
			}

			return orphanList;
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * Retrieves the most recent users who have signed up, ordered by user ID in descending order
	 *
	 * @return list of userModel containing username of the latest user
	 * @throws SQLException
	 */
	public List<UserModel> getLatestUserData() throws SQLException {
		List<UserModel> userList = new ArrayList<UserModel>();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT username FROM USER WHERE username != 'admin' ORDER BY user_id DESC LIMIT 3";

		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserModel userModel = new UserModel();
				userModel.setUsername(rs.getString("username"));
				userList.add(userModel);
			}
			return userList;
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
