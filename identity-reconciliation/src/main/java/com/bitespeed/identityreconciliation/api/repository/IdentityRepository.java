package com.bitespeed.identityreconciliation.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bitespeed.identityreconciliation.api.entity.Identity;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long>, JpaSpecificationExecutor<Identity> {

}
