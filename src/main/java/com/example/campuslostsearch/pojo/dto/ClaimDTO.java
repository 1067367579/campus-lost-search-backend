package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDTO {
    private Long itemId;
    private Integer itemType;
    private String description;
    private String evidence;
    private String claimType;
}
