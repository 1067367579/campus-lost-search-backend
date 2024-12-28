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
public class ItemVO {
    private String itemId;
    private String itemName;
    private String categoryName;
    private String description;
    private String location;
    private LocalDateTime time;
    private Integer status;
    // 扁平化 publisher
    private String publisherId;
    private String publisherName;
} 