package com.ges.securityservice.controller;

import com.ges.securityservice.dto.UserKeyclaokAll;
import com.ges.securityservice.dto.UserKeycloak;
import com.ges.securityservice.service.AdminClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WIAM
 **/
@RestController
public class GraphQLController {
    @Autowired
    private AdminClientService adminClientService;
    @QueryMapping
    public List<UserKeyclaokAll> searchAll(){
        return adminClientService.searchAll();
    }

    @QueryMapping
    public String getUserName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        String preferredUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            preferredUsername = jwt.getClaim("preferred_username");
        }
        System.out.println("hanniiii" + preferredUsername);
        return preferredUsername;
    }
    @MutationMapping
    public String addRole(@Argument String id){
        adminClientService.addRole(id);
        return "Done";
    }
    @MutationMapping
    public String removeRole(@Argument String id){
        adminClientService.removeRole(id);
        return "Done";
    }
    @MutationMapping
    public String addUserRole(@Argument String id){
        adminClientService.addUserRole(id);
        return "Done";
    }
    @MutationMapping
    public String removeUserRole(@Argument String id){
        adminClientService.removeUserRole(id);
        return "Done";
    }
    @MutationMapping
    public String addUser(@Argument UserKeycloak userKeycloak){
        return adminClientService.addUser(userKeycloak);
    }
}
