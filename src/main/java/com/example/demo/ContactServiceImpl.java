package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	public List<Contact> getMatchedContact(String searchPart) {
		List<Contact> filteredList = new ArrayList<Contact>();
		List<Contact> contactList = contactRepository.findAll();
		if(searchPart.matches("[a-zA-Z]+")) {
			for(Contact contact: contactList) {
				String name = contact.getFirstName() + contact.getLastName();
				if(name.toLowerCase().contains(searchPart.toLowerCase())) {
					filteredList.add(contact);
				}
			}
		} else if(searchPart.matches("^[0-9]*$")) {
			for(Contact contact: contactList) {
				String phonenum = contact.getPhoneNumber();
				if(phonenum.startsWith(searchPart)) {
					filteredList.add(contact);
				}
			}
		}
		return filteredList;
	}
	
	@Override
	public void deleteContact(Long id) {
		contactRepository.delete(contactRepository.findOne(id));
	}

	@Override
	public Contact addContact(Contact contact) throws IllegalArgumentException {
		if(contact.getPhoneNumber().length() != 10) {
			throw new IllegalArgumentException("Please enter a 10 digit phonenumber");
		}
		Contact newContact = null;
		try{
			 newContact = contactRepository.save(contact);
		} catch(DataIntegrityViolationException e) {
			handleDataIntegrityViolationException(e);
			throw e;
		}
		
		return newContact;
	}

	@Override
	public Contact updateContact(Long id, Contact contact) {
		contact.setId(id);
		if(contact.getPhoneNumber().length() != 10) {
			throw new IllegalArgumentException("Please enter a 10 digit phonenumber");
		}
		try {
			contactRepository.save(contact);
		} catch(DataIntegrityViolationException e) {
			handleDataIntegrityViolationException(e);
			throw e;
		}
		return contactRepository.findOne(id);
	}
	
	private void handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		Throwable exception = e.getCause();
		if(exception instanceof ConstraintViolationException ) {
			ConstraintViolationException c = (ConstraintViolationException)e.getCause();
			if(c.getConstraintName().startsWith("\"UNIQUE_PHONE")) {
				throw new IllegalArgumentException("The phone number provided is already associated with another contact");
			}
		}
	}

}
