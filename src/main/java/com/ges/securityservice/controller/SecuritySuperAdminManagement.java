package com.ges.securityservice.controller;

import com.ges.securityservice.dto.UserKeyclaokAll;
import com.ges.securityservice.dto.UserKeycloak;
import com.ges.securityservice.service.AdminClientService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author WIAM
 **/
@RestController
public class SecuritySuperAdminManagement {
    @Autowired
    AdminClientService adminClientService;

    @GetMapping("/searchUsers")
    public void searchUsers(){
        adminClientService.searchUsers();
    }
    @GetMapping("/searchAll")
    public List<UserKeyclaokAll> searchAll(){
        return adminClientService.searchAll();
    }
    @GetMapping("/addRole")
    public void addRole(@RequestBody String id){
        adminClientService.addRole(id);
    }
    @PostMapping("/addUser")
    public void addUser(@RequestBody UserKeycloak userKeycloak){
        adminClientService.addUser(userKeycloak);
    }
    @PostMapping("/getRolesByUserId")
    public List<String> getRolesByUserId(@RequestBody Map<String, String> id){
        return adminClientService.getRoles(id.get("id"));
    }
}
