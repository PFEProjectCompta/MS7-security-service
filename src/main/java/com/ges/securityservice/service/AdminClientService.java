package com.ges.securityservice.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WIAM
 **/
@Service
public class AdminClientService {
    private static final Logger logger= LoggerFactory.getLogger(AdminClientService.class);
    @Autowired
    Keycloak keycloak;


    public void searchUsers() {
        searchByUsername("wiam", false);
    }
    private static final String REALM_NAME = "compta-realm";

    public void searchByUsername(String username, boolean exact) {
        logger.info("Searching by username: {} (exact {})", username, exact);
        System.out.println("nom : "+username);
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
                .users()
                .searchByUsername(username, exact);

        logger.info("Users found by username {}", users.stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList()));
    }
    public void searchAll() {
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
                .users().list();

        logger.info("Users found by username {}", users.stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList()));
    }
    public void addRole() {
        UserResource userResource = keycloak.realm(REALM_NAME).users().get("dce0a3eb-b969-4a15-b0bb-d5fef965191c");

        // Get the realm roles
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        RoleRepresentation role = realmResource.roles().get("ADMIN").toRepresentation();

        // Add the role to the user
        userResource.roles().realmLevel().add(Arrays.asList(role));
    }
}