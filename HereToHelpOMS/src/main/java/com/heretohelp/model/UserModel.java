package com.heretohelp.model;

import java.time.LocalDate;

public class UserModel {

	public UserModel(String imageUrl) {
		super();
		this.imageUrl = imageUrl;
	}

	public UserModel(String firstName, String lastName, String username, LocalDate dob, String gender,
			String number, String email, String password, String imageUrl) {
		super();
		this.firstName = firstName;
		LastName = lastName;
		Username = username;
		Dob = dob;
		this.gender = gender;
		this.number = number;
		this.email = email;
		this.password = password;
		this.imageUrl = imageUrl;
	}

	public UserModel(String username, String password) {
		super();
		Username = username;
		this.password = password;
	}

	private int userId;
	private String firstName;
	private String LastName;
	private String Username;
	private LocalDate Dob;
	private String gender;
	private String number;
	private String email;
	private String password;
	private String imageUrl;

	public UserModel(String firstName, String lastName, String username, LocalDate dob, String gender, String number,
			String email, String password) {
		super();
		this.firstName = firstName;
		this.LastName = lastName;
		this.Username = username;
		this.Dob = dob;
		this.gender = gender;
		this.number = number;
		this.email = email;
		this.password = password;
	}

	public UserModel(int userId, String firstName, String lastName, String username, LocalDate dob, String gender,
			String number, String email, String password) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		LastName = lastName;
		Username = username;
		Dob = dob;
		this.gender = gender;
		this.number = number;
		this.email = email;
		this.password = password;
	}

	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getImageUrl() {
	    return imageUrl;
	}

	public void setImageUrl(String profilePicture) {
	    this.imageUrl = profilePicture;
	}

}
