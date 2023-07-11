package com.ges.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WIAM
 **/
@NoArgsConstructor @AllArgsConstructor @Builder @Data
public class UserKeycloak {
    private String userName;
    private String email;
    private String password;
    private String firstname;
    private String lastName;
}
