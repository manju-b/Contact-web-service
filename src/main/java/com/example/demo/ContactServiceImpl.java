package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	ContactRepository contactRepository;
	
	@Override
	public Contact getContactById(Long id) {
		return contactRepository.findOne(id);
	}

	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	@Override
	public List<Contact> getContactByName(String anyPartOfName) {
		List<Contact> filteredList = new ArrayList<Contact>();
		List<Contact> contactList = contactRepository.findAll();
		for(Contact contact: contactList) {
			String name = contact.getFirstName() + contact.getLastName();
			if(name.contains(anyPartOfName)) {
				filteredList.add(contact);
			}
		}
		return filteredList;
	}

	@Override
	public void deleteContact(Long id) {
		contactRepository.delete(contactRepository.findOne(id));
		
	}

	@Override
	public Contact addContact(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	public Contact updateContact(Long id, Contact contact) {
		contactRepository.findOne(id);
		contactRepository.save(contact);
		return contactRepository.findOne(id);
	}

}
