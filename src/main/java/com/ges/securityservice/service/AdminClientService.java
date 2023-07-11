package com.ges.securityservice.service;

import com.ges.securityservice.dto.UserKeyclaokAll;
import com.ges.securityservice.dto.UserKeycloak;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.core.Response;
import java.util.*;
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
    public List<UserKeyclaokAll> searchAll() {
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
                .users().list();

        return users.stream().map(user->UserKeyclaokAll.builder()
                    .id(user.getId())
                    .userName(user.getUsername())
                    .lastName(user.getLastName())
                    .firstname(user.getFirstName())
                    .email(user.getEmail())
                    .roles(getRoles(user.getId()))
                    .build()
        ).collect(Collectors.toList());

    }
    public List<String> getRoles(String id) {
        UserResource userResource = keycloak.realm(REALM_NAME).users().get(id);
        // Get the realm roles
        return userResource.roles().realmLevel().listAll().stream().toList().stream().map(role->role.getName()).collect(Collectors.toList());
//        RealmResource realmResource = keycloak.realm(REALM_NAME);
//        RoleRepresentation role = realmResource.roles().get("ADMIN").toRepresentation();
//        // Add the role to the user
//        userResource.roles().realmLevel().add(Arrays.asList(role));
    }
    public void addRole(String id) {
        UserResource userResource = keycloak.realm(REALM_NAME).users().get(id);
        // Get the realm roles
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        RoleRepresentation role = realmResource.roles().get("ADMIN").toRepresentation();
        // Add the role to the user
        userResource.roles().realmLevel().add(Arrays.asList(role));
    }
    public void removeRole(String id) {
        UserResource userResource = keycloak.realm(REALM_NAME).users().get(id);
        // Get the realm roles
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        RoleRepresentation role = realmResource.roles().get("ADMIN").toRepresentation();
        userResource.roles().realmLevel().remove(Arrays.asList(role));
    }
    public void addUserRole(String id) {
        UserResource userResource = keycloak.realm(REALM_NAME).users().get(id);
        // Get the realm roles
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        RoleRepresentation role = realmResource.roles().get("USER").toRepresentation();
        // Add the role to the user
        userResource.roles().realmLevel().add(Arrays.asList(role));
    }
    public void removeUserRole(String id) {
        UserResource userResource = keycloak.realm(REALM_NAME).users().get(id);
        // Get the realm roles
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        RoleRepresentation role = realmResource.roles().get("USER").toRepresentation();
        userResource.roles().realmLevel().remove(Arrays.asList(role));
    }
    public String addUser(UserKeycloak userKeycloak){
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userKeycloak.getPassword());
        credential.setTemporary(true);
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userKeycloak.getUserName());
        user.setEmail(userKeycloak.getEmail());
        user.setFirstName(userKeycloak.getFirstname());
        user.setLastName(userKeycloak.getLastName());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(
               credential
        ));
        RealmResource realmResource = keycloak.realm(REALM_NAME);
        Response response = realmResource.users().create(user);
        //keycloak.realm(REALM_NAME).users().create(user);
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        UserResource userResource = realmResource.users().get(userId);
        List<RoleRepresentation> rolesToAdd = new ArrayList<>();
        RoleRepresentation roleRepresentation = realmResource.roles().get("USER").toRepresentation();
        rolesToAdd.add(roleRepresentation);
        userResource.roles().realmLevel().add(rolesToAdd);
        return userId;
    }
    public String getCurrentUserName() {
        return null;
    }
}