package com.bitespeed.identityreconciliation.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitespeed.identityreconciliation.api.entity.Contact;
import com.bitespeed.identityreconciliation.api.model.IdentityRequest;
import com.bitespeed.identityreconciliation.api.model.IdentityResponse;
import com.bitespeed.identityreconciliation.api.repository.ContactRepository;

@Service
public class IdentityService {
	
    @Autowired
    private ContactRepository contactRepository;

    /**
     * Performs identity reconciliation based on the provided IdentityRequest.
     *
     * @param request The IdentityRequest containing email and/or phoneNumber for identification.
     * @return IdentityResponse containing the consolidated contact details.
     */
    public IdentityResponse identifyContact(IdentityRequest request) {
    	String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        Contact primaryContact;
        List<Contact> secondaryContacts = new ArrayList<>();
        List<Contact> foundByEmail= new ArrayList();
        List<Contact> foundByPhone= new ArrayList();
        IdentityResponse response = new IdentityResponse();
        // Create a new list to store the indices of contacts with LinkPrecedence set to PRIMARY
        List<Integer> primaryIndicesList = new ArrayList<>();
        
        
        //Check if the given contact information is already existing in the DB. If yes then return consolidated linked contact response
        if(email!=null && phoneNumber!=null) {
        	Contact foundByEmailAndPhone= contactRepository.findByPhoneNumberAndEmail(phoneNumber, email).orElse(null);
        	if(foundByEmailAndPhone!=null) {
        		if(foundByEmailAndPhone.getLinkPrecedence()==Contact.LinkPrecedence.PRIMARY) {
        			primaryContact= foundByEmailAndPhone;
        			secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
        	    	response.setContact(primaryContact, secondaryContacts);
        	    	return response;
        		}
        		else {
        			primaryContact= contactRepository.findById(foundByEmailAndPhone.getLinkedId()).orElse(null);
        			secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
        	    	response.setContact(primaryContact, secondaryContacts);
        	    	return response;
        		}
        	}
        }
        else if(email!=null && phoneNumber==null) {
        	foundByEmail= contactRepository.findByEmail(email);
        	if(!foundByEmail.isEmpty()) {
        		if(foundByEmail.get(0).getLinkPrecedence()==Contact.LinkPrecedence.PRIMARY) {
        			primaryContact= foundByEmail.get(0);
        			secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
        	    	response.setContact(primaryContact, secondaryContacts);
        	    	return response;
        		}
        		else {
        			primaryContact= contactRepository.findById(foundByEmail.get(0).getLinkedId()).orElse(null);
        			secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
        	    	response.setContact(primaryContact, secondaryContacts);
        	    	return response;
        		}
        	}
        }
        else if(email==null && phoneNumber!=null) {
        	foundByPhone= contactRepository.findByPhoneNumber(phoneNumber);
        	if(!foundByPhone.isEmpty()) {
        		if(foundByPhone.get(0).getLinkPrecedence()==Contact.LinkPrecedence.PRIMARY) {
        			primaryContact= foundByPhone.get(0);
        			secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
        	    	response.setContact(primaryContact, secondaryContacts);
        	    	return response;
        		}
        		else {
        			primaryContact= contactRepository.findById(foundByPhone.get(0).getLinkedId()).orElse(null);
        			secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
        	    	response.setContact(primaryContact, secondaryContacts);
        	    	return response;
        		}
        	}
        }
        
        // If not existing in the database, then create entry in db based on conditions
        if(email!=null) {
        	foundByEmail= contactRepository.findByEmail(email);
        	
        	if (foundByEmail.isEmpty()) {
                // No contacts found with the specified email
                System.out.println("No contacts found with the email: " + email);
            } else {
                // Contacts found with the specified email
                System.out.println("Found " + foundByEmail.size() + " contacts with the email: " + email);
        
                for (Contact contact : foundByEmail) {
                    System.out.println("Contact ID: " + contact.getId());
                    // Print other contact details as needed
                }
            }
        }
        
        if(phoneNumber!=null) {
        	foundByPhone= contactRepository.findByPhoneNumber(phoneNumber);
        	
        	if (foundByPhone.isEmpty()) {
                // No contacts found with the specified email
                System.out.println("No contacts found with the phone: " + phoneNumber);
            } else {
                // Contacts found with the specified email
                System.out.println("Found " + foundByPhone.size() + " contacts with the phone: " + phoneNumber);
                
                for (Contact contact : foundByPhone) {
                    System.out.println("Contact ID: " + contact.getId());
                    // Print other contact details as needed
                }
            }
        }
        
        //Merge both lists for further processing
        List<Contact> linkedContacts= new ArrayList();
        linkedContacts.addAll(foundByEmail);
        linkedContacts.addAll(foundByPhone);
        
        //if linkedContact list has only two elements and both of them are pointing to same entry in DB
        if((linkedContacts.size()==2)&&
        		(linkedContacts.get(0).getId()==linkedContacts.get(1).getId())) {
        	response.setContact(linkedContacts.get(0), new ArrayList());
    		return response;
        }
        
        
        if(foundByEmail.isEmpty() && foundByPhone.isEmpty()) {
        	//This is a new contact, hence create a new entry in DB and return response
        	System.out.println("This is a new contact, hence create a new entry in DB");
	    	Contact newContact= new Contact(phoneNumber, email, null, Contact.LinkPrecedence.PRIMARY, new Date(), new Date(), null);
	    	
	    	try{
	    		newContact= contactRepository.saveAndFlush(newContact);
	    	}
	    	catch(Error e) {
	    		System.out.print(e);
	    	}
	    	
	    	// Create the response object and return
	        response.setContact(newContact, new ArrayList());
	        return response;
        }
        
        
        System.out.println("Printing Linked contacts list");
        for(Contact contact: linkedContacts) {
        	System.out.println(contact);
        }
    
        // Create a custom comparator to sort by createdAt field
        Comparator<Contact> createdAtComparator = Comparator.comparing(Contact::getCreatedAt);

        // Sort the list using the custom comparator by createdAt field
        Collections.sort(linkedContacts, createdAtComparator);
        
        // Iterate through the contactsList and find contacts with LinkPrecedence set to PRIMARY
        for (int i = 0; i < linkedContacts.size(); i++) {
            Contact contact = linkedContacts.get(i);
            if (contact.getLinkPrecedence() == Contact.LinkPrecedence.PRIMARY) {
                // If the contact has LinkPrecedence set to PRIMARY, add its index to the primaryIndicesList
                primaryIndicesList.add(i);
            }
        }

        // Get the count of contacts with LinkPrecedence set to PRIMARY
        int primaryCount = primaryIndicesList.size();
        primaryContact= linkedContacts.get(primaryIndicesList.get(0));
        
        if((!foundByEmail.isEmpty() && !foundByPhone.isEmpty())) {
        	System.out.println("Two different contact but pointing to same person");
        	
            linkedContacts.get(primaryIndicesList.get(1)).setLinkPrecedence(Contact.LinkPrecedence.SECONDARY);
            linkedContacts.remove(primaryContact);
            for(int i=0; i<linkedContacts.size(); i++) {
            	if(linkedContacts.get(i).getLinkedId()!=primaryContact.getId()) {
            		linkedContacts.get(i).setLinkedId(primaryContact.getId());
            		linkedContacts.get(i).setUpdatedAt(new Date());
            	}
            }
            contactRepository.saveAll(linkedContacts);
            secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
        	response.setContact(primaryContact, secondaryContacts);
            return response;
        }
        
        if(email!=null && phoneNumber!=null) {
	        System.out.println("Either email or phone are existing in the DB");
	    	
	    	Contact newContact= new Contact(phoneNumber, email, primaryContact.getId(), Contact.LinkPrecedence.SECONDARY, new Date(), new Date(), null);
	    	contactRepository.saveAndFlush(newContact);
        }
        secondaryContacts = contactRepository.findByLinkedId(primaryContact.getId());
    	response.setContact(primaryContact, secondaryContacts);
    	return response;
        
    }
    
}

