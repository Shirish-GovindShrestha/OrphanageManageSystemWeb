package com.heretohelp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.heretohelp.config.DbConfig;
import com.heretohelp.model.EducationModel;
import com.heretohelp.model.OrphanEducationSchoolModel;
import com.heretohelp.model.OrphanModel;
import com.heretohelp.model.SchoolModel;

public class OrphanService {

	private Connection dbConn;

	public OrphanService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Updates an orphan's details, including basic information, education, and
	 * school details.
	 *
	 * @param orphan The OrphanModel object with updated values.
	 * @return true if the update was successful, false otherwise.
	 * @throws SQLException if a database access error occurs
	 */
	public boolean updateOrphan(OrphanModel orphanModel) throws SQLException {
		if (dbConn == null) {
			return false;
		}

		try {
			dbConn.setAutoCommit(false); // Start a transaction

			// Step 1: Update orphan details in the orphan table
			String orphanQuery = "UPDATE orphan SET first_name = ?, last_name = ?, dob = ?, gender = ?, status = ?, admission_date = ? WHERE orphan_id = ?";
			try (PreparedStatement orphanPs = dbConn.prepareStatement(orphanQuery)) {
				orphanPs.setString(1, orphanModel.getFirstName());
				orphanPs.setString(2, orphanModel.getLastName());
				orphanPs.setString(3, orphanModel.getDob().toString());
				orphanPs.setString(4, orphanModel.getGender());
				orphanPs.setString(5, orphanModel.getStatus());
				orphanPs.setString(6, orphanModel.getAdmissionDate().toString());
				orphanPs.setInt(7, orphanModel.getOrphanId());
				orphanPs.executeUpdate();
			}

			// Step 2: Update education and school data
			for (OrphanEducationSchoolModel eduSchool : orphanModel.getEducationSchoolRecords()) {
				EducationModel education = eduSchool.getEducation();
				SchoolModel school = eduSchool.getSchool();

				// Step 2a: Update education record
				String eduQuery = "UPDATE education SET grade = ?, performance = ?, remarks = ? WHERE education_id = ?";
				try (PreparedStatement eduPs = dbConn.prepareStatement(eduQuery)) {
					eduPs.setString(1, education.getGrade());
					eduPs.setString(2, education.getPerformance());
					eduPs.setString(3, education.getRemarks());
					eduPs.setInt(4, education.getEducationId());
					eduPs.executeUpdate();
				}

				// Step 2b: Update school record
				String schoolQuery = "UPDATE school SET school_name = ? WHERE school_id = ?";
				try (PreparedStatement schoolPs = dbConn.prepareStatement(schoolQuery)) {
					schoolPs.setString(1, school.getSchoolName());
					schoolPs.setInt(2, school.getSchoolId());
					schoolPs.executeUpdate();
				}

				// Step 3: Link orphan to education and school in the orphan_education_school
				// table
				String linkQuery = "UPDATE orphan_education_school SET school_id = ?, education_id = ? WHERE orphan_id = ?";
				try (PreparedStatement linkPs = dbConn.prepareStatement(linkQuery)) {
					linkPs.setInt(1, school.getSchoolId());
					linkPs.setInt(2, education.getEducationId());
					linkPs.setInt(3, orphanModel.getOrphanId());
					linkPs.executeUpdate();
				}
			}

			dbConn.commit(); // Commit the transaction if everything is successful
			return true;
		} catch (SQLException e) {
			dbConn.rollback(); // Rollback the transaction if something goes wrong
			e.printStackTrace();
			return false;
		} finally {
			dbConn.setAutoCommit(true); // Restore auto-commit mode
		}
	}

	/**
	 * Fetches the details of the orphan that matches the orphan_id
	 *
	 *@param int Orphan id for search
	 * @return orphan model
	 * @throws SQLException
	 */
	public OrphanModel getOrphanFromId(int orphanId) throws SQLException {
		OrphanModel orphan = new OrphanModel();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT * FROM orphan WHERE orphan_id = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(selectQuery)) {
			ps.setInt(1, orphanId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				orphan.setOrphanId(rs.getInt("orphan_id"));
				orphan.setFirstName(rs.getString("first_name"));
				orphan.setLastName(rs.getString("last_name"));
				orphan.setDob(LocalDate.parse(rs.getString("dob")));
				orphan.setGender(rs.getString("gender"));
				orphan.setStatus(rs.getString("status"));
				orphan.setAdmissionDate(LocalDate.parse(rs.getString("admission_date")));
				orphan.setImageUrl(rs.getString("image_url"));
			} else {
				return null;
			}

			// Fetch education and school info
			String educationQuery = "SELECT e.education_id, e.grade, e.performance, e.remarks, s.school_id, s.school_name "
					+ "FROM education e " + "JOIN orphan_education oe ON e.education_id = oe.education_id "
					+ "JOIN orphan_education_school oes ON oe.orphan_id = oes.orphan_id AND oe.education_id = oes.education_id "
					+ "JOIN school s ON oes.school_id = s.school_id " + "WHERE oe.orphan_id = ?";

			try (PreparedStatement psEducation = dbConn.prepareStatement(educationQuery)) {
				psEducation.setInt(1, orphanId);
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

			return orphan;
		}
	}

	/**
	 * Retrieves all school records from the database.
	 * 
	 * @return List of all SchoolModel objects.
	 * @throws SQLException if a database access error occurs.
	 */
	public List<SchoolModel> getAllSchools() throws SQLException {
		List<SchoolModel> schools = new ArrayList<>();
		String query = "SELECT * FROM school";
		try (PreparedStatement ps = dbConn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				SchoolModel school = new SchoolModel();
				school.setSchoolId(rs.getInt("school_id"));
				school.setSchoolName(rs.getString("school_name"));
				schools.add(school);
			}
		}
		return schools;
	}

	/**
	 * Retrieves all education level records from the database.
	 * 
	 * @return List of all EducationModel objects.
	 * @throws SQLException if a database access error occurs.
	 */
	public List<EducationModel> getAllEducationLevels() throws SQLException {
		List<EducationModel> educationLevels = new ArrayList<>();
		String query = "SELECT * FROM education";
		try (PreparedStatement ps = dbConn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				EducationModel education = new EducationModel();
				education.setEducationId(rs.getInt("education_id"));
				education.setGrade(rs.getString("grade"));
				education.setPerformance(rs.getString("performance"));
				educationLevels.add(education);
			}
		}
		return educationLevels;
	}

	/**
	 * Retrieves orphan data from database with input firstname or last name
	 *
	 *@param searchItem String for searching parameter in the database
	 * @return list of orphanModel
	 * @throws SQLException
	 */
	public List<OrphanModel> getOrphanData(String searchItem) throws SQLException {
		List<OrphanModel> orphanList = new ArrayList<OrphanModel>();
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String selectQuery = "SELECT * FROM orphan WHERE status = 'active' AND (first_name LIKE ? OR last_name LIKE ?)  ";

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
}
