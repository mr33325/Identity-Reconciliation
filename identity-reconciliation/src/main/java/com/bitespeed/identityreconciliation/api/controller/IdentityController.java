package com.bitespeed.identityreconciliation.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitespeed.identityreconciliation.api.model.IdentityRequest;
import com.bitespeed.identityreconciliation.api.model.IdentityResponse;
import com.bitespeed.identityreconciliation.api.service.IdentityService;

@RestController
@RequestMapping("/identity-reconsiliation")
public class IdentityController {
	
	@Autowired
    private IdentityService identityService;
	
	/**
     * Endpoint to check if the identity reconciliation service is responding properly.
     * @return "pong" as a response indicating successful service availability.
     */
    @GetMapping("/ping")
    public String identifyContact() {
        // Cheking if the service is responding properly
        return "pong";
    }
    
    /**
     * Endpoint to perform identity reconciliation based on the provided JSON payload.
     * The request body should contain either an email or phoneNumber for identification.
     *
     * @param request The JSON request containing email or phoneNumber for identification.
     * @return ResponseEntity with IdentityResponse containing the consolidated contact details.
     */
    @PostMapping("/identify")
    public ResponseEntity<IdentityResponse> identifyContact(@RequestBody IdentityRequest request) {
        // Delegate the identity reconciliation logic to the service
        IdentityResponse response = identityService.identifyContact(request);
        return ResponseEntity.ok(response);
    }
}

