package com.beingjavaguys.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String gender;
	private String city;

	public User(String firstname, int id) {
		this.firstName = firstname;
		this.userId = id;
	}

	public User() {

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
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return new StringBuffer(" First Name : ").append(this.firstName)
				.append(" Last Name : ").append(this.lastName)
				.append(" Gender : ").append(this.gender).append(" ID : ")
				.append(this.userId).toString();
	}

}