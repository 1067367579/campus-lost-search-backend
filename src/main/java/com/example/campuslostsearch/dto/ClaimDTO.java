package com.example.campuslostsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClaimDTO {
    private String itemId;
    private Integer itemType;
    private String description;
} 