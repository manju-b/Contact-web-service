package com.example.demo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ContactServiceImplTest {

	@Mock
	ContactRepository contactRepositoryMock;
	
	ContactServiceImpl service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new ContactServiceImpl(contactRepositoryMock);
	}
	
	@Test
	public void testGetContactById() {
		Contact ct3 = new Contact("john", "smith", "hotmail.com", "9999999999");
		ct3.setId(3);
		Long l = 2L;
		when(contactRepositoryMock.findOne(any(Long.class))).thenReturn(ct3);
		Assert.assertEquals(ct3, service.getContactById(l));
		verify(contactRepositoryMock, times(1)).findOne(l);
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
		when(contactRepositoryMock.findAll()).thenReturn(list);
		Assert.assertEquals(list, service.getAllContacts());
		verify(contactRepositoryMock, times(1)).findAll();
	}
	
	@Test
	public void testDeleteContact() {
		Contact ct3 = new Contact("lavanya", "ravuri", "hotmail.com", "9999999999");
		ct3.setId(3);
		Long l = 2L;
		when(contactRepositoryMock.findOne(l)).thenReturn(ct3);
		service.deleteContact(l);
		verify(contactRepositoryMock, times(1)).delete(ct3);
		verify(contactRepositoryMock, times(1)).findOne(l);

	}
	
	@Test
	public void testGetMatchedContact() {
		
	}
	
	
	
}
