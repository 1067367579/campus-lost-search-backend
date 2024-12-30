package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
