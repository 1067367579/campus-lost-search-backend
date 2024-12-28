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
public class FoundItem {
    private String foundItemId;
    private String userId;
    private String categoryId;
    private String itemName;
    private String description;
    private String location;
    private LocalDateTime foundTime;
    private Integer status;
    private LocalDateTime createTime;
    private String username;
    private String categoryName;
} 