package com.ges.securityservice;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}
	@Bean
	Keycloak keycloak() {
		return KeycloakBuilder.builder()
				.serverUrl("http://localhost:9900")
				.realm("compta-realm")
				.clientId("office-client")
				.grantType(OAuth2Constants.PASSWORD)
				.username("superadmin")
				.password("12345")
				.build();
	}
}
