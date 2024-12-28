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
public class LostItem {
    private String lostItemId;
    private String userId;
    private String categoryId;
    private String itemName;
    private String description;
    private String location;
    private LocalDateTime lostTime;
    private Integer status;
    private LocalDateTime createTime;
    private String username;
    private String categoryName;
} 