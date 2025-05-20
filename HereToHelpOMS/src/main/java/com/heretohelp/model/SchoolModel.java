package com.heretohelp.model;

public class SchoolModel {

	/**
	 * No-argument constructor for SchoolModel. Initializes a new instance of the
	 * SchoolModel class.
	 */
	public SchoolModel() {
		super();
	}

	// School details
	private int schoolId;
	private String schoolName;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

}
