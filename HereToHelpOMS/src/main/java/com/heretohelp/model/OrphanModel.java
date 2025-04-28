package com.heretohelp.model;

import java.time.LocalDate;

public class OrphanModel {
	public OrphanModel() {
		super();
	}

	public OrphanModel(int orphanId, String firstName, String lastName, LocalDate dob, String gender, String status,
			LocalDate admissionDate) {
		super();
		this.orphanId = orphanId;
		this.firstName = firstName;
		this.lastName = lastName;
		Dob = dob;
		this.gender = gender;
		this.status = status;
		this.admissionDate = admissionDate;
	}

	private int orphanId;
	private String firstName;
	private String lastName;
	private LocalDate Dob;
	private String gender;
	private String status;
	private LocalDate admissionDate;

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
		return Dob;
	}

	public void setDob(LocalDate dob) {
		Dob = dob;
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

}
