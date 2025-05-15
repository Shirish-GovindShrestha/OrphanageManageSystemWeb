package com.heretohelp.model;
public class EducationModel {
	public EducationModel(int educationId, String grade, String performance, String remarks) {
		super();
		this.educationId = educationId;
		this.grade = grade;
		this.performance = performance;
		this.remarks = remarks;
	}

	public EducationModel() {
		super();
	}
	
	// Education details
	private int educationId;
	private String grade;
	private String performance;
	private String remarks;

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public int getEducationId() {
		return educationId;
	}

	public void setEducationId(int educationId) {
		this.educationId = educationId;
	}


}
