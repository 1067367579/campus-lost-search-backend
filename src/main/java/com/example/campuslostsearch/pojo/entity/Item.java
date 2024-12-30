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
public class Item {
    private Long itemId;
    private Long userId;
    private Integer categoryId;
    private String itemName;
    private String description;
    private String location;
    private LocalDateTime keyTime;
    private Integer status;
    private LocalDateTime createTime;
    private Integer itemType;
}
