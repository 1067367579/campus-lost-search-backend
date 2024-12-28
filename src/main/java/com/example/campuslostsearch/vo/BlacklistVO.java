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
public class BlacklistVO {
    private String blacklistId;
    private String userId;
    private String username;
    private String reason;
    private Integer type;
    private LocalDateTime createTime;
} 