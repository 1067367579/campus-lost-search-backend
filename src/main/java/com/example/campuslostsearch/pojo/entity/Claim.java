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
public class Claim {
    private Long claimId;
    private Long userId;
    private Long itemId;
    private Integer itemType;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime handleTime;
    private String handleRemark;
    private String evidence;
    private String claimType;
}
