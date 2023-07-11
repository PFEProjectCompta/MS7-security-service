package com.ges.securityservice.controller;
import com.ges.securityservice.service.AdminClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WIAM
 **/
@RestController
public class TestController {
    @Autowired
    AdminClientService adminClientService;
//
//    @GetMapping("/searchUsers")
//    public void searchUsers(){
//        adminClientService.searchUsers();
//    }
//    @GetMapping("/searchAll")
//    public List<UserKeyclaokAll> searchAll(){
//        return adminClientService.searchAll();
//    }
//    @GetMapping("/addRole")
//    public void addRole(){
//        adminClientService.addRole();
//    }
//    @PostMapping("/addUser")
//    public void addUser(@RequestBody UserKeycloak userKeycloak){
//         adminClientService.addUser(userKeycloak);
//    }
    @GetMapping("getName")
    public String getName(){
        return "hello";
    }
    @GetMapping("/test")
    public String loadUserDetail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String preferredUsername = null;
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            preferredUsername = jwt.getClaim("preferred_username");
        }
        return preferredUsername;
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            String currentUserName = authentication.getName();
//            return  authentication.getPrincipal();
//        }else{
//            throw new RuntimeException("No User");
//        }
    }
}
