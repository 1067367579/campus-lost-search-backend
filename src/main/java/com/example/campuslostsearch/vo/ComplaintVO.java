package com.example.campuslostsearch.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ComplaintVO {
    private String complaintId;
    private String category;
    private String reason;
    private String adminFeedback;
    private Integer status;
    private LocalDateTime createTime;
    // 扁平化的字段
    private String complainantId;
    private String complainantName;
    private String claimId;
    private String claimDescription;
    private String itemId;
    private String itemName;
    private Integer itemType;
} 