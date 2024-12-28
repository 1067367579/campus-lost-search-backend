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
public class Claim {
    private String claimId;
    private String userId;
    private String itemId;
    private Integer itemType; // 0:丢失物品 1:拾取物品
    private String description;
    private Integer status; // 0:待处理 1:已通过 2:已拒绝
    private LocalDateTime createTime;
    private String itemName; // 添加物品名称字段
} 