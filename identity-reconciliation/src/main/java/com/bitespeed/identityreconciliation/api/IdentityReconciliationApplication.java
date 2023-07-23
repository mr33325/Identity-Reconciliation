package com.bitespeed.identityreconciliation.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class IdentityReconciliationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityReconciliationApplication.class, args);
	}

    @Bean
    RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
