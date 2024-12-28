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
public class Complaint {
    private String complaintId;
    private String userId;
    private String claimId;
    private String category;
    private String reason;
    private String adminFeedback;
    private Integer status; // 0:待处理 1:已处理
    private LocalDateTime createTime;
    private String complainantName;
    private String claimDescription;
    private String itemId;
    private String itemName;
    private Integer itemType;
} 