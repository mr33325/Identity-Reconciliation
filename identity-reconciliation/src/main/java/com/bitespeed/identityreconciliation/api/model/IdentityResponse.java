package com.bitespeed.identityreconciliation.api.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bitespeed.identityreconciliation.api.entity.Contact;

public class IdentityResponse {
	private ContactDTO contact;

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(Contact primaryContact, List<Contact> secondaryContacts) {
        // Map primary and secondary contacts to the DTO
        ContactDTO contactDTO = new ContactDTO();
        List<String> emails= new ArrayList<String>();
        List<String> phones= new ArrayList<String>();
        if (primaryContact != null) {
            contactDTO.setPrimaryContactId(primaryContact.getId());
            emails.add(primaryContact.getEmail());
            phones.add(primaryContact.getPhoneNumber());
        }
        if (!secondaryContacts.isEmpty()) {
	        for(int i=0; i<secondaryContacts.size(); i++) {
	        	emails.add(secondaryContacts.get(i).getEmail());
	            phones.add(secondaryContacts.get(i).getPhoneNumber());
	        }
        }
        
        Set<String> uniqueSet = new HashSet<>(emails);
        emails= new ArrayList<>(uniqueSet);
        
        uniqueSet = new HashSet<>(phones);
        phones= new ArrayList<>(uniqueSet);
        
        contactDTO.setEmails(emails);
        contactDTO.setPhoneNumbers(phones);
        List<Long> secondaryContactIds = secondaryContacts.stream().map(Contact::getId).collect(Collectors.toList());
        contactDTO.setSecondaryContactIds(secondaryContactIds);
        this.contact = contactDTO;
    }

}
