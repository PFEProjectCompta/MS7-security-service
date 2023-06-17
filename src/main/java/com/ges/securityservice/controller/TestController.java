package com.ges.securityservice.controller;

import com.ges.securityservice.service.AdminClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WIAM
 **/
@RestController
public class TestController {
    @Autowired
    AdminClientService adminClientService;

    @GetMapping("/test")
    public void work(){
        adminClientService.searchUsers();
    }
    @GetMapping("/test1")
    public void work1(){
        adminClientService.searchAll();
    }
    @GetMapping("/test2")
    public void work2(){
        adminClientService.addRole();
    }
}
