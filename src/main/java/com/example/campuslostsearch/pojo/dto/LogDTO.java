package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String operationType;
    private String username;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
