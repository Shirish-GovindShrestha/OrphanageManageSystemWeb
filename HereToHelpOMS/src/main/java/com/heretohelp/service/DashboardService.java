package com.heretohelp.service;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.heretohelp.config.DbConfig;
import com.heretohelp.model.EducationModel;
import com.heretohelp.model.OrphanEducationSchoolModel;
import com.heretohelp.model.OrphanModel;
import com.heretohelp.model.SchoolModel;
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
		List<OrphanModel> orphanList = new ArrayList<>();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		// SQL query to fetch orphan details along with the latest education and school
		String selectQuery = "SELECT o.orphan_id, o.first_name, o.last_name, o.dob, o.gender, o.status, o.admission_date "
				+ "FROM orphan o " + "ORDER BY o.admission_date DESC LIMIT 4";

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

				String educationQuery = "SELECT e.education_id, e.grade, e.performance, e.remarks, s.school_id, s.school_name "
						+ "FROM education e " + "JOIN orphan_education oe ON e.education_id = oe.education_id "
						+ "JOIN orphan_education_school oes ON oe.orphan_id = oes.orphan_id AND oe.education_id = oes.education_id "
						+ "JOIN school s ON oes.school_id = s.school_id "
						+ "WHERE oe.orphan_id = ? ORDER BY e.grade Desc LIMIT 1";

				try (PreparedStatement psEducation = dbConn.prepareStatement(educationQuery)) {
					psEducation.setInt(1, orphanModel.getOrphanId());
					ResultSet rsEducation = psEducation.executeQuery();

					List<OrphanEducationSchoolModel> eduSchoolList = new ArrayList<>();

					while (rsEducation.next()) {
						EducationModel edu = new EducationModel(rsEducation.getInt("education_id"),
								rsEducation.getString("grade"), rsEducation.getString("performance"),
								rsEducation.getString("remarks"));

						SchoolModel school = new SchoolModel();
						school.setSchoolId(rsEducation.getInt("school_id"));
						school.setSchoolName(rsEducation.getString("school_name"));

						eduSchoolList.add(new OrphanEducationSchoolModel(edu, school));
					}

					orphanModel.setEducationSchoolRecords(eduSchoolList);
				}
				orphanList.add(orphanModel);
			}

			return orphanList;
		} catch (SQLException e) {
			System.err.println("Error while fetching orphan data: " + e.getMessage());
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
			} else {

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
				OrphanModel orphan = new OrphanModel();
				orphan.setOrphanId(rs.getInt("orphan_id"));
				orphan.setFirstName(rs.getString("first_name"));
				orphan.setLastName(rs.getString("last_name"));
				orphan.setDob(LocalDate.parse(rs.getString("dob")));
				orphan.setGender(rs.getString("gender"));
				orphan.setStatus(rs.getString("status"));
				orphan.setAdmissionDate(LocalDate.parse(rs.getString("admission_date")));
				orphan.setImageUrl(rs.getString("image_url"));

				String educationQuery = "SELECT e.education_id, e.grade, e.performance, e.remarks, s.school_id, s.school_name "
						+ "FROM education e " + "JOIN orphan_education oe ON e.education_id = oe.education_id "
						+ "JOIN orphan_education_school oes ON oe.orphan_id = oes.orphan_id AND oe.education_id = oes.education_id "
						+ "JOIN school s ON oes.school_id = s.school_id "
						+ "WHERE oe.orphan_id = ? ORDER BY e.grade Desc LIMIT 1";

				try (PreparedStatement psEducation = dbConn.prepareStatement(educationQuery)) {
					psEducation.setInt(1, orphan.getOrphanId());
					ResultSet rsEducation = psEducation.executeQuery();

					List<OrphanEducationSchoolModel> eduSchoolList = new ArrayList<>();

					while (rsEducation.next()) {
						EducationModel edu = new EducationModel(rsEducation.getInt("education_id"),
								rsEducation.getString("grade"), rsEducation.getString("performance"),
								rsEducation.getString("remarks"));

						SchoolModel school = new SchoolModel();
						school.setSchoolId(rsEducation.getInt("school_id"));
						school.setSchoolName(rsEducation.getString("school_name"));

						eduSchoolList.add(new OrphanEducationSchoolModel(edu, school));
					}

					orphan.setEducationSchoolRecords(eduSchoolList);
				}
				orphanList.add(orphan);
			}

			// Fetch education and school info

			return orphanList;
		} catch (SQLException e) {
			System.err.println("Error while loggin " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves the most recent users who have signed up, ordered by user ID in
	 * descending order
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

	public boolean deleteOrphanById(int orphanId) {
		boolean isDeleted = false;
		String deleteQuery = "DELETE FROM orphan WHERE orphan_id = ?";

		try (PreparedStatement ps = dbConn.prepareStatement(deleteQuery)) {
			ps.setInt(1, orphanId);
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			System.err.println("Error deleting orphan: " + e.getMessage());
			e.printStackTrace();
		}

		return isDeleted;
	}

	public Boolean addOrphanWithEducation(OrphanModel orphanModel) throws SQLException {
		if (dbConn == null)
			return null;

		try {
			dbConn.setAutoCommit(false); // Begin transaction

			// Insert orphan and get orphan ID
			int orphanId = insertOrphan(orphanModel);
			if (orphanId == -1)
				throw new SQLException("Failed to insert orphan.");

			// Proceed if education records are provided
			List<OrphanEducationSchoolModel> eduRecords = orphanModel.getEducationSchoolRecords();
			if (eduRecords != null && !eduRecords.isEmpty()) {
				for (OrphanEducationSchoolModel eduModel : eduRecords) {
					// Handle school and education records
					int schoolId = insertOrGetSchool(eduModel.getSchool().getSchoolName());
					int educationId = insertOrGetEducation(eduModel.getEducation());

					// Link orphan, education, and school in the respective tables
					linkOrphanEducation(orphanId, educationId);
					linkOrphanEducationSchool(orphanId, educationId, schoolId);
				}
			}

			dbConn.commit(); // All done
			return true;

		} catch (Exception e) {
			dbConn.rollback(); // Roll back if anything fails
			e.printStackTrace();
			return false;
		} finally {
			dbConn.setAutoCommit(true); // Restore auto-commit
		}
	}

	private int insertOrphan(OrphanModel orphanModel) throws SQLException {
		String orphanQuery = "INSERT INTO orphan (first_name, last_name, dob, gender, status, admission_date, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement orphanPs = dbConn.prepareStatement(orphanQuery, Statement.RETURN_GENERATED_KEYS)) {
			orphanPs.setString(1, orphanModel.getFirstName());
			orphanPs.setString(2, orphanModel.getLastName());
			orphanPs.setString(3, orphanModel.getDob().toString());
			orphanPs.setString(4, orphanModel.getGender());
			orphanPs.setString(5, orphanModel.getStatus());
			orphanPs.setString(6, orphanModel.getAdmissionDate().toString());
			orphanPs.setString(7, orphanModel.getImageUrl());

			int orphanRows = orphanPs.executeUpdate();
			if (orphanRows == 0)
				return -1;

			ResultSet orphanKeys = orphanPs.getGeneratedKeys();
			return orphanKeys.next() ? orphanKeys.getInt(1) : -1;
		}
	}

	private int insertOrGetSchool(String schoolName) throws SQLException {
		// Check if the school already exists
		String schoolQuery = "SELECT school_id FROM school WHERE school_name = ?";
		try (PreparedStatement schoolPs = dbConn.prepareStatement(schoolQuery)) {
			schoolPs.setString(1, schoolName);
			ResultSet schoolRs = schoolPs.executeQuery();
			if (schoolRs.next()) {
				return schoolRs.getInt("school_id"); // School exists
			}

			// Insert into school if not exists
			String insertSchoolQuery = "INSERT INTO school (school_name) VALUES (?)";
			try (PreparedStatement insertSchoolPs = dbConn.prepareStatement(insertSchoolQuery,
					Statement.RETURN_GENERATED_KEYS)) {
				insertSchoolPs.setString(1, schoolName);
				insertSchoolPs.executeUpdate();
				ResultSet newSchoolRs = insertSchoolPs.getGeneratedKeys();
				if (newSchoolRs.next()) {
					return newSchoolRs.getInt(1); // New school ID
				}
			}
		}
		return -1;
	}

	private int insertOrGetEducation(EducationModel educationModel) throws SQLException {
		// Check if the education record already exists
		String educationQuery = "SELECT education_id FROM education WHERE grade = ? AND performance = ? AND remarks = ?";
		try (PreparedStatement eduPs = dbConn.prepareStatement(educationQuery)) {
			eduPs.setString(1, educationModel.getGrade());
			eduPs.setString(2, educationModel.getPerformance());
			eduPs.setString(3, educationModel.getRemarks());
			ResultSet eduRs = eduPs.executeQuery();
			if (eduRs.next()) {
				return eduRs.getInt("education_id"); // Education exists
			}

			// Insert into education if not exists
			String insertEduQuery = "INSERT INTO education (grade, performance, remarks) VALUES (?, ?, ?)";
			try (PreparedStatement insertEduPs = dbConn.prepareStatement(insertEduQuery,
					Statement.RETURN_GENERATED_KEYS)) {
				insertEduPs.setString(1, educationModel.getGrade());
				insertEduPs.setString(2, educationModel.getPerformance());
				insertEduPs.setString(3, educationModel.getRemarks());
				insertEduPs.executeUpdate();
				ResultSet newEduRs = insertEduPs.getGeneratedKeys();
				if (newEduRs.next()) {
					return newEduRs.getInt(1); // New education ID
				}
			}
		}
		return -1;
	}

	private void linkOrphanEducation(int orphanId, int educationId) throws SQLException {
		String orphanEduQuery = "INSERT INTO orphan_education (orphan_id, education_id) VALUES (?, ?)";
		try (PreparedStatement orphanEduPs = dbConn.prepareStatement(orphanEduQuery)) {
			orphanEduPs.setInt(1, orphanId);
			orphanEduPs.setInt(2, educationId);
			orphanEduPs.executeUpdate();
		}
	}

	private void linkOrphanEducationSchool(int orphanId, int educationId, int schoolId) throws SQLException {
		String linkQuery = "INSERT INTO orphan_education_school (orphan_id, education_id, school_id) VALUES (?, ?, ?)";
		try (PreparedStatement linkPs = dbConn.prepareStatement(linkQuery)) {
			linkPs.setInt(1, orphanId);
			linkPs.setInt(2, educationId);
			linkPs.setInt(3, schoolId);
			linkPs.executeUpdate();
		}
	}

}
