package com.heretohelp.model;

public class OrphanEducationSchoolModel {
    private EducationModel education;
    private SchoolModel school;

    public OrphanEducationSchoolModel(EducationModel education, SchoolModel school) {
        this.education = education;
        this.school = school;
    }

    public OrphanEducationSchoolModel() {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public EducationModel getEducation() {
        return education;
    }

    public void setEducation(EducationModel education) {
        this.education = education;
    }

    public SchoolModel getSchool() {
        return school;
    }

    public void setSchool(SchoolModel school) {
        this.school = school;
    }
}
