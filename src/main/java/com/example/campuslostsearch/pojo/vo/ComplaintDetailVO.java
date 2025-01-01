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
public class ComplaintDetailVO {
    private Long complaintId;
    private String reason;
    private Integer status;
    private LocalDateTime createTime;
    private Long complainantUserId;
    private String complainantUserName;
    private String complainantPhone;
    private String complainantEmail;
    private Long claimId;
    private Long itemId;
    private String itemName;
    private String claimType;
    private String claimDescription;
    private String evidence;
    private LocalDateTime claimCreateTime;
    private LocalDateTime handleTime;
    private Long applicantUserId;
    private String applicantUserName;
    private String applicantPhone;
    private String applicantEmail;
}
