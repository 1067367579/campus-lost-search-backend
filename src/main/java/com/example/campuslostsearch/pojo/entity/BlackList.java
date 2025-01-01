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
public class BlackList {
    private Long blackListId;
    private Long userId;
    private String reason;
    private Integer type;
    private LocalDateTime createTime;
    private Long adminId;
}
