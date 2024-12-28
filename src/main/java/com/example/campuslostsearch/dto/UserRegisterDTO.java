package com.example.campuslostsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRegisterDTO {
    private String username;
    private String password;
    private String idNumber;
    private String phone;
    private String email;
    private Integer userType;
} 