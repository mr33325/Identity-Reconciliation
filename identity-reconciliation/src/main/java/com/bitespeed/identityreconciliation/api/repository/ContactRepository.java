package com.bitespeed.identityreconciliation.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitespeed.identityreconciliation.api.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Custom query methods, if any
}
