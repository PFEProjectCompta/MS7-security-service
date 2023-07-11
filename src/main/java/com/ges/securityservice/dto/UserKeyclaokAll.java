package com.ges.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WIAM
 **/
@AllArgsConstructor @Data @NoArgsConstructor @Builder
public class UserKeyclaokAll {
    private String id;
    private String userName;
    private String email;
    private List<String> roles;
    private String firstname;
    private String lastName;
}
