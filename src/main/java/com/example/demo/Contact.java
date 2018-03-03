package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name="contact", 
	uniqueConstraints=@UniqueConstraint(columnNames={"phoneNumber"}, name ="UNIQUE_PHONE")
	)
public class Contact {
	
	@Id
	@GeneratedValue
	private Long Id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phoneNumber;
	
	
	
	public Contact() {

	}

	public Contact(String firstName, String lastName, String emailId, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void validate() throws IllegalArgumentException {
		if((firstName == null || firstName.isEmpty()) || (phoneNumber == null || phoneNumber.isEmpty())) {
			throw new IllegalArgumentException("Contact will not be saved with out firstname and phonenumber");
		} 
	}
	
	@Override
	public String toString() {
		return "Contact [Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	
}
