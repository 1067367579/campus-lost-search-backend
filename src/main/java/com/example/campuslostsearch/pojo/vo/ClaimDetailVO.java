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
public class ClaimDetailVO {
    private Long claimId;
    private Long itemId;
    private String username;
    private Long userId;
    private String itemName;
    private Integer itemType;
    private String claimType;
    private String categoryName;
    private String location;
    private LocalDateTime lostTime;
    private LocalDateTime foundTime;
    private List<String> images;
    private String itemDescription;
    private String description;
    private String evidence;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime handleTime;
    private String handleRemark;
}
