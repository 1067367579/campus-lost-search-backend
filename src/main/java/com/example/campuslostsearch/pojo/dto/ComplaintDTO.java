package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDTO {
    private Long claimId;
    private String reason;
}
