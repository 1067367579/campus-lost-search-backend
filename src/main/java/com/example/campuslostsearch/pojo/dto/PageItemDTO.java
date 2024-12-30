package com.example.campuslostsearch.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageItemDTO {
    private Integer pageNum;
    private Integer pageSize;
    private Integer categoryId;
    private String keyword;
    private Integer status;
    private Integer itemType;
    private Long userId;
}
