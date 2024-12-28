package com.example.campuslostsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ComplaintDTO {
    private String claimId;
    private String category;
    private String reason;
} 