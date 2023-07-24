package com.bitespeed.identityreconciliation.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class IdentityReconciliationApplication {

	 /**
     * The main entry point of the Identity Reconciliation service.
     *
     * This method starts the Spring Boot application, initializes the Spring context,
     * and starts the embedded web server to handle incoming HTTP requests.
     *
     * @param args The command-line arguments passed to the application (if any).
     */
	
	public static void main(String[] args) {
		SpringApplication.run(IdentityReconciliationApplication.class, args);
	}

}
