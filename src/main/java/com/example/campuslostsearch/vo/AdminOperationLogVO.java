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
public class AdminOperationLogVO {
    private String logId;
    private String adminId;
    private String adminName;
    private String operationType;
    private String operationContent;
    private LocalDateTime createTime;
} 