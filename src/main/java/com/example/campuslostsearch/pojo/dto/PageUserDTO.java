package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageUserDTO {
    private String username;
    private Integer userType;
    private Integer status;
}
