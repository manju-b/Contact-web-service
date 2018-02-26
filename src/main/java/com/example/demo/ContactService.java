package com.example.demo;

import java.util.List;

public interface ContactService {
	Contact getContactById(Long id);
	List<Contact> getAllContacts();
	List<Contact> getContactByName(String anyPartOfName);
	void deleteContact(Long id);
	Contact addContact(Contact contact);
	Contact updateContact(Long id, Contact contact);
}
