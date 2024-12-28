package com.example.campuslostsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String userId;
    private String username;
    private String password;
    private String idNumber;
    private String phone;
    private String email;
    private Integer userType;
    private Integer status;
    private LocalDateTime createTime;
} 