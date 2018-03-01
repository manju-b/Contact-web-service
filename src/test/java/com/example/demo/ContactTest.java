package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class ContactTest {
	
	public String firstName;
	public String phoneNumber;
	Contact ct;
	
	@Test 
	public void testValidateMethodWith_ValidInput() {
		try {
			ct = new Contact();
			ct.setFirstName("manju");
			ct.setPhoneNumber("0101010101");
			ct.validate();
		} catch (IllegalArgumentException e) {
			Assert.assertNull(e.getMessage());
		}
	}
	
	@Test 
	public void testValidateMethodWith_InValidInput_WhichHasPhonenumberAsNull() throws IllegalArgumentException {
		try {
			ct = new Contact();
			ct.setFirstName("manju");
			ct.validate();
		} catch(IllegalArgumentException e) {
			assertEquals("Contact will not be saved with out firstname and phonenumber",e.getMessage());
		}
	}
	
	@Test
	public void testValidateMethodWith_InValidInput_WhichHasFirstNameAsNull() throws IllegalArgumentException {
		try {
			ct = new Contact();
			ct.setPhoneNumber("0909090909");
			ct.validate();
		} catch(IllegalArgumentException e) {
			assertEquals("Contact will not be saved with out firstname and phonenumber", e.getMessage());
		}
	}
	
	@Test
	public void testValidateMethodWith_InValidInput_WhichHasFirstNameAsEmpty() throws IllegalArgumentException {
		try {
			ct = new Contact();
			ct.setPhoneNumber("0909090909");
			ct.setFirstName("");
			ct.validate();
		} catch(IllegalArgumentException e) {
			assertEquals("Contact will not be saved with out firstname and phonenumber", e.getMessage());
		}
	}
	
	@Test
	public void testValidateMethodWith_InValidInput_WhichHasPhonenumberAsEmpty() throws IllegalArgumentException {
		try {
			ct = new Contact();
			ct.setFirstName("manju");
			ct.setPhoneNumber("");
			ct.validate();
		} catch(IllegalArgumentException e) {
			assertEquals("Contact will not be saved with out firstname and phonenumber", e.getMessage());
		}
	}
	

}
