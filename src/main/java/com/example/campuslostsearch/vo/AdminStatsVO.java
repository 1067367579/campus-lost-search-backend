package com.example.campuslostsearch.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminStatsVO {
    private Long pendingComplaints;
    private Long pendingClaims;
} 