package com.example.campuslostsearch.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintVO {
    private Long complaintId;
    private Long userId;
    private String reason;
    private Integer status;
    private LocalDateTime createTime;
    private String adminFeedBack;
    private Long claimId;
    private String itemName;
    private String claimType;
    private LocalDateTime claimCreateTime;
    private String description;
    private String username;
}
