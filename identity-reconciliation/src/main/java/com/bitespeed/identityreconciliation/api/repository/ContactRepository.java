package com.bitespeed.identityreconciliation.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitespeed.identityreconciliation.api.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
	 List<Contact> findByEmail(String email);

	 List<Contact> findByPhoneNumber(String phoneNumber);
	 
	 List<Contact> findByLinkedId(Long id);
}
