package com.libraapps.api.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity (name= "Student")
@Table (name = "student")
public class StudentEntity {

	@Id
	private String dni;
	
	private String name;
	
	private String lastName;	
	
    private String email;
    
    private String mobile;
    
	public StudentEntity() {
		super();
	}

	public StudentEntity(String name, String lastName, String dni, String email, String mobile) {
		super();
		
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
    
    

}
