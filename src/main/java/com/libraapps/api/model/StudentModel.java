package com.libraapps.api.model;

public class StudentModel {

	private String name;
	
	private String lastName;
	
	private String dni;
	
    private String email;
    
    private String mobile;

        
	public StudentModel() {
	}	
	
	public StudentModel(String name, String lastName, String dni, String email, String mobile) {
		this.name = name;
		this.lastName = lastName;
		this.dni = dni;
		this.email = email;
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "StudentModel [name=" + name + ", lastName=" + lastName + ", dni=" + dni + ", email=" + email
				+ ", mobile=" + mobile + "]";
	}
	
	
    
}
