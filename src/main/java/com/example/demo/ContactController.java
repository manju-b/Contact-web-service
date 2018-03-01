package com.example.demo;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ContactController {

	@Autowired
	ContactService contactService;
	
	@RequestMapping("/")
    public String index() {
        return "Greetings from manju!";
    }
	
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@RequestMapping(value="/contacts" , method= RequestMethod.GET)
    public List<Contact> getMatchedContactList(@RequestParam(value="searchPart", required= false) String searchPart) {
		if(searchPart != null) {
				List<Contact> list = contactService.getMatchedContact(searchPart);
	    		if(list.isEmpty()) {
	    			throw new IllegalArgumentException("No matched Contacts");
	    		} else {
	        		return list;
	    		}
		} else {
    		return contactService.getAllContacts();
    	}
	}
	
	@RequestMapping(value = "/contacts/{id}" , method= RequestMethod.DELETE)
    public void deleteContactFromList(@PathVariable("id") Long contactId) {
    	contactService.deleteContact(contactId);
    }
    
    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public Contact creatingAContact(@RequestBody Contact contact) {
    	contact.validate();
    	return contactService.addContact(contact);
    }
    
    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.PUT)
    public Contact updatingAContact(@PathVariable("id") Long contactId, @RequestBody Contact contact) {
    	contact.validate();
    	return contactService.updateContact(contactId, contact);
    }
}
