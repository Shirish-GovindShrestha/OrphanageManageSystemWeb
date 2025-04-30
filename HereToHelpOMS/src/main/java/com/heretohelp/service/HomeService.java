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

/**
 * LoginService handles the login of old users
 * 
 */
public class HomeService {
	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public HomeService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Fetches a list of active orphans from the database.
	 *
	 * @return list of orphanModel having status active, or null if database connection is unavailable
	 * @throws SQLException
	 */
	public List<OrphanModel> getOrphanModels() throws SQLException{
		List<OrphanModel> orphanList = new ArrayList<OrphanModel>();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT * from orphan where status='Active' ";
		try {
			PreparedStatement ps = dbConn.prepareStatement(selectQuery);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				OrphanModel orphanModel = new OrphanModel();
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
	


}
