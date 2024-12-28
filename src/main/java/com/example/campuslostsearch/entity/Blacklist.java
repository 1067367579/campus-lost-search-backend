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
public class Blacklist {
    private String blacklistId;
    private String userId;
    private String username;
    private String reason;
    private Integer type;
    private LocalDateTime createTime;
} 