package com.bitespeed.identityreconciliation.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitespeed.identityreconciliation.api.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	Optional<Contact> findById(Long id);
    
	 List<Contact> findByEmail(String email);

	 List<Contact> findByPhoneNumber(String phoneNumber);
	 
	 @Query("SELECT c FROM Contact c WHERE c.phoneNumber = :phoneNumber AND c.email = :email")
	 Optional<Contact> findByPhoneNumberAndEmail(String phoneNumber, String email);
	 
	 List<Contact> findByLinkedId(Long id);
}
