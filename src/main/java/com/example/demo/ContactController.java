package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping("/redirect")
    public ResponseEntity index2() {
        return ResponseEntity.status(302).header("location", "www.blahblah.com").build();
    }
	
	
	@RequestMapping(value="/contacts" , method= RequestMethod.GET)
    public List<Contact> getMatchedContactList(@RequestParam(value="anyPartOfName", required= false) String anyPartOfName) {
    	
    	if(anyPartOfName != null) {
    		return contactService.getContactByName(anyPartOfName);
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
    	return contactService.addContact(contact);
    }
    
    @RequestMapping(value = "/contacts/{id}", method = RequestMethod.PUT)
    public Contact updatingAContact(@PathVariable("id") Long contactId, @RequestBody Contact contact) {
    	return contactService.updateContact(contactId, contact);
    }
}
