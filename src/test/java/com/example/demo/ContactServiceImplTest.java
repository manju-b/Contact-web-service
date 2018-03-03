package com.example.demo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ContactServiceImplTest {

	@Mock
	ContactRepository mockContactRepository;
	
	ContactServiceImpl service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new ContactServiceImpl(mockContactRepository);
	}
	
	@Test
	public void testGetContactById() {
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999");
		ct3.setId(3);
		Long l = 2L;
		when(mockContactRepository.findOne(any(Long.class))).thenReturn(ct3);
		Assert.assertEquals(ct3, service.getContactById(l));
		verify(mockContactRepository, times(1)).findOne(l);
	}
	
	@Test
	public void testGetAllContacts() {
		List<Contact> list = new ArrayList<>();
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000");
		ct1.setId(1);
		Contact ct2 = new Contact("david", "lee", "yahoo.com", "1111111111");
		ct1.setId(2);
		list.add(ct1);
		list.add(ct2);
		when(mockContactRepository.findAll()).thenReturn(list);
		Assert.assertEquals(list, service.getAllContacts());
		verify(mockContactRepository, times(1)).findAll();
	}
	
	@Test
	public void testDeleteContact() {
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999");
		ct3.setId(3);
		Long l = 2L;
		when(mockContactRepository.findOne(l)).thenReturn(ct3);
		service.deleteContact(l);
		verify(mockContactRepository, times(1)).delete(ct3);
		verify(mockContactRepository, times(1)).findOne(l);

	}
	
	@Test
	public void testUpdateContactWith_InvalidPhoneNumberWhichDoesNotContain10Digits() {
		try{
			Contact ct3 = new Contact("john", "smith", "hotmail.com", "999999999");
			ct3.setId(3);
			service.updateContact(ct3.getId(), ct3);
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("Please enter a 10 digit phonenumber", e.getMessage());
		}
	}
	
	@Test
	public void testUpdateContactWith_InvalidPhoneNumberWhichContainsAlphabetsAndNumeric() {
		try{
			Contact ct3 = new Contact("john", "smith", "hotmail.com", "999dst9999");
			ct3.setId(3);
			service.updateContact(ct3.getId(), ct3);
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("Please enter a 10 digit phonenumber", e.getMessage());
		}
	}
	
	@Test
	public void testUpdateContactWhen_DataConstraintExceptionIsThrown() {
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999");
		ct3.setId(3); Long l = 3L;
		DataIntegrityViolationException e = new DataIntegrityViolationException("Caught Exception");
		SQLException sqlE = new SQLException();
		e.initCause(new ConstraintViolationException("someExceptionMessage", sqlE, "\"UNIQUE_PHONE"));
		when(mockContactRepository.save(ct3)).thenThrow(e);
		boolean isExceptionOccured = false;
		try {
			service.updateContact(l, ct3);
		} catch(IllegalArgumentException ex) {
			isExceptionOccured = true;
			Assert.assertEquals("The phone number provided is already associated with another contact", ex.getMessage());
		}
		Assert.assertTrue(isExceptionOccured);
	}
	
	@Test
	public void testUpdateContactWith_ValidInput() {
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999");
		ct3.setId(3);
		Long l = 3L;
		when(mockContactRepository.findOne(l)).thenReturn(ct3);
		Assert.assertEquals(ct3, service.updateContact(l, ct3));
		verify(mockContactRepository, times(1)).save(ct3);
		verify(mockContactRepository, times(1)).findOne(l);
	}
		
	@Test
	public void testAddContactWith_InvalidPhoneNumberWhichDoesNotContain10Digits() {
		try{
			Contact ct3 = new Contact("john", "smith", "hotmail.com", "999999999");
			ct3.setId(3);
			service.addContact(ct3);
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("Please enter a 10 digit phonenumber", e.getMessage());
		}
	}
	
	@Test
	public void testAddContactWhen_DataConstraintExceptionIsThrown() {
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999");
		ct3.setId(3); Long l = 3L;
		DataIntegrityViolationException e = new DataIntegrityViolationException("Caught Exception");
		SQLException sqlE = new SQLException();
		e.initCause(new ConstraintViolationException("someExceptionMessage", sqlE, "\"UNIQUE_PHONE"));
		when(mockContactRepository.save(ct3)).thenThrow(e);
		boolean isExceptionOccured = false;
		try {
			service.addContact(ct3);
		} catch(IllegalArgumentException ex) {
			isExceptionOccured = true;
			Assert.assertEquals("The phone number provided is already associated with another contact", ex.getMessage());
		}
		Assert.assertTrue(isExceptionOccured);
	}
	
	@Test
	public void testAddContactWith_InvalidPhoneNumberWhichContainsAlphabetsAndNumeric() {
		try{
			Contact ct3 = new Contact("john", "smith", "hotmail.com", "99hju99999");
			ct3.setId(3);
			service.addContact(ct3);
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("Please enter a 10 digit phonenumber", e.getMessage());
		}
	}
	
	@Test
	public void testAddContactWith_ValidInput() {
			Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999");
			ct3.setId(3);
			when(mockContactRepository.save(ct3)).thenReturn(ct3);
			service.updateContact(ct3.getId(), ct3);
			Assert.assertEquals(ct3, service.addContact(ct3));
			
	}
	
	@Test
	public void testGetMatchedContactWhenA_StringWithAlphabetsIsPassedAsInput() {
		List<Contact> list = new ArrayList<>();
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1);
		Contact ct2 = new Contact("david", "lee", "yahoo.com", "1111111111"); ct1.setId(2);
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999"); ct3.setId(3);
		Contact ct4 = new Contact("Lillian", "Ruth", "gmail.com", "2222222222"); ct3.setId(3);
		list.add(ct1); list.add(ct2); list.add(ct3); list.add(ct4);
		List<Contact> expectedList = new ArrayList<>();
		expectedList.add(ct1); expectedList.add(ct4);
		when(mockContactRepository.findAll()).thenReturn(list);
		Assert.assertEquals(expectedList, service.getMatchedContact("llia"));
	}
	
	@Test
	public void testGetMatchedContactWhenA_StringWithNumericsIsPassedAsInput() {
		List<Contact> list = new ArrayList<>();
		Contact ct1 = new Contact("william", "johns", "gmail.com", "2220000000"); ct1.setId(1);
		Contact ct2 = new Contact("david", "lee", "yahoo.com", "1111111111"); ct1.setId(2);
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999"); ct3.setId(3);
		Contact ct4 = new Contact("Lillian", "Ruth", "gmail.com", "2222222222"); ct3.setId(3);
		list.add(ct1); list.add(ct2); list.add(ct3); list.add(ct4);
		List<Contact> expectedList = new ArrayList<>();
		expectedList.add(ct1); expectedList.add(ct4);
		when(mockContactRepository.findAll()).thenReturn(list);
		Assert.assertEquals(expectedList, service.getMatchedContact("222"));
	}
	
	@Test
	public void testGetMatchedContactWhenA_StringWithMixOfAlphabetAndNumericIsPassedAsInput() {
		List<Contact> list = new ArrayList<>();
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1);
		Contact ct2 = new Contact("david", "lee", "yahoo.com", "1111111111"); ct1.setId(2);
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999"); ct3.setId(3);
		Contact ct4 = new Contact("Lillian", "Ruth", "gmail.com", "2222222222"); ct3.setId(3);
		list.add(ct1); list.add(ct2); list.add(ct3); list.add(ct4);
		List<Contact> expectedList = new ArrayList<>();
		when(mockContactRepository.findAll()).thenReturn(list);
		Assert.assertEquals(expectedList, service.getMatchedContact("ll23ia"));
	}
	
	
	
}
