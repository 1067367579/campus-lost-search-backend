package com.example.campuslostsearch.pojo.entity;

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
    private Long complaintId;
    private Long userId;
    private Long claimId;
    private String reason;
    private String adminFeedback;
    private Integer status;
    private LocalDateTime createTime;
}
