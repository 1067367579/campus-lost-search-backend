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
public class AdminOperationLog {
    private String logId;
    private String adminId;
    private String adminName;
    private String operationType;
    private String operationContent;
    private LocalDateTime createTime;
} 