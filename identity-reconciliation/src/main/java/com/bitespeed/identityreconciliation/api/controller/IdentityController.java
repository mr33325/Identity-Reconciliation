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
	
    @GetMapping("/ping")
    public String identifyContact() {
        // Cheking if the service is responding properly
        return "pong";
    }
    
    @PostMapping("/identify")
    public ResponseEntity<IdentityResponse> identifyContact(@RequestBody IdentityRequest request) {
        // Delegate the identity reconciliation logic to the service
        IdentityResponse response = identityService.identifyContact(request);
        return ResponseEntity.ok(response);
    }
}

