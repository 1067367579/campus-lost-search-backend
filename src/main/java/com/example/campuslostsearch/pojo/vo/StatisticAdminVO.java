package com.example.campuslostsearch.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticAdminVO {
    private Long totalUsers;
    private Long totalItems;
    private Long pendingClaims;
    private Long pendingComplaints;
    private Long blacklistUsers;
    private Long todayNewItems;
    private Long todayNewClaims;
    private Long todayNewComplaints;
}
