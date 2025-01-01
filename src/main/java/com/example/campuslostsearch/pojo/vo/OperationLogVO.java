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
public class OperationLogVO {
    private Long logId;
    private String username;
    private String operationType;
    private String operationContent;
    private LocalDateTime createTime;
}
