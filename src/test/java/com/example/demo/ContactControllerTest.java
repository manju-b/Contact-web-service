package com.example.demo;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;

@RunWith(SpringRunner.class)
public class ContactControllerTest {

	@InjectMocks
	ContactController contactController;
	
	@Mock
	ContactService mockContactService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetMatchedContactListWhen_AlphabetStringIsPassedAsInput() {
		List<Contact> list = new ArrayList<>();
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1);
		Contact ct2 = new Contact("Lillian", "Ruth", "gmail.com", "2222222222"); ct2.setId(2);
		list.add(ct1); list.add(ct2); 
		when(mockContactService.getMatchedContact("illi")).thenReturn(list);
		Assert.assertEquals(list, contactController.getMatchedContactList("illi"));
	}
	
	@Test
	public void testGetMatchedContactListWhen_NumericStringIsPassedAsInput() {
		List<Contact> list = new ArrayList<>();
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1);
		Contact ct2 = new Contact("Lillian", "Ruth", "gmail.com", "0002222222"); ct2.setId(2);
		list.add(ct1); list.add(ct2); 
		when(mockContactService.getMatchedContact("000")).thenReturn(list);
		Assert.assertEquals(list, contactController.getMatchedContactList("000"));
	}
	
	@Test
	public void testGetMatchedContactListWhen_NonExistingContactIsPassedAsInput() {
		try {
			List<Contact> list = new ArrayList<>();
			when(mockContactService.getMatchedContact("smith")).thenReturn(list);
			contactController.getMatchedContactList("smith");
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("No matched Contacts", e.getMessage());
		}
	}
	
	@Test
	public void testGetMatchedContactListWhen_StringWithMixOfAlphabetAndNumericIsPassedAsInput() {
		try {
			List<Contact> list = new ArrayList<>();
			when(mockContactService.getMatchedContact("llia23")).thenReturn(list);
			contactController.getMatchedContactList("llia23");
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("No matched Contacts", e.getMessage());
		}	
	}
	
	@Test
	public void testGetMatchedContactListWhen_NoInputIsPassed() {
		List<Contact> list = new ArrayList<>();
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1);
		Contact ct2 = new Contact("Lillian", "Ruth", "gmail.com", "2222222222"); ct2.setId(2);
		list.add(ct1); list.add(ct2); 
		when(mockContactService.getAllContacts()).thenReturn(list);
		Assert.assertEquals(list, contactController.getMatchedContactList(null));
		verify(mockContactService, never()).getMatchedContact("String");
	}
	
	@Test
	public void testdeleteContactFromList() {
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1); Long l = 1L;
		contactController.deleteContactFromList(l);
		verify(mockContactService, times(1)).deleteContact(l);
	}
	
	@Test
	public void testCreatingContactWhen_InvalidInputIsPassed() {
		Contact ct1 = null; 
		try {
			ct1 = new Contact("william", "johns", "gmail.com", ""); ct1.setId(1); 
			contactController.creatingAContact(ct1);
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("Contact will not be saved with out firstname and phonenumber", e.getMessage());
		}
		verify(mockContactService, never()).addContact(ct1);
	}
	
	@Test
	public void testCreatingContactWhen_ValidContactIsPassedAsInput() {
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1); 
		when(mockContactService.addContact(ct1)).thenReturn(ct1);
		Assert.assertEquals(ct1, contactController.creatingAContact(ct1));
	}
	
	@Test
	public void testUpdatingContactWhen_InvalidContactIsPassedAsInput() {
		Contact ct1 = null; Long l = null;
		try {
			ct1 = new Contact("william", "johns", "gmail.com", ""); ct1.setId(1); l =1L;
			contactController.updatingAContact(l, ct1);
		} catch(IllegalArgumentException e) {
			Assert.assertEquals("Contact will not be saved with out firstname and phonenumber", e.getMessage());
		}
		verify(mockContactService, never()).updateContact(l, ct1);
	}
	
	@Test
	public void testUpdatingContactWhen_ValidContactIsPassedAsInput() {
		Contact ct1 = new Contact("william", "johns", "gmail.com", "0000000000"); ct1.setId(1); Long l =1L;
		Contact updatedContact = new Contact("william", "johns", "gmail.com", "9999999999"); ct1.setId(1); 
		when(mockContactService.updateContact(l, ct1)).thenReturn(updatedContact);
		Assert.assertEquals(updatedContact, contactController.updatingAContact(l, ct1));
	}
	
	
	
}
