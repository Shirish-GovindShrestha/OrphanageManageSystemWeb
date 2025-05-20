package com.heretohelp.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class OrphanModel {
	// Constructor
	public OrphanModel() {
	}
	
	/**
	 * Parameterized constructor for OrphanModel.
	 * Initializes the orphan object with provided details.
	 * 
	 * @param firstName    The first name of the orphan.
	 * @param lastName     The last name of the orphan.
	 * @param dob          The date of birth of the orphan.
	 * @param gender       The gender of the orphan.
	 * @param status       The current status of the orphan.
	 * @param admissionDate The admission date of the orphan.
	 * @param imageUrl     The URL of the orphan's image.
	 */
	public OrphanModel(String firstName, String lastName, LocalDate dob, String gender, String status,
			LocalDate admissionDate, String imageUrl) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.status = status;
		this.admissionDate = admissionDate;
		this.imageUrl = imageUrl;
	}

	private int orphanId;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private String gender;
	private String status;
	private LocalDate admissionDate;
	private String imageUrl;
	private List<OrphanEducationSchoolModel> educationSchoolRecords;

	public List<OrphanEducationSchoolModel> getEducationSchoolRecords() {
		return educationSchoolRecords;
	}

	public void setEducationSchoolRecords(List<OrphanEducationSchoolModel> educationSchoolRecords) {
		this.educationSchoolRecords = educationSchoolRecords;
	}

	public int calculateAge() {
		return (dob == null) ? 0 : Period.between(dob, LocalDate.now()).getYears();
	}

	// Getters and Setters
	public int getOrphanId() {
		return orphanId;
	}

	public void setOrphanId(int orphanId) {
		this.orphanId = orphanId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(LocalDate admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
