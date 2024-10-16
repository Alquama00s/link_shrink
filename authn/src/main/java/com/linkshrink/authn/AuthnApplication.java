package com.linkshrink.authn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "Auditor")
public class AuthnApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthnApplication.class, args);
	}

}
