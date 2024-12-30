package com.example.campuslostsearch.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemVO {
    private Long itemId;
    private Integer categoryId;
    private String categoryName;
    private String itemName;
    private String description;
    private String location;
    private Integer status;
    private List<String> images;
    private LocalDateTime createTime;
    private String username;
    private String phone;
    private String email;
    private LocalDateTime foundTime;
    private LocalDateTime lostTime;
}
