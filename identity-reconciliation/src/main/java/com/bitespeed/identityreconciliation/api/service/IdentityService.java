package com.bitespeed.identityreconciliation.api.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitespeed.identityreconciliation.api.entity.Contact;
import com.bitespeed.identityreconciliation.api.model.ContactDTO;
import com.bitespeed.identityreconciliation.api.model.IdentityRequest;
import com.bitespeed.identityreconciliation.api.model.IdentityResponse;
import com.bitespeed.identityreconciliation.api.repository.ContactRepository;

@Service
public class IdentityService {
	
    @Autowired
    private ContactRepository contactRepository;

    public IdentityResponse identifyContact(IdentityRequest request) {
        // Add your logic here to query the database and find the linked contacts
    	Contact contact= new Contact();
    	contact.setEmail(request.getEmail());
    	contact.setPhoneNumber(request.getPhoneNumber());
    	contact.setLinkPrecedence(Contact.LinkPrecedence.PRIMARY);
    	contact.setCreatedAt(new Date());
    	contact.setUpdatedAt(new Date());
    	try{
    	contactRepository.saveAndFlush(contact);
    	}
    	catch(Error e) {
    		System.out.print(e);
    	}
        // Sample response for illustration purposes
        IdentityResponse response = new IdentityResponse();
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setPrimaryContactId(1L);
        contactDTO.setEmails(Arrays.asList("lorraine@hillvalley.edu", "mcfly@hillvalley.edu"));
        contactDTO.setPhoneNumbers(Collections.singletonList("123456"));
        contactDTO.setSecondaryContactIds(Collections.singletonList(23L));
        response.setContact(contactDTO);

        return response;
    }
}

